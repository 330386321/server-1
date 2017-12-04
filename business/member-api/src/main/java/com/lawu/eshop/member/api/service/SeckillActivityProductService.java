package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.SeckillActivityProductBuyPageDTO;
import com.lawu.eshop.product.dto.SeckillActivityProductInformationDTO;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;

/**
 * 抢购活动商品服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
@FeignClient(value = "product-srv", path="seckillActivityProduct/")
public interface SeckillActivityProductService {
    
    /**
     * 根据id和查询参数分页查询抢购活动商品列表
     * 用于用户端
     * 
     * @param id 抢购活动id
     * @param param 分页查询参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(path = "page/{id}", method = RequestMethod.PUT)
    Result<Page<SeckillActivityProductBuyPageDTO>> page(@PathVariable("id") Long id, @RequestBody SeckillActivityProductPageQueryParam param);
    
    /**
     * 查询抢购活动商品信息
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(path = "information/{id}", method = RequestMethod.GET)
    Result<SeckillActivityProductInformationDTO> information(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);

    /**
     * 查询抢购商品型号库存
     *
     * @param seckillActivityProductModelId 抢购活动商品型号id
     * @author jiangxinjun
     * @createDate 2017年11月30日
     * @updateDate 2017年11月30日
     */
    @RequestMapping(value = "inventory/{seckillActivityProductModelId}", method = RequestMethod.GET)
    Result<Integer> getInventory(@PathVariable("seckillActivityProductModelId") Long seckillActivityProductModelId);
}
