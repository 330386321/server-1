package com.lawu.eshop.operator.api.service.impl;

import om.lawu.eshop.shiro.AuthService;
import om.lawu.eshop.shiro.model.ShiroRole;
import om.lawu.eshop.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Leach
 * @date 2017/4/18
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public ShiroUser find(String account) {
        return test(account);
    }

    @Override
    public ShiroUser find(String account, String password) {
        return test(account);
    }

    /**
     * 测试方法，TODO remove
     * @param account
     * @return
     */
    private ShiroUser test(String account) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setAccount(account);

        List<ShiroRole> roles = new ArrayList<>();
        Set<String> rolesName = new HashSet<>();

        ShiroRole shiroRole = new ShiroRole();
        shiroRole.setRoleName("admin");
        List<String> permissionsName = new ArrayList<>();
        permissionsName.add("user:edit");
        shiroRole.setPermissionsName(permissionsName);
        roles.add(shiroRole);

        shiroUser.setRoles(roles);
        shiroUser.setRolesName(rolesName);

        return shiroUser;
    }
}
