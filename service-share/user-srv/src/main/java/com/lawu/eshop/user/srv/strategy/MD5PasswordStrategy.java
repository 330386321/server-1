package com.lawu.eshop.user.srv.strategy;

import com.lawu.eshop.utils.MD5;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/27
 */
@Component
public class MD5PasswordStrategy implements PasswordStrategy {

    @Override
    public String encode(String originalPassword) {
        return MD5.MD5Encode(originalPassword);
    }
}
