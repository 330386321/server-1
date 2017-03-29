package com.lawu.eshop.member.api.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.SmsRecordService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.user.constants.UserInviterTypeEnum;
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
    private SmsRecordService smsRecordService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member （章勇）", httpMethod = "GET")
     @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberInfo", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo() {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<UserDTO> result = memberService.findMemberInfo(memberId);
        return result;
    }

    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息（章勇）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo", method = RequestMethod.PUT)
    public Result updateMemberInfo(@ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam) {
        long id = UserUtil.getCurrentUserId(getRequest());
        Result r = memberService.updateMemberInfo(memberParam, id);
        return r;
    }

    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        long id = UserUtil.getCurrentUserId(getRequest());
        return memberService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @ApiOperation(value = "修改支付密码", notes = "根据会员编号修改支付密码。[1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                               @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviterByAccount/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return memberService.getInviterByAccount(account);
    }

    @ApiOperation(value = "我的E友", notes = "我的E有查询,[1000|1001]（张荣成）", httpMethod = "POST")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<Page<MemberDTO>> findMemberListByUser(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "查询信息") MemberQuery query) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<Page<MemberDTO>> page = memberService.findMemberListByUser(userId, query);
        return page;
    }

    @ApiOperation(value = "注册", notes = "会员注册。[1012|1013|1016] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                           @ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam,
                           UserInviterTypeEnum inviterType) {
        Result accountResult = memberService.getMemberByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return failCreated(ResultCode.RECORD_EXIST);
        }
        Result smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return failCreated(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = (VerifyCodeDTO) smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return failCreated(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        return memberService.register(registerParam, inviterType);
    }

    @ApiOperation(value = "根据账号查询会员信息", notes = "根据账号查询会员信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMember", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberByAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String account = UserUtil.getCurrentAccount(getRequest());
        return memberService.getMemberByAccount(account);
    }

    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码。[1000|1001|1006|1007|1008|1014] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result sendSms(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                          @RequestParam @ApiParam(required = true, value = "图形验证码") String picCode,
                          VerifyCodePurposeEnum purpose) {
        Result result = verifyCodeService.verifyPicCode(mobile, picCode);
        if (!isSuccess(result)) {
            return failCreated(ResultCode.VERIFY_PIC_CODE_FAIL);
        }
        String ip = IpUtil.getIpAddress(getRequest());
        return smsRecordService.sendSms(mobile, ip, purpose);
    }

    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码。 (梅述全)", httpMethod = "GET")
    @RequestMapping(value = "getPicCode/{mobile}", method = RequestMethod.GET)
    public void getPicCode(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile, VerifyCodePurposeEnum purpose) throws IOException {
        BufferedImage buffImg = new BufferedImage(60, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        String picCode = VerifyCodeUtil.getVerifyCode(g);
        verifyCodeService.savePicCode(mobile, picCode, purpose);

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
