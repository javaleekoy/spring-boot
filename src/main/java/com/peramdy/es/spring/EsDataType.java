package com.peramdy.es.spring;

/**
 * @author pd on 2018/3/16.
 */
public enum EsDataType {

    STRING(1, "text"),
    INTEGER(2, "integer"),
    LONG(3, "long"),
    SHORT(4, "short"),
    BYTE(5, "byte"),
    DOUBLE(6, "double"),
    FLOAT(7, "float");
    private int id;
    private String value;

    EsDataType(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue(Integer id) {
        for (EsDataType esDataType : EsDataType.values()) {
            if (esDataType.id == id) {
                return esDataType.value;
            }
        }
        return null;
    }

    public Integer getId(String value) {
        for (EsDataType esDataType : EsDataType.values()) {
            if (esDataType.value.equals(value)) {
                return esDataType.id;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
