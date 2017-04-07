package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.srv.bo.NearStoreBO;
import com.lawu.eshop.user.srv.converter.NearStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.service.NearStoreService;
import com.lawu.eshop.utils.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Service
public class NearStoreServiceImpl implements NearStoreService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Autowired
    private FavoriteMerchantDOMapper favoriteMerchantDOMapper;

    @Override
    public List<NearStoreBO> listNearStore(Double longitude, Double latitude, String industryPath) {
        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andStatusEqualTo(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val).andIndustryPathEqualTo(industryPath);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if (merchantStoreDOS.isEmpty()) {
            return null;
        }
        List<NearStoreBO> nearStoreBOS = NearStoreConverter.convertBO(merchantStoreDOS);
        for (NearStoreBO nearStoreBO : nearStoreBOS) {
            int distance = DistanceUtil.getDistance(longitude, latitude, nearStoreBO.getLongitude().doubleValue(), nearStoreBO.getLatitude().doubleValue());
            nearStoreBO.setDistance(distance);
        }

        Collections.sort(nearStoreBOS, new Comparator<NearStoreBO>() {
            @Override
            public int compare(NearStoreBO o1, NearStoreBO o2) {
                int distance1 = o1.getDistance();
                int distance2 = o2.getDistance();
                return distance1 - distance2;
            }
        });

        List<NearStoreBO> nearStoreBOList = new ArrayList<NearStoreBO>(10);
        for (NearStoreBO nearStoreBO : nearStoreBOS) {
            //查询门店logo
            MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
            merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(nearStoreBO.getMerchantId()).andStatusEqualTo(true).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
            List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
            nearStoreBO.setLogo(merchantStoreImageDOS.isEmpty() ? "" : merchantStoreImageDOS.get(0).getPath());

            //查询门店收藏数
            FavoriteMerchantDOExample favoriteMerchantDOExample = new FavoriteMerchantDOExample();
            favoriteMerchantDOExample.createCriteria().andMerchantIdEqualTo(nearStoreBO.getMerchantId());
            int favCount = favoriteMerchantDOMapper.countByExample(favoriteMerchantDOExample);
            nearStoreBO.setFavCount(favCount);

            nearStoreBOList.add(nearStoreBO);
            if (nearStoreBOList.size() > 10) {
                break;
            }
        }
        return nearStoreBOList;
    }

}
