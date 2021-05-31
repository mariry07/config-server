package com.optical.controller;

import com.optical.Service.FallAlarmService;
import com.optical.bean.OpWebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mary on 2021/2/25.
 */
@Controller
@RequestMapping(value = "/xitang/alarm")
public class AlarmController {

    @Autowired
    private FallAlarmService fallAlarmService;

    @RequestMapping(value = "/alarmList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult getAlarmList(String deviceCode, Integer page, Integer limit, Integer status){
        return fallAlarmService.getFallEventDeviceList(deviceCode, page, limit, status);
    }

    @RequestMapping(value = "/realFall", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult realFall(String id){
        return fallAlarmService.updateRealFall(id);
    }

    @RequestMapping(value = "/type", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult alarmTypeChange(Integer id, Integer type){
        return fallAlarmService.alarmTypeChange(id, type);
    }


    @RequestMapping(value = "/description", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult updateDescription(Integer id, String description){
        return fallAlarmService.updateDescription(id, description);
    }



    @RequestMapping(value = "/wechatList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult wechatList(String deviceCode, Integer page, Integer limit, Integer status){
        return fallAlarmService.getwechatList(deviceCode, page, limit, status);
    }

}
