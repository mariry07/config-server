package com.optical.controller;

import com.optical.Service.LoginService;
import com.optical.bean.LoginForm;
import com.optical.bean.OpWebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mary on 2021/2/26.
 */
@Controller
@RequestMapping(value = "/xitang")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult login(@RequestBody LoginForm loginForm){

        return loginService.login(loginForm);
//        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
//        log.info("username: " + loginForm.getUsername() + ", password: " + loginForm.getPassword());
//        Map map = new HashMap<>();
//        map.put("token","admin-token");
//        op.setData(map);
//
//        return op;
    }

    @RequestMapping(value = "/user/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult userInfo(Long vendorId){

        return loginService.userInfo(vendorId);

    }

    @RequestMapping(value = "/user/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult logout(String username, String password){
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

//        log.info("username: " + username + ", password: " + password);

        Map map = new HashMap<>();
        map.put("token","admin-token");
        op.setData(map);

        return op;
    }

}
