package com.lawu.eshop.member.api.service.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;
import com.lawu.eshop.member.api.service.OnlineActivityService;

/**
 * @author Sunny
 * @date 2017/3/23
 */
@Component
public class OnlineActivityServiceHystrix implements OnlineActivityService {

    @Override
    public List<OnlineActivityDTO> list(@RequestParam("merchantId") Long merchantId) {
    	
    	List<OnlineActivityDTO> OnlineActivityDTOS = new ArrayList<OnlineActivityDTO>();
    	
        return OnlineActivityDTOS;
    }

	@Override
	public OnlineActivityDTO get(@RequestParam("id") Long id) {

		OnlineActivityDTO onlineActivityDTO = new OnlineActivityDTO();
		onlineActivityDTO.setId(-1L);

		return onlineActivityDTO;
	}

	@Override
	public void save(@ModelAttribute OnlineActivityDTO onlineActivityDTO) {
		
	}

	@Override
	public void delete(@RequestParam("id") Long id) {
		
	}

	@Override
	public void update(@ModelAttribute OnlineActivityDTO onlineActivityDTO) {
		
	}

}
