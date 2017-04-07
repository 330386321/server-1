package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.query.NearStoreParam;
import com.lawu.eshop.user.srv.bo.NearStoreBO;
import com.lawu.eshop.user.srv.converter.NearStoreConverter;
import com.lawu.eshop.user.srv.service.NearStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "nearStore/")
public class NearStoreController extends BaseController {

    @Autowired
    private NearStoreService nearStoreService;

    /**
     * 查询附近门店
     *
     * @param nearStoreParam
     * @return
     */
    @RequestMapping(value = "listNearStore", method = RequestMethod.GET)
    public Result<Page<NearStoreDTO>> listNearStore(@RequestBody NearStoreParam nearStoreParam) {
        Page<NearStoreBO> nearStoreBOPage = nearStoreService.listNearStore(nearStoreParam);
        Page<NearStoreDTO> page = new Page<>();
        page.setRecords(NearStoreConverter.convertDTO(nearStoreBOPage.getRecords()));
        page.setTotalCount(nearStoreBOPage.getTotalCount());
        page.setCurrentPage(nearStoreBOPage.getCurrentPage());
        return successGet(page);
    }
}
