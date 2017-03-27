package com.lawu.eshop.utils;

/**
 * 
 * <p>
 * Description: 数据转换工具类
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午8:03:20
 *
 */
public class DataTransUtil {

	/**
	 * Integer转byte
	 * @param i
	 * @return
	 */
	public static byte intToByte(Integer i) {
        int x = i;
        return (byte) x;
    }

	/**
	 * byte转Integer
	 * @param b
	 * @return
	 */
    public static Integer byteToInt(byte b) {
        return b & 0xFF;
    }
    
    
    
    
}
