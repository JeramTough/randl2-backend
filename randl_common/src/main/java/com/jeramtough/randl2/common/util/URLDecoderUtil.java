package com.jeramtough.randl2.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <pre>
 * Created on 2021/1/20 11:48
 * by @author WeiBoWen
 * </pre>
 */
public class URLDecoderUtil {

    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
