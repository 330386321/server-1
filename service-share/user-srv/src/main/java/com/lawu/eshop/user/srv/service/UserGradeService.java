package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.srv.bo.UserGradeBO;

/**
 * 会员等级
 */
public interface UserGradeService {


    Integer selectLotteryActivityPointByGradeValue(Byte gradeValue);

    List<UserGradeBO> selectGradeList();
}
