package com.lawu.eshop.user.srv.bo;

/**
 * 邀请人BO
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public class InviterBO {

    private Long inviterId;

    private Byte inviterType;

    private String inviterName;

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Byte getInviterType() {
        return inviterType;
    }

    public void setInviterType(Byte inviterType) {
        this.inviterType = inviterType;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }
}
