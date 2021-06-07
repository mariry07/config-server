package com.optical.component;

import com.alibaba.fastjson.JSON;
import com.optical.common.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mary on 2021/5/19.
 */

@Component
@EnableScheduling
public class PushTask {
    private static Logger log = LoggerFactory.getLogger(PushTask.class);

    /*
    第三方推送域名（或 ip:port)
     */
    private String uri = "test.zhaohu365.com";
//    private String uri = "47.105.53.60:11007";



    // 福寿康 设备心跳模拟
//    @Scheduled(cron = "0/12 * * * * ?")
//    @Scheduled(cron = "0/14 0-30 17 25 * ?")
//    @Scheduled(cron = "0/15 53-55 9 * * ?")
    public void HeartBeatJob() {

        Map map = new HashMap();
        map.put("type", 7);
        map.put("device_code", "xtd20211700012");
        String param = JSON.toJSONString(map);
        log.info("### param: " + param);
        try{
            String jsionResult= HttpUtil.post("http://" + uri + "/xitang/open/device/log/XTDYT3", param);

            log.info("heartbeat: " + jsionResult);
        }catch (Exception e) {
            log.error("HeartBeatJob error! e={}", e);
        }
    }


//     福寿康 设备报警模拟
//    @Scheduled(cron = "0/15 * * * * ?")
//    @Scheduled(cron = "0/15 0-30 17 25 * ?")
    @Scheduled(cron = "0/15 15-17 11 * * ?")
    public void FallEventJob() {

        Map map = new HashMap();
        map.put("type", 1);
        map.put("device_code", "xtd20211700012");
        map.put("unique_id", System.currentTimeMillis());
        map.put("confidence", 94);
        map.put("reason", 0);
        Integer frame = (int)(Math.random()*1000) + 150;
        map.put("frame_id", frame );
        map.put("event_start_frame", frame - 150);
        map.put("event_end_frame", frame);
        double height = Double.valueOf(String.format("%.2f", Math.random()).toString()) + 0.8;
        map.put("height_curr", height);
        map.put("height_before", height - 0.8);

        String param = JSON.toJSONString(map);
        log.info("### param: " + param);
        try{
            String jsionResult= HttpUtil.post("http://" + uri + "/xitang/open/device/log/XTDYT3",
                    param);

            log.info("fall event: " + jsionResult);
        }catch (Exception e) {
            log.error("FallEventJob error! e={}", e);
        }
    }

//     福寿康 消警事件
//    @Scheduled(cron = "0/13 * * * * ?")
//    @Scheduled(cron = "0/16 0-30 17 25 * ?")
    @Scheduled(cron = "0/15 41-43 9 * * ?")
    public void RaiseEventJob() {

        Map map = new HashMap();
        map.put("type", 2);
        map.put("device_code", "xtd20211700012");
        map.put("unique_id", System.currentTimeMillis());
        map.put("reason", 0);

        String param = JSON.toJSONString(map);
        try{
            String jsionResult = HttpUtil.post("http://" + uri + "/xitang/open/device/log/XTDYT3",
                    param);
            log.info("raise event: " + jsionResult);

        }catch (Exception e) {
            log.error("RaiseEventJob error! e={}", e);
        }
    }


//     福寿康 人员出现事件
//    @Scheduled(cron = "0/16 * * * * ?")
//    @Scheduled(cron = "0/17 0-30 17 25 * ?")
    @Scheduled(cron = "0/15 46-48 9 * * ?")
    public void HumanDetectJob() {

        Map map = new HashMap();
        map.put("type", 4);
        map.put("device_code", "xtd20211700012");
        map.put("unique_id", System.currentTimeMillis());
        Double t = Math.random();
        map.put("reason", 1);


        String param = JSON.toJSONString(map);
        try{
            String jsionResult= HttpUtil.post("http://" + uri + "/xitang/open/device/log/XTDYT3"
                    ,param);

            log.info("humanDetect event: "+jsionResult);

        }catch (Exception e) {
            log.error("HumanDetectJob error! e={}", e);
        }
    }

}
