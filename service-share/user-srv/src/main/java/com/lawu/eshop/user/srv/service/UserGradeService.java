package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.UserGradeQuery;
import com.lawu.eshop.user.param.UserGradeUpdateParam;
import com.lawu.eshop.user.srv.bo.UserGradeBO;

/**
 * 会员等级
 */
public interface UserGradeService {

    /**
     * 获取等级值对应的抽奖消费积分数
     * @param gradeValue
     * @return
     */
    Integer selectLotteryActivityPointByGradeValue(Byte gradeValue);

    /**
     * 获取所有会员等级列表
     * @return
     */
    List<UserGradeBO> selectGradeList();

    /**
     * 获取距离成长值最近的一个等级
     * @param resultMoney
     * @return
     */
    UserGradeBO selectUserGradeByMinGrowthValue(Integer resultMoney);

    /**
     * 分页查询
     * @param query
     * @return
     */
    Page<UserGradeBO> selectPage(UserGradeQuery query);

    /**
     *
     * @param id
     * @param param
     */
    int updateById(Long id, UserGradeUpdateParam param);

    /**
     *
     * @param param
     * @return
     */
    int save(UserGradeUpdateParam param);

    /**
     *
     * @param id
     * @return
     */
    UserGradeBO selectUserGradeById(Long id);
}
