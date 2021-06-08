package com.optical.common;

import com.aliyun.tea.*;
import com.aliyun.dyvmsapi20170525.*;
import com.aliyun.dyvmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

public class AliCallUtil {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dyvmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dyvmsapi.aliyuncs.com";
        return new com.aliyun.dyvmsapi20170525.Client(config);
    }


    //accessKeyId 前半截 LTAI4G4x6y9
    //acdessKeyId 后半截 GX3daHo6EgZV
    //accessKeySecret 前半截 DE5cDCqpnBIsaCY
    // accessKeySecret 后半截 HDXTWtn6fL8vutV
    public static SingleCallByTtsResponse singleCallByTts2Phone(String tgtPhone) throws Exception {

        com.aliyun.dyvmsapi20170525.Client client = AliCallUtil.createClient("accessKey", "accessKeySectet");

        SingleCallByTtsRequest singleCallByTtsRequest = new SingleCallByTtsRequest()
                .setCalledNumber(tgtPhone)
                .setTtsCode("TTS_212482029")
                .setPlayTimes(2);
        SingleCallByTtsResponse response = client.singleCallByTts(singleCallByTtsRequest);
        return response;
    }


}