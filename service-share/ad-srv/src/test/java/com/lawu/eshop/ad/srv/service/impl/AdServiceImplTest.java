package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.framework.core.page.Page;

/**
 * 广告测试
 * @author zhangrc
 * @date 2017/07/12
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class AdServiceImplTest {
	
	@Autowired
	private AdDOMapper adDOMapper;
	
	@Autowired
	private AdService adService;
	
	
	@Transactional
    @Rollback
    @Test
    public void save() {

		AdSaveParam adSaveParam=new AdSaveParam();
    	adSaveParam.setLatitude(BigDecimal.valueOf(22.547153));
    	adSaveParam.setLongitude(BigDecimal.valueOf(113.960333));
    	adSaveParam.setMerchantId(1002l);
    	adSaveParam.setUserNum("B856392484215848969");
    	adSaveParam.setMediaUrl("ad_image/1494582624025648401.png");
    	AdParam param=new AdParam();
    	param.setAdCount(20);
    	param.setBeginTime("2017-05-17 11:35:00");
    	param.setContent("广告测试内容");
    	param.setPoint(BigDecimal.valueOf(0.5));
    	param.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
    	param.setRegionName("全国");
    	param.setTitle("广告测试标题");
    	param.setTotalPoint(BigDecimal.valueOf(100));
    	param.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
    	adSaveParam.setAdParam(param);
    	adService.saveAd(adSaveParam);

        List<AdDO> adDOS = adDOMapper.selectByExample(null);
        Assert.assertNotNull(adDOS);
        Assert.assertTrue(adDOS.size() == 1);
    }
	
	
	@Transactional
    @Rollback
    @Test
    public void selectListByMerchant() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        adDOMapper.insertSelective(ad);
        
        AdMerchantParam adMerchantParam=new AdMerchantParam();
        adMerchantParam.setPageSize(10);
        adMerchantParam.setCurrentPage(1);
        adMerchantParam.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
        adMerchantParam.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
        adMerchantParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        Page<AdBO> page= adService.selectListByMerchant(adMerchantParam, 1002l);
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size() >0);

    }
	
	
	@Transactional
    @Rollback
    @Test
    public void updateStatus() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
       
        adService.updateStatus(Long.valueOf(id.toString()));
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_OUT.val);
        
        List<AdDO> adDOS = adDOMapper.selectByExample(example);
        Assert.assertNotNull(adDOS);
        Assert.assertTrue(adDOS.size() == 1);

    }

	@Transactional
    @Rollback
    @Test
    public void remove() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
       
        adService.remove(Long.valueOf(id.toString()));
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
        
        List<AdDO> adDOS = adDOMapper.selectByExample(example);
        Assert.assertNotNull(adDOS);
        Assert.assertTrue(adDOS.size() == 1);

    }
	
	@Transactional
    @Rollback
    @Test
    public void auditVideoPass() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_VIDEO.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_AUDIT.val);
        Integer id=adDOMapper.insertSelective(ad);
        
       
        adService.auditVideo(Long.valueOf(id.toString()), 1, "通过", AuditEnum.AD_AUDIT_PASS);
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
        
        List<AdDO> adDOS = adDOMapper.selectByExample(example);
        Assert.assertNotNull(adDOS);
        Assert.assertTrue(adDOS.size() == 1);

    }
	
	@Transactional
    @Rollback
    @Test
    public void auditVideoUnPass() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_VIDEO.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_AUDIT.val);
        Integer id=adDOMapper.insertSelective(ad);
        
       
        adService.auditVideo(Long.valueOf(id.toString()), 1, "视频不通过", AuditEnum.AD_AUDIT_UN_PASS);
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_AUDIT_FAIL.val);
        
        List<AdDO> adDOS = adDOMapper.selectByExample(example);
        Assert.assertNotNull(adDOS);
        Assert.assertTrue(adDOS.size() == 1);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectListByPlatForm() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        AdFindParam adPlatParam=new AdFindParam();
        adPlatParam.setCurrentPage(1);
        adPlatParam.setPageSize(20);
        adPlatParam.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
        adPlatParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        adPlatParam.setStatusEnum(AdStatusEnum.AD_STATUS_ADD);
        Page<AdBO> page= adService.selectListByPlatForm(adPlatParam);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectListByMember() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPoint(BigDecimal.valueOf(0.5));
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        AdMemberParam adMemberParam=new AdMemberParam();
        adMemberParam.setCurrentPage(1);
        adMemberParam.setPageSize(20);
        adMemberParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        Page<AdBO> page= adService.selectListByMember(adMemberParam, null);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	
}
