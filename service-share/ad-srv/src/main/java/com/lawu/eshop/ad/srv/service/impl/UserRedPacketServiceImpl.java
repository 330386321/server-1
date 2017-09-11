/**
 *
 */
package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.RedPacketArithmetic;
import com.lawu.eshop.ad.constants.RedPacketPutWayEnum;
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
import com.lawu.eshop.ad.srv.service.UserRedPacketService;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.synchronization.lock.service.LockService;
import com.lawu.eshop.utils.DateUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		if (param.getRedPacketPutWayEnum() == RedPacketPutWayEnum.PUT_WAY_COMMON) {// 普通红包
			saveNormalUserTakedRedpacked(userRed);
		} else if (param.getRedPacketPutWayEnum() == RedPacketPutWayEnum.PUT_WAY_LUCK) {// 手气红包
			saveLockUserTakedRedpacked(userRed);
		}
		UserRedPacketAddReturnBO dto =UserRedPacketConverter.convertAddBO(userRed);
		return dto;
	}

	/**
	 * 保存手气红包
	 *
	 * @param userRed
	 */
	private void saveLockUserTakedRedpacked(UserRedPacketDO userRed) {
		List<Double> lists = RedPacketArithmetic.spiltRedPackets(userRed.getTotalMoney().doubleValue(),
				userRed.getTotalCount());
		for (int i = 0; i < userRed.getTotalCount(); i++) {
			UserTakedRedPacketDO userTaked = new UserTakedRedPacketDO();
			userTaked.setGmtCreate(new Date());
			userTaked.setGmtModified(new Date());
			userTaked.setMoney(new BigDecimal(lists.get(i)));
			userTaked.setOrdinal(i);
			userTaked.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
			userTaked.setType(userRed.getType());
			userTaked.setUserRedPackId(userRed.getId());
			userTakedRedPacketDOMapper.insert(userTaked);
		}

	}

	/**
	 * 保存普通红包
	 *
	 * @param userRed
	 */
	private void saveNormalUserTakedRedpacked(UserRedPacketDO userRed) {
		BigDecimal usedMoney = new BigDecimal(0);
		BigDecimal money = userRed.getTotalMoney().divide(new BigDecimal(userRed.getTotalCount()), 2,
				RoundingMode.HALF_UP);
		for (int i = 0; i < userRed.getTotalCount(); i++) {
			UserTakedRedPacketDO userTaked = new UserTakedRedPacketDO();
			userTaked.setGmtCreate(new Date());
			userTaked.setGmtModified(new Date());
			if (i + 1 == userRed.getTotalCount()) {
				userTaked.setMoney(userRed.getTotalMoney().subtract(usedMoney));
			} else {
				userTaked.setMoney(money);
			}
			userTaked.setOrdinal(i);
			userTaked.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
			userTaked.setType(userRed.getType());
			userTaked.setUserRedPackId(userRed.getId());
			userTakedRedPacketDOMapper.insert(userTaked);
			usedMoney = usedMoney.add(money);
		}
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
	@Override
	@Transactional
	public UserRedpacketMaxMoney getUserRedpacketMoney(Long redPacketId, String userNum) {
		UserTakedRedPacketDOExample userTakedExample = new UserTakedRedPacketDOExample();
		userTakedExample.createCriteria().andUserRedPackIdEqualTo(redPacketId)
				.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val);
		List<UserTakedRedPacketDO> listTaked = userTakedRedPacketDOMapper.selectByExample(userTakedExample);
		String lockKey ="UserRedpacket"+redPacketId+userNum;
		boolean lock = lockService.tryLock(lockKey);
		UserRedpacketMaxMoney getMoney=new UserRedpacketMaxMoney();
		if(lock){
			boolean flag = checkUserGetRedpacket(redPacketId, userNum);
			getMoney.setFlag(flag);
			if(flag){
				if (null != listTaked && listTaked.size() > 0) {
					UserTakedRedPacketDO userTabed = listTaked.get(0);
					userTabed.setGmtModified(new Date());
					userTabed.setTakedTime(new Date());
					userTabed.setUserNum(userNum);
					userTabed.setStatus(PointPoolStatusEnum.AD_POINT_GET.val);
					userTakedRedPacketDOMapper.updateByPrimaryKeySelective(userTabed);
					if(listTaked.size()==1){//最后一个
						UserRedPacketDO userRed = userRedPacketDOMapper.selectByPrimaryKey(redPacketId);
						userRed.setGmtModified(new Date());
						userRed.setStatus(UserRedPacketEnum.USER_STATUS_OVER.val);
						userRedPacketDOMapper.updateByPrimaryKeySelective(userRed);
					}
					
					memberGetRedPacketTransactionMainServiceImpl.sendNotice(userTabed.getId());
					
					getMoney.setMaxMoney(userTabed.getMoney());
				}
			}
		}else{
			lockService.unLock(lockKey);
			getMoney.setFlag(false);
		}
		return getMoney;
	}

	/**
	 * 查询最大的红包金额
	 */
	@Override
	public UserRedpacketMaxMoney getUserRedpacketMaxMoney(Long redPacketId) {
		return userTakedRedpacketBOMapperExtend.getMaxMoney(redPacketId);
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
}
