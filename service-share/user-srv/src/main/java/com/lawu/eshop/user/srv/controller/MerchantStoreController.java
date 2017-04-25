package com.lawu.eshop.user.srv.controller;


import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.ApplyStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.service.MerchantAuditService;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.user.srv.service.MerchantStoreService;
import com.lawu.eshop.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 商家门店
 * Created by Administrator on 2017/3/24.
 */
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController extends BaseController {

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantAuditService merchantAuditService;


    /**
     * 门店信息查询
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "selectMerchantStoreByMId", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long merchantId) {

        MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(merchantId);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.convertStoreDTO(merchantStoreBO);
        return successGet(merchantStoreDTO);


    }


    /**
     * 门店信息查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id) {

        MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStore(id);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.coverDTO(merchantStoreBO);

        return successGet(merchantStoreDTO);


    }

    /**
     * 新增门店信息
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息（）
     * @return
     */
    @RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId,
                                        @RequestBody MerchantStoreParam merchantStoreParam) {

        //判断门店是否存在
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (merchantStoreInfoBO != null) {
            return successCreated(ResultCode.RECORD_EXIST);
        }
        //判断该营业执照是否存在相同记录
        if (!StringUtils.isEmpty(merchantStoreParam.getRegNumber())) {
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(), 1);
            if (merchantStoreProfileBO != null) {
                return successCreated(ResultCode.RECORD_EXIST);
            }
        }

        //判断该身份证号是否存在相同记录
        if (!StringUtils.isEmpty(merchantStoreParam.getOperatorCardId())) {
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getOperatorCardId(), 2);
            if (merchantStoreProfileBO != null) {
                return successCreated(ResultCode.RECORD_EXIST);
            }
        }

        merchantStoreInfoService.saveMerchantStoreInfo(merchantId, merchantStoreParam);

        return successCreated();
    }

    /**
     * 修改门店信息
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息
     * @return
     */
    @RequestMapping(value = "updateMerchantStoreInfo/{merchantStoreId}", method = RequestMethod.PUT)
    public Result updateMerchantStoreInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStore(merchantStoreId);
        if (merchantStoreInfoBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }

        merchantStoreInfoService.updateMerchantStoreInfo(merchantId, merchantStoreParam, merchantStoreId);

        return successCreated();
    }

    /**
     * 根据商家ID获取商家门店的名称
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "getNameBymerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<String> getNameByMerchantId(@PathVariable("merchantId") Long merchantId) {

        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);

        if (merchantStoreInfoBO == null || StringUtils.isEmpty(merchantStoreInfoBO.getName())) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }

        return successGet(merchantStoreInfoBO.getName());
    }

    /**
     * 增加门店审核信息记录
     *
     * @param merchantStoreId
     * @param merchantId
     * @param merchantStoreParam
     * @return
     */
    @RequestMapping(value = "saveMerchantStoreAuditInfo/{merchantStoreId}", method = RequestMethod.POST)
    public Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
        //查询是否存在未审核记录
        MerchantStoreAuditBO auditBO = merchantAuditService.getMerchantAuditInfoByUncheck(merchantId, MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_UNCHECK.val);
        if (auditBO != null) {
            return successCreated(ResultCode.MERCHANT_STORE_AUDIT_EXIST);
        }
        merchantStoreInfoService.saveMerchantStoreAuditInfo(merchantId, merchantStoreParam, merchantStoreId);
        return successCreated();
    }

    /**
     * 根据商家ID查询该商家是否支持七天无理由退货
     *
     * @param merchantId
     * @return
     * @author Yangqh
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "findIsNoReasonReturnById", method = RequestMethod.GET)
    public Result findIsNoReasonReturnById(@RequestParam Long merchantId) {

        if (merchantId == null || merchantId == 0L) {
            return successCreated(ResultCode.ID_EMPTY);
        }
        MerchantStoreInfoBO storeBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (storeBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successCreated(storeBO.getIsNoReasonReturn());
    }

    /**
     * 根据商家ID列表批量查询该商家是否支持七天无理由退货
     *
     * @param merchantIdList
     */
    @RequestMapping(value = "selectNoReasonReturnByMerchantIds", method = RequestMethod.GET)
    public Result<List<MerchantStoreNoReasonReturnDTO>> selectNoReasonReturnByMerchantIds(@RequestParam List<Long> merchantIdList) {
        if (merchantIdList == null || merchantIdList.isEmpty()) {
            return successGet(ResultCode.ID_EMPTY);
        }

        List<MerchantStoreNoReasonReturnBO> merchantStoreNoReasonReturnBOList = merchantStoreInfoService.selectNoReasonReturnByMerchantIds(merchantIdList);

        if (merchantStoreNoReasonReturnBOList == null || merchantStoreNoReasonReturnBOList.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }

        return successGet(MerchantStoreConverter.convertMerchantStoreNoReasonReturnDTOList(merchantStoreNoReasonReturnBOList));
    }

    /**
     * 根据门店ID查询门店详细信息
     *
     * @param id
     * @param memberId
     * @return
     */
    @RequestMapping(value = "storeDetail/{id}", method = RequestMethod.GET)
    public Result<StoreDetailDTO> storeDetail(@PathVariable Long id, @RequestParam Long memberId) {
        StoreDetailBO storeDetailBO = merchantStoreInfoService.getStoreDetailById(id, memberId);
        if (storeDetailBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantStoreConverter.convertDTO(storeDetailBO));
    }

    /**
     * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
     *
     * @param id
     * @return
     * @throws Exception
     * @author Yangqh
     */
    @RequestMapping(value = "findCashUserInfo/{id}", method = RequestMethod.GET)
    public CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id) throws Exception {
        CashUserInfoBO cashUserInfoBO = merchantStoreInfoService.findCashUserInfo(id);
        if (cashUserInfoBO == null) {
            return null;
        }
        CashUserInfoDTO dto = new CashUserInfoDTO();
        BeanUtil.copyProperties(cashUserInfoBO, dto);
        return dto;
    }

    /**
     * 买单查询商家名称和门店图片
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "findStoreNameAndImgByMerchantId/{merchantId}")
    public MerchantStoreDTO findStoreNameAndImgByMerchantId(@PathVariable("merchantId") Long merchantId) {
        if (merchantId == null) {
            return null;
        }
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.findStoreNameAndImgByMerchantId(merchantId);
        if (merchantStoreInfoBO == null) {
            return null;
        }
        MerchantStoreDTO merchantStoreDTO = new MerchantStoreDTO();
        merchantStoreDTO.setName(merchantStoreInfoBO.getName());
        merchantStoreDTO.setStoreUrl(merchantStoreInfoBO.getStoreUrl());
        return merchantStoreDTO;
    }

    /**
     * 申请实体店铺
     *
     * @return
     */
    @RequestMapping(value = "applyPhysicalStore/{merchantId}", method = RequestMethod.PUT)
    public Result applyPhysicalStore(@PathVariable(value = "merchantId") Long merchantId, @RequestBody ApplyStoreParam param) {
        if (merchantId == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (merchantStoreBO == null) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }
        //添加审核记录
        Integer row = merchantStoreInfoService.applyPhysicalStore(merchantId, merchantStoreBO.getMerchantStoreId(), param);
        if (row < 0) {
            return successCreated(ResultCode.MERCHANT_STORE_AUDIT_EXIST);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 加入7天退货保障
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "updateNoReasonReturn/{merchantId}")
    public Result updateNoReasonReturn(@PathVariable("merchantId") Long merchantId) {
        MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(merchantId);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.MERCHANT_STORE_NO_EXIST);
        }
        merchantStoreService.updateNoReasonReturn(merchantStoreBO.getId());
        return successCreated();
    }

    /**
     * 要购物门店详情
     *
     * @param id
     * @param memberId
     * @return
     */
    @RequestMapping(value = "shoppingStoreDetail/{id}", method = RequestMethod.GET)
    public Result<ShoppingStoreDetailDTO> shoppingStoreDetail(@PathVariable Long id, @RequestParam Long memberId) {
        ShoppingStoreDetailBO shoppingStoreDetailBO = merchantStoreInfoService.getShoppingStoreDetailById(id, memberId);
        if (shoppingStoreDetailBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantStoreConverter.convertDTO(shoppingStoreDetailBO));
    }

    /**
     * 根据门店ID查询门店信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getMerchantStore/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> getMerchantStore(@PathVariable Long id) {
        MerchantStoreBO merchantStoreBO = merchantStoreService.getMerchantStoreById(id);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MerchantStoreConverter.convertStoreDTO(merchantStoreBO));
    }

    @RequestMapping(value = "selectAllMerchantStore/{id}", method = RequestMethod.POST)
    public Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(MerchantStoreParam param){
    	 List<MerchantStoreBO> boList = merchantStoreService.selectAllMerchantStore(param);
    	 List<MerchantStorePlatDTO> list=new ArrayList<>();
         if (!boList.isEmpty()) {
            for (MerchantStoreBO merchantStoreBO : boList) {
            	MerchantStorePlatDTO dto=new MerchantStorePlatDTO();
            	dto.setMerchantStoreId(merchantStoreBO.getId());
            	dto.setName(merchantStoreBO.getName());
            	list.add(dto);
			}
         }
         return successGet(list);
    }

    /**
     * 查询所有审核通过的门店
     *
     * @return
     */
    @RequestMapping(value = "listMerchantStore", method = RequestMethod.GET)
    public Result<List<MerchantStoreDTO>> listMerchantStore() {
        List<MerchantStoreBO> merchantStoreBOS = merchantStoreService.listMerchantStore();
        if (merchantStoreBOS == null || merchantStoreBOS.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successGet(MerchantStoreConverter.convertStoreDTO(merchantStoreBOS));
    }

    /**
     * 更新门店统计数据，同时更新solr
     *
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value = "updateStoreStatistics/{id}", method = RequestMethod.PUT)
    public Result updateStoreStatistics(@PathVariable Long id, @RequestBody StoreStatisticsParam param) {
        MerchantStoreBO merchantStoreBO = merchantStoreService.getMerchantStoreById(id);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        merchantStoreService.updateStoreStatisticsById(id, param);
        return successCreated();
    }

}
