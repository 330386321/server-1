package com.lawu.eshop.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;

public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    
    private final static Pattern emoji = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]");
    
    private StringUtil(){}

    /**
     * 获取25位随机数
     *
     * @param prefix 业务前缀
     * @return
     */
//    public static String getRandomNum(String prefix) {
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssssss");
//        return prefix + df.format(new Date()) + getRandom(1, 7);
//    }

    /**
     * 获取随机数
     *
     * @param suffix 业务后缀
     * @return
     */
//    public static String getRandomNumAppend(String suffix) {
//        DateFormat df = new SimpleDateFormat("yyMMddHHmmssssss");
//        return df.format(new Date()) + suffix;
//    }

    /**
     * 获取随机数
     *
     * @param model
     * @param length
     * @return
     */
//    public static String getRandom(int model, int length) {
//        String strBase;
//        switch (model) {
//            case 1:
//                strBase = "0123456789";
//                break;
//            case 2:
//                strBase = "abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ";
//                break;
//            case 3:
//                strBase = "0123456789abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ";
//                break;
//            default:
//                return "";
//        }
//        Random random = new Random();
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            int n = random.nextInt(strBase.length());
//            String oneString = strBase.substring(n, n + 1);
//            str.append(oneString);
//        }
//        return str.toString();
//    }

    /**
     * 隐藏用户账号
     *
     * @param account
     * @return
     */
    public static String hideUserAccount(String account) {
        if (account == null || "".equals(account.trim())) {
            return "";
        }
        if (account.length() < 11) {
            return account;
        }
        return account.substring(0, 3) + "****" + account.substring(7);
    }

    /**
     * byte 与 int 的相互转换
     *
     * @param x
     * @return
     */
    public static byte intToByte(int x) {
        return (byte) x;
    }

    /**
     * byte 与 int 的相互转换
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * json集合字符串转化成字符串
     * ["a","b","c"]转化成"a","b","c"
     *
     * @param json
     * @return
     */
    public static String getJsonListToString(String json) {
        List<Object> list = JSONArray.parseArray(json, Object.class);
        StringBuilder str = new StringBuilder();
        for (Object o : list) {
            str.append(o.toString()).append(',');
        }
        String st = str.toString();
        if(!"".equals(st) && st.indexOf(',') > 0){
            st = st.substring(0, st.lastIndexOf(','));
        }
        return st;
    }

    /**
     * json字符串转化成字符串集合
     *
     * @param jsonList
     * @return
     */
    public static List<String> getJsonListToStringList(String jsonList) {
        List<Object> list = JSONArray.parseArray(jsonList, Object.class);
        List<String> rt = new ArrayList<>();
        for (Object o : list) {
            rt.add((String) o);
        }
        return rt;
    }

    /**
     * ISO-8859-1 转换 UTF-8编码
     *
     * @param isoString
     * @return
     */
    public static String getUtf8String(String isoString){
        if(isoString == null || isoString.trim().length() == 0){
            return "";
        }
        try {
            return new String(isoString.getBytes("iso-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转换UTF-8异常：{}", e);
        }
        return isoString;
    }
    
    /**
     * 
     * @param d1
     * @param d2
     * @return
     * @date 2017年6月21日 下午4:17:46
     */
    public static int doubleCompareTo(double d1,double d2){
    	if(Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2)){
    		return 0;
    	}else if(Double.doubleToLongBits(d1) > Double.doubleToLongBits(d2)){
    		return 1;
    	}else {
    		return -1;
    	}
    }

    /**
     * 金额经度转换，去掉金额字符串后面的0
     * @param money
     * @return
     */
    public static String moneyPrecisionConvert(String money){
        if(money == null || money.equals("")){
            return "0";
        }
        String[] moneys = money.split("");
        int j = 0;
        for(int i = money.length() -1 ; i >= 0 ; i--){
            if(!moneys[i].equals("0")){
                j = i;
                break;
            }
        }
        money = money.substring(0,j+1);
        if(money.endsWith(".")){
            money = money.substring(0,money.indexOf("."));
        }
        return money;
    }
    
    /**
     * 对用户昵称进行统一处理
     * 
     * @param value
     * @return
     * @author jiangxinjun
     * @date 2017年11月3日
     */
    public static String anonymous(String value) {
        if (value == null) {
            return null;
        }
        int length = value.length();
        if (length == 0) {
            return value;
        }
        Matcher matcher = emoji.matcher(value);
        /* 
         * 初始和结尾字符的长度，可能为表情，是两个字符
         */
        int startCharLength = 1;
        int endCharLength = 1;
        while (matcher.find()) {
            if (matcher.start() == 0) {
                startCharLength = matcher.end() - matcher.start();
            }
            if (matcher.end() == length) {
                endCharLength = matcher.end() - matcher.start();
            }
        }
        StringBuilder sb = new StringBuilder();
        if (length > startCharLength) {
            sb.append(value.substring(0, startCharLength)).append("***").append(value.substring(length - endCharLength, length));
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
}
