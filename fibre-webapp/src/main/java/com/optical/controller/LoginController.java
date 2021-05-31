package com.optical.controller;

import com.optical.bean.OpWebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult login(String username, String password){
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        log.info("username: " + username + ", password: " + password);

        Map map = new HashMap<>();
        map.put("token","admin-token");
        op.setData(map);

        return op;
    }

    @RequestMapping(value = "/user/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult userInfo(String token){
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        log.info("token: " + token);

        String[] strArr = new String[1];
        strArr[0] = "admin";

        Map map = new HashMap<>();
        map.put("roles",strArr);
        map.put("name","admin");
        map.put("introduction", "demo user");
        map.put("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.17qq.com%2Fimg_qqtouxiang%2F88596391.jpeg&refer=http%3A%2F%2Fwww.17qq.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616925103&t=749646f1376cd7846e067f3fb1f39ebe");
        op.setData(map);

        return op;
    }
}
