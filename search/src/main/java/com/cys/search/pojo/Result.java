package com.cys.search.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;

public class Result {
    private Integer code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }


    public Result success(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public Result success(Object object) {
        Result result = success();
        result.setData(object);
        return result;
    }

    public Result error(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public Result error(Integer code, String message, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(object);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
