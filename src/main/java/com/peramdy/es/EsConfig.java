package com.peramdy.es;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/13.
 */
@Service
@DisconfFile(filename = "es.properties")
@Scope("singleton")
public class EsConfig {

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.clusterNodes}")
    private String clusterNodes;

    private String clusterName;

    private long pingTimeout;

    private boolean ignoreClusterName;

    private long nodesSamplerInterval;

    private boolean sniff;

    @DisconfFileItem(name = "es.url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "es.port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @DisconfFileItem(name = "es.cluster")
    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @DisconfFileItem(name = "es.pingTimeout")
    public long getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(long pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    @DisconfFileItem(name = "es.ignoreClusterName")
    public boolean isIgnoreClusterName() {
        return ignoreClusterName;
    }

    public void setIgnoreClusterName(boolean ignoreClusterName) {
        this.ignoreClusterName = ignoreClusterName;
    }

    @DisconfFileItem(name = "es.nodesSamplerInterval")
    public long getNodesSamplerInterval() {
        return nodesSamplerInterval;
    }

    public void setNodesSamplerInterval(long nodesSamplerInterval) {
        this.nodesSamplerInterval = nodesSamplerInterval;
    }

    @DisconfFileItem(name = "es.sniff")
    public boolean isSniff() {
        return sniff;
    }

    public void setSniff(boolean sniff) {
        this.sniff = sniff;
    }

    @DisconfFileItem(name = "es.clusterNodes")
    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
