/**
 *
 */
package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PropertyType;
import com.lawu.eshop.ad.constants.RedPacketPutWayEnum;
import com.lawu.eshop.ad.constants.SpiltRedPacketUntil;
import com.lawu.eshop.ad.constants.UserRedPacketEnum;
import com.lawu.eshop.ad.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.ad.param.UserPacketRefundParam;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectNumParam;
import com.lawu.eshop.ad.param.UserRedPacketUpdateParam;
import com.lawu.eshop.ad.srv.bo.UserRedPacketAddReturnBO;
import com.lawu.eshop.ad.srv.bo.UserRedPacketBO;
import com.lawu.eshop.ad.srv.converter.UserRedPacketConverter;
import com.lawu.eshop.ad.srv.domain.UserRedPacketDO;
import com.lawu.eshop.ad.srv.domain.UserRedPacketDOExample;
import com.lawu.eshop.ad.srv.domain.UserRedPacketDOExample.Criteria;
import com.lawu.eshop.ad.srv.domain.UserTakedRedPacketDO;
import com.lawu.eshop.ad.srv.domain.UserTakedRedPacketDOExample;
import com.lawu.eshop.ad.srv.domain.extend.UserRedpacketMaxMoney;
import com.lawu.eshop.ad.srv.mapper.UserRedPacketDOMapper;
import com.lawu.eshop.ad.srv.mapper.UserTakedRedPacketDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.UserTakedRedpacketBOMapperExtend;
import com.lawu.eshop.ad.srv.service.AdCountRecordService;
import com.lawu.eshop.ad.srv.service.UserRedPacketService;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;
import com.lawu.eshop.utils.DateUtil;

