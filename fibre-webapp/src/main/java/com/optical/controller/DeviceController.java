package com.optical.controller;

import com.optical.Service.FallAlarmService;
import com.optical.Service.FallDeviceService;
import com.optical.bean.OpResult;
import com.optical.bean.OpWebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mary on 2021/2/25.
 */

@Controller
@RequestMapping(value = "/xitang/device")
public class DeviceController {
    private static Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private FallDeviceService fallDeviceService;

    @RequestMapping(value = "/deviceList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult getDeviceList(Long vendorId, String deviceCode, String imei, Integer status,
                                     Integer page, Integer limit){
        OpWebResult op = new OpWebResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        return fallDeviceService.getFallEventDeviceList(vendorId, deviceCode, imei, status, page, limit);
    }


    @RequestMapping(value = "/configChange", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult updateConfig(String deviceCode, Double sensorFixHeight, Double onGroundDuration,
                                    Double leaveGroundDuration, Double confidenceThreshold){
        OpWebResult op = new OpWebResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        return fallDeviceService.updateConfig(deviceCode, sensorFixHeight,onGroundDuration,
                leaveGroundDuration, confidenceThreshold);
    }

    @RequestMapping(value = "/phoneEnable", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult phoneEnable(String deviceCode, Integer phoneEnable){
        OpWebResult op = new OpWebResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        log.info("deviceCode: " + deviceCode + ", enable: " + phoneEnable);
        return fallDeviceService.updatePhoneEnable(deviceCode, phoneEnable);
    }


    @RequestMapping(value = "/nameChange", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult nameChange(String deviceCode, String deviceName){

        return fallDeviceService.updateDeviceName(deviceCode, deviceName);
    }


    @RequestMapping(value = "/phoneChange", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult updatePhone(String deviceCode, String phone){
        log.info("deviceCode: " + deviceCode + ", phone: " + phone);
        return fallDeviceService.updatePhone(deviceCode, phone);
    }

    @RequestMapping(value = "/testDeliveMsg", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult testDeliveMsg(String deviceCode, String phone){
        OpWebResult op = new OpWebResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        log.info("deviceCode: " + deviceCode + ", phone: " + phone);
        //https://blog.csdn.net/weixin_43881309/article/details/109649842
        return fallDeviceService.testDeliveMsg(deviceCode, phone);
    }


    @RequestMapping(value = "/vue-element-admin/user/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult login(){

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        return op;
    }


}
