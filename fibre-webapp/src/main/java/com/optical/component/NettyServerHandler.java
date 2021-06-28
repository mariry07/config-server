package com.optical.component;

import com.alibaba.fastjson.JSON;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsResponse;
import com.optical.Service.DeviceStatusLogService;
import com.optical.Service.impl.ConfigServiceImpl;
import com.optical.Service.impl.EventServiceImpl;
import com.optical.bean.*;
import com.optical.common.*;
import com.optical.mapper.TerminalAssignMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.EndpointAdapter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import static com.optical.component.StaticMapRunner.otaProcessMap;
import static com.optical.component.StaticMapRunner.staticMap;
import static com.optical.component.StaticMapRunner.vendorPushMap;

/**
 *
 * netty服务端处理器
 **/
@Slf4j
@Mapper
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    private String OTA_FILE_PATH = "/ota/SG-KL2AIO01-11.0.0.0-20210610V1.01.bin";
//    private String OTA_FILE_PATH = "D:\\SG-KL2AIO01-11.0.0.0-20210610V1.01.bin";
    private String sperateStr = "\n}";

    //ota下发每次下发大小
    private Integer PACK_SIZE = 1000;


    private final BlockingQueue<String> list;
    //队列大小，先这么丑陋的写一下
    private final int maxSize;
    public NettyServerHandler(BlockingQueue list, int maxSize) {
        this.list = list;
        this.maxSize = maxSize;
    }
    @Autowired
    private MyWebsocketServer websocketServer;

    private ConfigServiceImpl configServiceImpl;
    private EventServiceImpl eventServiceImpl;
    private DeviceStatusLogService deviceStatusLogService;
//    private WebSocketService webSocketService;


    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //TODO: 完善hashMap
        //往channel map中添加channel信息
        NettyServer.getMap().put(getIPString(ctx), ctx.channel());
        //往channel 的messageMap 中放入currentString,并初始化为
        NettyServer.getMessageMap().put(getIPString(ctx), "");


//        TODO: 初始化一个针对当前channel的consumer，只消费当前channel数据；初始化时带入当前map的key值
//        启动消费者线程
//        MsgConsumer c = new MsgConsumer(list);
//        Thread consumer = new Thread(c);
//        consumer.setName("消费者"+ getIPString(ctx));
//        consumer.start();

        log.info("Channel active......");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String rtnStr = "";
        String devicePhone = "";
        String tmpName = "";
        String atCmd = "";

        //按照16进制发送时，在ServiceChannelInitializer类里需取消自动按照UTF-8来decode，以防止出现乱码
        ByteBuf buf = (ByteBuf) msg;
        byte[] receiveMsgBytes = new byte[buf.readableBytes()];
        buf.readBytes(receiveMsgBytes);
        String hexStr = EncodeUtil.binary2Hex(receiveMsgBytes);
        String jsonStr = ByteUtil.getString(receiveMsgBytes, "utf-8") + "}";

//        log.info("服务器收到消息: " + hexStr);
//        log.info("jsonStr: " + jsonStr);

//        String logInfo = formatCurrentTimeStr() + "服务器收到消息: " + jsonStr;
        WebSocketMsg push = new WebSocketMsg(2, 0, "");
//        websocketServer.sendInfo(JSON.toJSONString(push), null);

//        if(jsonStr.contains("AT*")){
//            ctx.write("wrong json with AT command, drop!");
//            ctx.flush();
//            return;
//        }
//        String sperateStr = "\r\n}";

        if(jsonStr.equals("}") || jsonStr.equals(sperateStr)) {
//            if(jsonStr.contains("ok")) {
                //根据otaProcessStage 判断下一步操作
                log.info("here in jsonStr = nothing");

            //根据otaProcessMap 的stage 判断下一步操作
            //ota stage ：test  -  test op - file info - transfer  -  transfer end  -  end ota
            // 数字：      0    -   1      -   2       -   30~31   -       4        -      5
            Integer curStage = Integer.parseInt(otaProcessMap.get("stage"));
            if(curStage == 0) {
                log.info("####### current stage: 0, will deliver at_test, at_op_set");
                curStage = 1;
                otaProcessMap.put("stage", curStage.toString());

                //测试时的临时解决方案：
                ChannelHandlerContext tmpCtx = NettyServer.getCtxMap().get(otaProcessMap.get("deviceCode"));
                if(tmpCtx != null) {
                    log.info("stage 0->1, use tmpCtx");
                    String t1 = formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_SET, null, null, null);
                    tmpCtx.write(t1);
                    //string 转byte
//                    byte[] llll = ByteUtil.getBytes(t1, "utf-8");
//                    tmpCtx.write(Unpooled.copiedBuffer(llll));
                    tmpCtx.flush();
                }else{
                    log.info("stage 0->1, tmpCtx is null, use ctx instead");
                    String t1 = formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_SET, null, null, null);
                    ctx.write(t1);
                    ctx.flush();
                }

