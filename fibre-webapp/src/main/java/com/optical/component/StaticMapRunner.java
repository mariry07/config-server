package com.optical.component;

import com.alibaba.fastjson.JSON;
import com.optical.Service.impl.FallDeviceServiceImpl;
import com.optical.bean.TerminalAssign;
import com.optical.mapper.TerminalAssignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mary on 2021/5/20.
 */
@Component
public class StaticMapRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StaticMapRunner.class);

    public static final Map<String, Object> staticMap = new ConcurrentHashMap<>();

    public static final Map<String, Long> vendorDeviceMap = new ConcurrentHashMap<>();

    public static final Map<String, String> vendorPushMap = new ConcurrentHashMap<>();

    @Autowired
    private TerminalAssignMapper terminalAssignMapper;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        log.info("here in StaticMapRunner starting up...");

        List<TerminalAssign> deviceInfo = terminalAssignMapper.getAllDeviceCodeImei();

        Long currentSecond = System.currentTimeMillis();

        for(TerminalAssign ta : deviceInfo) {
            staticMap.put(ta.getDeviceCode(), currentSecond);
            vendorDeviceMap.put(ta.getDeviceCode(), ta.getVendorId());
            vendorPushMap.put(ta.getDeviceCode(), ta.getPushAddr());
        }
        log.info("init success, staticMap and vendorDeviceMap ");
        log.info("staticMap: " + JSON.toJSONString(staticMap));
        log.info("vendorDeviceMap: " + JSON.toJSONString(vendorDeviceMap));
        log.info("vendorPushMap: " + JSON.toJSONString(vendorPushMap));

    }

}
