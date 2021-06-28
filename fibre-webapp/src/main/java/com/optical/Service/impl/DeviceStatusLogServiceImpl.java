package com.optical.Service.impl;

import com.optical.Service.DeviceStatusLogService;
import com.optical.bean.DeviceStatusLog;
import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;
import com.optical.mapper.DeviceStatusLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by mary on 2021/6/8.
 */
@Service
public class DeviceStatusLogServiceImpl implements DeviceStatusLogService{
    private static final Logger log = LoggerFactory.getLogger(DeviceStatusLogServiceImpl.class);

    @Autowired
    private DeviceStatusLogMapper deviceStatusLogMapper;

    @Override
    public OpWebResult addDeviceStatusLog(RadarEventBean reb) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            DeviceStatusLog deviceLog = new DeviceStatusLog();
            deviceLog.setDeviceCode(reb.getDevice_code());
            deviceLog.setTiMaxTemprate(reb.getTi_max_temprate());
            deviceLog.setTiTmpmaxRegion(reb.getTi_tmpmax_region());
            deviceStatusLogMapper.insert(deviceLog);

        }catch (Exception e) {
            log.error("DeviceStatusLogServiceImpl.addDeviceStatusLog error! e={}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }



}
