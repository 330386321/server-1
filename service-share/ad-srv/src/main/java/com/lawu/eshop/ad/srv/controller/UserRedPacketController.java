/**
 * 
 */
package com.lawu.eshop.ad.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.ad.dto.UserRedPacketDTO;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.ad.srv.bo.UserRedPacketBO;
import com.lawu.eshop.ad.srv.converter.UserRedPacketConverter;
import com.lawu.eshop.ad.srv.domain.extend.UserRedpacketMaxMoney;
import com.lawu.eshop.ad.srv.service.UserRedPacketService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * 用户红包Controller
 * 
 * @author lihj
 * @date 2017年8月3日
 */
@RestController
@RequestMapping(value = "userRedPacket/")
public class UserRedPacketController extends BaseController {

	/**
	 * 用户红包Service
	 */
	@Autowired
	private UserRedPacketService userRedPacketService;

	/**
	 * 新增红包记录
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "addUserRedPacket", method = RequestMethod.POST)
	public Result addUserRedPacket(@RequestBody UserRedPacketSaveParam param) {
		Long id = userRedPacketService.addUserRedPacket(param);
		if (null == id || id < 0) {
			successCreated(ResultCode.SAVE_FAIL);
		}
		return successCreated(id);
	}

	/**
	 * 查询用户红包列表
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "selectUserRedPacketList", method = RequestMethod.POST)
	public Result<Page<UserRedPacketDTO>> selectUserRedPacketList(@RequestBody UserRedPacketSelectParam param) {
		Page<UserRedPacketBO> pageBO = userRedPacketService.selectUserRedPacketList(param);
		Page<UserRedPacketDTO> pageDTO = new Page<UserRedPacketDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(UserRedPacketConverter.convertDTOS(pageBO.getRecords()));
		return successCreated(pageDTO);
	}

	/**
	 * 判断红包是否还有可领取的 true 还有、false 没有
	 * 
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value = "isExistsRedPacket/{redPacketId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable Long redPacketId) {
		boolean flag = userRedPacketService.isExistsRedPacket(redPacketId);
		IsExistsRedPacketDTO dto = new IsExistsRedPacketDTO();
		dto.setIsExistsRedPacket(flag);
		return successGet(dto);
	}

	/**
	 * 红包倒计时任务
	 * 
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "executeUserRedPacketData", method = RequestMethod.POST)
	public Result executeUserRedPacketData() {
		userRedPacketService.executeUserRedPacketData();
		return successCreated();
	}

	/**
	 * 领取红包
	 * 
	 * @param redPacketId
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "getUserRedpacketMoney", method = RequestMethod.POST)
	public Result getUserRedpacketMoney(@RequestParam Long redPacketId, @RequestParam String userNum) {
		userRedPacketService.getUserRedpacketMoney(redPacketId, userNum);
		return successCreated();
	}

	/**
	 * 获取最大的红包金额
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value = "getUserRedpacketMaxMoney", method = RequestMethod.POST)
	public Result getUserRedpacketMaxMoney(@RequestParam Long redPacketId) {
		UserRedpacketMaxMoney maxMoney =userRedPacketService.getUserRedpacketMaxMoney(redPacketId);
		return successCreated(maxMoney);
	}	

	/**
	 * 根据红包ID 获取红包金额、和orderNum支付时调用第三方用
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value="selectUserRedPacketInfoForThrid",method=RequestMethod.GET)
	public Result<ThirdPayCallBackQueryPayOrderDTO> selectUserRedPacketInfoForThrid(@RequestParam Long redPacketId){
		ThirdPayCallBackQueryPayOrderDTO result = userRedPacketService.selectUserRedPacketInfoForThrid(redPacketId);
		return successGet(result); 
	}
	
}
