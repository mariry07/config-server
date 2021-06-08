package com.optical.bean;

import java.util.Date;

public class DeviceStatusLog {
    private Long id;

    private String deviceCode;

    private Integer tiTmpmaxRegion;

    private Integer tiMaxTemprate;

    private Long vendorId;

    private Date createTime;

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

    public Integer getTiTmpmaxRegion() {
        return tiTmpmaxRegion;
    }

    public void setTiTmpmaxRegion(Integer tiTmpmaxRegion) {
        this.tiTmpmaxRegion = tiTmpmaxRegion;
    }

    public Integer getTiMaxTemprate() {
        return tiMaxTemprate;
    }

    public void setTiMaxTemprate(Integer tiMaxTemprate) {
        this.tiMaxTemprate = tiMaxTemprate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}