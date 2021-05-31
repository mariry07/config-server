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

    public static SingleCallByTtsResponse singleCallByTts() throws Exception {
        com.aliyun.dyvmsapi20170525.Client client = AliCallUtil.createClient(getAccessKeyId(), getAccessKeySecret());
        SingleCallByTtsRequest singleCallByTtsRequest = new SingleCallByTtsRequest()
                .setCalledNumber("18661712617")
                .setTtsCode("TTS_212482029")
                .setPlayTimes(2);
        SingleCallByTtsResponse response = client.singleCallByTts(singleCallByTtsRequest);
        return response;

    }

    public static SingleCallByTtsResponse singleCallByTts2Phone(String tgtPhone) throws Exception {
        com.aliyun.dyvmsapi20170525.Client client = AliCallUtil.createClient(getAccessKeyId(), getAccessKeySecret());
        SingleCallByTtsRequest singleCallByTtsRequest = new SingleCallByTtsRequest()
                .setCalledNumber(tgtPhone)
                .setTtsCode("TTS_212482029")
                .setPlayTimes(2);
        SingleCallByTtsResponse response = client.singleCallByTts(singleCallByTtsRequest);
        return response;
    }


    private static String getAccessKeyId(){
        return "";
    }

    private static String getAccessKeySecret() {
        return "";
    }
}