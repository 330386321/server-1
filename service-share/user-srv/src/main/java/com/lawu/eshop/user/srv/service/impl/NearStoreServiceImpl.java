package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.query.NearStoreParam;
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
    public Page<NearStoreBO> listNearStore(NearStoreParam nearStoreParam) {
        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andStatusEqualTo(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val).andIndustryPathEqualTo(nearStoreParam.getIndustryPath());
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if (merchantStoreDOS.isEmpty()) {
            return null;
        }
        List<NearStoreBO> nearStoreBOS = NearStoreConverter.convertBO(merchantStoreDOS);
        for (NearStoreBO nearStoreBO : nearStoreBOS) {
            int distance = DistanceUtil.getDistance(nearStoreParam.getLongitude(), nearStoreParam.getLatitude(), nearStoreBO.getLongitude().doubleValue(), nearStoreBO.getLatitude().doubleValue());
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

        Page<NearStoreBO> page = new Page<>();
        page.setCurrentPage(nearStoreParam.getCurrentPage());
        page.setTotalCount(nearStoreBOS.size());

        List<NearStoreBO> nearStoreBOList = new ArrayList<NearStoreBO>(nearStoreParam.getPageSize());
        for (int i = nearStoreParam.getOffset(); i < nearStoreBOS.size(); i++) {
            nearStoreBOList.add(nearStoreBOS.get(i));
            if (nearStoreBOList.size() == nearStoreParam.getPageSize()) {
                break;
            }
        }
        if (nearStoreBOList.isEmpty()) {
            page.setRecords(nearStoreBOList);
            return page;
        }

        for (NearStoreBO nearStoreBO : nearStoreBOList) {
            //查询门店照
            MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
            merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(nearStoreBO.getMerchantId()).andStatusEqualTo(true).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
            List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
            nearStoreBO.setStorePic(merchantStoreImageDOS.isEmpty() ? "" : merchantStoreImageDOS.get(0).getPath());

            //查询门店收藏数
            FavoriteMerchantDOExample favoriteMerchantDOExample = new FavoriteMerchantDOExample();
            favoriteMerchantDOExample.createCriteria().andMerchantIdEqualTo(nearStoreBO.getMerchantId());
            int favCount = favoriteMerchantDOMapper.countByExample(favoriteMerchantDOExample);
            nearStoreBO.setFavCount(favCount);
        }
        page.setRecords(nearStoreBOList);
        return page;
    }

}
