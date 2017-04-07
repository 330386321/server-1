package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.query.NearStoreParam;
import com.lawu.eshop.user.srv.bo.NearStoreBO;
import org.springframework.stereotype.Service;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Service
public interface NearStoreService {

    /**
     * 查询附近门店
     *
     * @param nearStoreParam
     * @return
     */
    Page<NearStoreBO> listNearStore(NearStoreParam nearStoreParam);
}
