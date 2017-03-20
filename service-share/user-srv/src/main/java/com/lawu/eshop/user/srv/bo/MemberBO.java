package com.lawu.eshop.user.srv.bo;


/**
 * 会员BO
 *
 * @author Leach
 * @date 2017/3/13
 */
public class MemberBO {

    private Long id;

    private String account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
