package com.lawu.eshop.property.param;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月6日 下午1:23:29
 *
 */
public class PointDetailSaveDataParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 积分标题
	 */
	private String title;

	/**
	 * 积分编号
	 */
	private String pointNum;

	/**
	 * 用户编号
	 */
	private String userNum;

	/**
	 * 积分类型
	 */
	private Byte pointType;

	/**
	 * 积分
	 */
	private Integer point;

	/**
	 * 备注
	 */
	private String remark;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPointNum() {
		return pointNum;
	}

	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public Byte getPointType() {
		return pointType;
	}

	public void setPointType(Byte pointType) {
		this.pointType = pointType;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}