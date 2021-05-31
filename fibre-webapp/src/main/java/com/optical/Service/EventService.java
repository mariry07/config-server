package com.optical.Service;

import com.optical.bean.RadarEventBean;

/**
 * Created by mary on 2021/2/25.
 */
public interface EventService {

    //跌倒报警事件上报
    public String fallEventDetected(RadarEventBean reb);

    //跌倒消警事件上报
    public String fallEventDismissed(RadarEventBean reb);

    //人员发现
    public String humanDetectEvent(RadarEventBean reb);


    //跌倒点云上传
    public String pointCloudReport();



}
