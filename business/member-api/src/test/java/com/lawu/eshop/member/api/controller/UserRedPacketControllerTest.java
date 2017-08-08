/**
 * 
 */
package com.lawu.eshop.member.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lawu.eshop.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.eshop.member.api.MemberApiApplicationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author lihj
 * @date 2017年8月8日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MemberApiApplicationTest.class)
@WebAppConfiguration
public class UserRedPacketControllerTest {

	private MockMvc mvc;

	@Autowired
	private UserRedPacketController userRedPacketController;
	
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(userRedPacketController).addInterceptors(authorizationInterceptor)
				.build();
	}

//	@Test
//	public void addUserRedPacket(){
//		RequestBuilder request = post("/");
//		
//	}
	
	
}
