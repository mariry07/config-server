package com.optical.Service;

import com.optical.bean.OpResult;
import com.optical.bean.OpWebResult;
import org.springframework.stereotype.Controller;

/**
 * Created by mary on 2021/3/24.
 */
public interface FallAlarmService {

    public OpWebResult getFallEventDeviceList(Long vendorId, String deviceCode, Integer page, Integer limit, Integer status);

    public OpWebResult updateRealFall(String deviceCode);

    public OpWebResult getwechatList(String deviceCode, Integer page, Integer limit, Integer status);

    public OpWebResult updateDescription(Integer id, String description);

    public OpWebResult alarmTypeChange(Integer id, Integer type);


}
