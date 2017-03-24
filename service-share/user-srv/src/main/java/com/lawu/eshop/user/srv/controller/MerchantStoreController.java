package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家门店
 * Created by Administrator on 2017/3/24.
 */
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController {

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    @RequestMapping(value = "/merchantStore/{id}", method = RequestMethod.GET)
    public MerchantStoreDTO selectMerchantStore(@PathVariable("id") Long id){

        MerchantStoreInfoBO merchantStoreBO =merchantStoreInfoService.selectMerchantStore(id);
        MerchantStoreDTO merchantStoreDTO = new MerchantStoreDTO();

        return merchantStoreDTO;


    }
}
