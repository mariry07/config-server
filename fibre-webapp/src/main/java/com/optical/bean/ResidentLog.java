package com.optical.bean;

public class ResidentLog {
    private Long id;

    private Long vendorId;

    private Long residentId;

    private String code;

    private String name;

    private Integer fallCount;

    private Integer wcCount;

    private Integer nightGetUpCount;

    private Integer visitor;

    private String sleepTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFallCount() {
        return fallCount;
    }

    public void setFallCount(Integer fallCount) {
        this.fallCount = fallCount;
    }

    public Integer getWcCount() {
        return wcCount;
    }

    public void setWcCount(Integer wcCount) {
        this.wcCount = wcCount;
    }

    public Integer getNightGetUpCount() {
        return nightGetUpCount;
    }

    public void setNightGetUpCount(Integer nightGetUpCount) {
        this.nightGetUpCount = nightGetUpCount;
    }

    public Integer getVisitor() {
        return visitor;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime == null ? null : sleepTime.trim();
    }
}