package com.lawu.eshop.ad.srv.thread;

import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolTypeEnum;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;

public class SpiltPraisePointThread implements Runnable {
	
	private PointPoolDOMapper pointPoolDOMapper;
	
	private AdDO adDO;
	
	private double[] points;
	
	private int j;
	
	public SpiltPraisePointThread(PointPoolDOMapper pointPoolDOMapper,AdDO adDO,double[] points,int j) {
		this.pointPoolDOMapper=pointPoolDOMapper;
		this.adDO=adDO;
		this.points=points;
		this.j=j;
	}

	@Override
	public void run() {
		PointPoolDO pointPool = new PointPoolDO();
		pointPool.setAdId(adDO.getId());
		pointPool.setMerchantId(adDO.getMerchantId());
		pointPool.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
		pointPool.setType(PointPoolTypeEnum.AD_TYPE_PRAISE.val);
		pointPool.setGmtCreate(new Date());
		pointPool.setGmtModified(new Date());
		pointPool.setOrdinal(j);
		pointPool.setPoint(BigDecimal.valueOf(points[j]));
		pointPoolDOMapper.insert(pointPool);
	}

} 