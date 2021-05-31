package com.optical.bean;

import java.io.Serializable;

/**
 * Created by mary on 2021/3/24.
 */
public class OpWebResult implements Serializable {


    private static final long serialVersionUID = -5217263946675824590L;
    public static final int OP_SUCCESS = 1;
    public static final int OP_FAILED = 0;

    private int result;//1 成功  0失败
    private String msg;//提示信息
    private Object dataValue;//数据值
    private Object data;

    private Long vendorId;


    public OpWebResult(){}

    public OpWebResult(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDataValue() {
        return dataValue;
    }

    public void setDataValue(Object dataValue) {
        this.dataValue = dataValue;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public static final class OpMsg {
        public static final String OP_SUCCESS = "success";
        public static final String OP_FAIL = "failed";
        public static final String SAVE_SUCCESS = "save_success";
        public static final String SAVE_FAIL = "save_failed";
        public static final String MODIFY_SUCCESS = "modify_success";
        public static final String MODIFY_FAIL = "modify_failed";
    }
}
