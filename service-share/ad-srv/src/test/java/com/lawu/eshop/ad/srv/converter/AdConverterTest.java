package com.lawu.eshop.ad.srv.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdDetailBO;
import com.lawu.eshop.ad.srv.domain.AdDO;

/**
 * 
 * @author zhangrc
 * @date 2017/07/19
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AdConverterTest {

	@Test
    public void convertBO(){
		
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
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.getVal());
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        AdBO  bo = AdConverter.convertBO(ad);
        
        Assert.assertNotNull(bo);
    }
	
	
	@Test
    public void convertBOS(){
		
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
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.getVal());
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        
        List<AdDO> list = new ArrayList<AdDO>();
        list.add(ad);
        List<AdBO>  boList = AdConverter.convertBOS(list);
        
        Assert.assertNotNull(boList);
    }
	
	@Test
    public void convertDTO(){
        
        AdBO adBO=new AdBO();
        adBO.setId(1l);
		adBO.setAdCount(10);
		adBO.setBeginTime(new Date());
		adBO.setMediaUrl("ad_image/1494582624025648401.png");
		adBO.setMerchantId(1l);
		adBO.setGmtCreate(new Date());
		adBO.setAdCount(10);
		adBO.setPoint(BigDecimal.valueOf(10));
		adBO.setTitle("广告测试内容");
		adBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adBO.setPutWayEnum(PutWayEnum.PUT_WAY_FENS);
		adBO.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
		adBO.setTotalPoint(BigDecimal.valueOf(10));
		adBO.setViewCount(10);
		adBO.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		adBO.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		adBO.setAreas("11/12");
		adBO.setRadius(15);
		adBO.setContent("内容");
        
        AdDTO  dto = AdConverter.convertDTO(adBO);
        
        Assert.assertNotNull(dto);
    }
	
	
	@Test
    public void convertDTOS(){
        
        AdBO adBO=new AdBO();
        adBO.setId(1l);
		adBO.setAdCount(10);
		adBO.setBeginTime(new Date());
		adBO.setMediaUrl("ad_image/1494582624025648401.png");
		adBO.setMerchantId(1l);
		adBO.setGmtCreate(new Date());
		adBO.setAdCount(10);
		adBO.setPoint(BigDecimal.valueOf(10));
		adBO.setTitle("广告测试内容");
		adBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adBO.setPutWayEnum(PutWayEnum.PUT_WAY_FENS);
		adBO.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
		adBO.setTotalPoint(BigDecimal.valueOf(10));
		adBO.setViewCount(10);
		adBO.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		adBO.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		adBO.setAreas("11/12");
		adBO.setRadius(15);
		adBO.setContent("内容");
        
        List<AdBO> list = new ArrayList<AdBO>();
        list.add(adBO);
        List<AdDTO>  dtoList = AdConverter.convertDTOS(list);
        
        Assert.assertNotNull(dtoList);
    }
	
	@Test
    public void convertMerchantAdDTO(){
        
        AdBO adBO=new AdBO();
        
        adBO.setId(1l);
		adBO.setAdCount(10);
		adBO.setBeginTime(new Date());
		adBO.setMediaUrl("ad_image/1494582624025648401.png");
		adBO.setMerchantId(1l);
		adBO.setGmtCreate(new Date());
		adBO.setAdCount(10);
		adBO.setPoint(BigDecimal.valueOf(10));
		adBO.setTitle("广告测试内容");
		adBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adBO.setPutWayEnum(PutWayEnum.PUT_WAY_FENS);
		adBO.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
		adBO.setTotalPoint(BigDecimal.valueOf(10));
		adBO.setViewCount(10);
		adBO.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		adBO.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		adBO.setAreas("11/12");
		adBO.setRadius(15);
		adBO.setContent("内容");
        
        AdMerchantDTO  dtoList = AdConverter.convertMerchantAdDTO(adBO);
        
        Assert.assertNotNull(dtoList);
    }
	
	@Test
    public void convertPraiseDTOS(){
        
        AdBO adBO=new AdBO();
        adBO.setId(1l);
		adBO.setAdCount(10);
		adBO.setBeginTime(new Date());
		adBO.setMediaUrl("ad_image/1494582624025648401.png");
		adBO.setMerchantId(1l);
		adBO.setGmtCreate(new Date());
		adBO.setAdCount(10);
		adBO.setPoint(BigDecimal.valueOf(10));
		adBO.setTitle("广告测试内容");
		adBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adBO.setPutWayEnum(PutWayEnum.PUT_WAY_FENS);
		adBO.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
		adBO.setTotalPoint(BigDecimal.valueOf(10));
		adBO.setViewCount(10);
		adBO.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		adBO.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		adBO.setAreas("11/12");
		adBO.setRadius(15);
		adBO.setContent("内容");
        
        List<AdBO> list = new ArrayList<AdBO>();
        list.add(adBO);
        List<AdPraiseDTO>  dtoList = AdConverter.convertPraiseDTOS(list);
        
        Assert.assertNotNull(dtoList);
    }
	
	
	@Test
    public void convertSolrInputDocument(){
		
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
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.getVal());
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        
        SolrInputDocument  sd = AdConverter.convertSolrInputDocument(ad);
        
        Assert.assertNotNull(sd);
    }
	
	@Test
    public void convertMerchantDetailAdDTO(){
        
        AdBO adBO=new AdBO();
        
        adBO.setId(1l);
		adBO.setAdCount(10);
		adBO.setBeginTime(new Date());
		adBO.setMediaUrl("ad_image/1494582624025648401.png");
		adBO.setMerchantId(1l);
		adBO.setGmtCreate(new Date());
		adBO.setAdCount(10);
		adBO.setPoint(BigDecimal.valueOf(10));
		adBO.setTitle("广告测试内容");
		adBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adBO.setPutWayEnum(PutWayEnum.PUT_WAY_FENS);
		adBO.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
		adBO.setTotalPoint(BigDecimal.valueOf(10));
		adBO.setViewCount(10);
		adBO.setMerchantLatitude(BigDecimal.valueOf(22.547153));
		adBO.setMerchantLongitude(BigDecimal.valueOf(113.960333));
		adBO.setAreas("11/12");
		adBO.setRadius(15);
		adBO.setContent("内容");
        
		AdMerchantDetailDTO  dto = AdConverter.convertMerchantDetailAdDTO(adBO);
        
        Assert.assertNotNull(dto);
    }
	
	@Test
    public void convertSolrUpdateDocument(){
		
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
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.getVal());
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        
        SolrInputDocument  sd = AdConverter.convertSolrUpdateDocument(ad);
        
        Assert.assertNotNull(sd);
    }
	
	
	
	@Test
    public void convertDetailBO(){
		
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
		ad.setType(AdTypeEnum.AD_TYPE_FLAT.getVal());
        ad.setGmtCreate(new Date());
        ad.setGmtModified(new Date());
        ad.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
        
        AdDetailBO  bo = AdConverter.convertDetailBO(ad);
        
        Assert.assertNotNull(bo);
    }
	
	@Test
    public void convertDetailDTO(){
		
		AdDetailBO adDetailBO=new AdDetailBO();
		
		adDetailBO.setId(1l);
		adDetailBO.setAdCount(11);
		adDetailBO.setMediaUrl("ad_image/1494582624025648401.png");
		adDetailBO.setGmtCreate(new Date());
		adDetailBO.setAdCount(10);
		adDetailBO.setPoint(BigDecimal.valueOf(10));
		adDetailBO.setTitle("广告单元测试");
		adDetailBO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
		adDetailBO.setPutWayEnum(PutWayEnum.PUT_WAY_RADAR);
		adDetailBO.setStatusEnum(AdStatusEnum.AD_STATUS_ADD);
		adDetailBO.setTotalPoint(BigDecimal.valueOf(100));
		adDetailBO.setRadius(15);
		adDetailBO.setContent("aaaa");
		adDetailBO.setRadius(15);
		adDetailBO.setRegionName("全国");
		adDetailBO.setRemark("test");
		adDetailBO.setAuditTime(new Date());
        
		AdDetailDTO  dto = AdConverter.convertDetailDTO(adDetailBO);
        
        Assert.assertNotNull(dto);
    }
	
}
