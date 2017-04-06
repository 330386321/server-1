package com.lawu.eshop.property.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PointDetailDTO;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.converter.PointDetailConverter;
import com.lawu.eshop.property.srv.service.PointDetailService;

/**
 * @author Sunny
 * @date 2017/3/30
 */
@RestController
@RequestMapping(value = "pointDetail/")
public class PointDetailController extends BaseController {

    @Autowired
    private PointDetailService pointDetailService;

    /**
     * 根据用户编号和查询参数查询交易明细
     * 
     * @param userNo 用户编号
     * @param pointDetailQueryParam 查询参数
     * @return
     */
    @RequestMapping(value = "findPageByUserNum/{userNum}", method = RequestMethod.POST)
    public Result<Page<PointDetailDTO>> findPageByUserNum(@PathVariable("userNum") String userNum, @RequestBody PointDetailQueryParam pointDetailQueryParam) {
    	
    	Page<PointDetailBO> pointDetailBOPage = pointDetailService.findPageByUserNum(userNum, pointDetailQueryParam);
    	
    	Page<PointDetailDTO> pointDetailDTOPage = new Page<PointDetailDTO>();
    	pointDetailDTOPage.setCurrentPage(pointDetailBOPage.getCurrentPage());
    	pointDetailDTOPage.setTotalCount(pointDetailBOPage.getTotalCount());
    	pointDetailDTOPage.setRecords(PointDetailConverter.convertDTOS(pointDetailBOPage.getRecords()));
    	
        return successCreated(pointDetailDTOPage);
    }
    
    /**
     * 保存积分明细记录
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody PointDetailSaveDataParam param) {
		return successCreated(pointDetailService.save(param));
	}
}
