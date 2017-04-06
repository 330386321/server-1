package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.NearStoreBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Service
public interface NearStoreService {

    /**
     * 查询附近门店
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    List<NearStoreBO> listNearStore(Double longitude, Double latitude);
}
