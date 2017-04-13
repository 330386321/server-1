package com.lawu.eshop.product.srv.converter;

import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.dto.ProductSolrDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.utils.DateUtil;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员信息转换器
 *
 * @author Yangqh
 * @date 2017/3/13
 */
public class ProductConverter {

    /**
     * BO转换
     *
     * @param productBOS
     * @return
     */
    public static List<ProductQueryDTO> convertDTOS(List<ProductQueryBO> productBOS) {
        if (productBOS == null) {
            return null;
        }

        List<ProductQueryDTO> productDTOS = new ArrayList<ProductQueryDTO>();
        for (ProductQueryBO productBO : productBOS) {
            ProductQueryDTO productDTO = new ProductQueryDTO();
            productDTO.setId(productBO.getId());
            productDTO.setName(productBO.getName());
            productDTO.setCategory(productBO.getCategory());
            productDTO.setFeatureImage(productBO.getFeatureImage());
            productDTO.setGmtCreate(productBO.getGmtCreate());
            productDTO.setSpec(productBO.getSpec());
            productDTO.setStatus(productBO.getStatus());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    /**
     * BO转换
     *
     * @param productDO
     * @return
     */
    public static ProductQueryBO convertQueryBO(ProductDO productDO) {
        ProductQueryBO productBO = new ProductQueryBO();
        productBO.setId(productDO.getId());
        productBO.setName(productDO.getName());
        productBO.setFeatureImage(productDO.getFeatureImage());
        productBO.setGmtCreate(DateUtil.getDateFormat(productDO.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
        productBO.setStatus(ProductStatusEnum.getEnum(productDO.getStatus()));
        return productBO;
    }

    public static ProductQueryDTO convertDTO(ProductQueryBO productBO) {
        ProductQueryDTO productDTO = new ProductQueryDTO();
        productDTO.setId(productBO.getId());
        productDTO.setName(productBO.getName());
        productDTO.setCategory(productBO.getCategory());
        productDTO.setFeatureImage(productBO.getFeatureImage());
        productDTO.setGmtCreate(productBO.getGmtCreate());
        productDTO.setSpec(productBO.getSpec());
        productDTO.setStatus(productBO.getStatus());
        return productDTO;
    }

    /**
     * 用户端商品详情BO转换
     *
     * @param productDO
     * @return
     */
    public static ProductInfoBO convertInfoBO(ProductDO productDO) {
        ProductInfoBO productInfoBO = new ProductInfoBO();
        productInfoBO.setId(productDO.getId());
        productInfoBO.setName(productDO.getName());
        productInfoBO.setFeatureImage(productDO.getFeatureImage());
        productInfoBO.setContent(productDO.getContent());
        productInfoBO.setMerchantId(productDO.getMerchantId());
        return productInfoBO;
    }

    /**
     * 商家编辑商品时商品详情BO转换
     *
     * @param productDO
     * @return
     */
    public static ProductEditInfoBO convertEditInfoBO(ProductDO productDO) {
        ProductEditInfoBO productEditInfoBO = new ProductEditInfoBO();
        productEditInfoBO.setId(productDO.getId());
        productEditInfoBO.setName(productDO.getName());
        productEditInfoBO.setCategory(productDO.getId());
        productEditInfoBO.setFeatureImage(productDO.getFeatureImage());
        productEditInfoBO.setContent(productDO.getContent());
        productEditInfoBO.setMerchantId(productDO.getMerchantId());
        return productEditInfoBO;
    }

    /**
     * 用户端商品详情DTO转换
     *
     * @param productBO
     * @return
     */
    public static ProductInfoDTO convertInfoDTO(ProductInfoBO productBO) {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setId(productBO.getId());
        productInfoDTO.setMerchantId(productBO.getMerchantId());
        productInfoDTO.setName(productBO.getName());
        productInfoDTO.setFeatureImage(productBO.getFeatureImage());
        productInfoDTO.setContent(productBO.getContent());
        productInfoDTO.setImagesHeadUrl(productBO.getImagesHeadUrl());
        productInfoDTO.setImageDetailUrl(productBO.getImageDetailUrl());
        productInfoDTO.setSpec(productBO.getSpec());
        productInfoDTO.setTotalSales(productBO.getTotalSales());
        productInfoDTO.setPriceMax(productBO.getPriceMax());
        productInfoDTO.setPriceMin(productBO.getPriceMin());
        return productInfoDTO;
    }

    /**
     * 商家编辑商品时查询商品详情DTO转换
     *
     * @param productBO
     * @return
     */
    public static ProductEditInfoDTO convertEditInfoDTO(ProductEditInfoBO productBO) {
        ProductEditInfoDTO productEditInfoDTO = new ProductEditInfoDTO();
        productEditInfoDTO.setId(productBO.getId());
        productEditInfoDTO.setMerchantId(productBO.getMerchantId());
        productEditInfoDTO.setCategory(productBO.getCategory());
        productEditInfoDTO.setName(productBO.getName());
        productEditInfoDTO.setFeatureImage(productBO.getFeatureImage());
        productEditInfoDTO.setContent(productBO.getContent());
        productEditInfoDTO.setImagesUrl(productBO.getImagesUrl());
        productEditInfoDTO.setSpec(productBO.getSpec());
        return productEditInfoDTO;
    }

    /**
     * Param转DO
     *
     * @param param
     * @return
     */
    public static ProductDO convertDO(EditProductDataParam param, Long id) {
        ProductDO productDO = new ProductDO();
        productDO.setName(param.getName());
        productDO.setCategoryId(param.getCategoryId());
        productDO.setMerchantId(param.getMerchantId());
//		productDO.setName(param.getNum());
        productDO.setContent(param.getContent());
        productDO.setFeatureImage(param.getFeatureImage());
//		productDO.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP.val);
        if (id == 0L) {
            productDO.setGmtCreate(new Date());
        }
        productDO.setGmtModified(new Date());
        return productDO;
    }

    /**
     * SolrInputDocument
     *
     * @param productId
     * @param param
     * @return
     */
    public static SolrInputDocument convertSolrInputDocument(Long productId, EditProductDataParam param) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", productId);
        document.addField("featureImage_s", param.getFeatureImage());
        document.setField("name_s", param.getName());
        document.addField("categoryId_i", param.getCategoryId());
        document.addField("content_s", param.getContent());
        document.addField("averageDailySales_d", 0);
        document.addField("salesVolume_i", 0);
        return document;
    }

    /**
     * SolrInputDocument
     *
     * @param productDO
     * @return
     */
    public static SolrInputDocument convertSolrInputDocument(ProductDO productDO) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", productDO.getId());
        document.addField("featureImage_s", productDO.getFeatureImage());
        document.setField("name_s", productDO.getName());
        document.addField("categoryId_i", productDO.getCategoryId());
        document.addField("content_s", productDO.getContent());
        document.addField("averageDailySales_d", productDO.getAverageDailySales());
        return document;
    }

    /**
     * SolrInputDocument
     *
     * @param solrDocumentList
     * @return
     */
    public static List<ProductSolrDTO> convertDTO(SolrDocumentList solrDocumentList) {
        if (solrDocumentList.isEmpty()) {
            return null;
        }

        List<ProductSolrDTO> productSolrDTOS = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            ProductSolrDTO productSolrDTO = new ProductSolrDTO();
            productSolrDTO.setFeatureImage(solrDocument.get("featureImage_s").toString());
            productSolrDTO.setName(solrDocument.get("name_s").toString());
            productSolrDTO.setContent(solrDocument.get("content_s").toString());
            productSolrDTO.setOriginalPrice(Double.valueOf(solrDocument.get("originalPrice_d").toString()));
            productSolrDTO.setPrice(Double.valueOf(solrDocument.get("price_d").toString()));
            productSolrDTOS.add(productSolrDTO);
        }
        return productSolrDTOS;
    }

}
