package com.optical.Service.impl;

import com.alibaba.fastjson.JSON;
import com.optical.Service.FallDeviceService;
import com.optical.bean.OpWebResult;
import com.optical.bean.TerminalAssign;
import com.optical.component.NettyOutBoundHandler;
import com.optical.component.NettyServer;
import com.optical.mapper.DeviceAlarmMapper;
import com.optical.mapper.TerminalAssignMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by mary on 2021/3/24.
 */
@Service
public class FallDeviceServiceImpl implements FallDeviceService {
    private static Logger log = LoggerFactory.getLogger(FallDeviceServiceImpl.class);

    @Autowired
    private TerminalAssignMapper terminalAssignMapper;

    @Autowired
    private DeviceAlarmMapper deviceAlarmMapper;

    @Override
    public OpWebResult getFallEventDeviceList(Long vendoId, String deviceCode, String imei, Integer status,
                                              Integer page, Integer limit) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Map rtnMap = new HashMap<>();
        List<TerminalAssign> list = null;
        Integer start = 0;
        Integer end = 10;
        if(page != null && page > 0) {
            if(limit == null || limit <= 0) limit = 10;
            start = (page - 1) * limit;
        }
        try{
            Map searchMap = new HashMap();
            searchMap.put("vendorId", vendoId);
            searchMap.put("deviceCode", deviceCode);
            searchMap.put("imei", imei);
            searchMap.put("status", status);
            searchMap.put("start", start);
            searchMap.put("end", end);
            Integer total = terminalAssignMapper.getListCountByCondition(searchMap);

            if(total > 0) {
                list = terminalAssignMapper.getListByCondition(searchMap);
            }
            rtnMap.put("total", total);
            rtnMap.put("list", list);
            op.setData(rtnMap);

        }catch (Exception e) {
            log.error("Exception! getFallEventDeviceList(). e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }
        return op;
    }

    @Override
    public OpWebResult updatePhoneEnable(String deviceCode, Integer enable) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.MODIFY_SUCCESS);
        if(StringUtils.isEmpty(deviceCode) || enable == null){
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("deviceCode或enable不可为空！");
            return op;
        }
        try{

            terminalAssignMapper.updatePhoneEnable(deviceCode, enable);

        }catch (Exception e) {
            log.error("updatePhoneEnable ERROR: e={}", e);
            op.setMsg(OpWebResult.OpMsg.MODIFY_FAIL);
            op.setResult(OpWebResult.OP_FAILED);
        }

        return op;
    }

    @Override
    public OpWebResult updateDeviceName(String deviceCode, String deviceName) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.MODIFY_SUCCESS);
        if(StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(deviceName)){
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("deviceCode或deviceName不可为空！");
            return op;
        }
        try{

            terminalAssignMapper.updateDeviceName(deviceCode, deviceName);

        }catch (Exception e) {
            log.error("updateDeviceName ERROR: e={}", e);
            op.setMsg(OpWebResult.OpMsg.MODIFY_FAIL);
            op.setResult(OpWebResult.OP_FAILED);
        }

        return op;
    }

    @Override
    public OpWebResult updatePhone(String deviceCode, String phone) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.MODIFY_SUCCESS);
        if(StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(phone)){
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("deviceCode或phone不可为空！");
            return op;
        }
        try{

            terminalAssignMapper.updatePhone(deviceCode, phone);

        }catch (Exception e) {
            log.error("updatePhone ERROR: e={}", e);
            op.setMsg(OpWebResult.OpMsg.MODIFY_FAIL);
            op.setResult(OpWebResult.OP_FAILED);
        }

        return op;
    }

    @Override
    public OpWebResult testDeliveMsg(String deviceCode, String phone) {
        //https://blog.csdn.net/weixin_43881309/article/details/109649842
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.MODIFY_SUCCESS);
        if(StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(phone)){
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("deviceCode或phone不可为空！");
            return op;
        }
        try{

            ChannelHandlerContext ctx = NettyServer.getCtxMap().get(deviceCode);
            ctx.write("test testDeliveMsg");
            ctx.flush();

        }catch (Exception e) {
            log.error("testDeliveMsg ERROR: e={}", e);
            op.setMsg(OpWebResult.OpMsg.MODIFY_FAIL);
            op.setResult(OpWebResult.OP_FAILED);
        }

        return op;
    }

    @Override
    public OpWebResult updateConfig(String deviceCode, Double sensorFixHeight, Double onGroundDuration,
                                    Double leaveGroundDuration, Double confidenceThreshold) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.MODIFY_SUCCESS);
        if(StringUtils.isEmpty(deviceCode) ){
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("deviceCode不可为空！");
            return op;
        }
        try{
            Map searchMap = new HashMap();
            searchMap.put("deviceCode", deviceCode);
            searchMap.put("sensorFixHeight", sensorFixHeight);
            searchMap.put("onGroundDuration", onGroundDuration);
            searchMap.put("leaveGroundDuration", leaveGroundDuration);
            searchMap.put("confidenceThreshold", confidenceThreshold);
            terminalAssignMapper.updateConfig(searchMap);


            Integer cloudUploadEnable = terminalAssignMapper.getCloudUploadEnable(deviceCode);

            //向设备下发配置json
            ChannelHandlerContext ctx = NettyServer.getCtxMap().get(deviceCode);
            if(ctx != null) {
                Map deliverMap = new HashMap();
                deliverMap.put("result", 1);
                deliverMap.put("msg", "success");
                deliverMap.put("type", 3);
                deliverMap.put("unique_id", "server-triger-v1.0.0");
                deliverMap.put("leave_ground_duration", Math.round(leaveGroundDuration * 100));
                deliverMap.put("senser_fix_height", Math.round(sensorFixHeight * 100) );
                deliverMap.put("on_ground_duration", Math.round(onGroundDuration * 100));
                deliverMap.put("confidence_threshold", Math.round(confidenceThreshold * 100));
                deliverMap.put("cloud_upload_enable", cloudUploadEnable);


                log.info("config deliver: " + JSON.toJSONString(deliverMap));
                ctx.write(JSON.toJSONString(deliverMap));
                ctx.flush();
            }else{
                log.error("NettyServer.getCtxMap() is null! deviceCode: " + deviceCode);
            }

        }catch (Exception e) {
            log.error("updateConfig ERROR: e={}", e);
            op.setMsg(OpWebResult.OpMsg.MODIFY_FAIL);
            op.setResult(OpWebResult.OP_FAILED);
        }

        return op;
    }

    @Override
    public OpWebResult getStatistic(Long vendorId) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Map rtnMap = new HashMap<>();

        try{
            //设备数 离线设备数
            Integer total = terminalAssignMapper.getDeviceCount(null);
            Integer onLine = terminalAssignMapper.getDeviceCount(0);

            //今日报警数
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            //我也不知道这个时区是怎么搞的
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();

            Integer todayAlarm = deviceAlarmMapper.getAlarmCountSinceToday(zero);

            //未处理报警数
            Integer unhandleAlarm = deviceAlarmMapper.getUnhandledAlarmCount();
            rtnMap.put("total", total);
            rtnMap.put("online", onLine);
            rtnMap.put("todayAlarm", todayAlarm);
            rtnMap.put("unhandleAlarm", unhandleAlarm);

            op.setData(rtnMap);

        }catch (Exception e) {
            log.error("Exception! getStatistic(). e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }
        return op;
    }

}
