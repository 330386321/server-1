package com.lawu.eshop.operator.srv.converter;

import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.srv.bo.PermissionBO;
import com.lawu.eshop.operator.srv.domain.PermissionDO;
import com.lawu.eshop.operator.srv.domain.extend.RolePermissionDOView;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class PermissionConverter {
    public static PermissionBO coverBO(RolePermissionDOView rolePermissionDOView) {
        if (rolePermissionDOView == null) {
            return null;
        }
        PermissionBO permissionBO = new PermissionBO();
        permissionBO.setPermissionName(rolePermissionDOView.getPermissionName());
        permissionBO.setPermissionKey(rolePermissionDOView.getPermissionKey());
        permissionBO.setId(rolePermissionDOView.getId());
        return permissionBO;
    }

    public static PermissionBO cover(PermissionDO p) {
        if (p == null) {
            return null;
        }
        PermissionBO permissionBO = new PermissionBO();
        permissionBO.setId(p.getId());
        permissionBO.setGmtCreate(p.getGmtCreate());
        permissionBO.setPermissionName(p.getPermissionName());
        permissionBO.setPermissionKey(p.getPermissionKey());
        permissionBO.setPermissionUrl(p.getPermissionUrl());
        permissionBO.setParentId(p.getParentId());
        return permissionBO;
    }

    public static PermissionListDTO coverDTO(PermissionBO permissionBO) {
        if (permissionBO == null) {
            return null;
        }
        PermissionListDTO permissionListDTO = new PermissionListDTO();
        permissionListDTO.setId(permissionBO.getId());
        permissionListDTO.setGmtCreate(permissionBO.getGmtCreate());
        permissionListDTO.setPermissionName(permissionBO.getPermissionName());
        permissionListDTO.setPermissionKey(permissionBO.getPermissionKey());
        permissionListDTO.setPermissionUrl(permissionBO.getPermissionUrl());
        return permissionListDTO;
    }
}
