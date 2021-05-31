package com.optical.bean;

import java.io.Serializable;

/**
 * Created by mary on 2021/2/20.
 */

public class ConfigInfo  implements Serializable {

    private static final long serialVersionUID = -5217263946675824590L;
    public static final int OP_SUCCESS = 1;
    public static final int OP_FAILED = 0;

    private Long vendorId;

    public Integer type;

    private String message;//提示信息

    private int result;//1 成功  0失败

    private String device_code;

    private String ip;

    private String port;

    private String product_key;

    private String device_key;


    public ConfigInfo() {}

    public ConfigInfo(int result, String message) {
        this.result = result;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getDevice_key() {
        return device_key;
    }

    public void setDevice_key(String device_key) {
        this.device_key = device_key;
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
        public static final String SUCCESS = "success";
        public static final String ILLEGAL = "illegal device";
        public static final String FAILED = "failed";
    }


}
