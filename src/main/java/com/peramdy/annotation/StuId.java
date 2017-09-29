package com.peramdy.annotation;

import com.peramdy.common.Enum.DateEnum;

import java.lang.annotation.*;

/**
 * Created by peramdy on 2017/9/28.
 *
 * @Target注解：用于描述注解的使用范围，超出范围时编译失败
 * 取值类型（ElementType）：
 * 1.CONSTRUCTOR:用于描述构造器
 * 2.FIELD:用于描述域（成员变量）
 * 3.LOCAL_VARIABLE:用于描述局部变量
 * 4.METHOD:用于描述方法
 * 5.PACKAGE:用于描述包
 * 6.PARAMETER:用于描述参数
 * 7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 *
 * @Retention：描述注解的生命周期，即注解的生效范围
 * 取值范围（RetentionPolicy）：
 * 1.SOURCE：在源文件中生效，仅存在java文件中，class文件将会去除注解。
 * 2.CLASS：在class文件中生效，仅保留在class文件中，运行时无法获取注解。
 * 3.RUNTIME:在运行时生效，保留在class文件中且运行时可通过反射机制获取。
 *
 * @Documented：用于指定javac生成API时显示该注解信息
 *
 * @Inherited：标明该注解可以由子类继承，及子类可以继承父类的注解。而默认情况下，子类是不继承父类注解的
 *
 * @读取注解
 * 通过反射机制我们可以读取注解信息。java在java.lang.reflect包下新增了AnnotatedElement接口，
 * 该接口定义了可以接受注解的元素为：Class(类)、Constructor(构造器)、Field(字段)、Method(方法)、Package(包)。
 * AnnotatedElement是所有注解元素的父接口，所有的注解元素都可以通过某个类反射获取AnnotatedElement对象，
 * 该对象有一下4个方法来访问Annotation信息。
 * （1）<T extends Annotation> T getAnnotation(Class<T> annotationClass)
 * 返回该程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回null。
 * （2）Annotation[] getAnnotations():返回该程序元素上存在的所有注解。
 * （3）boolean isAnnotationPresent(Class<?extends Annotation> annotationClass)
 * 判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false.
 * （4）Annotation[] getDeclaredAnnotations()
 * 返回直接存在于此元素上的所有注释。与此接口中的其他方法不同，该方法将忽略继承的注释。
 * （如果没有注释直接存在于此元素上，则返回长度为零的一个数组。）
 * 该方法的调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响。
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StuId {
    String desc() default "描述";

    String id() default "1";

    DateEnum week() default DateEnum.Friday;
}
