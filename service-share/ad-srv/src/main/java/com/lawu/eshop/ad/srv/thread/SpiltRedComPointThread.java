package com.lawu.eshop.ad.srv.thread;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolTypeEnum;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;

public class SpiltRedComPointThread implements Runnable {
	
	private PointPoolDOMapper pointPoolDOMapper;
	
	private AdDO adDO;
	
	private BigDecimal point;
	
	private int j;
	
	private BigDecimal usedMoney;
	
	public SpiltRedComPointThread(PointPoolDOMapper pointPoolDOMapper,AdDO adDO,BigDecimal point,int j,BigDecimal usedMoney) {
		this.pointPoolDOMapper=pointPoolDOMapper;
		this.adDO=adDO;
		this.point=point;
		this.j=j;
		this.usedMoney=usedMoney;
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
		if (j + 1 == adDO.getAdCount()) {
			pointPool.setPoint(adDO.getTotalPoint().subtract(usedMoney));
		} else {
			pointPool.setPoint(point);
		}
		pointPoolDOMapper.insert(pointPool);
	}

} 