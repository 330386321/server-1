package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.mall.srv.bo.IndustryTypeBO;
import com.lawu.eshop.mall.srv.converter.IndustryTypeConverter;
import com.lawu.eshop.mall.srv.service.IndustryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "industryType/")
public class IndustryTypeController extends BaseController {

    @Autowired
    private IndustryTypeService industryTypeService;

    /**
     * 查询所有行业
     *
     * @return
     */
    @RequestMapping(value = "listIndustryType", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> listIndustryType() {
        List<IndustryTypeBO> industryTypeBOS = industryTypeService.listIndustryType();
        if (industryTypeBOS == null || industryTypeBOS.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        List<IndustryTypeDTO> industryTypeDTOS = new ArrayList<>();
        for (IndustryTypeBO industryTypeBO : industryTypeBOS) {
            IndustryTypeDTO industryTypeDTO = IndustryTypeConverter.convertDTO(industryTypeBO);
            if (industryTypeDTO != null) {
                industryTypeDTO.setIndustryTypeDTOS(IndustryTypeConverter.convertDTO(industryTypeBO.getIndustryTypeBOList()));
            }
            industryTypeDTOS.add(industryTypeDTO);
        }
        return successGet(industryTypeDTOS);
    }

    /**
     * 查询父行业下的所有行业
     *
     * @return
     */
    @RequestMapping(value = "listIndustryType/{parentId}", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> listIndustryType(@PathVariable Short parentId) {
        List<IndustryTypeBO> industryTypeBOS = industryTypeService.listIndustryTypeByParentId(parentId);
        if (industryTypeBOS == null || industryTypeBOS.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successGet(IndustryTypeConverter.convertDTO(industryTypeBOS));
    }

}
