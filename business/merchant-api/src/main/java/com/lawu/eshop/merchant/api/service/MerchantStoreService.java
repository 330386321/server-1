package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.CashUserInfoDTO;
import com.lawu.eshop.user.dto.MerchantAuditInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.ApplyStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/3/22
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {


    /**
     * 根据商家id查询门店信息
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findMerchantStoreInfo/{id}")
    Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id);

    /**
     * 新增门店信息
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/saveMerchantStoreInfo/{merchantId}")
    Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam);

    /**
     * 修改门店信息TO审核
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/saveMerchantStoreAuditInfo/{merchantStoreId}")
    Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam);

    /**
     * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
     *
     * @param id
     * @return
     * @author Yangqh
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findCashUserInfo/{id}")
    CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id);

    /**
     * 查询门店审核成功和失败审核信息
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "audit/getMerchantAuditInfo/{merchantId}", method = RequestMethod.GET)
    Result<MerchantAuditInfoDTO> getMerchantAuditInfo(@PathVariable(value = "merchantId") Long merchantId);

    /**
     * 加入7天退货保障
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/updateNoReasonReturn/{merchantId}", method = RequestMethod.PUT)
    Result updateNoReasonReturn(@PathVariable("merchantId") Long merchantId);

    /**
     * 查询是否加入7天退货保障
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/findIsNoReasonReturnById", method = RequestMethod.GET)
    Result<Boolean> findIsNoReasonReturnById(@RequestParam("merchantId") Long merchantId);

    /**
     * 申请实体店铺
     *
     * @param merchantId
     * @param param
     * @return
     */
    @RequestMapping(value = "merchantStore/applyPhysicalStore/{merchantId}", method = RequestMethod.PUT)
    public Result applyPhysicalStore(@PathVariable(value = "merchantId") Long merchantId, @ModelAttribute ApplyStoreParam param);

    /**
     * 
     * @param id
     * @return
     * @author yangqh
     * @date 2017年5月10日 下午4:15:30
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/selectMerchantStoreByMId")
    Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long id);

    /**
     * 根据商家ID查询门店名称
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/getNameBymerchantId/{merchantId}")
    Result<String> getNameBymerchantId(@PathVariable("merchantId") Long merchantId);

}
