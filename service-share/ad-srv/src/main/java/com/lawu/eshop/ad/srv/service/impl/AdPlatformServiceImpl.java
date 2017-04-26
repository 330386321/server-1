package com.lawu.eshop.ad.srv.service.impl;

import com.lawu.eshop.ad.constants.GoodsTypeEnum;
import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.converter.AdPlatformConverter;
import com.lawu.eshop.ad.srv.domain.AdPlatformDO;
import com.lawu.eshop.ad.srv.domain.AdPlatformDOExample;
import com.lawu.eshop.ad.srv.domain.AdPlatformDOExample.Criteria;
import com.lawu.eshop.ad.srv.mapper.AdPlatformDOMapper;
import com.lawu.eshop.ad.srv.service.AdPlatformService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.utils.DataTransUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdPlatformServiceImpl implements AdPlatformService {

    @Autowired
    private AdPlatformDOMapper adPlatformDOMapper;

    @Override
    @Transactional
    public Integer saveAdPlatform(AdPlatformParam adPlatformParam, String url) {
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setTitle(adPlatformParam.getTitle());
        adPlatformDO.setMediaUrl(url);
        adPlatformDO.setType(adPlatformParam.getTypeEnum().val);
        adPlatformDO.setPosition(adPlatformParam.getPositionEnum().val);
        //纯链接
        if (adPlatformParam.getTypeEnum().equals(TypeEnum.TYPE_LINK)) {
            adPlatformDO.setLinkUrl(adPlatformParam.getLinkUrl());
        } else if (adPlatformParam.getTypeEnum().equals(TypeEnum.TYPE_PRODUCT)) { //商品
            adPlatformDO.setProductId(adPlatformParam.getProductId());
            adPlatformDO.setGoodsType(adPlatformParam.getGoodsTypeEnum().val);
        } else {
            adPlatformDO.setMerchantStoreId(adPlatformParam.getMerchantStoreId());
        }
        adPlatformDO.setStatus(new Byte("1"));
        adPlatformDO.setGmtCreate(new Date());
        adPlatformDO.setGmtModified(new Date());
        adPlatformDO.setContent(adPlatformParam.getContent());
        Integer id = adPlatformDOMapper.insert(adPlatformDO);
        return id;
    }

    @Override
    @Transactional
    public Integer removeAdPlatform(Long id) {
        AdPlatformDOExample example = new AdPlatformDOExample();
        example.createCriteria().andIdEqualTo(id);
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setStatus(new Byte("0"));
        Integer i = adPlatformDOMapper.updateByExampleSelective(adPlatformDO, example);
        return i;
    }

    @Override
    public List<AdPlatformBO> selectByPosition(PositionEnum positionEnum) {
        AdPlatformDOExample example = new AdPlatformDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(new Byte("2")).andPositionEqualTo(positionEnum.val);
        List<AdPlatformDO> DOS = adPlatformDOMapper.selectByExample(example);
        return DOS.isEmpty() ? null : AdPlatformConverter.convertBOS(DOS);
    }

    @Override
    public Page<AdPlatformBO> selectList(AdPlatformFindParam param) {
        AdPlatformDOExample example = new AdPlatformDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo(new Byte("0"));
        if (param.getPositionEnum() != null) {
            criteria.andPositionEqualTo(param.getPositionEnum().val);
        }
        if (param.getTypeEnum() != null) {
            criteria.andTypeEqualTo(param.getTypeEnum().val);
        }
        if (param.getTitle() != null) {
            criteria.andTitleLike("%" + param.getTitle() + "%");
        }
        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        List<AdPlatformDO> DOS = adPlatformDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<AdPlatformBO> bos = AdPlatformConverter.convertBOS(DOS);
        Page<AdPlatformBO> pageAd = new Page<AdPlatformBO>();
        pageAd.setCurrentPage(param.getCurrentPage());
        pageAd.setTotalCount(DOS.size());
        pageAd.setRecords(bos);
        return pageAd;
    }


    @Override
    @Transactional
    public Integer issueAd(Long id) {
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setId(id);
        adPlatformDO.setStatus(new Byte("2"));
        Integer i = adPlatformDOMapper.updateByPrimaryKeySelective(adPlatformDO);
        return i;
    }

    @Override
    @Transactional
    public Integer setPosition(Long id, PositionEnum positionEnum) {
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setId(id);
        adPlatformDO.setPosition(positionEnum.val);
        Integer i = adPlatformDOMapper.updateByPrimaryKeySelective(adPlatformDO);
        return i;
    }

    @Override
    public Integer update(Long id, AdPlatformParam adPlatformParam, String url) {
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setId(id);
        adPlatformDO.setTitle(adPlatformParam.getTitle());
        adPlatformDO.setMediaUrl(url);
        //纯链接
        if (adPlatformParam.getTypeEnum().equals(TypeEnum.TYPE_LINK)) {
            adPlatformDO.setType(new Byte("1"));
            adPlatformDO.setLinkUrl(adPlatformParam.getLinkUrl());
        } else { //商品
            adPlatformDO.setType(new Byte("2"));
            adPlatformDO.setProductId(adPlatformParam.getProductId());
        }
        Integer i = adPlatformDOMapper.updateByPrimaryKeySelective(adPlatformDO);
        return i;
    }

    @Override
    public AdPlatformBO select(Long id) {
        AdPlatformDO adPlatformDO = adPlatformDOMapper.selectByPrimaryKey(id);
        return AdPlatformConverter.convertBO(adPlatformDO);
    }

    @Override
    public void unShelve(Long id) {
        AdPlatformDO adPlatformDO = new AdPlatformDO();
        adPlatformDO.setId(id);
        adPlatformDO.setStatus(new Byte("3"));
        Integer i = adPlatformDOMapper.updateByPrimaryKeySelective(adPlatformDO);

    }

    @Override
    public List<AdPlatformBO> getAdPlatformByTypePosition(TypeEnum typeEnum, PositionEnum positionEnum) {
        AdPlatformDOExample example = new AdPlatformDOExample();
        example.createCriteria().andTypeEqualTo(typeEnum.val).andPositionEqualTo(positionEnum.val).andStatusEqualTo(DataTransUtil.intToByte(1));
        List<AdPlatformDO> adPlatformDOS = adPlatformDOMapper.selectByExample(example);
        return AdPlatformConverter.convertBOS(adPlatformDOS);
    }

    @Override
    public List<AdPlatformBO> getAdPlatformWithGoods(GoodsTypeEnum goodsTypeEnum) {
        AdPlatformDOExample example = new AdPlatformDOExample();
        example.createCriteria().andTypeEqualTo(TypeEnum.TYPE_PRODUCT.val).andPositionEqualTo(PositionEnum.POSITON_SHOP_GOODS.val).andGoodsTypeEqualTo(goodsTypeEnum.val).andStatusEqualTo(DataTransUtil.intToByte(1));
        List<AdPlatformDO> adPlatformDOS = adPlatformDOMapper.selectByExample(example);
        return AdPlatformConverter.convertBOS(adPlatformDOS);
    }

}
