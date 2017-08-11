/**
 *
 */
package com.lawu.eshop.member.api.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lawu.eshop.member.api.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.UserRedPacketDTO;
import com.lawu.eshop.ad.param.UserRedPacketAddParam;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.ad.param.UserRedpacketValue;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.utils.QrCodeUtil;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 用户红包
 *
 * @author lihj
 * @date 2017年8月3日
 */
@Api(tags = "userRedPacket")
@RestController
@RequestMapping(value = "userRedPacket/")
public class UserRedPacketController extends BaseController {

	@Autowired
	private UserRedPacketService userRedPacketService;

	@Autowired
	private PropertyInfoService propertyInfoService;

	@Autowired
	private MemberApiConfig memberApiConfig;

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "新增用户红包", notes = "新增用户红包（李洪军）", httpMethod = "POST")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "addUserRedPacket", method = RequestMethod.POST)
	public Result addUserRedPacket(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam(required = true, value = "新增红包信息") UserRedPacketAddParam param) {
		HttpServletRequest request = getRequest();
		if (null == param) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		if (param.getTotalCount() > UserRedpacketValue.MAX_USERREDPACKET_COUNT) {
			return successCreated(ResultCode.MAX_USERREDPACKET_COUNT);
		}
		if (param.getTotalMoney().compareTo(UserRedpacketValue.MAX_MONTY) >= 0) {
			return successCreated(ResultCode.MAX_USERREDPACKET_MONTY);
		}
		Result<PropertyBalanceDTO> resultMoney = propertyInfoService
				.getPropertyBalance(UserUtil.getCurrentUserNum(request));
		if (param.getTotalMoney().compareTo(resultMoney.getModel().getBalance()) >= 0) {
			return successCreated(ResultCode.PROPERTY_INFO_BALANCE_LESS);// 余额不足
		}
		UserRedPacketSaveParam saveParam = new UserRedPacketSaveParam();
		saveParam.setRedPacketPutWayEnum(param.getRedPacketPutWayEnum());
		saveParam.setTotalCount(param.getTotalCount());
		saveParam.setTotalMoney(param.getTotalMoney());
		saveParam.setUserAccount(UserUtil.getCurrentAccount(request));
		saveParam.setUserNum(UserUtil.getCurrentUserNum(request));
		saveParam.setOrderNum(StringUtil.getRandomNum(""));
		Result result = userRedPacketService.addUserRedPacket(saveParam);
		return successCreated(result);
	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "查询用户红包列表信息", notes = "查询用户红包列表信息(李洪军)", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectUserRedPacketList", method = RequestMethod.GET)
	public Result<Page<UserRedPacketDTO>> selectUserRedPacketList(
			@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam(required = true, value = "查询信息") UserRedPacketSelectParam param) {
		return userRedPacketService.selectUserRedPacketList(param);
	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "生成用户红包二维码", notes = "生成用户红包二维码(李洪军)", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "getUserQrCode", method = RequestMethod.GET)
	public void getUserQrCode(@RequestParam(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "红包ID") Long redPacketId) throws IOException {
		HttpServletRequest request = getRequest();
		Long memberId = UserUtil.getCurrentUserId(request);
		if (memberId != 0) {
			HttpServletResponse response = getResponse();
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			String content = memberApiConfig.getMemberQrCode() + memberId + "/" + redPacketId;
			BufferedImage buff = QrCodeUtil.generateQrCode(content);
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(buff, "jpeg", out);
			out.close();
		}
	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "扫描用户分享红包二维码", notes = "扫描用户分享红包二维码(李洪军)", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "getUserQrCodeContent/{redPacketId}", method = RequestMethod.GET)
	public void getUserQrCodeContent(@RequestParam @ApiParam(required = true, value = "用户ID") Long memberId,
			@PathVariable @ApiParam(required = true, value = "红包ID") Long redPacketId) throws IOException {
		HttpServletResponse response = getResponse();
		if (memberId == null || memberId <= 0) {
			return;
		}
		if (redPacketId == null || redPacketId <= 0) {
			return;
		}
		Result<IsExistsRedPacketDTO> result = userRedPacketService.isExistsRedPacket(redPacketId);
		if (result.getModel().getIsExistsRedPacket()) {// 还可以领取
			response.sendRedirect(memberApiConfig.getMemberShareRedpacketUrl() + "?memberId=" + memberId
					+ "&redPacketId=" + redPacketId);
		} else {
			// 不能领取了
			response.sendRedirect(
					memberApiConfig.getMemberShareOverUrl() + "?memberId=" + memberId + "&redPacketId=" + redPacketId);
		}
	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "获取红包中最大值", notes = "获取红包中最大值", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "getUserRedpacketMaxMoney", method = RequestMethod.POST)
	public Result getUserRedpacketMaxMoney(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "红包ID") Long redPacketId) {
		Result result = userRedPacketService.getUserRedpacketMaxMoney(redPacketId);
		return successGet(result);
	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "领取用户红包", notes = "领取用户红包", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "getUserRedpacketMoney", method = RequestMethod.POST)
	public Result getUserRedpacketMoney(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "红包ID") Long redPacketId) {
		HttpServletRequest request = getRequest();
		String userNum = UserUtil.getCurrentUserNum(request);
		Result result = userRedPacketService.getUserRedpacketMoney(redPacketId, userNum);
		return successCreated(result);
	}

}
