package om.lawu.eshop.shiro;

import om.lawu.eshop.shiro.model.ShiroUser;

/**
 * @author Leach
 * @date 2017/4/18
 */
public interface AuthService {

    ShiroUser find(String account);

    ShiroUser find(String account, String password);
}
