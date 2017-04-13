package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample;
import com.lawu.eshop.ad.srv.domain.RedPacketDO;
import com.lawu.eshop.ad.srv.domain.RedPacketDOExample;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.RedPacketDOMapper;
import com.lawu.eshop.ad.srv.service.RedPacketService;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
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
	
	@Autowired
	@Qualifier("adUserAddPointTransactionMainServiceImpl")
	private TransactionMainService transactionMainService;

	@Override
	public Integer save(RedPacketParam param,Long merchantId,String num) {
		RedPacketDO redPacketDO=new RedPacketDO();
		redPacketDO.setMerchantId(merchantId);
		redPacketDO.setMerchantNum(num);
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


	@Override
	public BigDecimal getRedPacket(Long merchantId,Long memberId) {
		PointPoolDOExample ppexample=new PointPoolDOExample();
		ppexample.createCriteria().andMemberIdEqualTo(merchantId).andTypeEqualTo(new Byte("2"))
				                   .andStatusEqualTo(new Byte("0"));
		//查询出没有领取的积分，取出一个给用户
		List<PointPoolDO>  list=pointPoolDOMapper.selectByExample(ppexample);
		if(!list.isEmpty()){ 
			PointPoolDO pointPoolDO=list.get(0);
			pointPoolDO.setStatus(new Byte("1"));
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			//给用户加积分
			transactionMainService.sendNotice(pointPoolDO.getId());
			return pointPoolDO.getPoint();
		}else{
			return new BigDecimal(0);
		}
		
	}
	

}
