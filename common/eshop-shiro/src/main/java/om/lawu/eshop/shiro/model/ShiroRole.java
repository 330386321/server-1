package om.lawu.eshop.shiro.model;

import java.util.List;

/**
 * @author Leach
 * @date 2017/4/18
 */
public class ShiroRole {

    private String roleName;

    private List<String> permissionsName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(List<String> permissionsName) {
        this.permissionsName = permissionsName;
    }
}
