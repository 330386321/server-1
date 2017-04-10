package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.ad.srv.domain.RedPacketDO;
import com.lawu.eshop.ad.srv.mapper.RedPacketDOMapper;
import com.lawu.eshop.ad.srv.service.RedPacketService;

/**
 * 紅包实现类
 * @author zhangrc
 * @date 2017/4/8
 *
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {
	
	@Autowired
	private RedPacketDOMapper redPacketDOMapper;

	@Override
	public Integer save(RedPacketParam param,Long merchantId) {
		RedPacketDO redPacketDO=new RedPacketDO();
		redPacketDO.setMerchantId(merchantId);
		if(param.getPutWayEnum().val==1){
			redPacketDO.setPackageCount(param.getPackageCount());
		}
		redPacketDO.setTotlePoint(param.getTotlePoint());
		redPacketDO.setPutWay(param.getPutWayEnum().val);
		redPacketDO.setGmtCreate(new Date());
		redPacketDO.setGmtModified(new Date());
		int id=redPacketDOMapper.insert(redPacketDO);
		if(param.getPutWayEnum().val==1){
			BigDecimal totlePoint= param.getTotlePoint();
			Integer packageCount= param.getPackageCount();
			BigDecimal point=totlePoint.divide(new BigDecimal(packageCount),2, RoundingMode.HALF_UP);
		}else{
			
		}
		return id;
	}

}
