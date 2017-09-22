package com.peramdy.utils;


import com.peramdy.common.Enum.ResponseCodeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by peramdy on 2017/9/13.
 */
public class ResponseUtil<T> extends ResponseSimpleUtil {

    private T data;

    public ResponseUtil() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseUtil(Integer status, String message) {
        super(status, message);
    }

    public ResponseUtil(ResponseCodeEnum codeEnum, T data) {
        super(codeEnum);
        this.data = data;
    }

    public ResponseUtil(ResponseCodeEnum codeEnum) {
        super(codeEnum);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
