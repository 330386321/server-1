package com.lawu.eshop.member.api;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {
	public static void main(String[] args) {
		String s = "%25+&sd%20&p2=%E4%B8%AD%E6%96%87";
//		s = URLEncoder.encode(s);
//		System.out.println(s);
		s = URLDecoder.decode(s);
		System.out.println(s);
	}
}
