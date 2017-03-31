package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.MerchantStoreAuditDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.utils.DateUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家门店service
 * Created by Administrator on 2017/3/24.
 */
@Service
public class MerchantStoreInfoServiceImpl implements MerchantStoreInfoService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;

    @Autowired
    MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Autowired
    private MerchantStoreAuditDOMapper merchantStoreAuditDOMapper;

    @Override
    public MerchantStoreInfoBO selectMerchantStore(Long merchantStoreId) {

        //商家门店基本信息

        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(merchantStoreId);
        if (merchantStoreDO == null) {
            return null;
        }

        //商家店铺扩展信息

        MerchantStoreProfileDO merchantStoreProfileDO = merchantStoreProfileDOMapper.selectByPrimaryKey(merchantStoreId);

        //商家店铺图片信息
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(merchantStoreId);
        List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);

        MerchantStoreInfoBO merchantStoreInfoBO = MerchantStoreConverter.coverter(merchantStoreDO);
        if (merchantStoreProfileDO != null) {
            merchantStoreInfoBO.setCompanyAddress(merchantStoreProfileDO.getCompanyAddress());
            merchantStoreInfoBO.setCompanyName(merchantStoreProfileDO.getCompanyName());
            merchantStoreInfoBO.setRegNumber(merchantStoreProfileDO.getRegNumber());
            merchantStoreInfoBO.setLicenseIndate(merchantStoreProfileDO.getLicenseIndate());
            merchantStoreInfoBO.setManageType(merchantStoreProfileDO.getManageType());
            merchantStoreInfoBO.setCertifType(merchantStoreProfileDO.getCertifType());
            merchantStoreInfoBO.setOperatorCardId(merchantStoreProfileDO.getOperatorCardId());
            merchantStoreInfoBO.setOperatorName(merchantStoreProfileDO.getOperatorName());
        }

        if (!merchantStoreImageDOS.isEmpty()) {
            merchantStoreInfoBO.setType(merchantStoreImageDOS.get(0).getType());
            merchantStoreInfoBO.setPath(merchantStoreImageDOS.get(0).getPath());

        }

        return merchantStoreInfoBO;
    }

    @Override
    @Transactional
    public void saveMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam) {

        //新增门店基本信息
        MerchantStoreDO merchantStoreDO = (MerchantStoreDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam, 1);
        merchantStoreDO.setMerchantId(merchantId);
        merchantStoreDO.setGmtCreate(new Date());
        merchantStoreDO.setGmtModified(new Date());
        //设置门店待审核状态
        merchantStoreDO.setStatus(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);
        Integer merchantStoreId = merchantStoreDOMapper.insert(merchantStoreDO);

        //新增商家店铺扩展信息
        MerchantStoreProfileDO merchantStoreProfileDO = (MerchantStoreProfileDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam, 2);
        merchantStoreProfileDO.setMerchantId(merchantId);

        merchantStoreProfileDO.setId(Long.valueOf(merchantStoreId));

        merchantStoreProfileDO.setManageType(merchantStoreParam.getManageType().val);
        merchantStoreProfileDO.setCertifType(merchantStoreParam.getCertifType().val);
        merchantStoreProfileDO.setGmtCreate(new Date());
        merchantStoreProfileDO.setGmtModified(new Date());

        merchantStoreProfileDOMapper.insert(merchantStoreProfileDO);

        MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
        merchantStoreImageDO.setMerchantId(merchantId);
        merchantStoreImageDO.setMerchantStoreId(Long.valueOf(merchantStoreId));
        merchantStoreImageDO.setGmtCreate(new Date());
        merchantStoreImageDO.setGmtModified(new Date());
        merchantStoreImageDO.setStatus(true);
        //新增门店照
        if (!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getStoreUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店环境照
        if (!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getEnvironmentUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增营业执照
        if (!StringUtils.isEmpty(merchantStoreParam.getLicenseUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getLicenseUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增其他许可证
        if (!StringUtils.isEmpty(merchantStoreParam.getOtherUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getOtherUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店LOGO
        if (!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getLogoUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店手持身份证照
        if (!StringUtils.isEmpty(merchantStoreParam.getIdcardUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getIdcardUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }

    }

    @Override
    public MerchantStoreProfileBO selectStoreInfoByExample(String example, Integer type) {
        MerchantStoreProfileDOExample merchantStoreProfileDOExample = new MerchantStoreProfileDOExample();
        if (type == 1) {
            merchantStoreProfileDOExample.createCriteria().andRegNumberEqualTo(example);
        } else {
            merchantStoreProfileDOExample.createCriteria().andOperatorCardIdEqualTo(example);
        }
        List<MerchantStoreProfileDO> merchantStoreProfileDOS = merchantStoreProfileDOMapper.selectByExample(merchantStoreProfileDOExample);
        if (!merchantStoreProfileDOS.isEmpty()) {
            return MerchantStoreConverter.convertBO(merchantStoreProfileDOS.get(0));
        }
        return null;
    }

    @Override
    @Transactional
    public void updateMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam, Long merchantStoreId) {

        MerchantStoreDO merchantStoreDO = (MerchantStoreDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam, 1);
        //设置门店待审核状态
        merchantStoreDO.setStatus(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);
        merchantStoreDO.setGmtModified(new Date());

        //更新门店信息
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andIdEqualTo(merchantStoreId);
        merchantStoreDOMapper.updateByExample(merchantStoreDO, example);

        //更新门店扩展信息
        MerchantStoreProfileDO merchantStoreProfileDO = (MerchantStoreProfileDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam, 2);
        merchantStoreProfileDO.setGmtModified(new Date());
        merchantStoreProfileDO.setManageType(merchantStoreParam.getManageType().val);
        merchantStoreProfileDO.setCertifType(merchantStoreParam.getCertifType().val);
        MerchantStoreProfileDOExample merchantStoreProfileDOExample = new MerchantStoreProfileDOExample();
        merchantStoreProfileDOExample.createCriteria().andIdEqualTo(merchantStoreId);
        merchantStoreProfileDOMapper.updateByExample(merchantStoreProfileDO, merchantStoreProfileDOExample);

        //更新门店图片信息

        //先删除对应商家门店图片---逻辑删除
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(merchantStoreId);
        MerchantStoreImageDO merchantStoreImageDODel = new MerchantStoreImageDO();
        merchantStoreImageDODel.setStatus(false);
        merchantStoreImageDOMapper.updateByExampleSelective(merchantStoreImageDODel,merchantStoreImageDOExample);

        MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
        merchantStoreImageDO.setMerchantId(merchantId);
        merchantStoreImageDO.setMerchantStoreId(merchantStoreId);
        merchantStoreImageDO.setGmtCreate(new Date());
        merchantStoreImageDO.setGmtModified(new Date());
        merchantStoreImageDO.setStatus(true);
        //新增门店照
        if (!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getStoreUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店环境照
        if (!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getEnvironmentUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增营业执照
        if (!StringUtils.isEmpty(merchantStoreParam.getLicenseUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getLicenseUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增其他许可证
        if (!StringUtils.isEmpty(merchantStoreParam.getOtherUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getOtherUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店LOGO
        if (!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getLogoUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店手持身份证照
        if (!StringUtils.isEmpty(merchantStoreParam.getIdcardUrl())) {
            merchantStoreImageDO.setPath(merchantStoreParam.getIdcardUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }


    }

    @Override
    public MerchantStoreInfoBO selectMerchantStoreByMId(Long merchantId) {
        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if(merchantStoreDOS.isEmpty()){
            return null;
        }
        return  MerchantStoreConverter.coverter(merchantStoreDOS.get(0));
    }

    @Override
    @Transactional
    public void saveMerchantStoreAuditInfo(Long merchantId, MerchantStoreParam merchantStoreParam, Long merchantStoreId) {

        MerchantStoreAuditDO merchantStoreAuditDO = new MerchantStoreAuditDO();
        merchantStoreAuditDO.setMerchantStoreId(merchantStoreId);
        merchantStoreAuditDO.setMerchantId(merchantId);
        Map<String,String> map = new HashMap<>();
        map.put("name",merchantStoreParam.getName());//店铺名称
        map.put("regionPath",merchantStoreParam.getRegionPath());
        map.put("address",merchantStoreParam.getAddress());
        map.put("longitude",String.valueOf(merchantStoreParam.getLongitude()));
        map.put("latitude",String.valueOf(merchantStoreParam.getLatitude()));
        map.put("industryPath",merchantStoreParam.getIndustryPath());
        map.put("intro",merchantStoreParam.getIntro());
        map.put("principalName",merchantStoreParam.getPrincipalName());
        map.put("principalMobile",merchantStoreParam.getPrincipalMobile());
        map.put("companyName",merchantStoreParam.getCompanyName());
        map.put("regNumber",merchantStoreParam.getRegNumber());
        map.put("companyAddress",merchantStoreParam.getCompanyAddress());
        map.put("licenseIndate", DateUtil.getDateTimeFormat(merchantStoreParam.getLicenseIndate()));
        map.put("manageType",String.valueOf(merchantStoreParam.getManageType()));
        map.put("certifType",String.valueOf(merchantStoreParam.getCertifType()));
        map.put("operatorCardId",merchantStoreParam.getOperatorCardId());
        map.put("operatorName",merchantStoreParam.getOperatorName());
        map.put("storeUrl",merchantStoreParam.getStoreUrl());
        map.put("environmentUrl",merchantStoreParam.getEnvironmentUrl());
        map.put("idcardUrl",merchantStoreParam.getIdcardUrl());
        map.put("logoUrl",merchantStoreParam.getLogoUrl());
        map.put("otherUrl",merchantStoreParam.getOtherUrl());
        JSONArray json = JSONArray.fromObject(map);
        merchantStoreAuditDO.setContent(json.toString());
        merchantStoreAuditDO.setGmtModified(new Date());
        merchantStoreAuditDO.setGmtCreate(new Date());
        merchantStoreAuditDO.setStatus(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);
        merchantStoreAuditDOMapper.insert(merchantStoreAuditDO);
    }
    
}
