package com.optical.component;

import com.alibaba.fastjson.JSON;
import com.optical.mapper.TerminalAssignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.optical.component.StaticMapRunner.staticMap;

/**
 * Created by mary on 2021/5/20.
 */
//@Component
//@EnableScheduling
public class DeviceOnLineTask {
    private static Logger log = LoggerFactory.getLogger(DeviceOnLineTask.class);

    @Autowired
    private TerminalAssignMapper terminalAssignMapper;


    @Scheduled(cron = "0/20 * * * * ?")
    public void OnLineCheckJob() {
        log.info("entered OnLineCheckJob......");
        // 检查线：最近一次信息上传发生在一分钟以内
        Long checkLine = System.currentTimeMillis() - 59999;
        //离线设备Code 列表
        List<String> offLineDevice = new ArrayList<>();
        //在线设备code 列表
        List<String> onLineDevice = new ArrayList<>();

        for(Map.Entry<String, Object> entry: staticMap.entrySet()) {
            if(Long.parseLong(entry.getValue().toString()) < checkLine) {
                offLineDevice.add(entry.getKey());
            }else{
                onLineDevice.add(entry.getKey());
            }
        }

        if(!StringUtils.isEmpty(offLineDevice) && offLineDevice.size() > 0) {
            // 离线设备 status = 1 . 仅当设备列表不为空，才执行update 状态
            terminalAssignMapper.updateDeviceOffline(offLineDevice);
        }
        if(!StringUtils.isEmpty(onLineDevice) && onLineDevice.size() > 0) {
            //在线设备 status = 0
            terminalAssignMapper.updateDeviceOnline(onLineDevice);
        }

    }

}
