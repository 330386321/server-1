package com.lawu.eshop.statistics.dto;

import java.util.Date;

/**
 * @author zhangrc
 * @date 2017/9/8.
 */
public class ReportNewDateDTO {


    private Date gmtReport;
    

    public ReportNewDateDTO(Date gmtReport) {
		super();
		this.gmtReport = gmtReport;
	}

	public Date getGmtReport() {
        return gmtReport;
    }

    public void setGmtReport(Date gmtReport) {
        this.gmtReport = gmtReport;
    }
}
