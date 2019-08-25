package com.BaseEntity;

import java.io.Serializable;

public class ResultData implements Serializable {
    private String msg;
    private int code;
    private Object body;

    public static ResultData success() {
        ResultData json = new ResultData();
        json.setCode(0);
        json.setMsg("success");
        return json;
    }

    public static ResultData success(Object body) {
        ResultData json = new ResultData();
        json.setCode(0);
        json.setMsg("success");
        json.setBody(body);
        return json;
    }

    public static ResultData error(String msg) {
        ResultData json = new ResultData();
        json.setCode(1);
        json.setMsg(msg);
        return json;
    }

    public static ResultData error(String msg, Object body) {
        ResultData json = new ResultData();
        json.setCode(1);
        json.setMsg(msg);
        json.setBody(body);
        return json;
    }

    public static ResultData error(int code, String msg) {
        ResultData json = new ResultData();
        json.setCode(code);
        json.setMsg(msg);
        return json;
    }

    public static ResultData error(int code, String msg, Object body) {
        ResultData json = new ResultData();
        json.setCode(code);
        json.setMsg(msg);
        json.setBody(body);
        return json;
    }

    public ResultData() {
    }

    public ResultData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultData(int code, String msg, Object body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getBody() {
        return this.body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}

