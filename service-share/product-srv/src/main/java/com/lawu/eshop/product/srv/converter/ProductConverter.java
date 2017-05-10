package com.lawu.eshop.product.srv.converter;

import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductDataParam_bak;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.bo.ProductSearchBO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;
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
        List<ProductQueryDTO> productDTOS = new ArrayList<ProductQueryDTO>();
        if (productBOS == null || productBOS.isEmpty()) {
            return productDTOS;
        }

        for (ProductQueryBO productBO : productBOS) {
            ProductQueryDTO productDTO = new ProductQueryDTO();
            productDTO.setId(productBO.getId());
            productDTO.setName(productBO.getName());
            productDTO.setCategory(productBO.getCategory());
            productDTO.setFeatureImage(productBO.getFeatureImage());
            productDTO.setGmtCreate(productBO.getGmtCreate());
            productDTO.setSpec(productBO.getSpec());
            productDTO.setStatus(productBO.getStatus());
            productDTO.setTotalInventory(productBO.getTotalInventory());
            productDTO.setTotalSalesVolume(productBO.getTotalSalesVolume());
            productDTO.setTotalFavorite(productBO.getTotalFavorite());
            productDTO.setMinPrice(productBO.getMinPrice());
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
        productBO.setGmtCreate(DateUtil.getDateFormat(productDO.getGmtCreate(), "yyyy-MM-dd"));
        productBO.setStatus(ProductStatusEnum.getEnum(productDO.getStatus()));
        productBO.setTotalInventory(productDO.getTotalInventory() == null ? "0" : productDO.getTotalInventory().toString());
        productBO.setTotalSalesVolume(productDO.getTotalSalesVolume() == null ? "0" : productDO.getTotalSalesVolume().toString());
        productBO.setTotalFavorite(productDO.getTotalFavorite() == null ? "0" : productDO.getTotalFavorite().toString());
        productBO.setMinPrice(productDO.getMinPrice() == null ? "0" : productDO.getMinPrice().toString());
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
        productInfoBO.setTotalSalesVolume(productDO.getTotalSalesVolume());
        productInfoBO.setTotalInventory(productDO.getTotalInventory());
        productInfoBO.setMaxPrice(String.valueOf(productDO.getMaxPrice()));
        productInfoBO.setMinPrice(String.valueOf(productDO.getMinPrice()));
        productInfoBO.setGmtCreate(productDO.getGmtCreate());
        productInfoBO.setCategoryId(productDO.getCategoryId());
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
        productEditInfoBO.setCategory(Long.valueOf(productDO.getCategoryId()));
        productEditInfoBO.setFeatureImage(productDO.getFeatureImage());
        productEditInfoBO.setContent(productDO.getContent());
        productEditInfoBO.setMerchantId(productDO.getMerchantId());
        
        String imageContent = (productDO.getImageContent() == null || "".equals(productDO.getImageContent())) ? "[]" : productDO.getImageContent();
        List<String> imageContents = StringUtil.getJsonListToStringList(imageContent);
        productEditInfoBO.setImageContent(imageContents);
        
        productEditInfoBO.setAllowRefund(productDO.getIsAllowRefund());
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
        productInfoDTO.setSpec(productBO.getSpec());
        productInfoDTO.setMaxPrice(productBO.getMaxPrice());
        productInfoDTO.setMinPrice(productBO.getMinPrice());
        productInfoDTO.setTotalSalesVolume(productBO.getTotalSalesVolume());
        productInfoDTO.setGmtCreate(productBO.getGmtCreate());
        return productInfoDTO;
    }

    /**
     * BO转换
     *
     * @param productDOList
     * @return
     */
    public static List<ProductInfoBO> convertInfoBO(List<ProductDO> productDOList) {
        List<ProductInfoBO> productInfoBOS = new ArrayList<>();
        if (productDOList == null || productDOList.isEmpty()) {
            return productInfoBOS;
        }
        for (ProductDO productDO : productDOList) {
            productInfoBOS.add(convertInfoBO(productDO));
        }
        return productInfoBOS;
    }

    /**
     * DTO转换
     *
     * @param productInfoBOList
     * @return
     */
    public static List<ProductInfoDTO> convertInfoDTO(List<ProductInfoBO> productInfoBOList) {
        List<ProductInfoDTO> productInfoDTOS = new ArrayList<>();
        if (productInfoBOList == null || productInfoBOList.isEmpty()) {
            return productInfoDTOS;
        }
        for (ProductInfoBO productInfoBO : productInfoBOList) {
            productInfoDTOS.add(convertInfoDTO(productInfoBO));
        }
        return productInfoDTOS;
    }

    /**
     * 商家编辑商品时查询商品详情DTO转换
     *
     * @param productBO
     * @return
     */
