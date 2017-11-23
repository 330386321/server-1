package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.product.constant.ActivityStatusEnum;
import com.lawu.eshop.product.dto.SeckillActivityJoinDTO;
import com.lawu.eshop.product.dto.SeckillActivityManagerDTO;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityManageBO;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.extend.SeckillActivityDOView;

/**
 * 参加活动转换类、
 * 
 * @author zhangrc
 * @date 2017/11/23
 */
public class SeckillActivityJoinConverter {
	
	
	public static List<SeckillActivityJoinBO> seckillActivityJoinBOConverter(List<SeckillActivityDO> list){
		List<SeckillActivityJoinBO>  joinList = new ArrayList<>();
		if(list.isEmpty()){
			return joinList;
		}
		
		for (SeckillActivityDO SeckillActivityDO : list) {
			SeckillActivityJoinBO seckillActivityJoinBO = new SeckillActivityJoinBO();
			seckillActivityJoinBO.setId(SeckillActivityDO.getId());
			seckillActivityJoinBO.setName(SeckillActivityDO.getName());
			seckillActivityJoinBO.setPicture(SeckillActivityDO.getPicture());
			seckillActivityJoinBO.setSellingPrice(SeckillActivityDO.getSellingPrice());
			seckillActivityJoinBO.setStartDate(SeckillActivityDO.getStartDate());
			joinList.add(seckillActivityJoinBO);
		}
		
		return joinList;
		
	}
	
	public static List<SeckillActivityJoinDTO> seckillActivityJoinDTOConverter(List<SeckillActivityJoinBO> list){
		List<SeckillActivityJoinDTO>  joinList = new ArrayList<>();
		if(list.isEmpty()){
			return joinList;
		}
		
		for (SeckillActivityJoinBO seckillActivityBO : list) {
			SeckillActivityJoinDTO seckillActivityJoinDTO = new SeckillActivityJoinDTO();
			seckillActivityJoinDTO.setId(seckillActivityBO.getId());
			seckillActivityJoinDTO.setName(seckillActivityBO.getName());
			seckillActivityJoinDTO.setPicture(seckillActivityBO.getPicture());
			seckillActivityJoinDTO.setSellingPrice(seckillActivityBO.getSellingPrice());
			seckillActivityJoinDTO.setStartDate(seckillActivityBO.getStartDate());
			joinList.add(seckillActivityJoinDTO);
		}
		
		return joinList;
		
	}

	public static List<SeckillActivityManageBO> seckillActivityJoinManageBOConverter(List<SeckillActivityDOView> list) {
		List<SeckillActivityManageBO>  joinList = new ArrayList<>();
		if(list.isEmpty()){
			return joinList;
		}
		
		for (SeckillActivityDOView seckillActivityDOView : list) {
			SeckillActivityManageBO seckillActivityManageBO = new SeckillActivityManageBO();
			seckillActivityManageBO.setId(seckillActivityDOView.getId());
			seckillActivityManageBO.setName(seckillActivityDOView.getName());
			seckillActivityManageBO.setPicture(seckillActivityDOView.getPicture());
			seckillActivityManageBO.setSellingPrice(seckillActivityDOView.getSellingPrice());
			seckillActivityManageBO.setStartDate(seckillActivityDOView.getStartDate());
			seckillActivityManageBO.setActivityStatusEnum(ActivityStatusEnum.getEnum(seckillActivityDOView.getActivityStatus()));
			joinList.add(seckillActivityManageBO);
		}
		
		return joinList;
	}
	
	
	public static List<SeckillActivityManagerDTO> seckillActivityJoinManageDTOConverter(List<SeckillActivityManageBO> list) {
		List<SeckillActivityManagerDTO>  joinList = new ArrayList<>();
		if(list.isEmpty()){
			return joinList;
		}
		
		for (SeckillActivityManageBO seckillActivityManageBO : list) {
			SeckillActivityManagerDTO seckillActivityManagerDTO = new SeckillActivityManagerDTO();
			seckillActivityManagerDTO.setId(seckillActivityManageBO.getId());
			seckillActivityManagerDTO.setName(seckillActivityManageBO.getName());
			seckillActivityManagerDTO.setPicture(seckillActivityManageBO.getPicture());
			seckillActivityManagerDTO.setSellingPrice(seckillActivityManageBO.getSellingPrice());
			seckillActivityManagerDTO.setStartDate(seckillActivityManageBO.getStartDate());
			seckillActivityManagerDTO.setActivityStatusEnum(seckillActivityManageBO.getActivityStatusEnum());
			joinList.add(seckillActivityManagerDTO);
		}
		
		return joinList;
	}
	

}
