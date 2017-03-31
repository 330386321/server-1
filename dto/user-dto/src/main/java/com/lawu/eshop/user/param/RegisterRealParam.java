package com.lawu.eshop.user.param;

/**
 * @author meishuquan
 * @date 2017/3/31.
 */
public class RegisterRealParam extends RegisterParam {

    //邀请人ID
    private Long inviterId;

    //邀请人编号
    private String userNum;

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