/**
 * 用户红包ServiceImpl
 *
 * @author lihj
 * @date 2017年8月3日
 */
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

	@Autowired
	private UserRedPacketDOMapper userRedPacketDOMapper;
	@Autowired
	private UserTakedRedPacketDOMapper userTakedRedPacketDOMapper;
	@Autowired
	private UserTakedRedpacketBOMapperExtend userTakedRedpacketBOMapperExtend;
	@Autowired
	@Qualifier("memberRedPacketRefundTransactionMainServiceImpl")
	private TransactionMainService<Reply> memberRedPacketRefundTransactionMainServiceImpl;

	@Autowired
	@Qualifier("memberGetRedPacketTransactionMainServiceImpl")
	private TransactionMainService<Reply> memberGetRedPacketTransactionMainServiceImpl;
	
	@Autowired
	private AdCountRecordService adCountRecordService;
	
	@Autowired
	private LockService lockService;
	/**
	 * 新增用户红包
	 */
	@Override
	@Transactional
	public UserRedPacketAddReturnBO addUserRedPacket(UserRedPacketSaveParam param) {
		UserRedPacketDO userRed = UserRedPacketConverter.converDO(param);
		Integer i = userRedPacketDOMapper.insert(userRed);
		if (null == i || i < 0) {
			return null;
		}
		UserRedPacketAddReturnBO dto =UserRedPacketConverter.convertAddBO(userRed);
		return dto;
	}

	/**
	 * 查询用户红包列表
	 */
	@Override
	@Transactional
	public Page<UserRedPacketBO> selectUserRedPacketList(UserRedPacketSelectNumParam param) {
		UserRedPacketDOExample example = new UserRedPacketDOExample();
		Criteria cr = example.createCriteria();
		cr.andUserNumEqualTo(param.getNum());
		cr.andPayTypeIsNotNull();
		example.setOrderByClause("status asc");
		example.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		List<UserRedPacketDO> listDO = userRedPacketDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<UserRedPacketBO> listBO = Lists.newArrayList();
		for (UserRedPacketDO userRed : listDO) {
			UserRedPacketBO userBO = UserRedPacketConverter.convertBO(userRed);
			Date overdueDate = DateUtil.getDayAfter(userBO.getGmtCreate());// 获取红包过期时间
			if(overdueDate.getTime()<new Date().getTime()&& userRed.getStatus().equals(UserRedPacketEnum.USER_STATUS_EFFECTIVE.val)){
				userBO.setUserRedPacketEnum(UserRedPacketEnum.USER_STATUS_OUT);
				setRedpacketOverDue(userBO.getId());
			}
			listBO.add(userBO);
		}
		Page<UserRedPacketBO> page = new Page<UserRedPacketBO>();
		page.setCurrentPage(param.getCurrentPage());
		page.setRecords(listBO);
		return page;
	}

	/**
	 * 判断用户红包是否领取完毕
	 * 
	 * @param redPacketId
	 * @return true 还有、false没有了
	 */
	@Override
	public boolean isExistsRedPacket(Long redPacketId) {
		UserRedPacketDO userRedpacket =userRedPacketDOMapper.selectByPrimaryKey(redPacketId);
		Date overdueDate = DateUtil.getDayAfter(userRedpacket.getGmtCreate());// 获取红包过期时间
		if(overdueDate.getTime()<new Date().getTime()){//过期
			setRedpacketOverDue(redPacketId);
			return false;
		}else{
			UserTakedRedPacketDOExample example = new UserTakedRedPacketDOExample();
			example.createCriteria().andUserRedPackIdEqualTo(redPacketId)
			.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val);
			int count = userTakedRedPacketDOMapper.countByExample(example);
			return count > 0 ? true : false;
		}
	}

	public UserPacketRefundParam selectBackTotalMoney(Long userRedpacketId){
		UserRedPacketDO userRedpacket = userRedPacketDOMapper.selectByPrimaryKey(userRedpacketId);
		UserPacketRefundParam param =UserRedPacketConverter.convertReFund(userRedpacket);
		return param;
	}	
	/**
	 * 设置红包过期
	 * @param userRedpacketId
	 */
	
	private void setRedpacketOverDue(Long userRedpacketId) {
		UserRedPacketDO userRedpacket =userRedPacketDOMapper.selectByPrimaryKey(userRedpacketId);
		userRedpacket.setGmtModified(new Date());
		userRedpacket.setStatus(UserRedPacketEnum.USER_STATUS_OUT.val);
		UserTakedRedPacketDOExample userTakedExample = new UserTakedRedPacketDOExample();
		userTakedExample.createCriteria().andUserRedPackIdEqualTo(userRedpacketId)
		.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val);
		List<UserTakedRedPacketDO> listTaked = userTakedRedPacketDOMapper.selectByExample(userTakedExample);
		BigDecimal totalBackMoney = getTotalBackMoney(listTaked);
		userRedpacket.setRefundMoney(totalBackMoney);
		userRedPacketDOMapper.updateByPrimaryKeySelective(userRedpacket);
		memberRedPacketRefundTransactionMainServiceImpl.sendNotice(userRedpacketId);
	}
	
	/**
	 * @param listTaked
	 * @return
	 */
	private BigDecimal getTotalBackMoney(List<UserTakedRedPacketDO> listTaked) {
		BigDecimal totalMoney = new BigDecimal(0);
		for (int i = 0; i < listTaked.size(); i++) {
			UserTakedRedPacketDO taked = listTaked.get(i);
			totalMoney = totalMoney.add(taked.getMoney());
			taked.setStatus(PointPoolStatusEnum.AD_POINT_OUT.val);
			taked.setGmtModified(new Date());
			userTakedRedPacketDOMapper.updateByPrimaryKeySelective(taked);
		}
		return totalMoney;
	}
	

	/**
	 * 用户领取红包
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserRedpacketMaxMoney getUserRedpacketMoney(Long redPacketId, String userNum) {
		
		Result<Object> result = adCountRecordService.getUserRedPacketCountRecord(redPacketId);
		UserRedpacketMaxMoney getMoney=new UserRedpacketMaxMoney();
		
		if(result.getModel()==null){ 
			getMoney.setFlag(false);
			return getMoney;
		}else{
			List<Object> list=(List<Object>) result.getModel();
			if((Integer)list.get(0)<0){
				getMoney.setSysWords(true);
				return getMoney;
			}
		}
		
		Boolean flag=lockService.tryLock(LockModule.LOCK_AD_SRV,"AD_USER_RED_PACKET_LOCK_",redPacketId);
		
		UserTakedRedPacketDO userTabed = new UserTakedRedPacketDO();
		
		if(flag){
			
			UserRedPacketDO  userRedPacketDO = userRedPacketDOMapper.selectByPrimaryKey(redPacketId);
			
			//已领取个数
			UserTakedRedPacketDOExample example = new UserTakedRedPacketDOExample();
			example.createCriteria().andUserRedPackIdEqualTo(redPacketId);
			int count = userTakedRedPacketDOMapper.countByExample(example);
			
			//已领取总金额
			UserRedpacketMaxMoney  view =userTakedRedpacketBOMapperExtend.getSumMoney(redPacketId);
			BigDecimal subMoney=new BigDecimal(0);
			//剩余积分
			if(view == null){
			    subMoney=userRedPacketDO.getTotalMoney().subtract(BigDecimal.valueOf(0));
			}else{
				subMoney=userRedPacketDO.getTotalMoney().subtract(view.getMaxMoney());
			}
			BigDecimal money=new BigDecimal(0);
			byte type;
			if(userRedPacketDO.getType()==RedPacketPutWayEnum.PUT_WAY_LUCK.val){ //手气红包
				 Double point= SpiltRedPacketUntil.spiltRedPackets(subMoney.doubleValue(), userRedPacketDO.getTotalCount(),count);
				 money=BigDecimal.valueOf(point);
				 type=RedPacketPutWayEnum.PUT_WAY_LUCK.val;
			}else{ //普通红包
				if(count==userRedPacketDO.getTotalCount()-1){
					money=subMoney;
				}else{
					money = userRedPacketDO.getTotalMoney().divide(new BigDecimal(userRedPacketDO.getTotalCount()), 2, RoundingMode.HALF_UP);
				}
				type=RedPacketPutWayEnum.PUT_WAY_COMMON.val;
			}
			
			userTabed.setGmtModified(new Date());
			userTabed.setGmtCreate(new Date());
			userTabed.setTakedTime(new Date());
			userTabed.setUserNum(userNum);
			userTabed.setMoney(money);
			userTabed.setOrdinal(count);
			userTabed.setUserRedPackId(redPacketId);
			userTabed.setType(type);
			userTabed.setStatus(PointPoolStatusEnum.AD_POINT_GET.val);
			userTakedRedPacketDOMapper.insert(userTabed);
			
			if(userRedPacketDO.getTotalCount()-1==count || count>=userRedPacketDO.getTotalCount()){
				userRedPacketDO.setGmtModified(new Date());
				userRedPacketDO.setStatus(UserRedPacketEnum.USER_STATUS_OVER.val);
				userRedPacketDOMapper.updateByPrimaryKeySelective(userRedPacketDO);
			}
			getMoney.setMaxMoney(userTabed.getMoney());
			
			//发送消息修改积分
			memberGetRedPacketTransactionMainServiceImpl.sendNotice(userTabed.getId());
			lockService.unLock(LockModule.LOCK_AD_SRV,"AD_USER_RED_PACKET_LOCK_",redPacketId);
			
		}
		
		return getMoney;
	}

	/**
	 * 查询最大的红包金额
	 */
	@Override
	public UserRedpacketMaxMoney getUserRedpacketMaxMoney(Long redPacketId) {
		UserRedPacketDO  userRedPacketDO = userRedPacketDOMapper.selectByPrimaryKey(redPacketId);
		UserRedpacketMaxMoney userRedpacketMaxMoney = new UserRedpacketMaxMoney();
		userRedpacketMaxMoney.setMaxMoney(userRedPacketDO.getTotalMoney().divide(BigDecimal.valueOf(userRedPacketDO.getTotalCount())).multiply(BigDecimal.valueOf(PropertyType.ad_red_packet_default)));
		return userRedpacketMaxMoney;
	}

	/**
	 * 根据红包ID 获取红包金额、和orderNum支付时调用第三方用
	 */
	@Override
	public ThirdPayCallBackQueryPayOrderDTO selectUserRedPacketInfoForThrid(Long redPacketId) {
		UserRedPacketDO userRed = userRedPacketDOMapper.selectByPrimaryKey(redPacketId);
		ThirdPayCallBackQueryPayOrderDTO third = UserRedPacketConverter.convertThirdPay(userRed);
		return third;
	}

	/**
	 * 第三方回调更新
	 * 
	 */
	@Override
	public boolean updateUserPacketInfo(UserRedPacketUpdateParam paran) {
		UserRedPacketDO user =UserRedPacketConverter.convertDO(paran);
		userRedPacketDOMapper.updateByPrimaryKeySelective(user);
		return true;
	}

	public boolean checkUserGetRedpacket(Long redPacketId, String userNum) {
		UserTakedRedPacketDOExample userTakedExample = new UserTakedRedPacketDOExample();
		userTakedExample.createCriteria().andUserRedPackIdEqualTo(redPacketId).andUserNumEqualTo(userNum)
				.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_GET.val);
		List<UserTakedRedPacketDO> listTaked = userTakedRedPacketDOMapper.selectByExample(userTakedExample);
		boolean flag =false;
		if(listTaked.size()==0){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 红包过期定时任务
	 */
	@Override
	@Transactional
	public void executeUserRedPacketData() {
		Date date = DateUtil.getDayBefore(new Date());// 前一天的时间
		UserRedPacketDOExample example = new UserRedPacketDOExample();
		List<Byte> status= new ArrayList<>();
		status.add(UserRedPacketEnum.USER_STATUS_EFFECTIVE.val);
		status.add(UserRedPacketEnum.USER_STATUS_OVER.val);
		Criteria cr = example.createCriteria();
		cr.andGmtCreateLessThan(date);
		cr.andStatusIn(status);
		List<UserRedPacketDO> list = userRedPacketDOMapper.selectByExample(example);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				UserRedPacketDO userRed = list.get(i);
				UserTakedRedPacketDOExample userTakedExample = new UserTakedRedPacketDOExample();
				userTakedExample.createCriteria().andUserRedPackIdEqualTo(userRed.getId())
						.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val);
				List<UserTakedRedPacketDO> listTaked = userTakedRedPacketDOMapper.selectByExample(userTakedExample);
				BigDecimal totalBackMoney = getTotalBackMoney(listTaked);
				userRed.setGmtModified(new Date());
				userRed.setStatus(UserRedPacketEnum.USER_STATUS_OUT.val);
				userRed.setRefundMoney(totalBackMoney);
				userRedPacketDOMapper.updateByPrimaryKeySelective(userRed);
				memberRedPacketRefundTransactionMainServiceImpl.sendNotice(userRed.getId());
			}
		}
	}
}
