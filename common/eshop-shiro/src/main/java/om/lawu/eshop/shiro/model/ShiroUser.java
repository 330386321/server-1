package om.lawu.eshop.shiro.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Leach
 * @date 2017/4/18
 */
public class ShiroUser {

    private String account;

    private String password;

    private List<ShiroRole> roles = new ArrayList<>();

    private Set<String> rolesName = new HashSet<>();

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ShiroRole> getRoles() {
        return roles;
    }

    public void setRoles(List<ShiroRole> roles) {
        this.roles = roles;
    }

    public Set<String> getRolesName() {
        return rolesName;
    }

    public void setRolesName(Set<String> rolesName) {
        this.rolesName = rolesName;
    }
}
