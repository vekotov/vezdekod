package ru.vekotov.gateway;

public class Hashcode {
    //package com.java2s;
    //License from project: Apache License

    public static int crc16(final byte[] bytes) {
        int crc = 0xFFFF;

        for (int j = 0; j < bytes.length; j++) {
            crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
            crc ^= (bytes[j] & 0xff);// byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }/* www.java2s.com */
        crc &= 0xffff;
        return crc;
    }
}
