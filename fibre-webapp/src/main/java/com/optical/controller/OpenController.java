package com.optical.controller;

import com.optical.Service.OpenService;
import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @RequestMapping(value = "/device/staticMap/reset", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult resident(String suger) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        //suger是密码糖，先写成xitang吧
        if(!StringUtils.isEmpty(suger) && suger.equals("xitang")) {
            //刷新map
            return openService.freshStaticMap();

        }else{
            //do nothing
            op.setMsg("nothing need to do");
        }
        return op;

    }

}
