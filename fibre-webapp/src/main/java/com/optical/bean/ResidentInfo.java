package com.optical.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ResidentInfo {
    private Long id;


    private String code;

    private String name;

    private String urgentPhone;

    private Integer gender;

    private Integer age;

    private Integer floor;

    private String roomName;

    private Integer roomNum;

    private String description;

    private String img;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date updateTime;

    private Long vendorId;

    private Long roleId;

    /**
     * 位置 0不在 1卫生间 2卧室 3起居室 4厨房
     */
    private Integer location;

    private Integer df;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUrgentPhone() {
        return urgentPhone;
    }

    public void setUrgentPhone(String urgentPhone) {
        this.urgentPhone = urgentPhone == null ? null : urgentPhone.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
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

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }



    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }
}