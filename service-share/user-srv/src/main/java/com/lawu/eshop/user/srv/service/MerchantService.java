package com.lawu.eshop.user.srv.service;

/**
 * 商户服务接口
 *
 * @author meishuquan
 * @date 2017/3/22
 */
public interface MerchantService {

    /**
     * 修改密码
     *
     * @param id  主键
     * @param pwd 密码
     */
    void updatePwd(Long id, String pwd);
}
