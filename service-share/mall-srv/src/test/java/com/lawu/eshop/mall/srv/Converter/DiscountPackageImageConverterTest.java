package com.lawu.eshop.mall.srv.Converter;

import com.lawu.eshop.mall.constants.StatusEnum;
import com.lawu.eshop.mall.dto.DiscountPackageImageDTO;
import com.lawu.eshop.mall.param.DiscountPackageImageSaveParam;
import com.lawu.eshop.mall.param.DiscountPackageImageUpdateParam;
import com.lawu.eshop.mall.srv.bo.DiscountPackageImageBO;
import com.lawu.eshop.mall.srv.converter.DiscountPackageImageConverter;
import com.lawu.eshop.mall.srv.domain.DiscountPackageImageDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class DiscountPackageImageConverterTest {

    @Test
    public void convert(){
        DiscountPackageImageDO imageDO = new DiscountPackageImageDO();
        imageDO.setDescription("test");
        imageDO.setDiscountPackageId(1l);
        imageDO.setGmtCreate(new Date());
        imageDO.setGmtModified(new Date());
        imageDO.setId(1L);
        imageDO.setStatus(StatusEnum.STATUS_VALID.val);
        imageDO.setImage("image");
        DiscountPackageImageBO packageImageBO = DiscountPackageImageConverter.convert(imageDO);
        Assert.assertEquals(imageDO.getId(),packageImageBO.getId());
        Assert.assertEquals(imageDO.getStatus(),packageImageBO.getStatus().val);
        Assert.assertEquals(imageDO.getDescription(),packageImageBO.getDescription());
    }

    @Test
    public void convertDiscountPackageImageBOList(){
        List<DiscountPackageImageDO> discountPackageImageDOList = new ArrayList<>();
        DiscountPackageImageDO imageDO = new DiscountPackageImageDO();
        imageDO.setDescription("test");
        imageDO.setDiscountPackageId(1l);
        imageDO.setGmtCreate(new Date());
        imageDO.setGmtModified(new Date());
        imageDO.setId(1L);
        imageDO.setStatus(StatusEnum.STATUS_VALID.val);
        imageDO.setImage("image");
        discountPackageImageDOList.add(imageDO);
        List<DiscountPackageImageBO> list = DiscountPackageImageConverter.convertDiscountPackageImageBOList(discountPackageImageDOList);
        Assert.assertEquals(discountPackageImageDOList.get(0).getId(),list.get(0).getId());
        Assert.assertEquals(discountPackageImageDOList.get(0).getStatus(),list.get(0).getStatus().val);
        Assert.assertEquals(discountPackageImageDOList.get(0).getDescription(),list.get(0).getDescription());
    }

    @Test
    public void convertDO(){
        DiscountPackageImageSaveParam param = new DiscountPackageImageSaveParam();
        param.setDescription("test");
        param.setImage("image");

        DiscountPackageImageDO discountPackageImageDO = DiscountPackageImageConverter.convert(1L,param);
        Assert.assertEquals(param.getDescription(),discountPackageImageDO.getDescription());
        Assert.assertEquals(param.getImage(),discountPackageImageDO.getImage());
    }

    @Test
    public void convertDO2(){
        DiscountPackageImageUpdateParam param = new DiscountPackageImageUpdateParam();
        param.setDescription("test");
        param.setImage("image");

        DiscountPackageImageDO discountPackageImageDO = DiscountPackageImageConverter.convert(1L,param);
        Assert.assertEquals(param.getDescription(),discountPackageImageDO.getDescription());
        Assert.assertEquals(param.getImage(),discountPackageImageDO.getImage());
    }

    @Test
    public void convertDO3(){
        DiscountPackageImageUpdateParam param = new DiscountPackageImageUpdateParam();
        param.setDescription("test");
        param.setImage("image");

        DiscountPackageImageDO discountPackageImageDO = DiscountPackageImageConverter.convert(param);
        Assert.assertEquals(param.getDescription(),discountPackageImageDO.getDescription());
        Assert.assertEquals(param.getImage(),discountPackageImageDO.getImage());
    }

    @Test
    public void convertDTO(){
        DiscountPackageImageBO param = new DiscountPackageImageBO();
        param.setDescription("test");
        param.setImage("image");

        DiscountPackageImageDTO discountPackageImageDTO = DiscountPackageImageConverter.convert(param);
        Assert.assertEquals(param.getDescription(),discountPackageImageDTO.getDescription());
        Assert.assertEquals(param.getImage(),discountPackageImageDTO.getImage());
    }

    @Test
    public void convertDiscountPackageImageDTOList(){
        List<DiscountPackageImageBO> discountPackageImageBOList = new ArrayList<>();
        DiscountPackageImageBO param = new DiscountPackageImageBO();
        param.setDescription("test");
        param.setImage("image");
        discountPackageImageBOList.add(param);
        List<DiscountPackageImageDTO> list =DiscountPackageImageConverter.convertDiscountPackageImageDTOList(discountPackageImageBOList);
        Assert.assertEquals(discountPackageImageBOList.get(0).getDescription(),list.get(0).getDescription());
        Assert.assertEquals(discountPackageImageBOList.get(0).getImage(),list.get(0).getImage());
    }
}
