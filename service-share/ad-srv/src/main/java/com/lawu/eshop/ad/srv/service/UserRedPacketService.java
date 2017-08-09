/**
 * 
 */
package com.lawu.eshop.ad.srv.service;

import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.ad.srv.bo.UserRedPacketBO;
import com.lawu.eshop.ad.srv.domain.extend.UserRedpacketMaxMoney;
import com.lawu.eshop.framework.core.page.Page;

/**
 * 用户红包Service
 * @author lihj
 * @date 2017年8月3日
 */
public interface UserRedPacketService {

	/**
	 * 
	 * @param param
	 * @return
	 */
	Integer addUserRedPacket(UserRedPacketSaveParam param);

	/**
	 * 查询用户红包列表
	 * @param param
	 * @return
	 */
	Page<UserRedPacketBO> selectUserRedPacketList(UserRedPacketSelectParam param);

	/**
	 * @param redPacketId
	 * @return
	 */
	boolean isExistsRedPacket(Long redPacketId);

	/**
	 * 
	 */
	@Deprecated
	void executeUserRedPacketData();

	/**
	 * 用户领取红包
	 * @param redPacketId
	 * @param userNum
	 */
	void getUserRedpacketMoney(Long redPacketId, String userNum);

	/**
	 * 获取最大的红包金额
	 * @param redPacketId
	 * @return
	 */
	UserRedpacketMaxMoney getUserRedpacketMaxMoney(Long redPacketId);

	
}
