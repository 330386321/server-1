package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.param.StoreIndexParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreService;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.RandomUtil;
import org.apache.solr.common.SolrDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MerchantStoreServiceImplTest {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;

    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private SolrService solrService;

    @Transactional
    @Rollback
    @Test
    public void selectMerchantStore() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(200L);
        Assert.assertNotNull(merchantStoreBO);
    }

    @Transactional
    @Rollback
    @Test
    public void updateNoReasonReturn() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(false);
        merchantStoreDOMapper.insertSelective(storeDO);

        merchantStoreService.updateNoReasonReturn(storeDO.getId());
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(storeDO.getId());
        Assert.assertNotNull(merchantStoreDO);
        Assert.assertTrue(merchantStoreDO.getIsNoReasonReturn());
    }

    @Transactional
    @Rollback
    @Test
    public void getMerchantStoreById() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreBO merchantStoreBO = merchantStoreService.getMerchantStoreById(storeDO.getId());
        Assert.assertNotNull(merchantStoreBO);
    }

    @Transactional
    @Rollback
    @Test
    public void selectAllMerchantStore() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreParam param = new MerchantStoreParam();
        List<MerchantStoreBO> storeBOS = merchantStoreService.selectAllMerchantStore(param);
        Assert.assertNotNull(storeBOS);
        Assert.assertEquals(1, storeBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void listMerchantStore() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreProfileDO profileDO = new MerchantStoreProfileDO();
        profileDO.setId(storeDO.getId());
        profileDO.setMerchantId(200L);
        profileDO.setManageType(DataTransUtil.intToByte(2));
        profileDO.setCertifType(DataTransUtil.intToByte(2));
        merchantStoreProfileDOMapper.insertSelective(profileDO);

        ListMerchantStoreParam param = new ListMerchantStoreParam();
        param.setStatus(DataTransUtil.intToByte(1));
        param.setManageType(DataTransUtil.intToByte(2));
        List<MerchantStoreBO> storeBOS = merchantStoreService.listMerchantStore(param);
        Assert.assertNotNull(storeBOS);
        Assert.assertEquals(1, storeBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void updateStoreStatisticsById() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        StoreStatisticsParam param = new StoreStatisticsParam();
        param.setAverageScore(new BigDecimal(80));
        merchantStoreService.updateStoreStatisticsById(storeDO.getId(), param);
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(storeDO.getId());
        Assert.assertNotNull(merchantStoreDO);
        Assert.assertEquals(80, merchantStoreDO.getAverageScore().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void updateStoreIndex() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        merchantStoreService.updateStoreIndex(storeDO.getId());
        SolrDocument solrDocument = solrService.getSolrDocsById(storeDO.getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        Assert.assertNull(solrDocument);
    }

    @Transactional
    @Rollback
    @Test
    public void rebuildStoreIndex() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreProfileDO profileDO = new MerchantStoreProfileDO();
        profileDO.setId(storeDO.getId());
        profileDO.setMerchantId(200L);
        profileDO.setManageType(DataTransUtil.intToByte(2));
        profileDO.setCertifType(DataTransUtil.intToByte(2));
        merchantStoreProfileDOMapper.insertSelective(profileDO);

        List<StoreIndexParam> indexParamList = new ArrayList<>();
        StoreIndexParam indexParam = new StoreIndexParam();
        indexParam.setMerchantStoreId(storeDO.getId());
        indexParam.setFavoreInfo("满100减10");
        indexParam.setDiscountPackage("两人豪华午餐");
        indexParam.setDiscountOrdinal(0.9);
        indexParamList.add(indexParam);
        merchantStoreService.rebuildStoreIndex(indexParamList);
        SolrDocument solrDocument = solrService.getSolrDocsById(storeDO.getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        Assert.assertNull(solrDocument);
    }

    @Transactional
    @Rollback
    @Test
    public void delInvalidStoreIndex() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(0));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreProfileDO profileDO = new MerchantStoreProfileDO();
        profileDO.setId(storeDO.getId());
        profileDO.setMerchantId(200L);
        profileDO.setManageType(DataTransUtil.intToByte(2));
        profileDO.setCertifType(DataTransUtil.intToByte(2));
        merchantStoreProfileDOMapper.insertSelective(profileDO);

        merchantStoreService.delInvalidStoreIndex();
        SolrDocument solrDocument = solrService.getSolrDocsById(storeDO.getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        Assert.assertNull(solrDocument);
    }

    @Transactional
    @Rollback
    @Test
    public void getAdMerchantStoreByIds() {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(merchantDO.getId());
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreProfileDO profileDO = new MerchantStoreProfileDO();
        profileDO.setId(storeDO.getId());
        profileDO.setMerchantId(merchantDO.getId());
        profileDO.setManageType(DataTransUtil.intToByte(2));
        profileDO.setCertifType(DataTransUtil.intToByte(2));
        merchantStoreProfileDOMapper.insertSelective(profileDO);

        MerchantStoreImageDO imageDO = new MerchantStoreImageDO();
        imageDO.setMerchantId(merchantDO.getId());
        imageDO.setMerchantStoreId(storeDO.getId());
        imageDO.setStatus(true);
        imageDO.setType(DataTransUtil.intToByte(3));
        imageDO.setPath("pic");
        imageDO.setGmtModified(new Date());
        imageDO.setGmtCreate(new Date());
        merchantStoreImageDOMapper.insertSelective(imageDO);

        List<Long> merchantIds = new ArrayList<>();
        merchantIds.add(merchantDO.getId());
        List<MerchantAdInfoBO> merchantAdInfoBOS = merchantStoreService.getAdMerchantStoreByIds(merchantIds);
        Assert.assertNotNull(merchantAdInfoBOS);
        Assert.assertEquals(1, merchantAdInfoBOS.size());
        Assert.assertEquals("pic", merchantAdInfoBOS.get(0).getPath());
    }

    @Transactional
    @Rollback
    @Test
    public void merchantStoreIsExist() {
        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(200L);
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantStoreStatusBO storeStatusBO = merchantStoreService.merchantStoreIsExist(storeDO.getId());
        Assert.assertNotNull(storeStatusBO);
        Assert.assertTrue(storeStatusBO.isExist());
    }

    @Transactional
    @Rollback
    @Test
    public void findAccountAndRegionPathByNum() {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum("B0001");
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        MerchantStoreDO storeDO = new MerchantStoreDO();
        storeDO.setMerchantId(merchantDO.getId());
        storeDO.setName("测试店铺");
        storeDO.setRegionPath("44/4403/440303");
        storeDO.setRegionName("广东省深圳市南山区");
        storeDO.setAddress("大冲商务中心");
        storeDO.setLongitude(new BigDecimal(104.23));
        storeDO.setLatitude(new BigDecimal(22.36));
        storeDO.setIntro("店铺介绍");
        storeDO.setStatus(DataTransUtil.intToByte(1));
        storeDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(storeDO);

        MerchantInfoBO merchantInfoBO = merchantStoreService.findAccountAndRegionPathByNum("B0001");
        Assert.assertNotNull(merchantInfoBO);
        Assert.assertEquals(storeDO.getRegionPath(), merchantInfoBO.getRegionPath());
    }

    @Transactional
    @Rollback
    @Test
    public void listNewMerchant() {
        List<NewMerchantStoreBO> storeBOS = merchantStoreService.listNewMerchant("44/4403");
        Assert.assertNotNull(storeBOS);
        Assert.assertEquals(0, storeBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void listRecommendFoodConsume() {
        List<RecommendFoodBO> foodBOS = merchantStoreService.listRecommendFoodConsume(10, "44/4403");
        Assert.assertNotNull(foodBOS);
        Assert.assertEquals(0, foodBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void listRecommendFoodComment() {
        List<RecommendFoodBO> foodBOS = merchantStoreService.listRecommendFoodComment(10, "44/4403");
        Assert.assertNotNull(foodBOS);
        Assert.assertEquals(0, foodBOS.size());
    }

}
