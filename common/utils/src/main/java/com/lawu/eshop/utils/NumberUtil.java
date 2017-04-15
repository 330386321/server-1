package com.lawu.eshop.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
public class NumberUtil {
	
	/**
	 * 数字的一般格式
	 */
	private final static String PATTERN_GENERAL = "#.00";
	
	/**
	 * 格式化BigDecimal为字符串
	 * 
	 * @param number 
	 * @param pattern 格式
	 * @return
	 * @author Sunny
	 */
	public static String format (BigDecimal number, String pattern) {
		String rtn = null;
		
		if (number == null) {
			return rtn;
		}
		
		DecimalFormat df = new DecimalFormat(pattern);
		df.setRoundingMode(RoundingMode.HALF_UP);
		rtn = df.format(number);
		
		return rtn;
	}
	
	/**
	 * 格式化BigDecimal为字符串
	 * 
	 * @param number
	 * @return
	 * @author Sunny
	 */
	public static String format (BigDecimal number) {
		String rtn = null;
		
		if (number == null) {
			return rtn;
		}
		
		DecimalFormat df = new DecimalFormat(PATTERN_GENERAL);
		df.setRoundingMode(RoundingMode.HALF_UP);
		rtn = df.format(number);
		
		return rtn;
	}
	
	/*
	public static void main(String[] args) {
		BigDecimal number = new BigDecimal("888.5554");
		System.out.println(format(number, "#.00"));
	}
	*/
}