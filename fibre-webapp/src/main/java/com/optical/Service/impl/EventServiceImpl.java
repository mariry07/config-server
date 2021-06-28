package com.optical.Service.impl;

import com.alibaba.fastjson.JSON;
import com.optical.Service.EventService;
import com.optical.bean.DeviceAlarm;
import com.optical.bean.OpResult;
import com.optical.bean.RadarEventBean;
import com.optical.mapper.DeviceAlarmMapper;
import com.optical.mapper.ResidentInfoMapper;
import com.optical.mapper.TerminalAssignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.naming.event.EventContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.optical.component.StaticMapRunner.vendorDeviceMap;

/**
 * Created by mary on 2021/2/25.
 */
@Component
@Configuration
public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private DeviceAlarmMapper deviceAlarmMapper;
    @Autowired
    private TerminalAssignMapper terminalAssignMapper;
    @Autowired
    private ResidentInfoMapper residentInfoMapper;



    @Override
    public String fallEventDetected(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        String rtnStr = "";

        try{
            DeviceAlarm alarm = new DeviceAlarm();
            String deviceName = terminalAssignMapper.getDeviceName(reb.getDevice_code());
            Long vendorId = vendorDeviceMap.get(reb.getDevice_code());
            alarm.setVendorId(vendorId);
            alarm.setDeviceCode(reb.getDevice_code());
            alarm.setDeviceName(deviceName);
            //status : 0未处理 1已处理 2误报
            alarm.setStatus(0);
            //type: 0 误报 1模拟摔 2真摔
            alarm.setType(0);
            alarm.setReason(reb.getReason());
            // 除100
            alarm.setConfidence(reb.getConfidence());

            alarm.setFrameId(reb.getFrame_id());
            alarm.setEventStartFrame(reb.getEvent_start_frame());
            alarm.setEventEndFrame(reb.getEvent_end_frame());
            alarm.setHeightBefore(reb.getHeight_before());
            alarm.setHeightCurr(reb.getHeight_curr());
            alarm.setDf(0);

            deviceAlarmMapper.insert(alarm);

            //更新住户状态    0不在 1卫生间 2卧室 3起居室 4厨房 6未知',
            //TODO: 常量
            residentInfoMapper.updateLocation(reb.getDevice_code(), 5);
            rtnStr = JSON.toJSONString(op);

        }catch (Exception e){
            log.error("Error! fallEventDetected() insert new event! e = {}", e);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
            rtnStr = JSON.toJSONString(op);
        }

        return rtnStr;
    }

    @Override
    public String fallEventDismissed(RadarEventBean reb) {
        //收到消警信息之后，将本设备的所有已有报警信息全部消警
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        String rtnStr = "";
        try{
            //TODO: 根据deviceCode 获取设备的vendorId
            //将本deviceCode下status = 0(待处理)的记录，用消警信息补全
            Integer unhandledAlarmCount = deviceAlarmMapper.getUnhandledAlarmCountByDeviceCode(reb.getDevice_code());
            if(unhandledAlarmCount > 0) {
                //TODO: 消警类型待处理
                deviceAlarmMapper.updateAlarmStatus(reb.getDevice_code(), reb.getReason() + 1);
            }
            //消警的时候肯定在卫生间
            residentInfoMapper.updateLocation(reb.getDevice_code(), 1);

        }catch (Exception e) {
            log.error("Error! fallEventDismissed()! e = {}", e);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
            rtnStr = JSON.toJSONString(op);
        }

        return rtnStr;
    }

    @Override
    public String humanDetectEvent(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        String rtnStr = "";
        try{

            //TODO: 记录人员轨迹log

            if(reb.getReason() == 1) {
                //人员发现，以设备位置更新住户表的当前位置
                Integer location = terminalAssignMapper.getLocationByDeviceCode(reb.getDevice_code());
                residentInfoMapper.updateLocation(reb.getDevice_code(), location);
            }else if(reb.getReason() == 0) {
                residentInfoMapper.updateLocation(reb.getDevice_code(), 0);
            }

        }catch (Exception e) {
            log.error("Error! fallEventDismissed()! e = {}", e);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
            rtnStr = JSON.toJSONString(op);
        }

        return rtnStr;
    }



    @Override
    public String pointCloudReport() {
        return null;
    }


}
