package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.MerchantInviterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;

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
    Page<MerchantInviterBO> getMerchantByInviter(Long userId, MerchantInviterParam pageQuery);

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
}
