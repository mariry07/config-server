package com.optical.mapper;

import com.optical.bean.DeviceAlarm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mary on 2021/2/25.
 */
public interface DeviceAlarmMapper {

    void insert(DeviceAlarm opticalFibreTemp);

    int getUnhandledAlarmCountByDeviceCode(@Param("deviceCode") String deviceCode);

    int updateRealFall(@Param("id") Integer id);

    int updateAlarmType(@Param("id") Integer id, @Param("type") Integer type);

    int updateAlarmStatus(@Param("deviceCode") String deviceCode, @Param("status") Integer reason);

    List<DeviceAlarm> getAll();

    Integer getDataCountByCondition(@Param("deviceCode") String deviceCode, @Param("status") Integer status);

    List<DeviceAlarm> getDataByCondition(@Param("deviceCode") String deviceCode, @Param("status") Integer status,
                                         @Param("start") Integer start, @Param("end") Integer end);

    Integer updateDescription(@Param("id") Integer id, @Param("description") String description);

    Integer getUnhandledAlarmCount();

    Integer getAlarmCountSinceToday(@Param("startTime") Date today);

}
