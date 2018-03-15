package com.peramdy.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author pd 2018/3/15.
 */
@Component
public class EsClientBuilder {

    private Logger logger = LoggerFactory.getLogger(EsClientBuilder.class);

    @Autowired
    private EsConfig esConfig;

    /**
     * 创建默认客户端
     *
     * @return
     */
    public TransportClient buildDefault() {
        logger.info("esConf-url : " + esConfig.getUrl());
        logger.info("esConf-port : " + esConfig.getPort());
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        try {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfig.getUrl()), esConfig.getPort()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }


    /**
     * 创建es客户端
     * cluster.name                                 设置ES实例的名称
     * client.transport.sniff                       自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
     * client.transport.ping_timeout                等待ping命令返回结果时间，默认为5秒
     * client.transport.nodes_sampler_interval      节点之间互相ping，互连检测时间间隔，默认为5秒
     * client.transport.ignore_cluster_name         设置为true时忽略集群名验证，默认为false
     * TransportClient                              提供了以上配置的默认值
     *
     * @return
     */
    public TransportClient buildCluster() {
        Settings.Builder builder = Settings.builder();
        if (esConfig.getClusterName() != null) {
            builder.put("cluster.name", esConfig.getClusterName());
        }
        if (esConfig.isSniff()) {
            builder.put("client.transport.sniff", esConfig.isSniff());
        }
        if (esConfig.getPingTimeout() > 0) {
            builder.put("client.transport.ping_timeout", new TimeValue(esConfig.getPingTimeout(), TimeUnit.SECONDS));
        }
        if (esConfig.getNodesSamplerInterval() > 0) {
            builder.put("client.transport.nodes_sampler_interval", new TimeValue(esConfig.getNodesSamplerInterval(), TimeUnit.SECONDS));
        }
        if (esConfig.isIgnoreClusterName()) {
            builder.put("client.transport.ignore_cluster_name", esConfig.isIgnoreClusterName());
        }
        Settings settings = builder.build();
        logger.info("esConf-url : " + esConfig.getUrl());
        logger.info("esConf-port : " + esConfig.getPort());
        TransportClient client = new PreBuiltTransportClient(settings);
        try {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfig.getUrl()), esConfig.getPort()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }


}
