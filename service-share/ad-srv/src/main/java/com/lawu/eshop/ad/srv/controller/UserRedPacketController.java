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
import com.lawu.eshop.ad.dto.UserRedPacketAddReturnDTO;
import com.lawu.eshop.ad.dto.UserRedPacketDTO;
import com.lawu.eshop.ad.dto.UserRedpacketMaxMoneyDTO;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectNumParam;
import com.lawu.eshop.ad.srv.bo.UserRedPacketAddReturnBO;
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
	public Result<UserRedPacketAddReturnDTO> addUserRedPacket(@RequestBody UserRedPacketSaveParam param) {
		UserRedPacketAddReturnBO bo = userRedPacketService.addUserRedPacket(param);
		if (null == bo) {
			successCreated(ResultCode.SAVE_FAIL);
		}
		UserRedPacketAddReturnDTO dto =UserRedPacketConverter.convertAddDTO(bo);
		return successCreated(dto);
	}

	/**
	 * 查询用户红包列表
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "selectUserRedPacketList", method = RequestMethod.POST)
	public Result<Page<UserRedPacketDTO>> selectUserRedPacketList(@RequestBody UserRedPacketSelectNumParam param) {
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
	public Result<UserRedpacketMaxMoneyDTO> getUserRedpacketMoney(@RequestParam Long redPacketId, @RequestParam String userNum) {
		boolean flag = userRedPacketService.isExistsRedPacket(redPacketId);
		UserRedpacketMaxMoneyDTO dto =new UserRedpacketMaxMoneyDTO();
		if(!flag){  //红包领取完了
			dto.setGetAll(flag);
			return successCreated(dto);
		}
		boolean userIsGet = userRedPacketService.checkUserGetRedpacket(redPacketId, userNum);
		if(!userIsGet){ //用户已经领取了红包
			dto.setFlag(userIsGet);
			return successCreated(dto);
		}
		
		UserRedpacketMaxMoney getMoney = userRedPacketService.getUserRedpacketMoney(redPacketId, userNum);
		if(!getMoney.isSysWords() && getMoney.isFlag()){
			dto.setMoney(getMoney.getMaxMoney());
			dto.setFlag(getMoney.isFlag());
		}
		return successCreated(dto);
	}
	
	/**
	 * 获取最大的红包金额
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value = "getUserRedpacketMaxMoney", method = RequestMethod.POST)
	public Result<UserRedpacketMaxMoneyDTO> getUserRedpacketMaxMoney(@RequestParam("redPacketId") Long redPacketId) {
		UserRedpacketMaxMoney maxMoney =userRedPacketService.getUserRedpacketMaxMoney(redPacketId);
		UserRedpacketMaxMoneyDTO dto =new UserRedpacketMaxMoneyDTO();
		dto.setMoney(maxMoney.getMaxMoney());
		return successCreated(dto);
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
