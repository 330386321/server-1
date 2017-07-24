package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;
import com.lawu.eshop.member.api.service.OnlineActivityService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class MockOnlineActivityService implements OnlineActivityService {


	@Override
	public List<OnlineActivityDTO> list(@RequestParam("merchantId") Long merchantId) {
		return null;
	}

	@Override
	public OnlineActivityDTO get(@RequestParam("id") Long id) {
		return null;
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
