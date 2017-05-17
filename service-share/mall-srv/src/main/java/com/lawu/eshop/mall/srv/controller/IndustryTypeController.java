package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.mall.srv.bo.IndustryTypeBO;
import com.lawu.eshop.mall.srv.converter.IndustryTypeConverter;
import com.lawu.eshop.mall.srv.service.IndustryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            return successGet(new ArrayList<>());
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
            return successGet(new ArrayList<>());
        }
        return successGet(IndustryTypeConverter.convertDTO(industryTypeBOS));
    }

    /**
     * 查询所有列表
     * @return
     */
    @RequestMapping(value = "getAllIndustryList", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> getAllIndustryList() {
        List<IndustryTypeBO> industryTypeBOS = industryTypeService.getAllIndustryList();
        if (industryTypeBOS == null || industryTypeBOS.isEmpty()) {
            return successGet(new ArrayList<>());
        }
        List<IndustryTypeDTO> industryTypeDTOS = new ArrayList<>();
        for (IndustryTypeBO industryTypeBO : industryTypeBOS) {
            IndustryTypeDTO industryTypeDTO = IndustryTypeConverter.convertDTO(industryTypeBO);
            industryTypeDTOS.add(industryTypeDTO);
        }
        return successGet(industryTypeDTOS);
    }

}
