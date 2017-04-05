package com.lawu.eshop.product.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
//@RefreshScope
public class TestController {
	@Value("${from}")
	private String from;

	@RequestMapping("/from")
	public String from() {

		return this.from;
	}

}
