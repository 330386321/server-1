package com.lawu.eshop.ad.srv.controller;

public class Test {
	public static void main(String[] args) {
		String str="100/101";
		
		String[] st=str.split("/");
		String path=st[st.length-1];
		String[] stlast=path.split(",");
		for (String string : stlast) {
			System.out.println(string);
		}
	}

}
