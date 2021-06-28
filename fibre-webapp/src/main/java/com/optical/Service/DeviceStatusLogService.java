package com.optical.Service;

import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;

/**
 * Created by mary on 2021/6/8.
 */
public interface DeviceStatusLogService {

    //温度信息入库
    public OpWebResult addDeviceStatusLog(RadarEventBean reb);

}
