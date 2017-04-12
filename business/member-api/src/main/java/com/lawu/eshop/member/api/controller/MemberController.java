package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.member.api.service.InviterService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.UserHeadImgDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UpdatePwdParam;
import com.lawu.eshop.user.param.UserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    private VerifyCodeService verifyCodeService;

    @Autowired
    private InviterService inviterService;

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member [1000]（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<UserDTO> result = memberService.findMemberInfo(memberId);
        return result;
    }


    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息  [1000]（章勇）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo", method = RequestMethod.PUT)
    public Result updateMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam) {
        long id = UserUtil.getCurrentUserId(getRequest());
        Result r = memberService.updateMemberInfo(memberParam, id);
        return r;
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。[1009|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        long id = UserUtil.getCurrentUserId(getRequest());
        return memberService.updateLoginPwd(id, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "忘记登录密码", notes = "根据会员账号修改登录密码。[1002|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "resetLoginPwd/{mobile}", method = RequestMethod.PUT)
    public Result resetLoginPwd(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                                @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result<MemberDTO> memResult = memberService.getMemberByAccount(mobile);
        if (!isSuccess(memResult)) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        MemberDTO memberDTO = memResult.getModel();
        long id = memberDTO.getId();
        return memberService.updateLoginPwd(id, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改支付密码", notes = "根据会员编号修改支付密码。[1009|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNum, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询是否设置支付密码", notes = "查询是否设置支付密码(true--已设置，false--未设置)。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "isSetPayPwd", method = RequestMethod.GET)
    public Result isSetPayPwd(@RequestHeader (UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.isSetPayPwd(userNum);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "注册", notes = "会员注册。[1002|1012|1013|1016] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                           @ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam) {
        RegisterRealParam registerRealParam = new RegisterRealParam();
        if (StringUtils.isNotEmpty(registerParam.getInviterAccount())) {
            Result<InviterDTO> inviterResult = inviterService.getInviterByAccount(registerParam.getInviterAccount());
            if (!isSuccess(inviterResult)) {
                return successGet(ResultCode.RESOURCE_NOT_FOUND);
            }
            InviterDTO inviterDTO = inviterResult.getModel();
            registerRealParam.setInviterId(inviterDTO.getInviterId());
            registerRealParam.setUserNum(inviterDTO.getUserNum());
        }
        Result accountResult = memberService.getMemberByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return successGet(ResultCode.RECORD_EXIST);
        }
        Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        registerRealParam.setAccount(registerParam.getAccount());
        registerRealParam.setPwd(registerParam.getPwd());
        return memberService.register(registerRealParam);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询当前会员信息", notes = "查询当前会员信息。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMember", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberByAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String account = UserUtil.getCurrentAccount(getRequest());
        return memberService.getMemberByAccount(account);
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改头像", notes = "修改头像。 (章勇)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveHeadImage", method = RequestMethod.POST)
    public Result<UserHeadImgDTO> saveHeadImage(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        String headImg = "";
        Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_HEAD);
        if(!"".equals(retMap.get("imgUrl"))){
             headImg = retMap.get("imgUrl").toString();
                 return    memberService.saveHeadImage(memberId, headImg);
        }
        return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
    }
}
