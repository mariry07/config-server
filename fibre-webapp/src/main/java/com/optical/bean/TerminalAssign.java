package com.optical.bean;

import java.util.Date;

public class TerminalAssign {

    private Long id;

    private Long vendorId;

    private String deviceCode;

    private String deviceName;

    private String imei;

    private String ip;

    private String port;

    private String deviceKey;

    private String productKey;

    private Date createTime;

    private Date lastestAssignTime;

    private Integer status;

    private Double leaveGroundDuration;

    private Double onGroundDuration;

    private Double confidenceThreshold;

    private Double sensorFixHeight;

    private String remarks;

    private Integer statusReportEnable;

    /**
     * 设备关联的第三方http推送地址 域名
     */
    private String pushAddr;

    /**
     * 设备是否启用语音紧急呼叫 1启用 0不启用
     */
    private Integer phoneEnable;

    private String phone;

    private Integer cloudUploadEnable;

    private Integer location;

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode == null ? null : deviceCode.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey == null ? null : deviceKey.trim();
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastestAssignTime() {
        return lastestAssignTime;
    }

    public void setLastestAssignTime(Date lastestAssignTime) {
        this.lastestAssignTime = lastestAssignTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getLeaveGroundDuration() {
        return leaveGroundDuration;
    }

    public void setLeaveGroundDuration(Double leaveGroundDuration) {
        this.leaveGroundDuration = leaveGroundDuration;
    }

    public Double getOnGroundDuration() {
        return onGroundDuration;
    }

    public void setOnGroundDuration(Double onGroundDuration) {
        this.onGroundDuration = onGroundDuration;
    }

    public Double getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public void setConfidenceThreshold(Double confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
    }

    public Double getSensorFixHeight() {
        return sensorFixHeight;
    }

    public void setSensorFixHeight(Double sensorFixHeight) {
        this.sensorFixHeight = sensorFixHeight;
    }

    public Integer getPhoneEnable() {
        return phoneEnable;
    }

    public void setPhoneEnable(Integer phoneEnable) {
        this.phoneEnable = phoneEnable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCloudUploadEnable() {
        return cloudUploadEnable;
    }

    public void setCloudUploadEnable(Integer cloudUploadEnable) {
        this.cloudUploadEnable = cloudUploadEnable;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getPushAddr() {
        return pushAddr;
    }

    public void setPushAddr(String pushAddr) {
        this.pushAddr = pushAddr;
    }

    public Integer getStatusReportEnable() {
        return statusReportEnable;
    }

    public void setStatusReportEnable(Integer statusReportEnable) {
        this.statusReportEnable = statusReportEnable;
    }


}