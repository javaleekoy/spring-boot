package com.peramdy.es.spring;

import com.peramdy.es.EsConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author pd 2018/3/15.
 *         集成 Spring FactoryBean，想的是在产生client的后不要每次都去close掉，用Spring的bean管理，这样效率更高
 */
@Component
public class EsClientFactory implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(EsClientFactory.class);


    private TransportClient client;
    private final static String COLON = ":";
    private final static String COMMA = ",";

    @Autowired
    private EsConfig esConfig;

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = new PreBuiltTransportClient(Settings.EMPTY);
        String[] clusterNodes = esConfig.getClusterNodes().split(COMMA);
        for (String node : clusterNodes) {
            String host = node.split(COLON)[0];
            String port = node.split(COLON)[1];
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
        }
        client.connectedNodes();
    }

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }

    }


}
