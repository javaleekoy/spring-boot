package com.peramdy.config.datasource;

/**
 * @author pd
 */
public enum PdDsEnum {
    DEFAULT(1, "master"),
    DS_MASTER(2, "master"),
    DS_SLAVE(3, "slave");

    private int dataSourceId;
    private String value;

    PdDsEnum(int dataSourceId, String value) {
        this.dataSourceId = dataSourceId;
        this.value = value;
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public String getValue() {
        return value;
    }
}
