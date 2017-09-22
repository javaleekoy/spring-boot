package com.peramdy.common.Enum;

/**
 * Created by peramdy on 2017/9/12.
 */
public enum ResponseCodeEnum {

    /*****common*****/

    OK(200, "请求成功"),
    ERROR(404, "请求错误"),
    FAIL(409, "请求失败"),
    BAD_REQUEST(400, "入参错误"),

    ;


    private Integer status;
    private String message;

    ResponseCodeEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
