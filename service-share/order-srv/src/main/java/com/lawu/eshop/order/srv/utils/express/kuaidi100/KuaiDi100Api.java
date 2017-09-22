package com.lawu.eshop.order.srv.utils.express.kuaidi100;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lawu.eshop.order.srv.utils.express.kuaidi100.config.KuaiDi100Config;
import com.lawu.eshop.utils.HttpUtil;

@Component
public class KuaiDi100Api {

	@Autowired
	private KuaiDi100Config config;

	/**
	 * 即时查询
	 *
	 * @throws Exception
	 */
	public String orderTraces(String expCode, String expNo) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", config.getAppKey());
		params.put("com", expCode);
		params.put("nu", expNo);
		params.put("show", "0");
		params.put("muti", "1");
		params.put("order", "desc");
		return HttpUtil.doGet(config.getReqUrl(), params);
	}

}
