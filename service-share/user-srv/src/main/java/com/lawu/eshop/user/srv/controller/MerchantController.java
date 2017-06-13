package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.MerchantInviterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.converter.LoginUserConverter;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantInviterConverter;
import com.lawu.eshop.user.srv.converter.RongYunConverter;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.utils.PwdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "withPwd/{account}", method = RequestMethod.GET)
    public Result<LoginUserDTO> find(@PathVariable String account, @RequestParam String pwd) {
        MerchantBO merchantBO = merchantService.find(account, pwd);
        if (merchantBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        return successGet(LoginUserConverter.convert(merchantBO));
    }


    /**
     * 修改登录密码
     *
     * @param id          ID
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     */
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {
        MerchantBO merchantBO = merchantService.getMerchantBOById(id);
        if (merchantBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!PwdUtil.verify(originalPwd, merchantBO.getPwd())) {
            return successGet(ResultCode.VERIFY_PWD_FAIL);
        }
        merchantService.updateLoginPwd(id, newPwd);
        return successCreated();
    }

    /**
     * 重置登录密码
     *
     * @param mobile 账号
     * @param newPwd 新密码
     */
    @RequestMapping(value = "resetLoginPwd/{mobile}", method = RequestMethod.PUT)
    public Result resetLoginPwd(@PathVariable String mobile, @RequestParam String newPwd) {
        MerchantBO merchantBO = merchantService.getMerchantByAccount(mobile);
        if (merchantBO == null) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        merchantService.updateLoginPwd(merchantBO.getId(), newPwd);
        return successCreated();
    }

    /**
     * 根据账号查询商户信息
     *
     * @param account 商户账号
     */
    @RequestMapping(value = "getMerchant/{account}", method = RequestMethod.GET)
    public Result<MerchantDTO> getMerchantByAccount(@PathVariable String account) {
        MerchantBO merchantBO = merchantService.getMerchantByAccount(account);
        if (merchantBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantConverter.convertDTO(merchantBO));
    }
    
    /**
     * 查询商户信息
     *
     * @param merchantId 商户账号
     */
    @RequestMapping(value = "selectMerchantInfo", method = RequestMethod.GET)
    public Result<MerchantSNSDTO> selectMerchantInfo(@RequestParam Long merchantId) {
        MerchantBO merchantBO = merchantService.selectMerchantInfo(merchantId);
        if (merchantBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantConverter.convertSNSDTO(merchantBO));
    }

    
    /**
     * 推荐商家
     *
     * @param pageQuery 用户id
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    @RequestMapping(value = "getMerchantByInviter", method = RequestMethod.POST)
    public Result<Page<MerchantInviterDTO>> getMerchantByInviter(@RequestParam Long userId, @RequestBody MerchantInviterParam pageQuery,byte inviterType) {
        Page<MerchantInviterBO> pageBO = merchantService.getMerchantByInviter(userId, pageQuery,inviterType);
        Page<MerchantInviterDTO> pageDTOS = MerchantInviterConverter.convertPageMIDOTS(pageBO);
        return successGet(pageDTOS);
    }

    /**
     * 商户注册
     *
     * @param registerRealParam 商户注册信息
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterRealParam registerRealParam) {
        merchantService.register(registerRealParam);
        return successCreated();
    }

    /**
     * 增加推送、即时通讯token
     * @param id
     * @param cid
     * @return
     */
    @RequestMapping(value = "setGtAndRongYunInfo/{id}",method = RequestMethod.PUT)
    public Result setGtAndRongYunInfo(@PathVariable("id") Long id,@RequestParam("cid") String cid){
        Integer row =  merchantService.setGtAndRongYunInfo(id,cid);
        if(row == null || row <=0){
            successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }


    @RequestMapping(value = "findMessagePushList",method = RequestMethod.GET)
    Result<List<MessagePushDTO>> findMessagePushList(@RequestParam(value = "area") String area){
        List<MessagePushBO> list = merchantService.findMessagePushList(area);
        if(list == null||list.isEmpty() || list.size() == 0){
          return   successGet(new ArrayList<>());
        }
        List<MessagePushDTO> messagePushDTOS = new ArrayList<>();
        for(MessagePushBO messagePushBO : list){
            MessagePushDTO messagePushDTO = new MessagePushDTO();
            messagePushDTO.setUserNum(messagePushBO.getUserNum());
            messagePushDTO.setGtCid(messagePushBO.getGtCid());
            messagePushDTOS.add(messagePushDTO);
        }
        return successGet(messagePushDTOS);
    }


    /**
     * 根据手机号查询userNum
     * @param moblie
     * @return
     */
    @RequestMapping(value = "findMessagePushByMobile", method = RequestMethod.GET)
    MessagePushDTO findMessagePushByMobile(@RequestParam("moblie") String moblie){
        MessagePushBO messagePushBO = merchantService.findMessagePushByMobile(moblie);
        if(messagePushBO == null){
            return null;
        }
        MessagePushDTO messagePushDTO = new MessagePushDTO();
        messagePushDTO.setUserNum(messagePushBO.getUserNum());
        return messagePushDTO;
    }

    /**
     * 修改头像
     *
     * @param merchantId
     * @param headimg
     * @return
     */
    @RequestMapping(value = "saveHeadImage/{merchantId}", method = RequestMethod.POST)
    public Result<UserHeadImgDTO> saveHeadImage(@PathVariable("merchantId") Long merchantId, @RequestParam String headimg) {
        merchantService.updateHeadImg(headimg, merchantId);
        UserHeadImgDTO userHeadImgDTO = new UserHeadImgDTO();
        userHeadImgDTO.setHeadImg(headimg);
        return successCreated(userHeadImgDTO);
    }
    
    
    /**
     * 获取商家电话
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "selectMobile/{merchantId}", method = RequestMethod.GET)
    public Result<MobileDTO> selectMobile(@PathVariable("merchantId") Long merchantId) {
    	MerchantBO merchantBO=merchantService.getMerchantBOById(merchantId);
    	MobileDTO dto=new MobileDTO();
    	dto.setMobile(merchantBO.getMobile());
        return successCreated(dto);
    }

    /**
     * 根据商家编号查询融云需要的信息
     *
     * @param num
     * @return
     */
    @RequestMapping(value = "getRongYunInfo/{num}", method = RequestMethod.GET)
    Result<RongYunDTO> getRongYunInfo(@PathVariable String num) {
        RongYunBO rongYunBO = merchantService.getRongYunInfoByNum(num);
        if (rongYunBO == null) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successGet(RongYunConverter.convertDTO(rongYunBO));
    }

    /**
     * 根据商家ID查询商家基本信息
     * @param merchantId
     * @return
     * @author yangqh
     * @date 2017年5月22日 上午10:52:54
     */
    @RequestMapping(value = "getMerchantById/{merchantId}", method = RequestMethod.GET)
    public Result<MerchantBaseInfoDTO> getMerchantById(@PathVariable Long merchantId) {
    	MerchantBaseInfoBO merchantBO = merchantService.getMerchantById(merchantId);
        if (merchantBO == null) {
            return successGet(ResultCode.ID_EMPTY);
        }
        MerchantBaseInfoDTO dto = new MerchantBaseInfoDTO();
        dto.setUserNum(merchantBO.getUserNum());
        return successGet(dto);
    }
    /**
     * 根据编号查询商家信息
     *
     * @param num
     * @return
     */
    @RequestMapping(value = "getMerchantByNum", method = RequestMethod.GET)
    public Result<MerchantDTO> getMerchantByNum(@RequestParam String num) {
        MerchantBO merchantBO = merchantService.getMerchantByNum(num);
        if (merchantBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantConverter.convertDTO(merchantBO));
    }
    
    /**
     * 是否注册
     * @param mobile
     * @return
     */
    @RequestMapping(value = "isRegister", method = RequestMethod.GET)
    public Result<Boolean> isRegister(@RequestParam String mobile) {
    	Boolean flag = merchantService.isRegister(mobile);
        return successGet(flag);
    }

    @RequestMapping(value = "delMerchantGtPush", method = RequestMethod.PUT)
    public Result delMerchantGtPush(@RequestParam Long merchantId){
        merchantService.delMerchantGtPush(merchantId);
        return successCreated();
    }

}
