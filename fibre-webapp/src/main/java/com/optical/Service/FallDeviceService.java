package com.optical.Service;

import com.optical.bean.OpWebResult;

/**
 * Created by mary on 2021/3/24.
 */
public interface FallDeviceService {

    public OpWebResult getFallEventDeviceList(Long vendorId, String deviceCode, String imei, Integer status,
                                              Integer page, Integer limit);

    public OpWebResult updatePhoneEnable(String deviceCode, Integer enable);

    public OpWebResult updateDeviceName(String deviceCode, String deviceName);

    public OpWebResult updatePhone(String deviceCode, String phone);

    public OpWebResult testDeliveMsg(String deviceCode, String phone);

    public OpWebResult updateConfig(String deviceCode, Double sensorFixHeight, Double onGroundDuration,
                        Double leaveGroundDuration, Double confidenceThreshold);

    public OpWebResult getStatistic(Long vendorId);

    public OpWebResult otaTriger(String deviceCode);

    public OpWebResult pointCloudConfig(String deviceCode);


}
