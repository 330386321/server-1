package com.lawu.eshop.product.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductPlatDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.dto.ProductRelateAdInfoDTO;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.product.param.ProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.ProductSrvConfig;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.bo.ProductRelateAdInfoBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.service.ProductService;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.utils.BeanUtil;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SolrService solrService;

    @Autowired
    private ProductSrvConfig productSrvConfig;

    /**
     * 查询商品列表
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> selectProduct(@RequestBody ProductDataQuery query) {
        Page<ProductQueryBO> page = productService.selectProduct(query);
        List<ProductQueryBO> list = page.getRecords();
        List<ProductQueryDTO> dtos = ProductConverter.convertDTOS(list);

        Page<ProductQueryDTO> retPage = new Page<>();
        retPage.setCurrentPage(query.getCurrentPage());
        retPage.setTotalCount(page.getTotalCount());
        retPage.setRecords(dtos);

        return successCreated(retPage);
    }

    /**
     * 商品批量操作
     *
     * @param ids           商品ID字符串
     * @param productStatus 目标修改的状态
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
    public Result updateProductStatus(@RequestParam String ids, @RequestParam ProductStatusEnum productStatus,@RequestParam Long merchantId) {
        int counts = productService.updateProductStatus(ids, productStatus,merchantId);
        if(counts == -1){
            return successCreated(ResultCode.GOODS_PRODUCT_INVENTORY);
        }
        if (counts == 0 || counts != ids.split(",").length) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 用户端商品详情，根据ID查询商品详情
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "selectProductById", method = RequestMethod.GET)
    public Result<ProductInfoDTO> selectProductById(@RequestParam Long productId) {
        if (productId == null) {
            return successGet(ResultCode.ID_EMPTY);
        }

        // 商品基本信息
        ProductInfoBO productBO = productService.selectProductById(productId);
        if (productBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        ProductInfoDTO productDTO = new ProductInfoDTO();
        productDTO.setGmtCreate(productBO.getGmtCreate());
        productDTO.setId(productBO.getId());
        productDTO.setMerchantId(productBO.getMerchantId());
        productDTO.setMerchantUserNum(productBO.getMerchantNum());
        productDTO.setCategoryId(productBO.getCategoryId());
        productDTO.setName(productBO.getName());
        productDTO.setFeatureImage(productBO.getFeatureImage());
        productDTO.setImagesHeadUrl(productBO.getImagesHeadUrl());
        productDTO.setMaxPrice(productBO.getMaxPrice());
        productDTO.setMinPrice(productBO.getMinPrice());
        productDTO.setTotalSalesVolume(productBO.getTotalSalesVolume());
        productDTO.setTotalInventory(productBO.getTotalInventory());
        productDTO.setSpec(productBO.getSpec());
        productDTO.setContent(productBO.getContent());
        productDTO.setImageDetail(productBO.getImageDetail());
        productDTO.setAllowRefund(productBO.isAllowRefund());
        productDTO.setProductStatus(productBO.getProductStatus());
        return successGet(productDTO);
    }

    /**
     * 商家端编辑商品时，根据ID查询商品
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestParam Long productId) throws Exception {
        if (productId == null) {
            return successCreated(ResultCode.ID_EMPTY, null);
        }

        // 商品基本信息
        ProductEditInfoBO productBO = productService.selectEditProductById(productId);
        if (productBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND, null);
        }
        ProductEditInfoDTO productDTO = new ProductEditInfoDTO();
        BeanUtil.copyProperties(productBO, productDTO);

        productDTO.setAllowRefund(productBO.isAllowRefund());
        productDTO.setKeywords(productBO.getKeywords());
        return successGet(productDTO);
    }

    /**
     * 添加、编辑商品
     *
     * @param product
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    public Result saveProduct(@RequestBody @Valid EditProductDataParam product,
                              BindingResult result) {
    	String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	
        productService.eidtProduct(product);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 操作库存
     *
     * @param productId
     * @param num       加减数量数量
     * @param flag      M-减、A-加
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "editTotalInventory", method = RequestMethod.POST)
    public Result editTotalInventory(@RequestParam Long productId, @RequestParam int num, @RequestParam String flag) {
        productService.editTotalInventory(productId, num, flag);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 操作销量
     *
     * @param productId
     * @param num       加减数量数量
     * @param flag      M-减、A-加
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "editTotalSaleVolume", method = RequestMethod.POST)
    public Result editTotalSaleVolume(@RequestParam Long productId, @RequestParam int num, @RequestParam String flag) {
        productService.editTotalSaleVolume(productId, num, flag);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 根据商品ID查询商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getProduct/{id}", method = RequestMethod.GET)
    public Result<ProductInfoDTO> getProduct(@PathVariable Long id) {
        ProductInfoBO productBO = productService.getProductById(id);
        if (productBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(ProductConverter.convertInfoDTO(productBO));
    }

    /**
     * 查询已审核的所有商品
     *
     * @param param
     * @return
     * @author zhangrc
     * @date 2017/4/25
     */
    @RequestMapping(value = "selectProductByPlat", method = RequestMethod.POST)
    public Result<List<ProductPlatDTO>> selectProductByPlat(@RequestBody ProductParam param) {
        List<ProductQueryBO> boList = productService.selectProductPlat(param);
        List<ProductPlatDTO> dtoList = new ArrayList<>();
        if (!boList.isEmpty()) {
            for (ProductQueryBO productQueryBO : boList) {
                ProductPlatDTO dto = new ProductPlatDTO();
                dto.setId(productQueryBO.getId());
                dto.setName(productQueryBO.getName());
                dtoList.add(dto);
            }
        }

        return successCreated(dtoList);
    }


    /**
     * 查询商家上架商品的总数量
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "selectProductCount", method = RequestMethod.GET)
    public Result<Integer> selectProductCount(@RequestParam Long merchantId) {
    	Integer count = productService.selectProductCount(merchantId);
        return successGet(count);
    }

    /**
     * 查询所有上架中商品
     *
     * @return
     */
    @RequestMapping(value = "listProduct", method = RequestMethod.POST)
    public Result<List<ProductInfoDTO>> listProduct(@RequestBody ListProductParam listProductParam) {
        List<ProductInfoBO> productInfoBOS = productService.listProduct(listProductParam);
        if (productInfoBOS == null || productInfoBOS.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successCreated(ProductConverter.convertInfoDTO(productInfoBOS));
    }

    /**
     * 更新商品平均日销量，同时更新solr
     *
     * @param id
     * @param averageDailySales
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "updateAverageDailySales/{id}", method = RequestMethod.PUT)
    public Result updateAverageDailySales(@PathVariable Long id, @RequestParam BigDecimal averageDailySales) {
        ProductInfoBO productBO = productService.getProductById(id);
        if (productBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        productService.updateAverageDailySalesById(id, averageDailySales);
        return successCreated();
    }

    /**
     * 重建商品索引
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "rebuildProductIndex", method = RequestMethod.GET)
    public Result rebuildProductIndex() {
        productService.rebuildProductIndex();
        return successGet();
    }

    /**
     * 删除无效的商品索引
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "delInvalidProductIndex", method = RequestMethod.GET)
    public Result delInvalidProductIndex() {
        productService.delInvalidProductIndex();
        return successGet();
    }

    /**
     * 查询所有上架的商品
     *
     * @param listProductParam
     * @return
     */
    @RequestMapping(value = "listAllProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> listAllProduct(@RequestBody ListProductParam listProductParam) {
        Page<ProductQueryBO> productQueryBOPage = productService.listAllProduct(listProductParam);
        Page<ProductQueryDTO> page = new Page<>();
        page.setCurrentPage(productQueryBOPage.getCurrentPage());
        page.setTotalCount(productQueryBOPage.getTotalCount());
        page.setRecords(ProductConverter.convertDTOS(productQueryBOPage.getRecords()));
        return successCreated(page);
    }

    /**
     * 根据ids查询商品信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "listProductByIds", method = RequestMethod.GET)
    public Result<List<ProductSearchDTO>> listProductByIds(@RequestParam List<Long> ids) {
        List<ProductBO> productBOS = productService.listProductByIds(ids);
        return successGet(ProductConverter.convertSearchDTO(productBOS));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "soldOutProductByMerchantId")
    public Result soldOutProductByMerchantId(@RequestParam(value = "id")  Long id){
        productService.soldOutProductByMerchantId(id);
        return successCreated();
    }

    /**
     * 根据商品ID查询商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "selectProductRelateAdInfo/{id}", method = RequestMethod.GET)
    public Result<ProductRelateAdInfoDTO> selectProductRelateAdInfo(@PathVariable Long id) {
    	ProductRelateAdInfoBO bo = productService.selectProductRelateAdInfo(id);
        if (bo == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        
        ProductRelateAdInfoDTO dto = new ProductRelateAdInfoDTO();
        dto.setImgUrl(bo.getImgUrl());
        dto.setName(bo.getName());
        
        return successGet(dto);
    }

    /**
     * 删除全部索引数据
     *
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "delAllProductIndex", method = RequestMethod.GET)
    public Result delAllProductIndex() {
        solrService.delAllSolrDocs(productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        return successGet();
    }

}
