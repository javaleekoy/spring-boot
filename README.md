### Multiple ataSource
##### 动态数据源切换
- 配置环境切换
> `-Dspring.profiles.active=dev`

- 配置多个数据源，注入`ApplicationContext`中
> PdDataSource.java 
```java
@Configuration
public class PdDataSource {

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource createDataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource createDataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDs")
    public DataSource dataSource() {
        PdDynamicRoutingDataSource pdDynamicDS = new PdDynamicRoutingDataSource();
        Map<Object, Object> map = new HashMap<>(2);
        map.put("master", createDataSourceOne());
        map.put("slave", createDataSourceTwo());
        // master、slave 数据源为target数据源
        pdDynamicDS.setTargetDataSources(map);
        // master 为默认数据源
        pdDynamicDS.setDefaultTargetDataSource(createDataSourceOne());
        return pdDynamicDS;
    }
}
```
- sessionFactory相关配置，注入`ApplicationContext`中
> PdDbConfigDynamic.java
```java
@Configuration
@MapperScan(basePackages = PdDbConfigDynamic.PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplateDynamic")
public class PdDbConfigDynamic {

    static final String PACKAGE = "com.peramdy.dao.dynamic";

    static final String MAPPER_LOCATION = "classpath:mapper/dynamic/*.xml";

    public static Logger logger = LoggerFactory.getLogger(PdDbConfigDynamic.class);

    @Autowired
    @Qualifier(value = "dynamicDs")
    private DataSource dynamicDs;

    @Bean
    public SqlSessionFactory sqlSessionFactoryDynamic() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        logger.info("sqlSessionFactoryDynamic：[{}]", dynamicDs.toString());
        /** 配置数据源，若没有则不能实现切换（重点） **/
        sqlSessionFactoryBean.setDataSource(dynamicDs);
        /** 设置扫描路径 **/
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }
    
    /**
     * SqlSessionTemplate是SqlSession的实现类，较SqlSession的默认实现类DefaultSqlSession来说，是线程安全的
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplateDynamic")
    public SqlSessionTemplate sqlSessionTemplateDynamic() throws Exception {
        logger.info("SqlSessionTemplate");
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryDynamic());
        return template;
    }

    /**
     * 事务生效需要添加 @Transactional 注解
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDs);
    }

}
```
- 上下文配置，数据源切换
> PdDynamicDsContextHolder.java
```java
public class PdDynamicDsContextHolder {

    /**
    * ThreadLocal类用来提供线程内部的局部变量。这种变量在多线程环境下访问(通过get或set方法访问)时能保证各个线程里的变量相对独立于
    * 其他线程内的变量。ThreadLocal实例通常来说都是private static类型的，用于关联线程和线程的上下文
    */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private static Logger logger = LoggerFactory.getLogger(PdAspectDataSource.class);

    public static Set<Object> keys = new HashSet<>();

    public static void setDataSourceKey(String dataSourceKey) {
        logger.info("setDataSourceKey：[{}]：", dataSourceKey);
        contextHolder.set(dataSourceKey);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static void clean() {
        contextHolder.remove();
    }

    public static boolean containDataSourceKey(String dataSourceKey) {
        return keys.contains(dataSourceKey);
    }
}

```
- dataSource路由
> 继承 `AbstractRoutingDataSource`类，访问数据调用`determineCurrentLookupKey`方法
```java
public class PdDynamicRoutingDataSource extends AbstractRoutingDataSource {
    private static Logger logger = LoggerFactory.getLogger(PdDataSource.class);
    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("determineCurrentLookupKey：[{}]", PdDynamicDsContextHolder.getDataSourceKey());
        return PdDynamicDsContextHolder.getDataSourceKey();
    }
}
```
- 注解类
> PdDS.java
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface PdDS {
    PdDsEnum value() default PdDsEnum.DEFAULT;
}
```
- aop 解析
> PdAspectDataSource.java
```java
@Aspect
@Component
public class PdAspectDataSource {

    private static Logger logger = LoggerFactory.getLogger(PdAspectDataSource.class);

    @Before(value = "@annotation(PdDS)")
    public void changeDs(JoinPoint joinPoint) {
        /**数据源默认值**/
        String value = PdDsEnum.DEFAULT.getValue();
        /**获取目标类**/
        Class<?> cla = joinPoint.getTarget().getClass();
        /**获取目标方法**/
        String methodName = joinPoint.getSignature().getName();
        /**获取目标方法入参数据类型**/
        Class[] args = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        try {
            /**目标方法**/
            Method method = cla.getMethod(methodName, args);
            if (method.isAnnotationPresent(PdDS.class)) {
                PdDS pdDS1 = method.getAnnotation(PdDS.class);
                value = pdDS1.value().getValue();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        logger.info("sourceType：[{}]", value);
        PdDynamicDsContextHolder.setDataSourceKey(value);
    }

    @After(value = "@annotation(PdDS)")
    public void removeDs(JoinPoint joinPoint) {
        logger.info("removeDs：[{}]", PdDynamicDsContextHolder.getDataSourceKey());
        PdDynamicDsContextHolder.clean();
    }
}
```
- 动态数源注解使用
> StudentServiceImpl.java
```java
@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    @PdDS(value = PdDsEnum.DS_SLAVE)
    public Student queryStudentInfoById_slave(Integer id) {
        return studentDao.queryStuInfo(id);
    }

    @Override
    @PdDS(value = PdDsEnum.DS_SLAVE)
    public int addStuInfo_slave(Student student) {
        return studentDao.addStuInfo(student);
    }
}
```
- druid 依赖jar
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.9</version>
</dependency>
```
- 修改启动banner
```text
在resource目录下新建一个banner.txt文件存放banner

 SpringApplication application = new SpringApplication(PeramdyApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
        
banner符生成网站： http://patorjk.com/       
```