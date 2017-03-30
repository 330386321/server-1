package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.user.constants.UserInviterTypeEnum;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UpdatePwdParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
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


    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member [1000]（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<UserDTO> result = memberService.findMemberInfo(memberId);
        return result;
    }


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

    /**
     *
     * @param token
     * @param updatePwdParam
     * @return
     * @audit  sunlinqing 2016.03.29
     */
    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successCreated(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        long id = UserUtil.getCurrentUserId(getRequest());
        return memberService.updateLoginPwd(id, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    /**
     *
     * @param token
     * @param updatePwdParam
     * @return
     * @audit  sunlinqing 2016.03.29
     */
    @ApiOperation(value = "修改支付密码", notes = "根据会员编号修改支付密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successCreated(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNo, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    // TODO 2016.03.29 邀请人类型请用枚举
    //@ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviterByAccount/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return memberService.getInviterByAccount(account);
    }

    // TODO 2016.03.29 性别用枚举，该接口请放入InviterController
    //@ApiOperation(value = "我的E友", notes = "我的E有查询,[1000|1001]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<Page<MemberDTO>> findMemberListByUser(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true, value = "查询信息") MemberQuery query) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<Page<MemberDTO>> page = memberService.findMemberListByUser(userId, query);
        return page;
    }

    // TODO 2016.03.29 通过用户编码进行邀请，不要传邀请人类型，类型可以根据编码去判断
    //@ApiOperation(value = "注册", notes = "会员注册。[1012|1013|1016] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                           @ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam,
                           UserInviterTypeEnum inviterType) {
        Result accountResult = memberService.getMemberByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return successCreated(ResultCode.RECORD_EXIST);
        }
        Result smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successCreated(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = (VerifyCodeDTO) smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return successCreated(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        return memberService.register(registerParam, inviterType);
    }

    // TODO 2016.03.29 性别用枚举，该接口请放入InviterController
    //@ApiOperation(value = "根据账号查询会员信息", notes = "根据账号查询会员信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMember", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberByAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String account = UserUtil.getCurrentAccount(getRequest());
        return memberService.getMemberByAccount(account);
    }

    /**
     *
     * @return
     * @audit  sunlinqing 2016.03.29
     */
    @ApiOperation(value = "修改头像", notes = "修改头像。 (章勇)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveHeadImage", method = RequestMethod.PUT)
    public Result saveHeadImage() {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        String headImg = "";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        String originalFilename = file.getOriginalFilename();
                        String fieldName = file.getName();
                        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
                        prefix = prefix.toLowerCase();
                        Map<String, String> retMap = UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_HEAD);
                        String resultFlag = retMap.get("resultFlag");
                        if (!"0".equals(resultFlag)) {
                            return successCreated(resultFlag);
                        }
                        headImg = retMap.get("imgUrl");
                    }
                }
            }
            return memberService.saveHeadImage(memberId, headImg);
        }
        return successCreated();
    }
}
