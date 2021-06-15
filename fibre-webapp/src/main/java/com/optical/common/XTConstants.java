package com.optical.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mary on 2021/6/8.
 */
public class XTConstants {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();


    public static final class WQ_CMD{
        public static final String AT_TEST = "OTATEST";
        public static final String AT_UARTCFG = "OTAUARTCFG";
        public static final String AT_FWINFO = "OTAFWINFO";
        public static final String AT_FWTR = "OTAFWTR";
        public static final String AT_FWFNS = "OTAFWFNS";
        public static final String AT_OP_TEST = "=?";
        public static final String AT_OP_QUERY = "?";
        public static final String AT_OP_SET = "=";
        public static final String AT_OP_EXE= "";
//        public static final String AT_END = b'\r';
        public static final String AT_RET_END = "\r\n";
        public static final String AT_RET_OK = "OK";
        public static final String AT_RET_ERROR = "ERROR";
        public static final String AT_HEAD = "AT+";
        public static final char AT_END = '\r';
//        public static final String AT_END = '\r';

    }




    public final static class DATATYPE {
        //smr1410 中继数据
        public static final String DATA = "DEVICE_DATA";
        //smr1410 报警数据
        public static final String ALARM = "SENSOR_DATA";
        //用电：data_type 固定为“REAL_DATA"/WARNING_DATA/ALARM_DATA/FAULT_DATA/IDENTIFY_DATA（电器识别推送）
        public static final String realData = "REAL_DATA";
        public static final String warningData = "WARNING_DATA";
        public static final String alarmData = "ALARM_DATA";
        public static final String faultData = "FAULT_DATA";
        public static final String identifyData = "IDENTIFY_DATA";


    }


    public final static class SensorType {
        //7	电气温度传感器
        public static final int dqwd = 7;
        //8	剩余电流传感器
        public static final int sydl = 8;
        //9	消防栓电流传感器
        public static final int xfsdl = 9;
        //10	消防栓电压传感器
        public static final int xfsdy = 10;
        //12	电量传感器
        public static final int dl = 12;
        //15	故障电弧传感器
        public static final int gzdh = 15;
        //16	继电器传感器
        public static final int jdq = 16;
        //17	功率传感器
        public static final int gl = 17;
        //18	能耗传感器
        public static final int nh = 18;
        //19	声光报警传感器
        public static final int sg = 19;
        //20	消火栓按钮传感器
        public static final int xhsan = 20;
        //21	移动监测传感器
        public static final int ydjc = 21;
        //22	无线烟雾传感器
        public static final int wxyg = 22;
        //23	电器接入传感器
        public static final int dqjr = 23;
        //24	NB烟雾传感器
        public static final int nbyw = 24;
        //25	NB温度传感器
        public static final int nbwd = 25;
        //26	NB电量传感器
        public static final int nbdl = 26;
        //38	有效电压传感器
        public static final int yxdy = 38;
        //39	有效电流传感器
        public static final int yxdl = 39;

    }


    public class DeviceStatus {
        public final static int unActive = 0;      //未激活
        public final static int offline = 1;      //离线
        public final static int online = 2;      //正常
        public final static int fault = 3;      //故障
        public final static int alarm = 4;      //报警
        public final static int forbidden = 5;      //禁用
    }

    public class DeviceOnOffLine{
        public final static boolean online = true;
        public final static boolean offline = false;
    }


    public final static class DEVICE_FAULT {
        //已处理
        public static final Integer OK = 1;
        //未处理
        public static final Integer UNDEAL = 0;
        //误报
        public static final Integer WRONGALARM = 2;

    }

}
