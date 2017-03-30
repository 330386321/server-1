package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.property.dto.PointDetailDTO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.domain.PointDetailDO;

/**
 * 积分明细转换器
 *
 * @author Sunny
 * @date 2017/3/30
 */
public class PointDetailConverter {
	
	public static PointDetailBO convert(PointDetailDO pointDetailDO) {
		if (pointDetailDO == null) {
			return null;
		}

		PointDetailBO pointDetailBO = new PointDetailBO();
		BeanUtils.copyProperties(pointDetailDO, pointDetailBO);

		return pointDetailBO;
	}

	public static List<PointDetailBO> convertBOS(List<PointDetailDO> pointDetailDOS) {
		if (pointDetailDOS == null || pointDetailDOS.isEmpty()) {
			return null;
		}

		List<PointDetailBO> pointDetailBOS = new ArrayList<PointDetailBO>();
		for (PointDetailDO pointDetailDO : pointDetailDOS) {
			pointDetailBOS.add(convert(pointDetailDO));
		}

		return pointDetailBOS;
	}
	
	public static PointDetailDTO convert(PointDetailBO pointDetailBO) {
		if (pointDetailBO == null) {
			return null;
		}

		PointDetailDTO pointDetailDTO = new PointDetailDTO();
		BeanUtils.copyProperties(pointDetailBO, pointDetailDTO);

		return pointDetailDTO;
	}
	
	public static List<PointDetailDTO> convertDTOS(List<PointDetailBO> pointDetailBOS) {
		if (pointDetailBOS == null || pointDetailBOS.isEmpty()) {
			return null;
		}

		List<PointDetailDTO> pointDetailDTOS = new ArrayList<PointDetailDTO>();
		for (PointDetailBO pointDetailBO : pointDetailBOS) {
			pointDetailDTOS.add(convert(pointDetailBO));
		}

		return pointDetailDTOS;
	}

}
