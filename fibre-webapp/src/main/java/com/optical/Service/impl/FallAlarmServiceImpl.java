package com.optical.Service.impl;

import com.optical.Service.FallAlarmService;
import com.optical.bean.DeviceAlarm;
import com.optical.bean.OpWebResult;
import com.optical.mapper.DeviceAlarmMapper;
import com.optical.mapper.ResidentInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mary on 2021/3/24.
 */
@Service
public class FallAlarmServiceImpl implements FallAlarmService {

    private static final Logger log = LoggerFactory.getLogger(FallAlarmServiceImpl.class);

    @Autowired
    private DeviceAlarmMapper deviceAlarmMapper;
    @Autowired
    private ResidentInfoMapper residentInfoMapper;

    @Override
    public OpWebResult getFallEventDeviceList(String deviceCode, Integer page, Integer limit, Integer status) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Integer start = null;
        List<DeviceAlarm> list = null;
        Map rtnMap = new HashMap<>();
        if(page != null && page > 0){
            if(limit == null || limit <= 0){
                limit = 20;
            }
            start = (page - 1 ) * limit;
        }
        try{

            Integer total = deviceAlarmMapper.getDataCountByCondition(deviceCode, status);
            if(total > 0) {
                list = deviceAlarmMapper.getDataByCondition(deviceCode, status, start, limit);
            }
            rtnMap.put("total", total);
            rtnMap.put("list", list);
            op.setData(rtnMap);

        }catch (Exception e) {
            log.error("Exception! getFallEventDeviceList() e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }

        return op;

    }

    @Override
    public OpWebResult updateRealFall(String id) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            Integer alarmId = Integer.parseInt(id);
            deviceAlarmMapper.updateRealFall(alarmId);

        }catch (Exception e) {
            log.error("updateRealFall Exception!! e : {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }
        return op;
    }

    @Override
    public OpWebResult getwechatList(String deviceCode, Integer page, Integer limit, Integer status) {
        //小程序获取今日报警信息接口，且需拼装报警关联的住户信息
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Integer start = 0;
        List<DeviceAlarm> list = null;
        Map rtnMap = new HashMap<>();
        if(page != null && page > 0){
            if(limit == null || limit <= 0){
                limit = 5;
            }
            start = (page - 1 ) * limit;
        }else{
            limit = 10;
        }
        try{

            //TODO: 小程序仅查今日报警
            Integer total = deviceAlarmMapper.getDataCountByCondition(deviceCode, status);
            if(total > 0) {
                list = deviceAlarmMapper.getDataByCondition(deviceCode, status, start, limit);
                for(DeviceAlarm da: list) {
                    String residentName = residentInfoMapper.getNameByResidentDeviceCode(da.getDeviceCode());
                    da.setRemarks(residentName);
                }
            }
            rtnMap.put("total", total);
            rtnMap.put("list", list);
            op.setData(rtnMap);

        }catch (Exception e) {
            log.error("Exception! getFallEventDeviceList() e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }

        return op;
    }

    @Override
    public OpWebResult updateDescription(Integer id, String description) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            deviceAlarmMapper.updateDescription(id, description);

        }catch (Exception e) {
            log.error("updateDescription Exception!! e : {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }
        return op;
    }

    @Override
    public OpWebResult alarmTypeChange(Integer id, Integer type) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            deviceAlarmMapper.updateAlarmType(id, type);

        }catch (Exception e) {
            log.error("alarmTypeChange Exception!! e : {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }
        return op;
    }
}
