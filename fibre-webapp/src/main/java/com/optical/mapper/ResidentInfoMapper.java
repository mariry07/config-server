package com.optical.mapper;

import com.optical.bean.ResidentInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mary on 2021/4/14.
 */
public interface ResidentInfoMapper {

    void insert(ResidentInfo residentInfo);

    void insertSelective(ResidentInfo info);

    Integer updateSelective(ResidentInfo residentInfo);

    ResidentInfo getByResidentCode(@Param("code") String code);

    String getNameByResidentDeviceCode(@Param("code") String code);

    ResidentInfo getById(@Param("id") Long id);

    Integer deleteById(@Param("id") Long id);

    List<ResidentInfo> getAll(Long vendorId);

    Integer getDataCountByCondition(@Param("floor") Integer floor, @Param("roomName") String roomName,
                                     @Param("code") String deviceCode);

    List<ResidentInfo> getDataByCondition(@Param("roomName") String roomName, @Param("floor") Integer floor,
                                         @Param("code") String deviceCode, @Param("start") Integer start, @Param("limit") Integer limit);

    Integer updateResident(Map map);

    Integer updateLocation(@Param("deviceCode") String deviceCode, @Param("location") Integer location);

    ResidentInfo getTabs(@Param("vendorId") Long vendorId);

}