//    public static ProductEditInfoDTO convertEditInfoDTO1(ProductEditInfoBO productBO) {
//        ProductEditInfoDTO productEditInfoDTO = new ProductEditInfoDTO();
//        productEditInfoDTO.setId(productBO.getId());
//        productEditInfoDTO.setMerchantId(productBO.getMerchantId());
//        productEditInfoDTO.setCategory(productBO.getCategory());
//        productEditInfoDTO.setName(productBO.getName());
//        productEditInfoDTO.setFeatureImage(productBO.getFeatureImage());
//        productEditInfoDTO.setContent(productBO.getContent());
//        productEditInfoDTO.setImagesUrl(productBO.getImagesUrl());
//        productEditInfoDTO.setSpec(productBO.getSpec());
//        productEditInfoDTO.setImageContent(productBO.getImageContent());
//        return productEditInfoDTO;
//    }

    /**
     * Param转DO
     *
     * @param param
     * @return
     */
    public static ProductDO convertDO(EditProductDataParam_bak param, Long id) {
        ProductDO productDO = new ProductDO();
        productDO.setName(param.getName());
        productDO.setCategoryId(param.getCategoryId());
        productDO.setMerchantId(param.getMerchantId());
        productDO.setContent(param.getContent());
        productDO.setFeatureImage(param.getFeatureImage());
        productDO.setImageContent(param.getImageContents());
        productDO.setImageContent(param.getImageContents());
        productDO.setIsAllowRefund(param.getIsAllowRefund());
        if (id == 0L) {
            productDO.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP.val);
            productDO.setGmtCreate(new Date());
        }
        productDO.setGmtModified(new Date());
        return productDO;
    }

    public static ProductDO convertDO(EditProductDataParam param, Long id) {
        ProductDO productDO = new ProductDO();
        productDO.setName(param.getName());
        productDO.setCategoryId(param.getCategoryId());
        productDO.setMerchantId(param.getMerchantId());
        productDO.setContent(param.getContent());
        productDO.setFeatureImage(param.getFeatureImage());
        productDO.setImageContent(param.getImageContents());
        productDO.setImageContent(param.getImageContents());
        productDO.setIsAllowRefund(param.getIsAllowRefund());
        if (id == 0L) {
            productDO.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP.val);
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
    public static SolrInputDocument convertSolrInputDocument(Long productId, EditProductDataParam_bak param) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", productId);
        document.addField("featureImage_s", param.getFeatureImage());
        document.setField("name_s", param.getName());
        document.addField("categoryId_i", param.getCategoryId());
        document.addField("content_s", param.getContent());
        document.addField("averageDailySales_d", 0);
        return document;
    }

    public static SolrInputDocument convertSolrInputDocument(Long productId, EditProductDataParam param) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", productId);
        document.addField("featureImage_s", param.getFeatureImage());
        document.setField("name_s", param.getName());
        document.addField("categoryId_i", param.getCategoryId());
        document.addField("content_s", param.getContent());
        document.addField("averageDailySales_d", 0);
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
        document.addField("averageDailySales_d", productDO.getAverageDailySales() == null ? 0 : productDO.getAverageDailySales().doubleValue());
        return document;
    }

    /**
     * SolrInputDocument
     *
     * @param solrDocumentList
     * @return
     */
    public static List<ProductSearchDTO> convertDTO(SolrDocumentList solrDocumentList) {
        List<ProductSearchDTO> productSearchDTOS = new ArrayList<>();
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return productSearchDTOS;
        }

        for (SolrDocument solrDocument : solrDocumentList) {
            ProductSearchDTO productSearchDTO = new ProductSearchDTO();
            productSearchDTO.setProductId(Long.valueOf(solrDocument.get("id").toString()));
            productSearchDTO.setFeatureImage(solrDocument.get("featureImage_s").toString());
            productSearchDTO.setName(solrDocument.get("name_s").toString());
            productSearchDTO.setContent(solrDocument.get("content_s").toString());
            productSearchDTO.setOriginalPrice(Double.valueOf(solrDocument.get("originalPrice_d").toString()));
            productSearchDTO.setPrice(Double.valueOf(solrDocument.get("price_d").toString()));
            productSearchDTO.setSalesVolume(Integer.valueOf(solrDocument.get("salesVolume_i").toString()));
            productSearchDTOS.add(productSearchDTO);
        }
        return productSearchDTOS;
    }

    /**
     * BO转换
     *
     * @param productDOS
     * @return
     */
    public static List<ProductSearchBO> convertBO(List<ProductDO> productDOS) {
        List<ProductSearchBO> productSearchBOS = new ArrayList<>();
        if (productDOS == null || productDOS.isEmpty()) {
            return productSearchBOS;
        }

        for (ProductDO productDO : productDOS) {
            ProductSearchBO productSearchBO = new ProductSearchBO();
            productSearchBO.setProductId(productDO.getId());
            productSearchBO.setName(productDO.getName());
            productSearchBO.setContent(productDO.getContent());
            productSearchBO.setFeatureImage(productDO.getFeatureImage());
            productSearchBO.setOriginalPrice(productDO.getMaxPrice().doubleValue());
            productSearchBO.setPrice(productDO.getMinPrice().doubleValue());
            productSearchBO.setSalesVolume(productDO.getTotalSalesVolume());
            productSearchBOS.add(productSearchBO);
        }
        return productSearchBOS;
    }

    /**
     * DTO转换
     *
     * @param productSearchBOS
     * @return
     */
    public static List<ProductSearchDTO> convertDTO(List<ProductSearchBO> productSearchBOS) {
        List<ProductSearchDTO> productSearchDTOS = new ArrayList<>();
        if (productSearchBOS == null || productSearchBOS.isEmpty()) {
            return productSearchDTOS;
        }

        for (ProductSearchBO productSearchBO : productSearchBOS) {
            ProductSearchDTO productSearchDTO = new ProductSearchDTO();
            productSearchDTO.setProductId(productSearchBO.getProductId());
            productSearchDTO.setName(productSearchBO.getName());
            productSearchDTO.setContent(productSearchBO.getContent());
            productSearchDTO.setFeatureImage(productSearchBO.getFeatureImage());
            productSearchDTO.setOriginalPrice(productSearchBO.getOriginalPrice());
            productSearchDTO.setPrice(productSearchBO.getPrice());
            productSearchDTO.setSalesVolume(productSearchBO.getSalesVolume());
            productSearchDTOS.add(productSearchDTO);
        }
        return productSearchDTOS;
    }

}
