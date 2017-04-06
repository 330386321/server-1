package com.lawu.eshop.user.srv.domain.extend;

import java.io.Serializable;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public class FansMerchantDOView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long memberId;

    private String num;

    private String account;

    private Byte sex;

    private String regionPath;

    private String age;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
