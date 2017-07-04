package com.lawu.eshop.user.constants;

/**
 * 
 * <p>
 * Description: 用户类型枚举类
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午4:17:25
 *
 */
public enum UserTypeEnum {

    MEMBER((byte) 0x01, "M"),      //用户
    MEMCHANT((byte) 0x02, "B");     //商家
    public Byte val;

	/**
	 * 用户编号前缀
	 */
	private String prefix;

	private UserTypeEnum(Byte value, String prefix) {
		this.val = value;
		this.prefix = prefix;
	}
    
	public String getPrefix() {
		return prefix;
	}
	
    public static UserTypeEnum getEnum(Byte val) {
        UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
    
	/**
	 * 根据用户编号获取用户类型
	 * @param userNum
	 * @return
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	public static UserTypeEnum getEnum(String userNum) {
		String prefix = String.valueOf(userNum.charAt(0));
		UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum object : values) {
            if (object.getPrefix().equals(prefix)) {
                return object;
            }
        }
        return null;
    }
}
