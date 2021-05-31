package com.optical.controller;

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
 * Created by mary on 2021/5/20.
 */
@Controller
@RequestMapping(value = "/xitang/homePage")
public class HomePageController {
    private static Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private FallDeviceService fallDeviceService;

    @RequestMapping(value = "/statistic", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult getStatistic(Long vendorId){
        OpWebResult op = new OpWebResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        return fallDeviceService.getStatistic(vendorId);
    }

}
