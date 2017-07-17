package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.lawu.eshop.ad.constants.AdPraiseStatusEnum;
import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.constants.MemberAdRecordStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdDetailBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.bo.RedPacketInfoBO;
import com.lawu.eshop.ad.srv.bo.ReportAdBO;
import com.lawu.eshop.ad.srv.bo.ViewBO;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDO;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.FavoriteAdDOMapper;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
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
	
	@Autowired
	private FavoriteAdDOMapper favoriteAdDOMapper;
	
	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;
	
	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;
	
	
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
	
	@Transactional
    @Rollback
    @Test
    public void selectAbById() {
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
        
        FavoriteAdDO favoriteAdDO=new FavoriteAdDO();
        favoriteAdDO.setAdId(Long.valueOf(id.toString()));
        favoriteAdDO.setMemberId(1l);
        favoriteAdDO.setGmtCreate(new Date());
        favoriteAdDOMapper.insert(favoriteAdDO);
        
        AdBO bo= adService.selectAbById(Long.valueOf(id.toString()), 1l);
        
        Assert.assertNotNull(bo);

    }
	
	
	@Transactional
    @Rollback
    @Test
    public void selectPraiseListByMember() {
		AdSaveParam adSaveParam=new AdSaveParam();
    	adSaveParam.setLatitude(BigDecimal.valueOf(22.547153));
    	adSaveParam.setLongitude(BigDecimal.valueOf(113.960333));
    	adSaveParam.setMerchantId(1002l);
    	adSaveParam.setCount(10);
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
    	param.setTypeEnum(AdTypeEnum.AD_TYPE_PRAISE);
    	adSaveParam.setAdParam(param);
    	adService.saveAd(adSaveParam);
        
        AdPraiseParam adPraiseParam=new AdPraiseParam();
        adPraiseParam.setCurrentPage(1);
        adPraiseParam.setPageSize(20);
        adPraiseParam.setStatusEnum(AdPraiseStatusEnum.AD_STATUS_TOBEGIN);
        
        Page<AdBO> page= adService.selectPraiseListByMember(adPraiseParam,1l);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectPraiseListShoot() {
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
		ad.setType(AdTypeEnum.AD_TYPE_PRAISE.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        AdPraiseParam adPraiseParam=new AdPraiseParam();
        adPraiseParam.setCurrentPage(1);
        adPraiseParam.setPageSize(20);
        adPraiseParam.setStatusEnum(AdPraiseStatusEnum.AD_STATUS_SHOOT);
        
        Page<AdBO> page= adService.selectPraiseListByMember(adPraiseParam,1l);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void clickPraise() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setMediaUrl("ad_image/1494582624025648401.png");
		ad.setAdCount(20);
		ad.setBeginTime(new Date());
		ad.setContent("广告测试内容");
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setRegionName("全国");
		ad.setTitle("广告测试标题");
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PRAISE.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        PointPoolDO pointPoolDO=new PointPoolDO();
        pointPoolDO.setAdId(Long.valueOf(id.toString()));
        pointPoolDO.setGmtCreate(new Date());
        pointPoolDO.setGmtModified(new Date());
        pointPoolDO.setMerchantId(1002l);
        pointPoolDO.setOrdinal(0);
        pointPoolDO.setPoint(BigDecimal.valueOf(15));
        pointPoolDO.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
        pointPoolDO.setType(PointPoolTypeEnum.AD_TYPE_PRAISE.val);
        pointPoolDOMapper.insert(pointPoolDO);
        
        BigDecimal point= adService.clickPraise(Long.valueOf(id.toString()), 1l, "aaa");
        Assert.assertNotNull(point);
        Assert.assertTrue(point.compareTo(BigDecimal.valueOf(0))==1);

    }
	
	
	@Transactional
    @Rollback
    @Test
    public void clickAd() {
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
        ad.setHits(0);
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        
        BigDecimal point= adService.clickAd(Long.valueOf(id.toString()), 1l, "aaa");
        Assert.assertNotNull(point);
        Assert.assertTrue(point.compareTo(BigDecimal.valueOf(0))==1);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectChoiceness() {
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
        
        AdMemberParam adMemberParam=new AdMemberParam();
        adMemberParam.setCurrentPage(1);
        adMemberParam.setPageSize(20);
        adMemberParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        Page<AdBO> page= adService.selectChoiceness(adMemberParam);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	
	@Transactional
    @Rollback
    @Test
    public void selectRPIsSend() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setAdCount(20);
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PACKET.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
       
        Integer count= adService.selectRPIsSend(1002l);
        Assert.assertTrue(count>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void getRedPacket() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setAdCount(20);
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PACKET.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
       
        PointPoolDO pointPoolDO=new PointPoolDO();
        pointPoolDO.setAdId(Long.valueOf(id.toString()));
        pointPoolDO.setGmtCreate(new Date());
        pointPoolDO.setGmtModified(new Date());
        pointPoolDO.setMerchantId(1002l);
        pointPoolDO.setOrdinal(0);
        pointPoolDO.setPoint(BigDecimal.valueOf(5));
        pointPoolDO.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
        pointPoolDO.setType(PointPoolTypeEnum.AD_TYPE_PACKET.val);
        pointPoolDOMapper.insert(pointPoolDO);
        
        BigDecimal  point= adService.getRedPacket(1002l, 1l, "aaa");
        Assert.assertNotNull(point);
        Assert.assertTrue(point.compareTo(BigDecimal.valueOf(0))==1);

    }
	
	@Transactional
    @Rollback
    @Test
    public void get() {
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
        
       
        AdBO bo= adService.get(Long.valueOf(id.toString()));
        
        Assert.assertNotNull(bo);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectRedPacketByMember() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setAdCount(20);
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PACKET.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
       
        PointPoolDO pointPoolDO=new PointPoolDO();
        pointPoolDO.setAdId(Long.valueOf(id.toString()));
        pointPoolDO.setGmtCreate(new Date());
        pointPoolDO.setGmtModified(new Date());
        pointPoolDO.setMerchantId(1002l);
        pointPoolDO.setOrdinal(0);
        pointPoolDO.setPoint(BigDecimal.valueOf(5));
        pointPoolDO.setMemberId(1l);
        pointPoolDO.setMemberNum("aaa");
        pointPoolDO.setStatus(PointPoolStatusEnum.AD_POINT_GET.val);
        pointPoolDO.setType(PointPoolTypeEnum.AD_TYPE_PACKET.val);
        pointPoolDOMapper.insert(pointPoolDO);
        
        Boolean  flag= adService.selectRedPacketByMember(1002l, 1l);
        Assert.assertTrue(flag);

    }
	
	@Transactional
    @Rollback
    @Test
    public void getClickAdPoint() {
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
        
        MemberAdRecordDO memberAdRecordDO=new MemberAdRecordDO();
        memberAdRecordDO.setAdId(Long.valueOf(id.toString()));
        memberAdRecordDO.setClickDate(new Date());
        memberAdRecordDO.setGmtCommission(new Date());
        memberAdRecordDO.setGmtCreate(new Date());
        memberAdRecordDO.setMemberId(1l);
        memberAdRecordDO.setMemberNum("aa");
        memberAdRecordDO.setOriginalPoint(BigDecimal.valueOf(0.5));
        memberAdRecordDO.setPoint(BigDecimal.valueOf(0.4));
        memberAdRecordDO.setStatus(MemberAdRecordStatusEnum.YES.getVal());
        memberAdRecordDOMapper.insert(memberAdRecordDO);
       
        ClickAdPointBO bo =adService.getClickAdPoint(1l, BigDecimal.valueOf(20));
        
        Assert.assertNotNull(bo);

    }
	
	
	
	@Transactional
    @Rollback
    @Test
    public void getAllAd() {
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
        
        List<ViewBO> list= adService.getAllAd();
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void updateViewCount() {
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
        
        adService.updateViewCount(Long.valueOf(id.toString()), 11);
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andViewcountGreaterThan(10);
        List<AdDO> list=adDOMapper.selectByExample(example);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void listAllAd() {
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
        
        ListAdParam listAdParam=new ListAdParam();
        listAdParam.setPageSize(10);
        listAdParam.setCurrentPage(1);
        listAdParam.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
        listAdParam.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
        listAdParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        listAdParam.setTitle("广告");
        Page<AdBO> page= adService.listAllAd(listAdParam);
        
        Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void selectById() {
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
        
        AdBO bo= adService.selectById(Long.valueOf(id.toString()));
        
        Assert.assertNotNull(bo);

    }
	
	@Transactional
    @Rollback
    @Test
    public void operatorUpdateAdStatus() {
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
        
     
        adService.operatorUpdateAdStatus(Long.valueOf(id.toString()), AdStatusEnum.AD_STATUS_DELETE);
        
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
        List<AdDO> list=adDOMapper.selectByExample(example);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void listFlatVideoAd() {
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
        
        ListAdParam listAdParam=new ListAdParam();
        listAdParam.setPageSize(10);
        listAdParam.setCurrentPage(1);
        listAdParam.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
        listAdParam.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
        listAdParam.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        listAdParam.setTitle("广告");
        List<AdBO> list= adService.listFlatVideoAd(listAdParam);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
    @Rollback
    @Test
    public void updateAdIndex() {
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
        
        adService.updateAdIndex(Long.valueOf(id.toString()));
        
        List<AdDO> list=adDOMapper.selectByExample(null);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	
	@Transactional
    @Rollback
    @Test
    public void rebuildAdIndex() {
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
        
        adService.rebuildAdIndex();
        
        List<AdDO> list=adDOMapper.selectByExample(null);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
	@Rollback
    @Test
    public void delInvalidAdIndex() {
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
        
        adService.delInvalidAdIndex();
        
        List<AdDO> list=adDOMapper.selectByExample(null);
        
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);

    }
	
	@Transactional
	@Rollback
    @Test
    public void getRedPacketInfo() {
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
        
        RedPacketInfoBO  bo = adService.getRedPacketInfo(1002l);
        Assert.assertNotNull(bo);

    }
	
	@Transactional
	@Rollback
    @Test
    public void isExistsRedPacket() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setAdCount(20);
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PACKET.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_OUT.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        Boolean  flag = adService.isExistsRedPacket(1002l);
        Assert.assertNotNull(flag);
        Assert.assertTrue(flag);
    }
	
	@Transactional
	@Rollback
    @Test
    public void isSendRedPacket() {
		AdDO ad=new AdDO();
		ad.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		ad.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		ad.setMerchantId(1002l);
		ad.setMerchantNum("B856392484215848969");
		ad.setAdCount(20);
		ad.setPutWay(PutWayEnum.PUT_WAY_AREAS.val);
		ad.setTotalPoint(BigDecimal.valueOf(100));
		ad.setType(AdTypeEnum.AD_TYPE_PACKET.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        Boolean  flag = adService.isSendRedPacket(1002l);
        Assert.assertNotNull(flag);
        Assert.assertFalse(flag);
    }
	
	
	@Transactional
	@Rollback
    @Test
    public void updatAdToPuted() {
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
		ad.setType(AdTypeEnum.AD_TYPE_PRAISE.val);
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        adService.updatAdToPuted();

    }
	
	@Transactional
	@Rollback
    @Test
    public void updatAdToPutting() {
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
        
        adService.updatAdToPutting();
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val);
        List<AdDO>  list = adDOMapper.selectByExample(example);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }
	
	@Transactional
	@Rollback
    @Test
    public void batchDeleteAd() {
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
        
        List<Long> ids=new ArrayList<>();
        ids.add(Long.valueOf(id.toString()));
        adService.batchDeleteAd(ids);
        AdDOExample example=new AdDOExample();
        example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
        List<AdDO>  list = adDOMapper.selectByExample(example);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }
	
	
	@Transactional
	@Rollback
    @Test
    public void selectDetailFlatAndVideo() {
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
        ad.setHits(0);
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        MemberAdRecordDO memberAdRecordDO=new MemberAdRecordDO();
        memberAdRecordDO.setAdId(Long.valueOf(id.toString()));
        memberAdRecordDO.setClickDate(new Date());
        memberAdRecordDO.setGmtCommission(new Date());
        memberAdRecordDO.setGmtCreate(new Date());
        memberAdRecordDO.setMemberId(1l);
        memberAdRecordDO.setMemberNum("aa");
        memberAdRecordDO.setOriginalPoint(BigDecimal.valueOf(0.5));
        memberAdRecordDO.setPoint(BigDecimal.valueOf(0.4));
        memberAdRecordDO.setStatus(MemberAdRecordStatusEnum.YES.getVal());
        memberAdRecordDOMapper.insert(memberAdRecordDO);
        
        AdDetailBO bo=adService.selectDetail(Long.valueOf(id.toString()));
        Assert.assertNotNull(bo);
    }
	
	@Transactional
	@Rollback
    @Test
    public void selectDetailPraise() {
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
        ad.setHits(0);
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        PointPoolDO pointPoolDO=new PointPoolDO();
        pointPoolDO.setAdId(Long.valueOf(id.toString()));
        pointPoolDO.setGmtCreate(new Date());
        pointPoolDO.setGmtModified(new Date());
        pointPoolDO.setMerchantId(1002l);
        pointPoolDO.setOrdinal(0);
        pointPoolDO.setPoint(BigDecimal.valueOf(15));
        pointPoolDO.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
        pointPoolDO.setType(PointPoolTypeEnum.AD_TYPE_PRAISE.val);
        pointPoolDOMapper.insert(pointPoolDO);
        
        AdDetailBO bo=adService.selectDetail(Long.valueOf(id.toString()));
        Assert.assertNotNull(bo);
    }
	
	
	@Transactional
	@Rollback
    @Test
    public void isMyData() {
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
        ad.setHits(0);
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        Boolean flag=adService.isMyData(Long.valueOf(id.toString()),1002l);
        Assert.assertNotNull(flag);
        Assert.assertTrue(flag);
    }
	
	@Transactional
	@Rollback
    @Test
    public void selectReportAdEarnings() {
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
        ad.setHits(0);
        ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
        Integer id=adDOMapper.insertSelective(ad);
        
        List<ReportAdBO> list=adService.selectReportAdEarnings();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }
}
