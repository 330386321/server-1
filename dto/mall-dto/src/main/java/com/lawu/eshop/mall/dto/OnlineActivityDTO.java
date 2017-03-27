package com.lawu.eshop.mall.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sunny
 * @date 2017/3/22
 */
public class OnlineActivityDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
    
    private Long merchantId;
    
    private String title;
    
    private String summary;
    
    private String content;
    
    private Byte status;
    
    private Date gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
