package com.optical.Service.impl;

import com.optical.Service.LoginService;
import com.optical.bean.LoginForm;
import com.optical.bean.OpWebResult;
import com.optical.bean.UserInfo;
import com.optical.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mary on 2021/6/1.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public OpWebResult login(LoginForm loginForm) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        if(StringUtils.isEmpty(loginForm.getUsername()) || StringUtils.isEmpty(loginForm.getPassword())) {
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        try{
            UserInfo user = userInfoMapper.getByUserCode(loginForm.getUsername());
            if(user == null) {
                //无此用户
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg("查无此人");
                return op;

            }else if(loginForm.getPassword().equals(user.getPassword())) {
                //登录正常，token返回vendorId
                Map map = new HashMap<>();
                map.put("token", user.getVendorId());
                op.setData(map);
                return op;
            }else{
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg("用户名或密码错误!");
                return op;
            }

        }catch (Exception e) {
            log.error("login error! e={}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

    }

    @Override
    public OpWebResult userInfo(Long vendorId) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        if(vendorId == null) {
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("vendorId 不可为空!");
            return op;
        }

        try{
            UserInfo user = userInfoMapper.getByVendorId(vendorId);
            if(user == null) {
                //无此用户
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg("查无此人");
                return op;

            }else{
                Map map = new HashMap<>();

                String[] strArr = new String[1];
                strArr[0] = "admin";
                map.put("roles",strArr);
                map.put("name",user.getUserCode());
                map.put("introduction", user.getUserName());
                map.put("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.17qq.com%2Fimg_qqtouxiang%2F88596391.jpeg&refer=http%3A%2F%2Fwww.17qq.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616925103&t=749646f1376cd7846e067f3fb1f39ebe");
                if(user.getVendorId() == 100000L){
                    map.put("vendorId", null);
                }else {
                    map.put("vendorId", user.getVendorId());
                }

                op.setData(map);
                return op;
            }

        }catch (Exception e) {
            log.error("login error! e={}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }



    }


}
