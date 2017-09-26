package com.lawu.eshop.member.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.InviterService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.RongYunDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.UserHeadImgDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.UploadFileUtil;

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

    @Autowired
    private MemberApiConfig memberApiConfig;

    @Autowired
    private MerchantService merchantService;

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member [1000]（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        return memberService.findMemberInfo(memberId);
    }


    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息  [1000]（章勇）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo", method = RequestMethod.PUT)
    public Result updateMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam) {
        long id = UserUtil.getCurrentUserId(getRequest());
        return memberService.updateMemberInfo(memberParam, id);
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。[1002|1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        long id = UserUtil.getCurrentUserId(getRequest());
        return memberService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "忘记登录密码", notes = "根据会员账号修改登录密码。[1100|1013|1016|1025] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "resetLoginPwd/{mobile}", method = RequestMethod.PUT)
    public Result resetLoginPwd(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                                @RequestParam @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                                @RequestParam @ApiParam(required = true, value = "手机验证码") String smsCode,
                                @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(verifyCodeId, smsCode);
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
        if (!mobile.equals(verifyCodeDTO.getMobile())) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        if(DateUtil.smsIsOverdue(smsResult.getModel().getGmtCreate(), memberApiConfig.getSmsValidMinutes())){
            return successGet(ResultCode.VERIFY_SMS_CODE_OVERTIME);
        }
        return memberService.resetLoginPwd(mobile,newPwd );
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改支付密码", notes = "根据会员编号修改支付密码。[1009|1100] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                               @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNum, originalPwd, newPwd);
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "忘记支付密码", notes = "根据会员编号重置支付密码。[1013|1100|1016|1025] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "resetPayPwd", method = RequestMethod.PUT)
    public Result resetPayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                              @RequestParam @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                              @RequestParam @ApiParam(required = true, value = "手机验证码") String smsCode,
                              @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String mobile = UserUtil.getCurrentAccount(getRequest());
        Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(verifyCodeId, smsCode);
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
        if (!verifyCodeDTO.getMobile().equals(mobile)) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        if(DateUtil.smsIsOverdue(smsResult.getModel().getGmtCreate(), memberApiConfig.getSmsValidMinutes())){
            return successGet(ResultCode.VERIFY_SMS_CODE_OVERTIME);
        }
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.resetPayPwd(userNum, newPwd);
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "设置支付密码", notes = "根据会员编号设置支付密码。[1100] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "setPayPwd", method = RequestMethod.PUT)
    public Result setPayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                            @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.setPayPwd(userNum, newPwd);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询是否设置支付密码", notes = "查询是否设置支付密码(true--已设置，false--未设置)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "isSetPayPwd", method = RequestMethod.GET)
    public Result isSetPayPwd(@RequestHeader (UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.isSetPayPwd(userNum);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "注册", notes = "会员注册。[1026|1027|1013|1016|1025] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                           @ModelAttribute @ApiParam RegisterParam registerParam) {
        RegisterRealParam registerRealParam = new RegisterRealParam();
        if (StringUtils.isNotEmpty(registerParam.getInviterAccount())) {
            if (registerParam.getUserTypeEnum() != null) {
                if (registerParam.getUserTypeEnum().val.equals(UserTypeEnum.MEMBER.val)) {
                    Result<MemberDTO> memberDTOResult = memberService.getMemberByAccount(registerParam.getInviterAccount());
                    if (isSuccess(memberDTOResult)) {
                        registerRealParam.setInviterId(memberDTOResult.getModel().getId());
                        registerRealParam.setUserNum(memberDTOResult.getModel().getNum());
                    }
                } else {
                    Result<MerchantDTO> merchantDTOResult = merchantService.getMerchantByAccount(registerParam.getInviterAccount());
                    if (isSuccess(merchantDTOResult)) {
                        registerRealParam.setInviterId(merchantDTOResult.getModel().getId());
                        registerRealParam.setUserNum(merchantDTOResult.getModel().getNum());
                    }
                }
            } else {
                Result<InviterDTO> inviterResult = inviterService.getInviterByAccount(registerParam.getInviterAccount());
                if (!isSuccess(inviterResult)) {
                    return successGet(ResultCode.INVITER_NO_EXIST);
                }
                InviterDTO inviterDTO = inviterResult.getModel();
                registerRealParam.setInviterId(inviterDTO.getInviterId());
                registerRealParam.setUserNum(inviterDTO.getUserNum());
            }
        }

        Result accountResult = memberService.getMemberByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return successGet(ResultCode.ACCOUNT_EXIST);
        }
        Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        if(DateUtil.smsIsOverdue(verifyCodeDTO.getGmtCreate(), memberApiConfig.getSmsValidMinutes())){
            return successGet(ResultCode.VERIFY_SMS_CODE_OVERTIME);
        }
        registerRealParam.setAccount(registerParam.getAccount());
        registerRealParam.setPwd(registerParam.getPwd());
        return memberService.register(registerRealParam);
    }

    @Deprecated
    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "修改头像", notes = "修改头像。 (章勇)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveHeadImage", method = RequestMethod.POST)
    public Result<UserHeadImgDTO> saveHeadImage(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        String headImg ;
        Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_HEAD, memberApiConfig.getImageUploadUrl());
        if(StringUtils.isNotEmpty(retMap.get("imgUrl"))){
             headImg = retMap.get("imgUrl");
            return    memberService.saveHeadImage(memberId, headImg);
        }
        return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "设置推送CID", notes = "设置推送CID。[1005] (章勇)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "setGetuiCid",method = RequestMethod.PUT)
    public Result setGetuiCid(@RequestParam("cid") String cid, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){
        Long id = UserUtil.getCurrentUserId(getRequest());
        if(id == null || id <= 0 ||  "".equals(cid)){
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return memberService.setGtAndRongYunInfo(id,cid);
    }

    @Audit(date = "2017-05-23", reviewer = "孙林青")
    @ApiOperation(value = "查询融云需要信息", notes = "查询融云需要信息。 [1004|1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getRongYunInfo/{num}", method = RequestMethod.GET)
    public Result<RongYunDTO> getRongYunInfo(@PathVariable @ApiParam(required = true, value = "用户编号") String num){
        if(StringUtils.isEmpty(num)){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        if(num.startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
            return memberService.getRongYunInfoByNum(num);
        }else{
            return merchantService.getRongYunInfoByNum(num);
        }
    }

    @Audit(date = "2017-08-08", reviewer = "孙林青")
    @ApiOperation(value = "修改头像", notes = "修改头像。 (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateHeadImg", method = RequestMethod.PUT)
    public Result updateHeadImg(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                @RequestParam @ApiParam(required = true, value = "头像路径") String headImg) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        return memberService.saveHeadImage(memberId, headImg);
    }

}
