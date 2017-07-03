package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.MerchantInviterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.domain.extend.MerchantDOView;

import java.util.List;

/**
 * 商户服务接口
 *
 * @author meishuquan
 * @date 2017/3/22
 */
public interface MerchantService {

    /**
     * 修改登录密码
     *
     * @param id     主键
     * @param newPwd 新密码
     */
    void updateLoginPwd(Long id, String newPwd);

    /**
     * 商家信息
     *
     * @param merchantProfileId
     * @return
     */
    MerchantInfoBO findMerchantInfo(Long merchantProfileId);

    /**
     * 根据账号查询商户信息
     *
     * @param account 商户账号
     * @return
     */
    MerchantBO getMerchantByAccount(String account);

    /**
     * 商户注册
     *
     * @param registerRealParam 商户注册信息
     */
    void register(RegisterRealParam registerRealParam);

    /**
     * 根据商户ID查询商户信息
     *
     * @param id 商户ID
     */
    MerchantBO getMerchantBOById(Long id);

    /**
     * 我推荐的商家
     *
     * @param pageQuery
     * @return
     * @author zhangrc
     * @date 2017/03/27
     */
    Page<MerchantInviterBO> getMerchantByInviter(Long userId, MerchantInviterParam pageQuery,byte  inviterType);

    /**
     * 查询会员信息
     *
     * @param account 登录账号
     * @param pwd     密码
     * @return
     */
    MerchantBO find(String account, String pwd);

    Integer setGtAndRongYunInfo(Long id, String cid);

    MerchantBO findMemberByNum(String userNum);

    /**
     * 商家个人中心信息
     * @param merchantId
     * @return
     */
	MerchantBO selectMerchantInfo(Long merchantId);

    List<MessagePushBO> findMessagePushList(String area);

    MessagePushBO findMessagePushByMobile(String moblie);

    void updateHeadImg(String headimg, Long merchantId);

    /**
     * 根据商家编号查询融云需要的信息
     *
     * @param num
     * @return
     */
    RongYunBO getRongYunInfoByNum(String num);

    /**
     * 根据商家ID查询商家基本信息
     * @param merchantId
     * @return
     * @author yangqh
     * @date 2017年5月22日 上午10:53:28
     */
	MerchantBaseInfoBO getMerchantById(Long merchantId);

    /**
     * 根据编号查询商户信息
     *
     * @param num
     * @return
     */
    MerchantBO getMerchantByNum(String num);
    
    /**
     * 判断商家是否注册
     * @param account
     * @return
     */
    Boolean isRegister(String account);

    int delMerchantGtPush(Long merchantId);

    /**
     * 根据商家ID查询商家基本信息
     *
     * @param id
     * @return
     */
    MerchantDOView getMerchantView(Long id);
}
