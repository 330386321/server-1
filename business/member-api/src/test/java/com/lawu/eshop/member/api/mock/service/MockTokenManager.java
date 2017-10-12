package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.authorization.manager.TokenClearType;
import com.lawu.eshop.authorization.manager.TokenManager;
import org.springframework.stereotype.Service;

@Service
public class MockTokenManager implements TokenManager {

	@Override
	public void delRelationship(String account, TokenClearType tokenClearType) {

	}

	@Override
	public void delRelationshipByToken(String token) {

	}

	@Override
	public String createToken(String type, String userNo, Long userId, String account) {
		return "fdsffffffsdafasfasdfsadfsadfsafdd";
	}

	@Override
	public String getAccount(String token) {
		return null;
	}

	@Override
	public TokenClearType getTokenClearType(String token) {
		return null;
	}
}
