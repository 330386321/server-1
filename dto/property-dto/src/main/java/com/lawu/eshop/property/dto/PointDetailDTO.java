package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.util.Date;

public class PointDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 积分标题
     */
    private String title;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 创建时间
     */
    private Date gmtCreate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}