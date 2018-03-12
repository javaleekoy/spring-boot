package com.peramdy.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pd 2018/3/12.
 */
@Configuration
public class DisconfConfig {


    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean getDisconfMgrBean() {
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        disconfMgrBean.setScanPackage("com.peramdy");
        return disconfMgrBean;
    }

    @Bean(destroyMethod = "destroy", initMethod = "init")
    public DisconfMgrBeanSecond getDisconfMgrBeanSecond() {
        DisconfMgrBeanSecond disconfMgrBeanSecond = new DisconfMgrBeanSecond();
        return disconfMgrBeanSecond;
    }
}
