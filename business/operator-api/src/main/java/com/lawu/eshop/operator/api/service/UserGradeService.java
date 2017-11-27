package com.lawu.eshop.operator.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.UserGradeDTO;

/**
 * @author meishuquan
 * @date 2017/11/24.
 */
@FeignClient(value = "user-srv")
public interface UserGradeService {

    /**
     * 查询会员等级
     *
     * @return
     * @author meishuquan
     */
    @RequestMapping(method = RequestMethod.GET, value = "grade/selectUserGradeList")
    Result<List<UserGradeDTO>> selectLotteryActivityPointByGradeValue();

}
