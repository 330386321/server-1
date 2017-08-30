package com.lawu.eshop.ad.srv.thread;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolTypeEnum;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;

public class SpiltRedLuckPointThread implements Runnable {
	
	private PointPoolDOMapper pointPoolDOMapper;
	
	private AdDO adDO;
	
	private List<Double> points;
	
	private int j;
	
	public SpiltRedLuckPointThread(PointPoolDOMapper pointPoolDOMapper,AdDO adDO,List<Double> points,int j) {
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
		pointPool.setType(PointPoolTypeEnum.AD_TYPE_PACKET.val);
		pointPool.setGmtCreate(new Date());
		pointPool.setGmtModified(new Date());
		pointPool.setOrdinal(j);
		pointPool.setPoint(new BigDecimal(points.get(j)));
		pointPoolDOMapper.insert(pointPool);
	}

} 