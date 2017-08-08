/**
 * 
 */
package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.UserRedPacketDTO;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

/**
 * @author lihj
 * @date 2017年8月3日
 */
@FeignClient(value = "ad-srv")
public interface UserRedPacketService {

	/**
	 * 保存新增红包记录
	 * 
	 * @param saveParam
	 * @return
	 */
	@RequestMapping(value = "userRedPacket/addUserRedPacket", method = RequestMethod.POST)
	Result addUserRedPacket(@RequestBody UserRedPacketSaveParam saveParam);

	/**
	 * 查询用户红包列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value="userRedPacket/selectUserRedPacketList",method=RequestMethod.GET)
	Result<Page<UserRedPacketDTO>> selectUserRedPacketList(@RequestBody UserRedPacketSelectParam param);

	/**
	 * 判断是否还有红包可以领取
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value="userRedPacket/isExistsRedPacket/{redPacketId}")
	Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("redPacketId") Long redPacketId);

	/**
	 * 领取红包接口
	 * @param redPacketId 红包id
	 * @param userNum 领取人num
	 * @return
	 */
	@RequestMapping(value="userRedPacket/getUserRedpacketMoney")
	Result getUserRedpacketMoney(@RequestParam("redPacketId") Long redPacketId, @RequestParam("userNum") String userNum);

	/**
	 * 
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value="userRedPacket/getUserRedpacketMaxMoney")
	Result getUserRedpacketMaxMoney(@RequestParam("redPacketId") Long redPacketId);

}
