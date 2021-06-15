package com.optical.mapper;

import com.optical.bean.TerminalAssign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mary on 2021/2/20.
 */

public interface TerminalAssignMapper {

    public TerminalAssign getById(@Param("id") Long id);

    public TerminalAssign getByImei(@Param("imei") String imei);

    public String getPhoneByDeviceCode(@Param("deviceCode") String deviceCode);

    public String getDeviceName(@Param("deviceCode") String deviceCode);

    public TerminalAssign getConfigInfoByDeviceCode(@Param("deviceCode") String deviceCode);

    public Integer updateLastestAssignTime(TerminalAssign assign);

    public Integer getListCountByCondition(Map map);

    public List<TerminalAssign> getListByCondition(Map map);

    public Integer updateConfig(Map map);

    public Integer updateUploadPointCloud(@Param("deviceCode") String deviceCode, @Param("cloudUploadEnable") Integer cloudUploadEnable);

    public Integer updatePhoneEnable(@Param("deviceCode") String deviceCode, @Param("phoneEnable") Integer phoneEnable);

    public Integer updatePhone(@Param("deviceCode") String deviceCode, @Param("phone") String phone);

    public Integer updateDeviceName(@Param("deviceCode") String deviceCode, @Param("deviceName") String deviceName);

    public List<TerminalAssign> getAllDeviceCodeImei();

    public Integer getCloudUploadEnable(@Param("deviceCode") String deviceCode);

    public Integer getLocationByDeviceCode(@Param("deviceCode") String deviceCode);


    //定时器调用 更新设备离线状态  所有设备在系统启动的时候默认在线，在2个心跳周期之后，恢复正常。
    //忽略这点小Gap
    // status: 0: 在线    1：不在线
    public Integer updateDeviceOffline(@Param("deviceCodeList") List<String> deviceCodeList);

    /**
     * 定时器调用，更新设备在线状态
     * status : 0: 在线  1：不在线
     * @return
     */
    public Integer updateDeviceOnline(@Param("deviceCodeList") List<String> deviceCodeList);

    /**
     * 获取设备总数 union 在线设备数
     * @return
     */
    public Integer getDeviceCount(@Param("status") Integer status);


}
