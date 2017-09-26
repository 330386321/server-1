package com.lawu.eshop.order.srv.utils.express.kdniao.constants;

/**
 * 返回编码枚举
 * 
 * @author jiangxinjun
 * @date 2017年9月5日
 */
public enum CodeEnum {
	
	SUCCESS(100, "成功"),
	
	MISSING_REQUIRED_PARAMETERS(101, "缺少必要参数"),
	
	CHECK_PROBLEM(102, "校验问题"),
	
	FORMAT_PROBLEM(103, "格式问题"),
	
	USER_PROBLEM(104, "用户问题"),
	
	OTHER_ERRORS(105, "其他错误"),
	
	REQUESTDATA_FORMAT_IS_WRONG(401, "RequestData格式有误"),
	
	NO_EXPRESS_SINGLE_NUMBER(402, "缺少快递单号"),
	
	SPECIAL_CHARACTERS_FOR_EXPRESS_NUMBER(403, "快递单号有特殊字符"),
	
	EXPRESS_SINGLE_NUMBER_LENGTH_DOES_NOT_MATCH(404, "快递单号长度不符"),
	
	EXCEEDED_QUERY_LIMIT(405, "超出查询次数限制(日查询次数<=3万)");
	
	private Integer value;
	
	private String label;
	
	CodeEnum(int value, String label){
		this.value = value;
		this.label = label;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
	
	public static CodeEnum getEnum(Integer value){
		for (CodeEnum item : CodeEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
