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

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.FavoriteAdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.ad.srv.bo.FavoriteAdDOViewBO;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.FavoriteAdDOMapper;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.ad.srv.service.FavoriteAdService;
import com.lawu.eshop.framework.core.page.Page;

/**
 * @author zhangrc
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FavoriteAdServiceImplTest {

    @Autowired
    private FavoriteAdService favoriteAdService;

    @Autowired
    private FavoriteAdDOMapper favoriteAdDOMapper;
    
    @Autowired
    private AdService adService;
    
    @Autowired
    private AdDOMapper adDOMapper;

    @Transactional
    @Rollback
    @Test
    public void save() {

    	favoriteAdService.save(1l, 1l);

        List<FavoriteAdDO> dos = favoriteAdDOMapper.selectByExample(null);
        Assert.assertNotNull(dos);
        Assert.assertTrue(dos.size() == 1);
    }

    @Transactional
    @Rollback
    @Test
    public void remove() {
    	favoriteAdService.save(1l, 1l);
    	favoriteAdService.remove(1l, 1l);
    	List<FavoriteAdDO> dos = favoriteAdDOMapper.selectByExample(null);
        Assert.assertTrue(dos.size() == 0);

    }
    
    @Transactional
    @Rollback
    @Test
    public void selectMyFavoriteAd() {
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
    	Integer  id=adService.saveAd(adSaveParam);
    	favoriteAdService.save(Long.parseLong(id.toString()), 1l);
    	
    	FavoriteAdParam paramQuery=new FavoriteAdParam();
    	paramQuery.setCurrentPage(1);
    	paramQuery.setPageSize(10);
    	paramQuery.setTypeEnum(FavoriteAdTypeEnum.AD_TYPE_EGAIN);
    	Page<FavoriteAdDOViewBO> page= favoriteAdService.selectMyFavoriteAd(paramQuery, 1l);
    	Assert.assertNotNull(page.getRecords());
        Assert.assertTrue(page.getRecords().size()>0);

    }
    
    
}
