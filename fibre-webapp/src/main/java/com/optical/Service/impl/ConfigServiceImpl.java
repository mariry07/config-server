package com.optical.Service.impl;

import com.alibaba.fastjson.JSON;
import com.optical.Service.ConfigService;
import com.optical.bean.ConfigInfo;
import com.optical.bean.OpResult;
import com.optical.bean.RadarEventBean;
import com.optical.bean.TerminalAssign;
import com.optical.mapper.TerminalAssignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by mary on 2021/2/20.
 */
@Component
@Configuration
public class ConfigServiceImpl implements ConfigService {
    private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private TerminalAssignMapper terminalAssignMapper;

    @Override
    public ConfigInfo getConfigService(String imei) {
        ConfigInfo ci = new ConfigInfo(ConfigInfo.OP_SUCCESS, ConfigInfo.OpMsg.SUCCESS);
        try{
            TerminalAssign assign = terminalAssignMapper.getByImei(imei);

            if(assign != null) {
                ci.setPort(assign.getPort());
                ci.setIp(assign.getIp());
                ci.setDevice_code(assign.getDeviceCode());

                //更新最新上电时间 lastest_assign_time
                terminalAssignMapper.updateLastestAssignTime(assign);


            }else{
               ci.setResult(ConfigInfo.OP_FAILED);
               ci.setMessage(ConfigInfo.OpMsg.ILLEGAL);
            }
            return ci;
        }catch (Exception e) {
            log.error("Exception! e = {}", e);
            ci.setResult(ConfigInfo.OP_FAILED);
            ci.setMessage(ConfigInfo.OpMsg.ILLEGAL);
            return ci;
        }
    }

    @Override
    public String getDevicePhone(String deviceCode) {

        String rtn = "";
        try{
            rtn = terminalAssignMapper.getPhoneByDeviceCode(deviceCode);

            return rtn;
        }catch (Exception e) {
            log.error("Exception! e = {}", e);
            return null;
        }
    }

    @Override
    public TerminalAssign getDeviceConfigInfo(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        TerminalAssign ta = null;
        try{
            ta = terminalAssignMapper.getConfigInfoByDeviceCode(reb.getDevice_code());

        }catch (Exception e) {
            log.error("Error! fallEventDismissed()! e = {}", e);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
        }

        return ta;

    }


}
