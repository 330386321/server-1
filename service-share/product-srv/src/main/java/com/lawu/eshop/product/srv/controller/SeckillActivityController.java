package com.lawu.eshop.product.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.SeckillActivityThatDayDTO;
import com.lawu.eshop.product.param.SeckillActivityPageQueryParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityConverter;
import com.lawu.eshop.product.srv.service.SeckillActivityService;

@RestController
@RequestMapping(path = "seckillActivity/")
public class SeckillActivityController extends BaseController {
    
    @Autowired
    private SeckillActivityService seckillActivityService;
    
    /**
     * 根据查询参数分页查询活动列表
     * 用于运营平台
     * 
     * @param param
     * @param bindingResult
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(path = "page", method = RequestMethod.PUT)
    public Result page(@RequestBody @Validated SeckillActivityPageQueryParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return null;
    }
    
    /**
     * 查询当天所有活动
     * 用于用户端
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    @RequestMapping(path = "thatday/list", method = RequestMethod.GET)
    public Result<List<SeckillActivityThatDayDTO>> thatDayList() {
        List<SeckillActivityBO> list = seckillActivityService.thatDayList();
        return successGet(SeckillActivityConverter.convertSeckillActivityThatDayDTOList(list));
    }
    
    /**
     * 查询当天所有活动
     * 用于用户端
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    @RequestMapping(path = "recently/list", method = RequestMethod.GET)
    public Result<List<SeckillActivityThatDayDTO>> recentlyList() {
        List<SeckillActivityBO> list = seckillActivityService.recentlyList();
        return successGet(SeckillActivityConverter.convertSeckillActivityThatDayDTOList(list));
    }

}
