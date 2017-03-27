package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;

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
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return 0--成功，1--原始密码不正确
     */
    void updateLoginPwd(Long id, String originalPwd, String newPwd);

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
     * @param registerParam 商户注册信息
     */
    void register(RegisterParam registerParam);

    /**
     * 根据商户ID查询商户信息
     *
     * @param id 商户ID
     */
    MerchantBO getMerchantBOById(Long id );
    
    /**
     * 我推荐的商家 
     * @author zhangrc
     * @date 2017/03/27
     * @param inviterId
     * @param account
     * @param pageParam
     * @return
     */
    Page<MerchantBO> getMerchantByInviter(Long inviterId ,String account,PageParam pageParam);
}
