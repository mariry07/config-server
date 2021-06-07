package com.optical.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DeviceAlarm {

    private Long id;

    private Long vendorId;

    private String deviceCode;

    private Integer type;

    private Integer confidence;

    private Integer reason;

    private Long frameId;

    private Long eventStartFrame;

    private Long eventEndFrame;

    private Double heightCurr;

    private Double heightBefore;

    private Integer status;

    private Long dealUserId;

    private String dealDetail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date dealTime;

    private String devicePhone;

    private Date phoneTime;

    private String deviceName;

    private Long deviceType;

    private Integer deviceVersion;

    private Integer count;

    private Integer df;

    //报警设备关联的人的姓名
    private String remarks;

    private Integer realFall;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRealFall() {
        return realFall;
    }

    public void setRealFall(Integer realFall) {
        this.realFall = realFall;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Long getFrameId() {
        return frameId;
    }

    public void setFrameId(Long frameId) {
        this.frameId = frameId;
    }

    public Long getEventStartFrame() {
        return eventStartFrame;
    }

    public void setEventStartFrame(Long eventStartFrame) {
        this.eventStartFrame = eventStartFrame;
    }

    public Long getEventEndFrame() {
        return eventEndFrame;
    }

    public void setEventEndFrame(Long eventEndFrame) {
        this.eventEndFrame = eventEndFrame;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(Long dealUserId) {
        this.dealUserId = dealUserId;
    }

    public String getDealDetail() {
        return dealDetail;
    }

    public void setDealDetail(String dealDetail) {
        this.dealDetail = dealDetail == null ? null : dealDetail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone == null ? null : devicePhone.trim();
    }

    public Date getPhoneTime() {
        return phoneTime;
    }

    public void setPhoneTime(Date phoneTime) {
        this.phoneTime = phoneTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Long getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Long deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(Integer deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Double getHeightCurr() {
        return heightCurr;
    }

    public void setHeightCurr(Double heightCurr) {
        this.heightCurr = heightCurr;
    }

    public Double getHeightBefore() {
        return heightBefore;
    }

    public void setHeightBefore(Double heightBefore) {
        this.heightBefore = heightBefore;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }



}