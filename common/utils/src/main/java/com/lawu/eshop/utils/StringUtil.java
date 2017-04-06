package com.lawu.eshop.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {

	/**
	 * 获取25位随机数
	 * @param prefix 业务前缀
	 * @return
	 */
	public static String getRandomNum(String prefix){
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssssss");
		return prefix + df.format(new Date()) + getRandom(1, 7);
	}

	/**
	 * 获取随机数
	 * @param model
	 * @param length
	 * @return
	 */
	public static String getRandom(int model, int length) {
		String strBase = "";
		switch (model) {
		case 1:
			strBase = "0123456789";
			break;
		case 2:
			strBase = "abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ";
			break;
		case 3:
			strBase = "0123456789abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ";
			break;
		default:
			return "";
		}
		Random random = new Random();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int n = random.nextInt(strBase.length());
			String oneString = strBase.substring(n, n + 1);
			str.append(oneString);
		}
		return str.toString();
	}
}
