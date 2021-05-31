package com.optical.Service.impl;

import com.alibaba.fastjson.JSON;
import com.optical.Service.OpenService;
import com.optical.bean.OpWebResult;
import com.optical.bean.RadarEventBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by mary on 2021/5/19.
 */
@Service
public class OpenServiceImpl implements OpenService {
    private static Logger log = LoggerFactory.getLogger(OpenServiceImpl.class);

    @Override
    public OpWebResult openReceiver(RadarEventBean radarEventBean) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        log.info("received: " + JSON.toJSONString(radarEventBean));



        return op;
    }
}
