package com.peramdy.config.disconf;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/12.
 *         <p>
 *         修改配置中心的值会回调该方法(只能是文件，单个值不可以)
 */
@Service
@Scope("singleton")
@DisconfUpdateService(classes = {RedisConfig.class})
public class SimpleRedisServiceUpdateCallback implements IDisconfUpdate {

    @Autowired
    private RedisConfig redisConfig;

    @Override
    public void reload() throws Exception {
        System.out.println(redisConfig.getInfo());
    }
}
