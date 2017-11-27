package com.lawu.eshop.product.srv.controller;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.SeckillActivityProductBuyPageDTO;
import com.lawu.eshop.product.dto.SeckillActivityProductInformationDTO;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductExtendBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityProductConverter;
import com.lawu.eshop.product.srv.exception.DataNotExistException;
import com.lawu.eshop.product.srv.service.SeckillActivityProductService;
import com.lawu.eshop.utils.DateUtil;

@RestController
@RequestMapping(value = "seckillActivityProduct/")
public class SeckillActivityProductController extends BaseController {
    
    private static Logger logger = LoggerFactory.getLogger(SeckillActivityProductController.class);
    
    @Autowired
    private SeckillActivityProductService seckillActivityProductService;
    
    /**
     * 根据id和查询参数分页查询抢购活动商品列表
     * 用于用户端
     * 
     * @param id 抢购活动id
     * @param param 分页查询参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(value = "page/{id}", method = RequestMethod.PUT)
    public Result<Page<SeckillActivityProductBuyPageDTO>> page(@PathVariable("id") Long id, @RequestBody @Validated SeckillActivityProductPageQueryParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        Page<SeckillActivityProductBO> page = seckillActivityProductService.page(id, param);
        Page<SeckillActivityProductBuyPageDTO> rtn = new Page<>();
        rtn.setCurrentPage(page.getCurrentPage());
        rtn.setTotalCount(page.getTotalCount());
        rtn.setRecords(SeckillActivityProductConverter.convertSeckillActivityProductBuyPageDTOList(page.getRecords()));
        return successCreated(rtn);
    }
    
    /**
     * 查询抢购活动商品信息
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(value = "information/{id}", method = RequestMethod.GET)
    public Result<SeckillActivityProductInformationDTO> information(@PathVariable("id") Long id) {
        SeckillActivityProductExtendBO seckillActivityProductExtendBO = null;
        try {
            seckillActivityProductExtendBO = seckillActivityProductService.information(id);
        } catch (DataNotExistException e) {
            logger.error(e.getMessage(), e);
            return successCreated(ResultCode.NOT_FOUND_DATA, e.getMessage());
        }
        SeckillActivityProductInformationDTO rtn = SeckillActivityProductConverter.convert(seckillActivityProductExtendBO);
        // 倒计时在服务端放入
        Long countdown = null;
        SeckillActivityBO seckillActivityBO = seckillActivityProductExtendBO.getSeckillActivity();
        switch (rtn.getActivityStatus()) {
            case NOT_STARTED:
                countdown = DateUtil.interval(new Date(), seckillActivityBO.getStartDate(), Calendar.MILLISECOND);
                break;
            case PROCESSING:
                countdown = DateUtil.interval(new Date(), seckillActivityBO.getEndDate(), Calendar.MILLISECOND);
                break;
            default:
                break;
        }
        rtn.setCountdown(countdown);
        return successGet(rtn);
    }
}
