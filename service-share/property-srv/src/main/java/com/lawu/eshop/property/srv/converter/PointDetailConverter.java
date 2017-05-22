package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.dto.PointDetailBackageDTO;
import com.lawu.eshop.property.dto.PointDetailDTO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分明细转换器
 *
 * @author Sunny
 * @date 2017/3/30
 */
public class PointDetailConverter {

	public static PointDetailBO convert(PointDetailDO pointDetailDO) {
		PointDetailBO rtn = null;

		if (pointDetailDO == null || pointDetailDO.getId() == null || pointDetailDO.getId() <= 0) {
			return rtn;
		}

		rtn = new PointDetailBO();

		BeanUtils.copyProperties(pointDetailDO, rtn);

		rtn.setDirection(ConsumptionTypeEnum.getEnum(pointDetailDO.getDirection()));

		return rtn;
	}

	public static List<PointDetailBO> convertBOS(List<PointDetailDO> pointDetailDOS) {
		List<PointDetailBO> rtn = null;

		if (pointDetailDOS == null || pointDetailDOS.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<PointDetailBO>();

		for (PointDetailDO item : pointDetailDOS) {
			rtn.add(convert(item));
		}

		return rtn;
	}

	public static PointDetailDTO convert(PointDetailBO pointDetailBO) {
		PointDetailDTO rtn = null;

		if (pointDetailBO == null) {
			return rtn;
		}

		rtn = new PointDetailDTO();
		BeanUtils.copyProperties(pointDetailBO, rtn);
		rtn.setIntegralDate(pointDetailBO.getGmtCreate());

		return rtn;
	}

	public static List<PointDetailDTO> convertDTOS(List<PointDetailBO> pointDetailBOS) {
		List<PointDetailDTO> rtn = new ArrayList<PointDetailDTO>();

		if (pointDetailBOS == null || pointDetailBOS.isEmpty()) {
			return rtn;
		}

		for (PointDetailBO item : pointDetailBOS) {
			rtn.add(convert(item));
		}

		return rtn;
	}
	
	public static Page<PointDetailDTO> convertDTOPage(Page<PointDetailBO> pointDetailBOPage) {
		Page<PointDetailDTO> rtn = new Page<PointDetailDTO>();
		rtn.setCurrentPage(pointDetailBOPage.getCurrentPage());
		rtn.setTotalCount(pointDetailBOPage.getTotalCount());
		rtn.setRecords(convertDTOS(pointDetailBOPage.getRecords()));
		return rtn;
	}

	public static Page<PointDetailBackageDTO> convertBackageDTOPage(Page<PointDetailBO> pointDetailBOPage) {
		Page<PointDetailBackageDTO> rtn = new Page<PointDetailBackageDTO>();
		rtn.setCurrentPage(pointDetailBOPage.getCurrentPage());
		rtn.setTotalCount(pointDetailBOPage.getTotalCount());
		List<PointDetailBackageDTO> pointDetailBackageDTOS = new ArrayList<>();
		if(pointDetailBOPage.getRecords() == null || pointDetailBOPage.getRecords().isEmpty()){
			rtn.setRecords(pointDetailBackageDTOS);
			return rtn;
		}
		for(PointDetailBO pointDetailBO : pointDetailBOPage.getRecords()){
			PointDetailBackageDTO pointDetailBackageDTO = new PointDetailBackageDTO();
			pointDetailBackageDTO.setTitle(pointDetailBO.getTitle());
			pointDetailBackageDTO.setUserNum(pointDetailBO.getUserNum());
			pointDetailBackageDTO.setPoint(pointDetailBO.getPoint());
			pointDetailBackageDTO.setGmtCreate(pointDetailBO.getGmtCreate());
			pointDetailBackageDTOS.add(pointDetailBackageDTO);
		}
		rtn.setRecords(pointDetailBackageDTOS);
		return rtn;
	}

}
