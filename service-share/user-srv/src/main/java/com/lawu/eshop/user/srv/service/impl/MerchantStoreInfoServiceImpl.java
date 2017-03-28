package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
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
    @Override
    public MerchantStoreInfoBO selectMerchantStore(Long id) {

        //商家门店基本信息
        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if(merchantStoreDOS.isEmpty()){
            return null;
        }

        //商家店铺扩展信息
        MerchantStoreProfileDOExample example = new MerchantStoreProfileDOExample();
        example.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreProfileDO> merchantStoreProfileDOS = merchantStoreProfileDOMapper.selectByExample(example);

        //商家店铺图片信息
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);

        MerchantStoreInfoBO merchantStoreInfoBO = MerchantStoreConverter.coverter(merchantStoreDOS.get(0));
        if(!merchantStoreProfileDOS.isEmpty()){
            merchantStoreInfoBO.setCompanyAddress(merchantStoreProfileDOS.get(0).getCompanyAddress());
            merchantStoreInfoBO.setCompanyName(merchantStoreProfileDOS.get(0).getCompanyName());
            merchantStoreInfoBO.setRegNumber(merchantStoreProfileDOS.get(0).getRegNumber());
            merchantStoreInfoBO.setLicenseIndate(merchantStoreProfileDOS.get(0).getLicenseIndate());
            merchantStoreInfoBO.setManageType(merchantStoreProfileDOS.get(0).getManageType());
            merchantStoreInfoBO.setCertifType(merchantStoreProfileDOS.get(0).getCertifType());
            merchantStoreInfoBO.setOperatorCardId(merchantStoreProfileDOS.get(0).getOperatorCardId());
            merchantStoreInfoBO.setOperatorName(merchantStoreProfileDOS.get(0).getOperatorName());
        }

        if(!merchantStoreImageDOS.isEmpty()){
            merchantStoreInfoBO.setType(merchantStoreImageDOS.get(0).getType());
            merchantStoreInfoBO.setPath(merchantStoreImageDOS.get(0).getPath());

        }

        return merchantStoreInfoBO;
    }

    @Override
    @Transactional
    public void saveMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam, MerchantStoreTypeEnum storeTypeEnum, CertifTypeEnum certifTypeEnum) {

        //新增门店基本信息
        MerchantStoreDO merchantStoreDO = (MerchantStoreDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam,1);
        merchantStoreDO.setMerchantId(merchantId);
        merchantStoreDO.setGmtCreate(new Date());
        merchantStoreDO.setGmtModified(new Date());
        //设置门店待审核状态
        merchantStoreDO.setStatus(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);
       Integer merchantStoreId = merchantStoreDOMapper.insert(merchantStoreDO);

        //新增商家店铺扩展信息
        MerchantStoreProfileDO merchantStoreProfileDO = (MerchantStoreProfileDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam,2);
        merchantStoreProfileDO.setMerchantId(merchantId);
        merchantStoreProfileDO.setManageType(storeTypeEnum.val);
        merchantStoreProfileDO.setCertifType(certifTypeEnum.val);
        merchantStoreProfileDO.setGmtCreate(new Date());
        merchantStoreProfileDO.setGmtModified(new Date());

        merchantStoreProfileDOMapper.insert(merchantStoreProfileDO);

        MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
        merchantStoreImageDO.setMerchantId(merchantId);
        merchantStoreImageDO.setMerchantStoreId(Long.valueOf(merchantStoreId));
        merchantStoreImageDO.setGmtCreate(new Date());
        merchantStoreImageDO.setGmtModified(new Date());
        //新增门店照
        if(!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getStoreUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店环境照
        if(!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getEnvironmentUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增营业执照
        if(!StringUtils.isEmpty(merchantStoreParam.getLicenseUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getLicenseUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增其他许可证
        if(!StringUtils.isEmpty(merchantStoreParam.getOtherUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getOtherUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店LOGO
        if(!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getLogoUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店手持身份证照
        if(!StringUtils.isEmpty(merchantStoreParam.getIdcardUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getIdcardUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }

    }

    @Override
    public MerchantStoreProfileBO selectStoreInfoByExample(String example,Integer type) {
        MerchantStoreProfileDOExample merchantStoreProfileDOExample = new MerchantStoreProfileDOExample();
        if(type == 1){
            merchantStoreProfileDOExample.createCriteria().andRegNumberEqualTo(example);
        }else{
            merchantStoreProfileDOExample.createCriteria().andOperatorCardIdEqualTo(example);
        }
        List<MerchantStoreProfileDO> merchantStoreProfileDOS = merchantStoreProfileDOMapper.selectByExample(merchantStoreProfileDOExample);
            if (!merchantStoreProfileDOS.isEmpty()) {
              return MerchantStoreConverter.convertBO(merchantStoreProfileDOS.get(0));
            }
        return null;
    }

    @Override
    public void updateMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam,Long merchantStoreId,MerchantStoreTypeEnum storeTypeEnum,CertifTypeEnum certifTypeEnum) {

        MerchantStoreDO merchantStoreDO = (MerchantStoreDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam,1);
        merchantStoreDO.setMerchantId(merchantId);
        //设置门店待审核状态
        merchantStoreDO.setStatus(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);
        merchantStoreDO.setGmtModified(new Date());

        //更新门店信息
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        merchantStoreDOMapper.updateByExample(merchantStoreDO,example);

        //更新门店扩展信息
        MerchantStoreProfileDO merchantStoreProfileDO = (MerchantStoreProfileDO) MerchantStoreConverter.couverDOByParam(merchantStoreParam,2);
        merchantStoreProfileDO.setMerchantId(merchantId);
        merchantStoreProfileDO.setGmtModified(new Date());
        merchantStoreProfileDO.setManageType(storeTypeEnum.val);
        merchantStoreProfileDO.setCertifType(certifTypeEnum.val);
        MerchantStoreProfileDOExample merchantStoreProfileDOExample = new MerchantStoreProfileDOExample();
        merchantStoreProfileDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        merchantStoreProfileDOMapper.updateByExample(merchantStoreProfileDO,merchantStoreProfileDOExample);

        //更新门店图片信息

        //先删除对应商家门店
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        merchantStoreImageDOMapper.deleteByExample(merchantStoreImageDOExample);

        MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
        merchantStoreImageDO.setMerchantId(merchantId);
        merchantStoreImageDO.setMerchantStoreId(merchantStoreId);
        merchantStoreImageDO.setGmtCreate(new Date());
        merchantStoreImageDO.setGmtModified(new Date());
        //新增门店照
        if(!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getStoreUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店环境照
        if(!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getEnvironmentUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增营业执照
        if(!StringUtils.isEmpty(merchantStoreParam.getLicenseUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getLicenseUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增其他许可证
        if(!StringUtils.isEmpty(merchantStoreParam.getOtherUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getOtherUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店LOGO
        if(!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getLogoUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }
        //新增门店手持身份证照
        if(!StringUtils.isEmpty(merchantStoreParam.getIdcardUrl())){
            merchantStoreImageDO.setPath(merchantStoreParam.getIdcardUrl());
            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val);
            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
        }


    }
}
