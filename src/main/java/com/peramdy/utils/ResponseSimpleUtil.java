package com.peramdy.utils;

import com.peramdy.common.Enum.ResponseCodeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by peramdy on 2017/9/21.
 */
public class ResponseSimpleUtil {

    private Integer status;
    private String message;

    public ResponseSimpleUtil(ResponseCodeEnum codeEnum) {
        this.status = codeEnum.getStatus();
        this.message = codeEnum.getMessage();
    }

    public ResponseSimpleUtil(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseSimpleUtil() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
