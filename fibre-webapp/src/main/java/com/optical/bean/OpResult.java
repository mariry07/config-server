package com.optical.bean;

import java.io.Serializable;

/**
 * Created by mariry on 2021/2/25.
 */
public class OpResult implements Serializable {

    private static final long serialVersionUID = -5217263946675824590L;
    public static final int OP_SUCCESS = 1;
    public static final int OP_FAILED = 0;

    private int result;//1 成功  0失败
    private String msg;//提示信息
    private Object unique_id;
    private Object dataValue;//数据值
    private Integer type;

    private Long vendorId;

    public OpResult(){}

    public OpResult(int result, String msg) {
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

    public Object getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(Object unique_id) {
        this.unique_id = unique_id;
    }

    public void setDataValue(Object dataValue) {
        this.dataValue = dataValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
