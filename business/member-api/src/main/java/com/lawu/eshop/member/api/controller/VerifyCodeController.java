package com.lawu.eshop.member.api.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.autotest.client.AutoTesting;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.SmsRecordService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.utils.IpUtil;
import com.lawu.eshop.utils.VerifyCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/3/29.
 */
@Api(tags = "verifyCode")
@RestController
@RequestMapping(value = "verifyCode/")
public class VerifyCodeController extends BaseController {

    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private VerifyCodeService verifyCodeService;
    
    @Autowired
    private MemberService memberService;

    @Audit(date = "2017-03-09", reviewer = "孙林青")
    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码。[1006|1007|1008|1014|1027|2013] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result sendSms(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                          @RequestParam @ApiParam(required = true, value = "图形验证码") String picCode,
                          VerifyCodePurposeEnum purpose) {
    	Result<Boolean> isRegisterFlag= memberService.isExistsMobile(mobile);
    	if(isSuccess(isRegisterFlag)){
    		if(purpose==VerifyCodePurposeEnum.FIND_LOGIN_PWD){ //找回登录密码
    			if(!isRegisterFlag.getModel()){
        			return successGet(ResultCode.MOBILE_IS_NOT_EXIST);
        		}
        	}else if(purpose==VerifyCodePurposeEnum.USER_REGISTER){ //注册
        		if(isRegisterFlag.getModel()){
        			return successGet(ResultCode.ACCOUNT_EXIST);
        		}
        	}
    	}
        Result result = verifyCodeService.verifyPicCode(mobile, picCode);
        if (!isSuccess(result)) {
            return successGet(ResultCode.VERIFY_PIC_CODE_FAIL);
        }
        String ip = IpUtil.getIpAddress(getRequest());
        return smsRecordService.sendSms(mobile, ip, purpose);
    }

    @Audit(date = "2017-03-09", reviewer = "孙林青")
    @ApiOperation(value = "获取短信验证码(无需图形验证码)", notes = "获取短信验证码。[1006|1007|1008|1027|2013] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getSmsCode/{mobile}", method = RequestMethod.GET)
    public Result getSmsCode(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                             VerifyCodePurposeEnum purpose) {
        String ip = IpUtil.getIpAddress(getRequest());
        Result<Boolean> isRegisterFlag= memberService.isExistsMobile(mobile);
    	if(isSuccess(isRegisterFlag)){
    		if(purpose==VerifyCodePurposeEnum.FIND_LOGIN_PWD){ //找回登录密码
    			if(!isRegisterFlag.getModel()){
        			return successGet(ResultCode.MOBILE_IS_NOT_EXIST);
        		}
        	}else if(purpose==VerifyCodePurposeEnum.USER_REGISTER){ //注册
        		if(isRegisterFlag.getModel()){
        			return successGet(ResultCode.ACCOUNT_EXIST);
        		}
        	} 
    		
    	}
        return smsRecordService.sendSms(mobile, ip, purpose);
    }

    @Audit(date = "2017-03-09", reviewer = "孙林青")
    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码。 (梅述全)", httpMethod = "GET")
    @RequestMapping(value = "getPicCode/{mobile}", method = RequestMethod.GET)
    public void getPicCode(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile, VerifyCodePurposeEnum purpose) throws IOException {
        BufferedImage buffImg = new BufferedImage(120, 40, BufferedImage.TYPE_INT_RGB);
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

    @AutoTesting
    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询验证码", notes = "查询验证码。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getVerifyCode/{id}", method = RequestMethod.GET)
    public Result getVerifyCode(@PathVariable @ApiParam(required = true, value = "ID") Long id) {
        return verifyCodeService.getVerifyCodeById(id);
    }
}