//                ctx.write(formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_SET, null, null, null));
//                ctx.flush();
                return;

            }else if(curStage == 1) {
                log.info("####### current stage: 1, will deliver file crc, file size, pack size");
                //下发文件信息
                curStage = 2;
                otaProcessMap.put("stage", curStage.toString());

                File file = new File(OTA_FILE_PATH);

                Long fileSize = file.length();
                String crc32 = getCRC32Str(file);
                String packSize = PACK_SIZE.toString();

                //测试时的临时解决方案：
                ChannelHandlerContext tmpCtx = NettyServer.getCtxMap().get(otaProcessMap.get("deviceCode"));
                if(tmpCtx != null) {
                        log.info("stage 1->2, use tmpCtx");
                        String t = formatCmd(XTConstants.WQ_CMD.AT_FWINFO, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(fileSize), crc32, packSize);
                        log.info(t);
                        tmpCtx.write(t);
                        tmpCtx.flush();
                    }else {
                        log.info("stage 1->2, use tmpCtx");
                        log.info("### ota 下发：");
                        String t = formatCmd(XTConstants.WQ_CMD.AT_FWINFO, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(fileSize), crc32, packSize);
                        log.info(t);
                        ctx.write(t);
                        ctx.flush();
                }

//                ctx.write(formatCmd(XTConstants.WQ_CMD.AT_FWINFO, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(fileSize), crc32, packSize));
//                ctx.flush();
                return;

            }else if(curStage >= 2 && curStage < 4) {
                log.info("####### current stage: 2 , 3x, will deliver file");
                // 传输升级包
                //TODO: 根据本次下发数据量大小是否 <1000 判断 是否跳转升级stage
                Long i = Long.parseLong(otaProcessMap.get("count"));
                FileInputStream fis = new FileInputStream(OTA_FILE_PATH);

                //指定位置
                fis.skip(i * PACK_SIZE);
                //指定长度
                byte[] filebuf = new byte[PACK_SIZE];
                byte[] infoBuf;
                int len = fis.read(filebuf);
                infoBuf = new byte[len];
                System.arraycopy(filebuf, 0, infoBuf, 0, len);
                String hexString = EncodeUtil.binary2Hex(infoBuf);
//                String thisCrc = getByteCRC32Str(infoBuf, len);
                String thisCrc = getByteCRC32Str(hexString);
                i ++;
                otaProcessMap.put("count", i.toString());

                //若是最后一个数据包，则下一次将需要跳转stage，则将stage置为4
                if(len < PACK_SIZE) {
                    otaProcessMap.put("stage", "4");
                    //计数器复位
                    otaProcessMap.put("count", "0");
                }

                //测试时的临时解决方案：
                ChannelHandlerContext tmpCtx = NettyServer.getCtxMap().get(otaProcessMap.get("deviceCode"));
                if(tmpCtx != null) {
                    log.info("2->3 4, use tmpCtx");
                    String t2 = formatCmd(XTConstants.WQ_CMD.AT_FWTR, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(i - 1), thisCrc, hexString);
//                    log.info(t2);
                    tmpCtx.write(t2);
                    tmpCtx.flush();
                }else{
                    log.info("2->3 4, use Ctx instead");
                    String t2 = formatCmd(XTConstants.WQ_CMD.AT_FWTR, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(i), thisCrc, hexString);
//                    log.info(t2);
                    ctx.write(t2);
                    ctx.flush();
                }

//                ctx.write(formatCmd(XTConstants.WQ_CMD.AT_FWTR, XTConstants.WQ_CMD.AT_OP_SET, Long.toString(i), thisCrc, hexString));
//                ctx.flush();
                return;
            }else if(curStage == 4) {
                log.info("####### current stage: 4 , will transfer end msg");
                //测试时的临时解决方案：
                ChannelHandlerContext tmpCtx = NettyServer.getCtxMap().get(otaProcessMap.get("deviceCode"));
                if(tmpCtx != null) {
                    log.info("4->5, use tmpCtx");
                    String t4 = formatCmd(XTConstants.WQ_CMD.AT_FWFNS, XTConstants.WQ_CMD.AT_OP_EXE, null, null, null);
                    tmpCtx.write(t4);
                    tmpCtx.flush();
                }else {
                    log.info("4->5, use Ctx instead");
                    String t4 = formatCmd(XTConstants.WQ_CMD.AT_FWFNS, XTConstants.WQ_CMD.AT_OP_EXE, null, null, null);
                    ctx.write(t4);
                    ctx.flush();
                }


                //下发 end 指令
//                ctx.write(formatCmd(XTConstants.WQ_CMD.AT_FWFNS, XTConstants.WQ_CMD.AT_OP_EXE, null, null, null));
//                ctx.flush();
                curStage = 5;
                otaProcessMap.put("stage", curStage.toString());
                return;
            }else {
                log.info("unknown curStage: " + curStage);
                return;
            }
        }


        //判断是否是心跳信息
        if(jsonStr.contains("$$$")) {
            //TODO：处理心跳包逻辑
            if(jsonStr.contains("868922053128126")) {
                //正泰临时补丁
                staticMap.put("xtd20211700015", System.currentTimeMillis());
            }else if(jsonStr.contains("868922052985955")) {
                staticMap.put("xtd20211700009", System.currentTimeMillis());
            }
            log.info("服务器收到消息: " + hexStr);
            log.info("jsonStr: " + jsonStr);
            push.setShowWindow(0);
            push.setData(jsonStr);
            websocketServer.sendInfo(JSON.toJSONString(push), null);
            rtnStr = "server-" + jsonStr;

        }else{
            //处理业务时间数据包逻辑
            try{
                //判断最后一位是 花括号} 0x7d.若不是,则说明是问题字符做折中处理串，
                if(jsonStr.charAt(jsonStr.length() - 1) != 0x7d) {
                    //找到最后一个逗号，将它替换成"}",并且只保留之前的数据
                    jsonStr = jsonStr.substring(0, jsonStr.lastIndexOf(",") - 1);
                    jsonStr += "}";
                }
                RadarEventBean reb = JSON.parseObject(jsonStr, RadarEventBean.class);

                if(reb.getType() == 0) {
                    log.info("服务器收到消息: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    push.setShowWindow(0);
                    push.setData(jsonStr);
                    websocketServer.sendInfo(JSON.toJSONString(push), null);

                    //注册事件
                    ConfigInfo ci = handleRegisterEvent(reb);
                    //在NettyServer deviceCodeMap 中添加deviceCode与ctx的映射关系
                    //https://blog.csdn.net/weixin_43881309/article/details/109649842
                    if(ci.getResult() == ConfigInfo.OP_SUCCESS) {
                        NettyServer.getMessageMap().put(getIPString(ctx), ci.getDevice_code());
                        NettyServer.getCtxMap().put(ci.getDevice_code(), ctx);
                        log.info("NettyServer.getCtxMap().put(ci.getDevice_code(): " + ci.getDevice_code() + ")");
                    }
                    ci.setTimestamp((int) (System.currentTimeMillis() / 1000));
                    rtnStr = JSON.toJSONString(ci);

                }else if(reb.getType() == 7) {
                    log.info("服务器收到消息: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    Map rtnMap = new HashMap();
                    rtnMap.put("type", 7);
                    rtnMap.put("msg", "got your heart beat msg");
//                    push.setShowWindow(0);
//                    push.setData(jsonStr);
//                    websocketServer.sendInfo(JSON.toJSONString(push), null);

                    rtnStr = JSON.toJSONString(rtnMap);
                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());
                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);
                    if(!StringUtils.isEmpty(vendorPushMap.get(reb.getDevice_code()))) {
                        //有推送地址，需进行推送
                        Map map = new HashMap();
                        map.put("type", 7);
                        map.put("device_code", reb.getDevice_code());
                        String param = JSON.toJSONString(map);
                        log.info("### param: " + param);
                        try{
                            String jsionResult= HttpUtil.post("http://" + vendorPushMap.get(reb.getDevice_code()) + "/xitang/open/device/log/XTDYT3", param);
                            log.info("### push heartbeat rtn: " + jsionResult);
                        }catch (Exception e) {
                            log.error("HeartBeatJob error! e={}", e);
                        }
                    }

                }if(reb.getType() == 9) {
                    log.info("jsonStr: " + jsonStr);
                    //todo: 测试期之后要放开
//                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);

                    //ota确认事件
                    //开始进入ota
                    //ota stage ：test  -  test op - file info - transfer  -  transfer end  -  end ota
                    // 数字：      0    -   1      -   2       -   30~31   -       4        -      5

                    otaProcessMap.put("stage", "0");
                    //升级包部分传送的数量 在进入 transfer stage 之前，都是0
                    otaProcessMap.put("count", "0");
                    //为了保存当前升级设备deviceCode，一遍保持ctx
                    otaProcessMap.put("deviceCode", reb.getDevice_code());

                    //TODO： 临时测试注释掉，正常后放开，组装test cmd 字符串，并下发
//                    ctx.write(formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_TEST, null, null, null));
//                    ctx.flush();

                    //TODO： 测试临时方案 稳定后待删除
                    ChannelHandlerContext tmpCtx = NettyServer.getCtxMap().get(reb.getDevice_code());
                    if(tmpCtx != null) {
                        log.info("use tmpCtx");
                        //string 转byte
//                    byte[] llll = ByteUtil.getBytes(t1, "utf-8");
                        String t1 = formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_TEST, null, null, null);
//                        tmpCtx.write(Unpooled.copiedBuffer(ByteUtil.getBytes(t1, "utf-8")));
                        tmpCtx.write(t1);
                        tmpCtx.flush();
                    }else{
                        log.info("tmpCtx is null, use current ctx instead");
                        //要是没数据，还要用这个
                        String t1 = formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_TEST, null, null, null);
                        ctx.write(t1);
//                        ctx.write(formatCmd(XTConstants.WQ_CMD.AT_TEST, XTConstants.WQ_CMD.AT_OP_TEST, null, null, null));
                        ctx.flush();
                    }


                    return;

                }else if(reb.getType() == 1) {
                    log.info("服务器收到消息1: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    // 报警事件

                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());

                    NettyServer.getMessageMap().put(getIPString(ctx), reb.getDevice_code());
                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);
                    log.info("1 NettyServer.getCtxMap().get(ci.getDevice_code(): " +
                    JSON.toJSONString(NettyServer.getCtxMap().get(reb.getDevice_code())));

                    // 1. 报警事件落库
                    rtnStr = handleAlarmEvent(reb);

                    //推送微信小程序
                    push.setShowWindow(1);
                    push.setMsgType(1);
                    push.setData(reb.getDevice_code());
                    websocketServer.sendInfo(JSON.toJSONString(push), null);
                    push.setMsgType(0);

                    //3. 检查是否需要推送第三方
                    if(!StringUtils.isEmpty(vendorPushMap.get(reb.getDevice_code()))) {
                        reb.setUnique_id(System.currentTimeMillis());
                        String param = JSON.toJSONString(reb);
                        log.info("### param: " + param);
                        try{
                            String jsionResult= HttpUtil.post("http://" + vendorPushMap.get(reb.getDevice_code()) + "/xitang/open/device/log/XTDYT3",
                                    param);
                            log.info("### fall event: " + jsionResult);
                        }catch (Exception e) {
                            log.error("FallEventJob error! e={}", e);
                        }
                    }

                    // 2. TODO：报警丢队列，等待消费打电话。
                    String phone = getAlarmPhone(reb.getDevice_code());
                    if(!StringUtils.isEmpty(phone)) {
                        SingleCallByTtsResponse response = AliCallUtil.singleCallByTts2Phone(phone);
                        log.info("here called tgtPhone from database " + phone + ", response: " + JSON.toJSONString(response));
                    }
                }else if(reb.getType() == 2){
                    log.info("服务器收到消息2: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    NettyServer.getMessageMap().put(getIPString(ctx), reb.getDevice_code());
                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);
                    log.info("2 NettyServer.getCtxMap().get(ci.getDevice_code(): " +
                            JSON.toJSONString(NettyServer.getCtxMap().get(reb.getDevice_code())));
                    //消警事件
                    rtnStr = handleDisalarmEvent(reb);

                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());

                    //推送第三方
                    //3. 检查是否需要推送第三方
                    if(!StringUtils.isEmpty(vendorPushMap.get(reb.getDevice_code()))) {
                        reb.setUnique_id(System.currentTimeMillis());
                        String param = JSON.toJSONString(reb);
                        log.info("### param: " + param);
                        try{
                            String jsionResult= HttpUtil.post("http://" + vendorPushMap.get(reb.getDevice_code()) + "/xitang/open/device/log/XTDYT3",
                                    param);
                            log.info("### fall dismiss: " + jsionResult);
                        }catch (Exception e) {
                            log.error("FallEventJob error! e={}", e);
                        }
                    }
                    push.setShowWindow(1);
                    push.setMsgType(2);
                    push.setData(reb.getDevice_code());
                    websocketServer.sendInfo(JSON.toJSONString(push), null);
                    push.setMsgType(0);

                }else if(reb.getType() == 3) {
                    log.info("服务器收到消息3: " + hexStr);
                    log.info("jsonStr: " + jsonStr);

//                    push.setShowWindow(0);
//                    push.setData(jsonStr);
//                    websocketServer.sendInfo(JSON.toJSONString(push), null);

                    NettyServer.getMessageMap().put(getIPString(ctx), reb.getDevice_code());
                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);
                    log.info("3 NettyServer.getCtxMap().get(ci.getDevice_code(): " +
                            JSON.toJSONString(NettyServer.getCtxMap().get(reb.getDevice_code())));
                    // 雷达高度、阈值等参数设置与获取
                    rtnStr = handleConfigSettingEvent(reb);

                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());


                }else if(reb.getType() == 4) {
                    //人员有无发现事件
                    log.info("服务器收到消息4: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    NettyServer.getMessageMap().put(getIPString(ctx), reb.getDevice_code());
                    NettyServer.getCtxMap().put(reb.getDevice_code(), ctx);

                    //根据设备位置，配置人员所在位置
                    rtnStr = handleHumanDetectEvent(reb);

                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());

                    //3. 检查是否需要推送第三方
                    if(!StringUtils.isEmpty(vendorPushMap.get(reb.getDevice_code()))) {
                        reb.setUnique_id(System.currentTimeMillis());
                        String param = JSON.toJSONString(reb);
                        log.info("### param: " + param);
                        try{
                            String jsionResult= HttpUtil.post("http://" + vendorPushMap.get(reb.getDevice_code()) + "/xitang/open/device/log/XTDYT3",
                                    param);
                            log.info("### human detect event: " + jsionResult);
                        }catch (Exception e) {
                            log.error("FallEventJob error! e={}", e);
                        }
                    }
                    push.setShowWindow(1);
                    push.setMsgType(4);
                    push.setData(reb.getDevice_code());
                    websocketServer.sendInfo(JSON.toJSONString(push), null);
                    push.setMsgType(0);

                }else if(reb.getType() == 5) {
                    Date date=new Date();
                    DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    tmpName = reb.getDevice_code() + "_" +  format.format(date) +".dat";

                    //TODO: 点云信息，先存，再解析
                    byte[] bufArr = EncodeUtil.decryptBASE64(reb.getPointcloud());
//                    log.info("LyRaPointCloud: \n" + EncodeUtil.binary2Hex(bufArr));
//                    pointcloudLog.info("&&PointCloud: \n" + EncodeUtil.binary2Hex(bufArr));

                    FileOutputStream fout = new FileOutputStream(tmpName, true);
                    DataOutputStream toFile=null;
                    toFile=new DataOutputStream(fout);
                    toFile.write(bufArr);
                    toFile.close();

                }else if(reb.getType() == 6) {
                    log.info("服务器收到消息6: " + hexStr);
                    log.info("jsonStr: " + jsonStr);
                    Date date=new Date();
                    DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    String srcName = reb.getDevice_code() + "_" +  format.format(date) + ".dat";
                    String tgtName = reb.getDevice_code() + "_" +  format.format(date) + "_"
                            + reb.getEvent_end_frame() +".dat";

                    File file = file=new File(srcName); //指定文件名及路径

                    if (file.renameTo(new File(tgtName))) {
                        log.info(srcName + " 修改为 " + tgtName);
                    }
                    else{
                        log.info(srcName + " 修改为 " + tgtName + " 失败!");
                    }
                    //刷新staticMap 中的设备最近一次信息上报事件
                    staticMap.put(reb.getDevice_code(), System.currentTimeMillis());
                }else if(reb.getType() == 8) {

//                    push.setShowWindow(0);
//                    push.setData(jsonStr);
//                    websocketServer.sendInfo(JSON.toJSONString(push), null);
//                    log.info("服务器收到消息: " + hexStr);
                    log.info("###### temperature: " + reb.getDevice_code() + ", " + jsonStr);
                    deviceStatusLogService = SpringBeanUtil.getBean(DeviceStatusLogService.class);
                    deviceStatusLogService.addDeviceStatusLog(reb);
                }
            }catch (Exception e) {
                log.error("error! e: {}", e);
                log.error("error info: " + jsonStr);
                ctx.write("wrong json format");
                ctx.flush();
            }
        }
        ctx.write(rtnStr);
        ctx.flush();

    }

    private String getAlarmPhone(String deviceCode) {
        String rtn = "";
        try{
            configServiceImpl = SpringBeanUtil.getBean(ConfigServiceImpl.class);
            rtn = configServiceImpl.getDevicePhone(deviceCode);
            return rtn;

        }catch (Exception e){
            log.error("getAlarmPhone Error! e={}", e);
            return rtn;

        }
    }
    private ConfigInfo handleRegisterEvent(RadarEventBean reb) {
        ConfigInfo ci = new ConfigInfo(ConfigInfo.OP_SUCCESS, ConfigInfo.OpMsg.SUCCESS);
        try{
            configServiceImpl = SpringBeanUtil.getBean(ConfigServiceImpl.class);

            ci = configServiceImpl.getConfigService(reb.getImei());
            ci.setType(reb.getType());

        }catch (Exception e) {
            log.error("Error! handleRegisterEvent(). reb: {}", reb);
            ci.setResult(ConfigInfo.OP_FAILED);
            ci.setMessage(ConfigInfo.OpMsg.FAILED);
        }
        return ci;
    }

    private String handleAlarmEvent(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);
        try{
            eventServiceImpl = SpringBeanUtil.getBean(EventServiceImpl.class);
            eventServiceImpl.fallEventDetected(reb);
            if(reb.getUnique_id() != null) {
                op.setUnique_id(reb.getUnique_id());
            }
            if(!StringUtils.isEmpty(reb.getType())) {
                op.setType(reb.getType());
            }
        }catch (Exception e) {
            log.error("Error! handleAlarmEvent(). reb: {}", reb);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
        }

        return JSON.toJSONString(op);
    }

    /*
        收到信息type=3，获取设备配置信息
     */
    private String handleConfigSettingEvent(RadarEventBean reb) {

        Map rtnMap = new HashMap<>();
        rtnMap.put("result", 1);
        rtnMap.put("msg", "success");

        if(reb.getUnique_id() != null) {
            rtnMap.put("unique_id", reb.getUnique_id());
        }
        if(!StringUtils.isEmpty(reb.getType())) {
            rtnMap.put("type", reb.getType());
        }

        try{
            configServiceImpl = SpringBeanUtil.getBean(ConfigServiceImpl.class);
            TerminalAssign ta = configServiceImpl.getDeviceConfigInfo(reb);
            if(reb.getUnique_id() != null) {
                rtnMap.put("unique_id", reb.getUnique_id());
            }
            if(!StringUtils.isEmpty(reb.getType())) {
                rtnMap.put("type", reb.getType());
            }
            if(ta != null) {
                rtnMap.put("leave_ground_duration", Math.round(ta.getLeaveGroundDuration() * 100));
                rtnMap.put("on_ground_duration", Math.round(ta.getOnGroundDuration() * 100));
                rtnMap.put("confidence_threshold", Math.round(ta.getConfidenceThreshold() * 100));
                // 注：因嵌入式上是er，故下面的senser必须是er，与嵌入式一致。
                rtnMap.put("senser_fix_height", Math.round(ta.getSensorFixHeight() * 100));
                rtnMap.put("cloud_upload_enable", ta.getCloudUploadEnable());
                rtnMap.put("status_report_enable", ta.getStatusReportEnable());
            }

        }catch (Exception e) {
            log.error("Error! handleConfigSettingEvent(). reb: {}", reb);
            rtnMap.put("result", 0);
            rtnMap.put("msg", "failed");
        }

        return JSON.toJSONString(rtnMap);
    }

    private String handleHumanDetectEvent(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);

        try{
            eventServiceImpl = SpringBeanUtil.getBean(EventServiceImpl.class);
            eventServiceImpl.humanDetectEvent(reb);
            if(reb.getUnique_id() != null) {
                op.setUnique_id(reb.getUnique_id());
            }
            if(!StringUtils.isEmpty(reb.getType())) {
                op.setType(reb.getType());
            }

        }catch (Exception e) {
            log.error("Error! handleDisalarmEvent(). reb: {}", reb);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
        }
        return JSON.toJSONString(op);
    }

    private String handleDisalarmEvent(RadarEventBean reb) {
        OpResult op = new OpResult(OpResult.OP_SUCCESS, OpResult.OpMsg.OP_SUCCESS);

        try{
            eventServiceImpl = SpringBeanUtil.getBean(EventServiceImpl.class);
            eventServiceImpl.fallEventDismissed(reb);
            if(reb.getUnique_id() != null) {
                op.setUnique_id(reb.getUnique_id());
            }
            if(!StringUtils.isEmpty(reb.getType())) {
                op.setType(reb.getType());
            }

        }catch (Exception e) {
            log.error("Error! handleDisalarmEvent(). reb: {}", reb);
            op.setResult(OpResult.OP_FAILED);
            op.setMsg(OpResult.OpMsg.OP_FAIL);
        }
        return JSON.toJSONString(op);
    }


    private String getDevicePhone(String deviceCode) {
        String rtn = "";
        try{
            configServiceImpl = SpringBeanUtil.getBean(ConfigServiceImpl.class);
            rtn = configServiceImpl.getDevicePhone(deviceCode);

        }catch (Exception e) {
            log.error("Error! getDevicePhone(). deviceCode: " + deviceCode);
            return null;
        }
        return rtn;
    }

    /**
     * 心跳机制  用户事件触发
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent e = (IdleStateEvent) evt;

            //检测 是否 这段时间没有和服务器联系
            if (e.state() == IdleState.ALL_IDLE)
            {
                //TODO: 检测心跳
//                checkIdle(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//      删除Channel Map中的失效Client
        NettyServer.getMap().remove(getIPString(ctx));
        String deviceCode = NettyServer.getMessageMap().get(getIPString(ctx));
        NettyServer.getCtxMap().remove(deviceCode);
        NettyServer.getMessageMap().remove(getIPString(ctx));
        ctx.close();
        //TODO: 同步杀掉consumer进程

    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

        //TODO: 同步杀掉进程
    }


    public static String getIPString(ChannelHandlerContext ctx){
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }

    private String produceMsg(String msg) {
        synchronized (list) {
            if(list.size() == maxSize) {
                log.error("缓冲区已满！新消息丢弃！ new msg = {}", msg);
            }else {
                list.add(msg);
                log.info("here queue size: " + list.size());
            }
        }
        return "";
    }
    private void handleSingleInfo(String info) {
        produceMsg(info);
        log.info("message received: " + info);
        return;
    }

    private void getDatFile(byte[] infoStr, String channelMapKey) throws IOException{
        byte[] infoBuf;
        String hexString = "";
        String unionStr = "";

        String[] infoArr;
        String currentInfoStr = NettyServer.getMessageMap().get(channelMapKey);
        try{
            hexString = EncodeUtil.binary2Hex(infoStr);
            //待处理数据包infoBuf
            //1. 寻找开头帧0x4c795261
            //需要考虑lyra头被拆分到上下两个包，在每个单独包中不完整，但聚合之后变完整的情况，
            // 所以需要与currentInfoStr合并之后统一做一次拆分
            unionStr = currentInfoStr + hexString;
            if(!unionStr.contains("4c795261")) {
                //当前不包含帧开头字节符，应全部追加到currentInfoStr中
                //检查拼接后的currentInfoStr是否包含lary头
                currentInfoStr = unionStr;
                NettyServer.getMessageMap().put(channelMapKey, currentInfoStr);
            }else {
                //找到第一个lyra头，并舍弃之前的部分
                Integer firstLyra = unionStr.indexOf("4c795261");
                if(firstLyra == -1 ){
                    // do nothing
                }
                if(firstLyra != 0) {
                    //开头不是lyra,则第一个lyra之前的部分全部舍弃掉
                    unionStr = unionStr.substring(firstLyra);
                }
                infoArr = unionStr.split("4c795261");
                //包含数据帧开头至少一个，则第一个数组应与currentInfoStr合并后发送
                //中间数组单独发送
                //最后一个数组元素需判断是否是一个完整消息(根据长度)：
                //若是，则currentInfoStr清空；若不是，则currentInfoStr替换为最后一个数组元素
                for(int i = 0; i < infoArr.length; i++) {
//                                log.info("i = " + i);

                    if(i < infoArr.length -1 ) {
                        //去掉分隔符在开头或结尾导致split来的空数组
                        if(StringUtils.isEmpty(infoArr[i])) {
                            log.info("empty str, continue");
                            continue;
                        }
                        //TODO: 处理数据，或者扔队列
                        handleSingleInfo(infoArr[i]);
                    }else {
                        //最后一个数据包，暂时不做处理;因为可以保证是lyra开头的，所以追加上lyra头之后，和下一次接收到的数据拼装到一起处理
                        currentInfoStr = "4c795261" + infoArr[i];
                        NettyServer.getMessageMap().put(channelMapKey, currentInfoStr);
                        log.info("currentStr in map: " + NettyServer.getMessageMap().get(channelMapKey));
                    }
                }
            }
        }catch (Exception e) {
            log.info("Error! e = {}", e);
            return;
        }
    }


    private String formatCurrentTimeStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(new Date()) + "-[INFO]: ";
    }


    private String formatCmd(String cmd, String op, String query1, String query2, String query3) {
//  private byte[] formatCmd(String cmd, String op, String query1, String query2, String query3) {
        String at_cmd = "";

        StringBuffer sb = new StringBuffer();
        sb.append(XTConstants.WQ_CMD.AT_HEAD).append(cmd).append(op);
        if(!StringUtils.isEmpty(query1)) {
            sb.append(query1);
        }
        if(!StringUtils.isEmpty(query2)) {
            sb.append(",").append(query2);
        }
        if(!StringUtils.isEmpty(query3)) {
            sb.append(",").append(query3);
        }
        sb.append(XTConstants.WQ_CMD.AT_END);

//        byte[] deliverInfo = ByteUtil.getBytes(sb.toString(), "utf-8");
//        return deliverInfo;
        return sb.toString();
    }

    //获取crc32结果 字符串
    public static String getCRC32Str(File file) throws IOException {
        CRC32 crc32 = new CRC32();
        FileInputStream fileinputstream = new FileInputStream(file);
        CheckedInputStream checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
        while (checkedinputstream.read() != -1) {
        }
        checkedinputstream.close();
        return Long.toString(crc32.getValue());
    }

    //获取crc32结果 字符串
    public static String getByteCRC32Str(String str) throws IOException {
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
//        crc32.update(buf);
        return Long.toString(crc32.getValue());
    }

//    public static String getByteCRC32Str(byte[] buf, int len) throws IOException {
//        CRC32 crc32 = new CRC32();
//        String ttt = "123456";
//        crc32.update(ttt.getBytes());
////        crc32.update(buf);
//        return Long.toString(crc32.getValue());
//    }

}
