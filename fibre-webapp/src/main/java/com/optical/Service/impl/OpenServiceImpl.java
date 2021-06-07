package com.optical.Service.impl;

import com.alibaba.fastjson.JSON;
import com.optical.Service.OpenService;
import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;
import com.optical.bean.TerminalAssign;
import com.optical.mapper.TerminalAssignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.optical.component.StaticMapRunner.vendorDeviceMap;
import static com.optical.component.StaticMapRunner.vendorPushMap;

/**
 * Created by mary on 2021/5/19.
 */
@Service
public class OpenServiceImpl implements OpenService {
    private static Logger log = LoggerFactory.getLogger(OpenServiceImpl.class);

    @Autowired
    private TerminalAssignMapper terminalAssignMapper;


    @Override
    public OpWebResult openReceiver(RadarEventBean radarEventBean) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        log.info("received: " + JSON.toJSONString(radarEventBean));

        return op;
    }

    @Override
    public OpWebResult freshStaticMap() {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            List<TerminalAssign> deviceInfo = terminalAssignMapper.getAllDeviceCodeImei();

            for(TerminalAssign ta : deviceInfo) {
                vendorDeviceMap.put(ta.getDeviceCode(), ta.getVendorId());
                vendorPushMap.put(ta.getDeviceCode(), ta.getPushAddr());
            }
            log.info("fresh success, vendorPushMap and vendorDeviceMap ");
            log.info("vendorDeviceMap: " + JSON.toJSONString(vendorDeviceMap));
            log.info("vendorPushMap: " + JSON.toJSONString(vendorPushMap));

        }catch (Exception e) {
            log.error("Exception! freshStaticMap() failed!");
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
        }

        return op;

    }
}
