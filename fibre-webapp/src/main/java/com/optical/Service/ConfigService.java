package com.optical.Service;

import com.optical.bean.ConfigInfo;
import com.optical.bean.RadarEventBean;
import com.optical.bean.TerminalAssign;

/**
 * Created by mary on 2021/2/20.
 */
public interface ConfigService {

    public ConfigInfo getConfigService(String imei);

    public String getDevicePhone(String deviceCode);

    public TerminalAssign getDeviceConfigInfo(RadarEventBean reb);

}
