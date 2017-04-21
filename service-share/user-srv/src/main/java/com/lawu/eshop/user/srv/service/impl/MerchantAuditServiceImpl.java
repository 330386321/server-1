package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.param.MerchantAuditTypeEnum;
import com.lawu.eshop.user.param.MerchantAuditParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreAuditBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.MerchantStoreAuditDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantAuditService;
import net.sf.json.JSONObject;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@Service
public class MerchantAuditServiceImpl implements MerchantAuditService {
    @Autowired
    private MerchantStoreAuditDOMapper merchantStoreAuditDOMapper;
    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;
    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;
    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Override
    @Transactional
    public void updateMerchantAudit(Long storeAuditId, MerchantAuditParam auditParam) {

        MerchantStoreAuditDO oldAudit = merchantStoreAuditDOMapper.selectByPrimaryKey(storeAuditId);
        if (oldAudit != null) {
            //先更新审核信息表记录
            MerchantStoreAuditDO merchantStoreAuditDO = new MerchantStoreAuditDO();
            merchantStoreAuditDO.setId(storeAuditId);
            merchantStoreAuditDO.setRemark(auditParam.getRemark());
            merchantStoreAuditDO.setStatus(auditParam.getAuditStatusEnum().val);
            merchantStoreAuditDO.setAuditTime(new Date());
            merchantStoreAuditDO.setSynTime(new Date());
            merchantStoreAuditDO.setGmtModified(new Date());
            merchantStoreAuditDO.setAuditorId(auditParam.getAuditorId());
            merchantStoreAuditDOMapper.updateByPrimaryKeySelective(merchantStoreAuditDO);

            //查询门店信息记录
            MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(auditParam.getMerchantStoreId());
            if (merchantStoreDO != null && merchantStoreDO.getId() > 0) {
                MerchantStoreDO newStoreDO = new MerchantStoreDO();
                newStoreDO.setId(merchantStoreDO.getId());
                newStoreDO.setStatus(auditParam.getStoreStatusEnum().val);
                newStoreDO.setGmtModified(new Date());
                if (MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_CHECKED.val == auditParam.getAuditStatusEnum().val) {
                    //审核通过
                    if(MerchantAuditTypeEnum.AUDIT_TYPE_EDIT_INFO.val==auditParam.getTypeEnum().val) {
                        //修改门店资料信息
                        if (MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val != merchantStoreDO.getStatus()) {
                            // 新增记录 修改门店信息状态
                            merchantStoreDOMapper.updateByPrimaryKeySelective(newStoreDO);
                        } else {
                            //修改更新门店信息
                            JSONObject jsonObject = JSONObject.fromObject(oldAudit.getContent());
                            MerchantStoreParam merchantStoreParam = (MerchantStoreParam) JSONObject.toBean(jsonObject, MerchantStoreParam.class);
                            newStoreDO.setName(merchantStoreParam.getName());
                            newStoreDO.setRegionPath(merchantStoreParam.getRegionPath());
                            newStoreDO.setAddress(merchantStoreParam.getAddress());
                            newStoreDO.setLongitude(merchantStoreParam.getLongitude());
                            newStoreDO.setLatitude(merchantStoreParam.getLatitude());
                            newStoreDO.setIndustryPath(merchantStoreParam.getIndustryPath());
                            newStoreDO.setIntro(merchantStoreParam.getIntro());
                            newStoreDO.setPrincipalName(merchantStoreParam.getPrincipalName());
                            newStoreDO.setPrincipalMobile(merchantStoreParam.getPrincipalMobile());
                            newStoreDO.setIndustryName(merchantStoreParam.getIndustryName());
                            merchantStoreDOMapper.updateByPrimaryKeySelective(newStoreDO);
                            //修改更新门店扩展信息
                            MerchantStoreProfileDO profile = new MerchantStoreProfileDO();
                            profile.setId(merchantStoreDO.getId());
                            profile.setCompanyName(merchantStoreParam.getCompanyName());
                            profile.setRegNumber(merchantStoreParam.getRegNumber());
                            profile.setCompanyAddress(merchantStoreParam.getCompanyAddress());
                            profile.setLicenseIndate(merchantStoreParam.getLicenseIndate());
                            profile.setManageType(merchantStoreParam.getManageType().val);
                            profile.setCertifType(merchantStoreParam.getCertifType().val);
                            profile.setOperatorCardId(merchantStoreParam.getOperatorCardId());
                            profile.setOperatorName(merchantStoreParam.getOperatorName());
                            profile.setGmtModified(new Date());
                            merchantStoreProfileDOMapper.updateByPrimaryKeySelective(profile);

                            //更新门店图片信息
                            //先删除对应商家门店图片---逻辑删除
                            MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
                            merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(merchantStoreDO.getId());
                            MerchantStoreImageDO merchantStoreImageDODel = new MerchantStoreImageDO();
                            merchantStoreImageDODel.setStatus(false);
                            merchantStoreImageDOMapper.updateByExampleSelective(merchantStoreImageDODel, merchantStoreImageDOExample);

                            MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
                            merchantStoreImageDO.setMerchantId(merchantStoreDO.getMerchantId());
                            merchantStoreImageDO.setMerchantStoreId(merchantStoreDO.getId());
                            merchantStoreImageDO.setGmtCreate(new Date());
                            merchantStoreImageDO.setGmtModified(new Date());
                            merchantStoreImageDO.setStatus(true);
                            //新增门店照
                            if (!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())) {
                                String storeUrl = merchantStoreParam.getStoreUrl();
                                String lastChar = storeUrl.substring(storeUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    storeUrl = storeUrl.substring(0, storeUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(storeUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }
                            //新增门店环境照
                            if (!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())) {
                                String evUrl = merchantStoreParam.getEnvironmentUrl();
                                String lastChar = evUrl.substring(evUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    evUrl = evUrl.substring(0, evUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(evUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }
                            //新增营业执照
                            if (!StringUtils.isEmpty(merchantStoreParam.getLicenseUrl())) {
                                String licenseUrl = merchantStoreParam.getLicenseUrl();
                                String lastChar = licenseUrl.substring(licenseUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    licenseUrl = licenseUrl.substring(0, licenseUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(licenseUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }
                            //新增其他许可证
                            if (!StringUtils.isEmpty(merchantStoreParam.getOtherUrl())) {
                                String otherUrl = merchantStoreParam.getOtherUrl();
                                String lastChar = otherUrl.substring(otherUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    otherUrl = otherUrl.substring(0, otherUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(otherUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }
                            //新增门店LOGO
                            if (!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())) {
                                String logoUrl = merchantStoreParam.getLogoUrl();
                                String lastChar = logoUrl.substring(logoUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    logoUrl = logoUrl.substring(0, logoUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(logoUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }
                            //新增门店手持身份证照
                            if (!StringUtils.isEmpty(merchantStoreParam.getIdcardUrl())) {
                                String idcardUrl = merchantStoreParam.getIdcardUrl();
                                String lastChar = idcardUrl.substring(idcardUrl.length() - 1);
                                if (",".equals(lastChar)) {
                                    idcardUrl = idcardUrl.substring(0, idcardUrl.length() - 1);
                                }
                                merchantStoreImageDO.setPath(idcardUrl);
                                merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val);
                                merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                            }

                            SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(merchantStoreDO.getMerchantId(), auditParam.getMerchantStoreId(), merchantStoreParam);
                            document.addField("storePic_s", merchantStoreParam.getStoreUrl());
                            document.addField("favoriteNumber_i", merchantStoreDO.getFavoriteNumber());
                            document.addField("averageConsumeAmount_d", merchantStoreDO.getAverageConsumeAmount());
                            document.addField("averageScore_d", merchantStoreDO.getAverageScore());
                            SolrUtil.addSolrDocs(document, SolrUtil.SOLR_MERCHANT_CORE);
                        }
                    }else{
                        //申请实体店铺
                        //先删除对应商家LOGO图片---逻辑删除
                        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
                        merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(merchantStoreDO.getId()).
                                andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
                        MerchantStoreImageDO merchantStoreImageDODel = new MerchantStoreImageDO();
                        merchantStoreImageDODel.setStatus(false);
                        merchantStoreImageDOMapper.updateByExampleSelective(merchantStoreImageDODel, merchantStoreImageDOExample);

                        JSONObject jsonObject = JSONObject.fromObject(oldAudit.getContent());
                        MerchantStoreParam merchantStoreParam = (MerchantStoreParam) JSONObject.toBean(jsonObject, MerchantStoreParam.class);
                        MerchantStoreImageDO merchantStoreImageDO = new MerchantStoreImageDO();
                        merchantStoreImageDO.setMerchantId(merchantStoreDO.getMerchantId());
                        merchantStoreImageDO.setMerchantStoreId(merchantStoreDO.getId());
                        merchantStoreImageDO.setGmtCreate(new Date());
                        merchantStoreImageDO.setGmtModified(new Date());
                        merchantStoreImageDO.setStatus(true);
                        //新增门店照
                        if (!StringUtils.isEmpty(merchantStoreParam.getStoreUrl())) {
                            String storeUrl = merchantStoreParam.getStoreUrl();
                            String lastChar = storeUrl.substring(storeUrl.length() - 1);
                            if (",".equals(lastChar)) {
                                storeUrl = storeUrl.substring(0, storeUrl.length() - 1);
                            }
                            merchantStoreImageDO.setPath(storeUrl);
                            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
                            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                        }
                        //新增门店环境照
                        if (!StringUtils.isEmpty(merchantStoreParam.getEnvironmentUrl())) {
                            String evUrl = merchantStoreParam.getEnvironmentUrl();
                            String lastChar = evUrl.substring(evUrl.length() - 1);
                            if (",".equals(lastChar)) {
                                evUrl = evUrl.substring(0, evUrl.length() - 1);
                            }
                            merchantStoreImageDO.setPath(evUrl);
                            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val);
                            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                        }
                        //新增门店LOGO
                        if (!StringUtils.isEmpty(merchantStoreParam.getLogoUrl())) {
                            String logoUrl = merchantStoreParam.getLogoUrl();
                            String lastChar = logoUrl.substring(logoUrl.length() - 1);
                            if (",".equals(lastChar)) {
                                logoUrl = logoUrl.substring(0, logoUrl.length() - 1);
                            }
                            merchantStoreImageDO.setPath(logoUrl);
                            merchantStoreImageDO.setType(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
                            merchantStoreImageDOMapper.insert(merchantStoreImageDO);
                        }

                    }

                } else {
                    //审核不通过
                    merchantStoreDOMapper.updateByPrimaryKeySelective(newStoreDO);
                }
            }
        }
    }

    @Override
    public MerchantStoreAuditBO getMerchantAuditInfo(Long merchantId) {
        MerchantStoreAuditDOExample example = new MerchantStoreAuditDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusNotEqualTo(MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_UNCHECK.val);
       List<MerchantStoreAuditDO> merchantStoreAuditDOS = merchantStoreAuditDOMapper.selectByExample(example);
       if(merchantStoreAuditDOS.isEmpty()){
           return  null;
       }
        MerchantStoreAuditBO merchantStoreAuditBO = new MerchantStoreAuditBO();
        merchantStoreAuditBO.setStatus(merchantStoreAuditDOS.get(0).getStatus());
        merchantStoreAuditBO.setRemark(merchantStoreAuditDOS.get(0).getRemark());
        return merchantStoreAuditBO;
    }

    @Override
    public MerchantStoreAuditBO getMerchantAuditInfoByUncheck(Long merchantId, Byte status) {
        MerchantStoreAuditDOExample example = new MerchantStoreAuditDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(status).andTypeEqualTo(MerchantAuditTypeEnum.AUDIT_TYPE_EDIT_INFO.val);
        List<MerchantStoreAuditDO> merchantStoreAuditDOS = merchantStoreAuditDOMapper.selectByExample(example);
        if(merchantStoreAuditDOS.isEmpty()){
            return  null;
        }
        MerchantStoreAuditBO merchantStoreAuditBO = new MerchantStoreAuditBO();
        merchantStoreAuditBO.setStatus(merchantStoreAuditDOS.get(0).getStatus());
        merchantStoreAuditBO.setRemark(merchantStoreAuditDOS.get(0).getRemark());
        return merchantStoreAuditBO;
    }
}
