package com.lawu.eshop.ad.srv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.ad.srv.bo.MemberAdRecodeCommissionBO;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.utils.BeanUtil;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午5:55:03
 *
 */
@RestController
@RequestMapping(value = "commission/")
public class ClickAdCommissionController extends BaseController{
	
	@Resource
	private MemberAdRecordService memberAdRecordService;
	
	
	/**
	 * 查询未计算提成的用户点击广告记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getNoneCommissionAds", method = RequestMethod.GET)
    public List<MemberAdRecodeCommissionDTO> getNoneCommissionAds() throws Exception {
		List<MemberAdRecodeCommissionBO> bos = memberAdRecordService.getNoneCommissionAds();
		List<MemberAdRecodeCommissionDTO> dtos = new ArrayList<MemberAdRecodeCommissionDTO>();
		for(MemberAdRecodeCommissionBO bo : bos){
			MemberAdRecodeCommissionDTO dto = new MemberAdRecodeCommissionDTO();
			BeanUtil.copyProperties(bo, dto);
			dtos.add(dto);
		}
		return dtos;
    }
	
	

}
