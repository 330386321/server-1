package com.lawu.eshop.framework.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[values.length];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = escape(values[i]);
		}
		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return escape(value);
	}

	/*
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return escape(value);
	}
	*/
	
	private static String escape(String value) {
		String rtn = value;
		if (rtn  == null) {
			return rtn;
		}
		// 1-Escape
		/*
		rtn = StringEscapeUtils.escapeSql(rtn);
		rtn = StringEscapeUtils.escapeHtml(rtn);
		*/
		// 2-半角转全角
		rtn = rtn.replace("'", "＇");
		rtn = rtn.replace("<", "＜");
		rtn = rtn.replace(">", "＞");
		rtn = rtn.replace(">", "＞");
		rtn = rtn.replace("#", "＃");
		rtn = rtn.replace("*", "×");
		rtn = rtn.replace("＝", "=");
		rtn = rtn.replace("!", "！");
		rtn = rtn.replace("+", "＋");
		rtn = rtn.replace("(", "（");
		rtn = rtn.replace(")", "）");
		// 3-URL编码
		/*
		try {
			rtn = URLEncoder.encode(rtn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("编码异常", e)
		}
		*/
		return rtn;
	}
}