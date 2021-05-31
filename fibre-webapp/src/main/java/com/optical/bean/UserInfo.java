package com.optical.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserInfo {
    private Long id;

    private String userCode;

    private String userName;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date lastLoginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date updateTime;

    private Long vendorId;

    private Long roleId;

    private Integer tokenTime;

    private String token;

    private String openId;

    private Integer df;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Integer tokenTime) {
        this.tokenTime = tokenTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }
}