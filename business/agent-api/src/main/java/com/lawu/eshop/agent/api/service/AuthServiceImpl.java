package com.lawu.eshop.agent.api.service;

import om.lawu.eshop.shiro.AuthService;
import om.lawu.eshop.shiro.model.ShiroRole;
import om.lawu.eshop.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import java.util.*;

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
        shiroRole.setRoleKey("admin");
        List<String> permissionsName = new ArrayList<>();
        permissionsName.add("user:edit");
        shiroRole.setPermissionsKey(permissionsName);
        roles.add(shiroRole);

        shiroUser.setRoles(roles);
        shiroUser.setRolesKey(rolesName);

        return shiroUser;
    }
}
