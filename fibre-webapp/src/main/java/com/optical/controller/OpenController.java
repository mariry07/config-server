package com.optical.controller;

import com.optical.Service.OpenService;
import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mary on 2021/5/19.
 */
@Controller
@RequestMapping(value = "/xitang/open")
public class OpenController {

    @Autowired
    private OpenService openService;

    @RequestMapping(value = "/device/log/XTDYT3", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult resident(@RequestBody RadarEventBean reb) {

        return openService.openReceiver(reb);
    }

}
