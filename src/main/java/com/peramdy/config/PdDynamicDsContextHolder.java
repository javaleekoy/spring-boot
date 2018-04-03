package com.peramdy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author pd 2018/4/2.
 */
public class PdDynamicDsContextHolder {

    /**
     * <p>ThreadLocal 简介</>
     * ThreadLocal类用来提供线程内部的局部变量。这种变量在多线程环境下访问(通过get或set方法访问)时能保证各个线程里的变量相对独立于
     * 其他线程内的变量。ThreadLocal实例通常来说都是private static类型的，用于关联线程和线程的上下文。
     * 可以总结为一句话：
     * ThreadLocal的作用是提供线程内的局部变量，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量
     * 的传递的复杂度。举个例子，我出门需要先坐公交再做地铁，这里的坐公交和坐地铁就好比是同一个线程内的两个函数，我就是一个线程，我
     * 要完成这两个函数都需要同一个东西：公交卡（北京公交和地铁都使用公交卡），那么我为了不向这两个函数都传递公交卡这个变量（相当于
     * 不是一直带着公交卡上路），我可以这么做：将公交卡事先交给一个机构，当我需要刷卡的时候再向这个机构要公交卡（当然每次拿的都是同
     * 一张公交卡）。这样就能达到只要是我(同一个线程)需要公交卡，何时何地都能向这个机构要的目的。
     * 有人要说了：你可以将公交卡设置为全
     * 局变量啊，这样不是也能何时何地都能取公交卡吗？但是如果有很多个人（很多个线程）呢？大家可不能都使用同一张公交卡吧(我们假设公交
     * 卡是实名认证的)，这样不就乱套了嘛。现在明白了吧？这就是ThreadLocal设计的初衷：提供线程内部的局部变量，在本线程内随时随地可
     * 取，隔离其他线程
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
