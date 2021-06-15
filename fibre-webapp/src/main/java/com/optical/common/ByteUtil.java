package com.optical.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

/**
 * Created by mariry on 2019/7/16.
 */
public class ByteUtil {
    private static final Logger log = LoggerFactory.getLogger(ByteUtil.class);

    public static byte getByte(int data) {
        byte value = (byte) (data & 0xff);
        return value;
    }

    public static byte[] getBytes(short data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(char data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    public static byte[] getBytes(int data)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getBytesByIntAsShort(int data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((data & 0xff00) >> 8);
        bytes[1] = (byte) (data & 0xff);
        return bytes;
    }

    public static byte[] getCRCBytesByIntAsShort(int data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(long data)
    {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    public static byte[] getBytes(float data)
    {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(double data)
    {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(String data, String charsetName)
    {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] getBytes(String data)
    {
        return getBytes(data, "GBK");
    }


    //大端模式,且为无符号整型
    public static int getTiny(byte[] bytes)
    {
        return (int) (0xff & (bytes[0] )) & 0xff;
    }

    //大端模式,且为无符号整型
    public static int getShort(byte[] bytes)
    {
        return (int) ((0xff00 & bytes[0]<< 8) | (0xff & (bytes[1] ))) & 0xffff;
    }

    public static char getChar(byte[] bytes)
    {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    //大端模式
    public static int getIntHighEndian(byte[] bytes)
    {
        return (0xff000000 & bytes[0] << 24) | (0xff0000 & (bytes[1] << 16)) | (0xff00 & (bytes[2] << 8)) | (0xff & (bytes[3]));
    }

    //小端模式
    public static int getIntLowEndian(byte[] bytes) {
        return (0xff000000 & bytes[3] << 24) | (0xff0000 & (bytes[2] << 16)) | (0xff00 & (bytes[1] << 8)) | (0xff & (bytes[0]));
    }

    public static int getInt(byte value)
    {
        return (int) value & 0xff;
    }

    public static int getUnsignedInt(byte value)
    {
        return (int) value & 0x0ff;
    }

    public static long getLong(byte[] bytes)
    {
        return(0xffL & (long)bytes[0]) | (0xff00L & ((long)bytes[1] << 8)) | (0xff0000L & ((long)bytes[2] << 16)) | (0xff000000L & ((long)bytes[3] << 24))
                | (0xff00000000L & ((long)bytes[4] << 32)) | (0xff0000000000L & ((long)bytes[5] << 40)) | (0xff000000000000L & ((long)bytes[6] << 48)) | (0xff00000000000000L & ((long)bytes[7] << 56));
    }

    public static float getHighFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(getIntHighEndian(bytes));
    }

    public static float getLowFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(getIntLowEndian(bytes));
    }


    public static double getDouble(byte[] bytes)
    {
        long l = getLong(bytes);
        System.out.println(l);
        return Double.longBitsToDouble(l);
    }

    public static String getString(byte[] bytes, String charsetName)
    {
        if(bytes == null) {
            return "";
        }
        return new String(bytes, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes)
    {
        return getString(bytes, "GBK");
    }


    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] Object2ByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

}
