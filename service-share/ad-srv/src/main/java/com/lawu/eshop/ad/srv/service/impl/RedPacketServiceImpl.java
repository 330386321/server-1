package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.RedPacketDO;
import com.lawu.eshop.ad.srv.domain.RedPacketDOExample;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.RedPacketDOMapper;
import com.lawu.eshop.ad.srv.service.RedPacketService;
import com.lawu.eshop.utils.AdArithmeticUtil;

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
	
	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;

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
		savePool(redPacketDO);
		return id;
	}
	
	
	public void savePool(RedPacketDO redPacketDO){
		Integer packageCount= redPacketDO.getPackageCount();
		BigDecimal totlePoint= redPacketDO.getTotlePoint();
		if(redPacketDO.getPutWay()==1){
			BigDecimal point=totlePoint.divide(new BigDecimal(packageCount),2, RoundingMode.HALF_UP);
			for (int j = 0; j < packageCount; j++) {
				PointPoolDO pointPool=new PointPoolDO();
				pointPool.setAdId(redPacketDO.getId());
				pointPool.setMerchantId(redPacketDO.getMerchantId());
				pointPool.setStatus(new Byte("0"));
				pointPool.setType(new Byte("2"));
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(point);
				pointPoolDOMapper.insert(pointPool);
			}
		}else{
			double[] points=AdArithmeticUtil.getRedPacketMoney(totlePoint.doubleValue(), packageCount);
			for (int j = 0; j < packageCount; j++) {
				PointPoolDO pointPool=new PointPoolDO();
				pointPool.setAdId(redPacketDO.getId());
				pointPool.setMerchantId(redPacketDO.getMerchantId());
				pointPool.setStatus(new Byte("0"));
				pointPool.setType(new Byte("2"));
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(new BigDecimal( points[j]));
				pointPoolDOMapper.insert(pointPool);
			}
		}
		
	}


	@Override
	public Integer selectCount(Long merchantId) {
		RedPacketDOExample example =new RedPacketDOExample();
		example.createCriteria().andStatusEqualTo(new Byte("1")).andMerchantIdEqualTo(merchantId);
		Long count=redPacketDOMapper.countByExample(example);
		return count.intValue();
	}
	
	
	

}
