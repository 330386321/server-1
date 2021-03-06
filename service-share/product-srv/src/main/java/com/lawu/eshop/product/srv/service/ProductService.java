package com.lawu.eshop.product.srv.service;

import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.product.param.ProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.bo.ProductRelateAdInfoBO;

/**
 * Created by Yangqh on 2017/3/23.
 */
public interface ProductService {

    /**
     * 商品列表查询
     *
     * @param query
     * @return
     */
    Page<ProductQueryBO> selectProduct(ProductDataQuery query);

    /**
     * 批量处理
     *
     * @param ids
     * @param productStatus
     * @return
     */
    int updateProductStatus(String ids, ProductStatusEnum productStatus, Long merchantId);

    /**
     * 用户端商品详情，根据商品ID获取商品信息
     *
     * @param id
     * @return
     */
    ProductInfoBO selectProductById(Long id);

    /**
     * 商家端编辑商品时，根据ID查询商品
     *
     * @param productId
     * @return
     */
    ProductEditInfoBO selectEditProductById(Long productId);

    /**
     * 编辑商品
     *
     * @param product
     */
    void eidtProduct(EditProductDataParam product);

    /**
     * 操作库存
     *
     * @param productId
     * @param num       加减数量数量
     * @param flag      M-减、A-加
     */
    void editTotalInventory(Long productId, int num, String flag);

    /**
     * 操作销量
     *
     * @param productId
     * @param num       加减数量数量
     * @param flag      M-减、A-加
     */
    void editTotalSaleVolume(Long productId, int num, String flag);

    /**
     * 操作收藏
     *
     * @param productId
     * @param num       加减数量数量
     * @param flag      M-减、A-加
     */
    void editTotalFavorite(Long productId, int num, String flag);

    /**
     * 根据商品ID查询商品
     *
     * @param id
     * @return
     */
    ProductInfoBO getProductById(Long id);


    /**
     * 运营平台查询所有已审核的商品
     *
     * @param param
     * @return
     */
    List<ProductQueryBO> selectProductPlat(ProductParam param);

    /**
     * 查询当前商家总有多少商品
     *
     * @param merchantId
     * @return
     */
    Integer selectProductCount(Long merchantId);

    /**
     * 查询所有上架中商品
     *
     * @return
     */
    List<ProductInfoBO> listProduct(ListProductParam listProductParam);

    /**
     * 更新商品平均日销量
     *
     * @param id
     * @param averageDailySales
     */
    void updateAverageDailySalesById(Long id, BigDecimal averageDailySales);

    /**
     * 更新商品平均日销量
     *
     * @param pageSize
     */
    void executeProductAverageDailySales(Integer pageSize);

    /**
     * 重建商品索引
     */
    void rebuildProductIndex(Integer pageSize);

    /**
     * 删除无效的商品索引
     */
    void delInvalidProductIndex();

    /**
     * 查询所有上架的商品
     *
     * @param listProductParam
     * @return
     */
    Page<ProductQueryBO> listAllProduct(ListProductParam listProductParam);

    /**
     * 根据ids查询商品信息
     *
     * @param ids
     * @return
     */
    List<ProductBO> listProductByIds(List<Long> ids);

    void soldOutProductByMerchantId(Long merchantId);

    /**
     * 根据商品id查询商品名称和图片
     *
     * @param id
     * @return
     * @author zhangrc
     * @data 2017/08/10
     */
    ProductRelateAdInfoBO selectProductRelateAdInfo(Long id);

    /**
     * 运营平台强制下架商品
     *
     * @param id
     * @param remark
     */
    void downOperatorById(Long id, String remark);

}
