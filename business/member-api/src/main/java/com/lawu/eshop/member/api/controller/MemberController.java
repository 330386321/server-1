package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.SmsRecordDTO;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.SmsRecordService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.utils.IpUtil;
import com.lawu.eshop.utils.VerifyCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author zhangyong on 2017/3/22.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member （章勇）", httpMethod = "GET")
    // @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberInfo", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo() {
        long memberId= UserUtil.getCurrentUserId(getRequest());
        Result<UserDTO> result = memberService.findMemberInfo(memberId);
        return result;
    }

    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息（章勇）", httpMethod = "PUT")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo", method = RequestMethod.PUT)
    public Result updateMemberInfo(@ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam) {
        long id=UserUtil.getCurrentUserId(getRequest());
        Result r = memberService.updateMemberInfo(memberParam, id);
        return r;
    }

    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        long id=UserUtil.getCurrentUserId(getRequest());
        return memberService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @ApiOperation(value = "修改支付密码", notes = "根据会员编号修改支付密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd/{userNo}", method = RequestMethod.PUT)
    public Result updatePayPwd(@PathVariable @ApiParam(required = true, value = "会员编号") String userNo,
                               @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                               @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        return propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviterByAccount/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return memberService.getInviterByAccount(account);
    }

    @ApiOperation(value = "我的E友", notes = "我的E有查询,[200]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<Page<MemberDTO>> findMemberListByUser(@ModelAttribute @ApiParam(required = true, value = "查询信息") MemberQuery query) {
    	Long userId=UserUtil.getCurrentUserId(getRequest());
        Result<Page<MemberDTO>> page = memberService.findMemberListByUser(userId,query);
        return page;
    }

    @ApiOperation(value = "注册", notes = "会员注册。[1009|1012] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam) {
        Result accountResult = memberService.getMemberByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return failCreated(ResultCode.RECORD_EXIST);
        }
        Result smsResult = smsRecordService.verifySmsRecord(registerParam.getSmsId(), registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return failCreated(ResultCode.VERIFY_PWD_FAIL);
        }
        return memberService.register(registerParam);
    }

    @ApiOperation(value = "根据账号查询会员信息", notes = "根据账号查询会员信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberByAccount(@PathVariable @ApiParam(required = true, value = "会员账号") String account) {
        return memberService.getMemberByAccount(account);
    }

    @ApiOperation(value = "发送短信", notes = "发送短信。[1000|1001|1006|1007|1008|1014] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result<SmsRecordDTO> sendSms(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                                        @RequestParam @ApiParam(required = true, value = "短信类型") Integer type,
                                        @RequestParam @ApiParam(required = true, value = "验证码ID") Long verifyCodeId) {
        Result result = verifyCodeService.verifyPicCode(verifyCodeId);
        if (!isSuccess(result)) {
            return failCreated(ResultCode.VERIFY_PIC_CODE_FAIL);
        }
        String ip = IpUtil.getIpAddress(getRequest());
        return smsRecordService.sendSms(mobile, ip, type);
    }

    @ApiOperation(value = "获取验证码", notes = "获取图形验证码。 (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getVerifyCode/{mobile}", method = RequestMethod.GET)
    public void getVerifyCode(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile, VerifyCodePurposeEnum purpose) throws IOException {
        BufferedImage buffImg = new BufferedImage(60, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        String verifyCode = VerifyCodeUtil.getVerifyCode(g);
        Result result = verifyCodeService.saveVerifyCode(mobile, verifyCode, purpose);

        HttpServletResponse response = getResponse();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}
