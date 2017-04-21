package com.lawu.eshop.operator.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import com.lawu.eshop.operator.srv.bo.PermissionBO;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
public interface PermissonService {
     Integer addPerssion(PerssionParam perssionParam);

     Page<PermissionBO> findPerminnionList(PermissionParam param);
}
