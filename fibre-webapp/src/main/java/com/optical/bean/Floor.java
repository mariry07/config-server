package com.optical.bean;

/**
 * Created by mary on 2021/4/15.
 */
public class Floor {

    private Long id;

    private Long vendorId;

    private String key;

    private String name;

    private Integer sort;

    private Long pid;

    private String type;

    public Floor(Long id, String key, String name, Integer sort) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.sort = sort;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
}
