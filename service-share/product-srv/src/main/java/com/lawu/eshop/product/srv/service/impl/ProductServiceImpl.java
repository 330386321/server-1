package com.lawu.eshop.product.srv.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductImgTypeEnum;
import com.lawu.eshop.product.constant.ProductModelInventoryTypeEnum;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.MemberProductImageDetailDTO;
import com.lawu.eshop.product.dto.MemberProductModelDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.product.param.ProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.ProductSrvConfig;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.bo.ProductRelateAdInfoBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductModelConverter;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductDOExample.Criteria;
import com.lawu.eshop.product.srv.domain.ProductImageDO;
import com.lawu.eshop.product.srv.domain.ProductImageDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelInventoryDO;
import com.lawu.eshop.product.srv.domain.extend.ProductDOView;
import com.lawu.eshop.product.srv.domain.extend.ProductModelDOView;
import com.lawu.eshop.product.srv.domain.extend.ProductNumsView;
import com.lawu.eshop.product.srv.mapper.ProductCategoryeDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductImageDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelInventoryDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.ProductDOMapperExtend;
import com.lawu.eshop.product.srv.mapper.extend.ProductModelDOMapperExtend;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import com.lawu.eshop.product.srv.service.ProductService;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDOMapper productDOMapper;

    @Autowired
    private ProductModelDOMapper productModelDOMapper;

    @Autowired
    private ProductModelInventoryDOMapper productModelInventoryDOMapper;

    @Autowired
    private ProductImageDOMapper productImageDOMapper;

    @Autowired
    private ProductCategoryeDOMapper productCategoryeDOMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductDOMapperExtend productDOMapperExtend;

    @Autowired
    private ProductSrvConfig productSrvConfig;

    @Autowired
    private ProductModelDOMapperExtend productModelDOMapperExtend;

    @Autowired
    @Qualifier("delProductCommentTransactionMainServiceImpl")
    private TransactionMainService<Reply> delProductCommentTransactionMainServiceImpl;

    @Autowired
    private SolrService solrService;

    @Override
    public Page<ProductQueryBO> selectProduct(ProductDataQuery query) {
        ProductDOExample example = new ProductDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andMerchantIdEqualTo(query.getMerchantId()).andStatusEqualTo(query.getProductStatus().getVal());
        if (query.getName() != null && !"".equals(query.getName())) {
            criteria.andNameLike("%" + query.getName() + "%");
        }
        if (query.getCategoryId() != null && !"".equals(query.getCategoryId())) {
            criteria.andCategoryIdEqualTo(Integer.valueOf(query.getCategoryId()));
        }
        example.setOrderByClause(query.getProductSortFieldEnum().getVal() + " " + query.getOrderType());

        // 查询总数
        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
        Page<ProductQueryBO> page = new Page<>();
        page.setTotalCount(Long.valueOf(productDOMapper.countByExample(example)).intValue());
        page.setCurrentPage(query.getCurrentPage());
        List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);

        List<ProductQueryBO> productBOS = new ArrayList<ProductQueryBO>();

        for (ProductDO productDO : productDOS) {

            String specJson = "";
            String category = "";
            if (!query.getIsApp()) {
            	ProductModelDOExample modelExample = new ProductModelDOExample();
                modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
                // 查询商品型号
                List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
                List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
                for (ProductModelDO productModelDO : productModelDOS) {
                    ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
                    ProductModelBOS.add(productModelBO);
                }
                specJson = JSON.toJSONString(ProductModelBOS);
                category = productCategoryService.getFullName(productDO.getCategoryId());
            }
            ProductQueryBO productBO = ProductConverter.convertQueryBO(productDO);
            productBO.setSpec(specJson);

            productBO.setCategory(category);

            productBOS.add(productBO);
        }
        page.setRecords(productBOS);

        return page;
    }

    @Override
    @Transactional
    public int updateProductStatus(String ids, ProductStatusEnum productStatus,Long merchantId) {
        int rows = 0;
        List<String> delIds = new ArrayList<>();
        Collection<SolrInputDocument> documents = new ArrayList<>();
        String idArray[] = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {

        	ProductDO productDO = productDOMapper.selectByPrimaryKey(Long.valueOf(idArray[i]));
        	if(productStatus.getVal().equals(ProductStatusEnum.PRODUCT_STATUS_UP.getVal())){
        		if(productDO.getTotalInventory() < 1){
        			return -1;
        		}
        	}

        	ProductDO productDOEdit = new ProductDO();
        	productDOEdit.setStatus(productStatus.getVal());
            if (ProductStatusEnum.PRODUCT_STATUS_DOWN.equals(productStatus)) {
            	productDOEdit.setGmtDown(new Date());
            } else {
            	productDOEdit.setGmtDown(null);
            }
            productDOEdit.setGmtModified(new Date());
            ProductDOExample example = new ProductDOExample();
            example.createCriteria().andIdEqualTo(Long.valueOf(idArray[i])).andMerchantIdEqualTo(merchantId);
            int row = productDOMapper.updateByExampleSelective(productDOEdit, example);
            rows = rows + row;

            //更新solr索引
            if (productStatus.getVal().byteValue() == ProductStatusEnum.PRODUCT_STATUS_DEL.getVal()
                    || productStatus.getVal().byteValue() == ProductStatusEnum.PRODUCT_STATUS_DOWN.getVal()) {
                    delIds.add(idArray[i]);
            }
            if (productStatus.getVal().byteValue() == ProductStatusEnum.PRODUCT_STATUS_UP.getVal()) {
                    SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO);
                    documents.add(document);
            }
        }
        if(!delIds.isEmpty()){
            solrService.delSolrDocsByIds(delIds, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }
        if(!documents.isEmpty()){
            solrService.addSolrDocsList(documents, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }
        return rows;
    }

    @Override
    public ProductInfoBO selectProductById(Long id) {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
        if (productDO == null) {
            return null;
        }

        ProductInfoBO productInfoBO = ProductConverter.convertInfoBO(productDO);
        String featureImage = productInfoBO.getFeatureImage();
        featureImage = featureImage.replaceAll("\\\\", "/");
        productInfoBO.setFeatureImage(featureImage);
        
        // 查询商品型号
        ProductModelDOExample modelExample = new ProductModelDOExample();
        modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
        List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
        if (productModelDOS == null || productModelDOS.isEmpty()) {
            return null;
        }
        List<MemberProductModelDTO> spec = new ArrayList<MemberProductModelDTO>();
        BigDecimal minPrice = new BigDecimal("0");
        BigDecimal maxPrice = new BigDecimal("0");
        int k = 0;
        for (ProductModelDO mdo : productModelDOS) {
            MemberProductModelDTO dto = new MemberProductModelDTO();
            dto.setId(mdo.getId());
            dto.setInventory(mdo.getInventory());
            dto.setName(mdo.getName());
            dto.setOriginalPrice(mdo.getOriginalPrice());
            dto.setPrice(mdo.getPrice());
            spec.add(dto);

            if (k == 0){
                minPrice = mdo.getPrice();
                maxPrice = mdo.getPrice();
            } else{
                if(mdo.getPrice().compareTo(minPrice) == -1){
                    minPrice = mdo.getPrice();
                }
                if(mdo.getPrice().compareTo(maxPrice) == 1){
                    maxPrice = mdo.getPrice();
                }
            }
            k++;
        }
        productInfoBO.setSpec(spec);
        productInfoBO.setMinPrice(minPrice.toString());
        productInfoBO.setMaxPrice(maxPrice.toString());


        // 查询商品图片
        ProductImageDOExample imageExample = new ProductImageDOExample();
        imageExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
        imageExample.setOrderByClause(" sortid asc ");
        List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
        List<String> imagesHead = new ArrayList<String>();
        List<String> imagesDetail = new ArrayList<String>();
        for (ProductImageDO image : imageDOS) {
            if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_HEAD.getVal().byteValue()) {
                imagesHead.add(image.getImagePath().replaceAll("\\\\", "/"));
            } else if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_DETAIL.getVal().byteValue()) {
                imagesDetail.add(image.getImagePath().replaceAll("\\\\", "/"));
            }
        }
        productInfoBO.setImagesHeadUrl(imagesHead);
        //商品描述图片
        String imageContent = productDO.getImageContent() == null ? "[]" : productDO.getImageContent();
        List<String> imageContents = StringUtil.getJsonListToStringList(imageContent);
        List<MemberProductImageDetailDTO> imageDetail = new ArrayList<MemberProductImageDetailDTO>();
        for (int i = 0; i < imageContents.size(); i++) {
            MemberProductImageDetailDTO d = new MemberProductImageDetailDTO();
            d.setDetail(imageContents.get(i));
            if(imagesDetail.size() < (i+1)){
                break;
            }
            d.setImageUrl(imagesDetail.get(i));
            imageDetail.add(d);
        }
        productInfoBO.setImageDetail(imageDetail);

        return productInfoBO;
    }

    @Override
    public ProductEditInfoBO selectEditProductById(Long productId) {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        if (productDO == null) {
            return null;
        }

        ProductEditInfoBO productEditInfoBO = ProductConverter.convertEditInfoBO(productDO);

        // 查询商品型号
        ProductModelDOExample modelExample = new ProductModelDOExample();
        modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
        List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);

        List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
        for (ProductModelDO productModelDO : productModelDOS) {
            ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
            ProductModelBOS.add(productModelBO);
        }
        productEditInfoBO.setSpec(ProductModelBOS);

        String featureImage = productEditInfoBO.getFeatureImage();
        featureImage = featureImage.replaceAll("\\\\", "/");
        productEditInfoBO.setFeatureImage(featureImage);

        // 查询滚动图片
        ProductImageDOExample imageExample = new ProductImageDOExample();
        imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
                .andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_HEAD.getVal()).andStatusEqualTo(true);
        List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
        List<String> images = new ArrayList<String>();
        for (ProductImageDO image : imageDOS) {
            String imageUrl = image.getImagePath();
            imageUrl = imageUrl.replaceAll("\\\\", "/");
            images.add(imageUrl);
        }
        productEditInfoBO.setImagesUrl(images);

        // 查询详情图片
        imageExample.clear();
        imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
                .andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.getVal()).andStatusEqualTo(true);
        List<ProductImageDO> imageDetailDOS = productImageDOMapper.selectByExample(imageExample);
        List<String> imageDetails = new ArrayList<String>();
        for (ProductImageDO image : imageDetailDOS) {
            String imageUrl = image.getImagePath();
            imageUrl = imageUrl.replaceAll("\\\\", "/");
            imageDetails.add(imageUrl);
        }
        productEditInfoBO.setImageDetailUrl(imageDetails);

        String category = productCategoryService.getFullName(productDO.getCategoryId());
        productEditInfoBO.setCategoryName(category);

        return productEditInfoBO;
    }

    @Override
    @Transactional
    public void eidtProduct(EditProductDataParam param) {

        Long productId = param.getProductId();
        int inventory = 0;
        int salesVolume = 0;
        BigDecimal price = new BigDecimal("0");
        BigDecimal mprice = new BigDecimal("0");//修改为型号中最低价对应的原价,用于商品首页显示
        int traverseCnt = 0;

        boolean isEdit = true;
        if (productId == null || productId <= 0) {
            // 保存商品信息
            ProductDO productDO = ProductConverter.convertDO(param, 0L);
            String featureImage = productDO.getFeatureImage();
            featureImage = featureImage.replaceAll("/", File.separator);
            productDO.setFeatureImage(featureImage);
            ProductCategoryeDO productCategoryeDO = productCategoryeDOMapper.selectByPrimaryKey(productDO.getCategoryId());
            if(productCategoryeDO != null && productCategoryeDO.getIsVirtual()){
                productDO.setIsAllowRefund(false);
            }
            productDOMapper.insertSelective(productDO);
            productId = productDO.getId();
            isEdit = false;
        } else {
            // 修改商品基本信息
            ProductDO productDO = ProductConverter.convertDO(param, productId);
            String featureImage = productDO.getFeatureImage();
            featureImage = featureImage.replace("/", File.separator);
            productDO.setFeatureImage(featureImage);
            ProductCategoryeDO productCategoryeDO = productCategoryeDOMapper.selectByPrimaryKey(productDO.getCategoryId());
            if(productCategoryeDO != null && productCategoryeDO.getIsVirtual()){
                productDO.setIsAllowRefund(false);
            }
            ProductDOExample example = new ProductDOExample();
            example.createCriteria().andIdEqualTo(productId).andMerchantIdEqualTo(param.getMerchantId());
            int ret = productDOMapper.updateByExampleSelective(productDO, example);
            if(ret == 0){
            	return;
            }
        }

        String spec = param.getSpec();
        List<ProductModelBO> speclist = JSON.parseArray(spec, ProductModelBO.class);
        if (!isEdit) {
            ProductModelDO pmDO = null;
            for (ProductModelBO dataBO : speclist) {
            	BigDecimal bdOriginalPrice = new BigDecimal("0");//原价等于空时保存数据库为0
            	if(dataBO.getOriginalPrice() != null){
            		bdOriginalPrice = dataBO.getOriginalPrice();
            	}
                pmDO = new ProductModelDO();
                pmDO.setMerchantId(param.getMerchantId());
                pmDO.setProductId(productId);
                pmDO.setName(dataBO.getName());
                pmDO.setOriginalPrice(bdOriginalPrice);
                pmDO.setPrice(dataBO.getPrice());
                pmDO.setInventory(dataBO.getInventory());
                pmDO.setStatus(true);
                pmDO.setGmtCreate(new Date());
                pmDO.setGmtModified(new Date());
                productModelDOMapper.insertSelective(pmDO);

                if (traverseCnt == 0) {
                    price = dataBO.getPrice();
                    mprice = dataBO.getOriginalPrice() == null ? dataBO.getPrice() : dataBO.getOriginalPrice();//原价等于空时取现价
                }
                if (dataBO.getPrice().compareTo(price) == -1) {
                    price = dataBO.getPrice();
                    mprice = dataBO.getOriginalPrice() == null ? dataBO.getPrice() : dataBO.getOriginalPrice();//原价等于空时取现价
                }
                inventory += dataBO.getInventory();
                traverseCnt++;
            }
        } else {
            for (ProductModelBO dataBO : speclist) {
            	BigDecimal bdOriginalPrice = new BigDecimal("0");
            	if(dataBO.getOriginalPrice() != null){
            		bdOriginalPrice = dataBO.getOriginalPrice();
            	}
                if (dataBO.getId() == null || dataBO.getId() == 0L) {
                    ProductModelDO pmDO = new ProductModelDO();
                    pmDO.setMerchantId(param.getMerchantId());
                    pmDO.setProductId(productId);
                    pmDO.setName(dataBO.getName());
                    pmDO.setOriginalPrice(bdOriginalPrice);
                    pmDO.setPrice(dataBO.getPrice());
                    pmDO.setInventory(dataBO.getInventory());
                    pmDO.setStatus(true);
                    pmDO.setGmtCreate(new Date());
                    pmDO.setGmtModified(new Date());
                    productModelDOMapper.insertSelective(pmDO);

                } else {
                	ProductModelDOView modelDO = new ProductModelDOView();
                    modelDO.setId(Long.valueOf(dataBO.getId()));
                    modelDO.setOriginalPrice(bdOriginalPrice);
                    modelDO.setPrice(dataBO.getPrice());
                    Integer gapInventory = dataBO.getInventory() - dataBO.getInventoryTrans();
                    modelDO.setInventory(gapInventory);
                    modelDO.setName(dataBO.getName());
                    modelDO.setGmtModified(new Date());
                    productModelDOMapperExtend.updateByExampleSelective(modelDO);

                    if (gapInventory != 0) {
                        ProductModelInventoryDO pmiDO = new ProductModelInventoryDO();
                        pmiDO.setProductModelId(dataBO.getId());
                        pmiDO.setQuantity(Math.abs(gapInventory.intValue()));
                        if (gapInventory > 0) {
                        	pmiDO.setType(ProductModelInventoryTypeEnum.PLUS.getValue());
                        }else if(gapInventory < 0){
                        	pmiDO.setType(ProductModelInventoryTypeEnum.MINUS.getValue());
                        }
                        pmiDO.setGmtCreate(new Date());
                        pmiDO.setGmtModified(new Date());
                        productModelInventoryDOMapper.insertSelective(pmiDO);
                    }
                }

                if (traverseCnt == 0) {
                    price = dataBO.getPrice();
                    mprice = dataBO.getOriginalPrice() == null ? dataBO.getPrice() : dataBO.getOriginalPrice();//原价等于空时取现价
                }
                if (dataBO.getPrice().compareTo(price) == -1) {
                    price = dataBO.getPrice();
                    mprice = dataBO.getOriginalPrice() == null ? dataBO.getPrice() : dataBO.getOriginalPrice();//原价等于空时取现价
                }
                inventory += dataBO.getInventory();
                salesVolume += dataBO.getSalesVolume();
                traverseCnt++;
            }
        }

        // 删除商品型号信息
        if (param.getDeleteSpecIds() != null && !"".equals(param.getDeleteSpecIds())) {
            String deleteSpecIds = param.getDeleteSpecIds();
            List<String> specIdsList = Arrays.asList(deleteSpecIds.split(","));
            for (String specId : specIdsList) {
                ProductModelDOExample modelExample = new ProductModelDOExample();
                modelExample.createCriteria().andIdEqualTo(Long.valueOf(specId));
                ProductModelDO modelDO = new ProductModelDO();
                modelDO.setStatus(false);
                modelDO.setGmtModified(new Date());
                productModelDOMapper.updateByExampleSelective(modelDO, modelExample);

                // 逻辑删除商品型号评价
                delProductCommentTransactionMainServiceImpl.sendNotice(Long.valueOf(specId));
            }
        }

        ProductDO productDO = new ProductDO();
        productDO.setTotalInventory(inventory);
        productDO.setTotalSalesVolume(salesVolume);
        productDO.setMinPrice(price);
        productDO.setMaxPrice(mprice);
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andIdEqualTo(productId);
        productDOMapper.updateByExampleSelective(productDO, example);

        if (isEdit) {
            // 删除产品图片
            ProductImageDOExample imageExample = new ProductImageDOExample();
            imageExample.createCriteria().andProductIdEqualTo(productId);
            ProductImageDO imageDO = new ProductImageDO();
            imageDO.setStatus(false);
            imageDO.setGmtModified(new Date());
            productImageDOMapper.updateByExampleSelective(imageDO, imageExample);
        }

        // 保存商品滚动图片信息
        ProductImageDO pcDO = null;
        String imageUrl = param.getProductImages();
        String[] imageUrls = imageUrl.split(",");
        for (int i = 0; i < imageUrls.length; i++) {
            String imgUrl = imageUrls[i];
            imgUrl = imgUrl.replace("/", File.separator);
            pcDO = new ProductImageDO();
            pcDO.setProductId(Long.valueOf(productId));
            pcDO.setImagePath(imgUrl);
            pcDO.setGmtCreate(new Date());
            pcDO.setGmtModified(new Date());
            pcDO.setSortid(0);
            pcDO.setStatus(true);
            pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_HEAD.getVal());
            if(StringUtils.isBlank(imgUrl)){
                continue;
            }
            productImageDOMapper.insert(pcDO);
        }
        // 保存商品详情图片
        String detaiImageUrl = param.getDetailImages();
        String[] detaiImageUrls = detaiImageUrl.split(",");
        for (int i = 0; i < detaiImageUrls.length; i++) {
            String imgUrl = detaiImageUrls[i];
            imgUrl = imgUrl.replace("/", File.separator);
            pcDO = new ProductImageDO();
            pcDO.setProductId(Long.valueOf(productId));
            pcDO.setImagePath(imgUrl);
            pcDO.setGmtCreate(new Date());
            pcDO.setGmtModified(new Date());
            pcDO.setStatus(true);
            pcDO.setSortid(i + 1);
            pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.getVal());
            if(StringUtils.isBlank(imgUrl)){
                continue;
            }
            productImageDOMapper.insert(pcDO);
        }

        if (param.getProductStatus() != null && ProductStatusEnum.PRODUCT_STATUS_DOWN.getVal().equals(param.getProductStatus().getVal())){
            solrService.delSolrDocsById(productId, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        } else {
            ProductDO productDO1 = productDOMapper.selectByPrimaryKey(productId);
            SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO1);
            solrService.addSolrDocs(document, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }
    }


    @Override
    @Transactional
    public void editTotalInventory(Long productId, int num, String flag) {
        ProductNumsView view = new ProductNumsView();
        view.setProductId(productId);
        view.setFlag(flag);
        view.setNum(num);
        view.setGmtModified(new Date());
        productDOMapperExtend.editTotalInventory(view);

        ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO);
        solrService.addSolrDocs(document, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
    }

    @Override
    @Transactional
    public void editTotalSaleVolume(Long productId, int num, String flag) {
        ProductNumsView view = new ProductNumsView();
        view.setProductId(productId);
        view.setFlag(flag);
        view.setNum(num);
        view.setGmtModified(new Date());
        productDOMapperExtend.editTotalSaleVolume(view);

        ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO);
        solrService.addSolrDocs(document, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
    }

    @Override
    public void editTotalFavorite(Long productId, int num, String flag) {
        ProductNumsView view = new ProductNumsView();
        view.setProductId(productId);
        view.setFlag(flag);
        view.setNum(num);
        view.setGmtModified(new Date());
        productDOMapperExtend.editTotalFavorite(view);

    }

    @Override
    public List<ProductQueryBO> selectProductPlat(ProductParam param) {
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());
        List<ProductDO> doList = productDOMapper.selectByExample(example);
        List<ProductQueryBO> boList = new ArrayList<>();
        if (!doList.isEmpty()) {
            for (ProductDO productDO : doList) {
                ProductQueryBO bo = new ProductQueryBO();
                bo.setId(productDO.getId());
                bo.setName(productDO.getName());
                boList.add(bo);
            }
        }
        return boList;
    }

    @Override
    public ProductInfoBO getProductById(Long id) {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
        return ProductConverter.convertInfoBO(productDO);
    }

    @Override
    public Integer selectProductCount(Long merchantId) {
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal()).andMerchantIdEqualTo(merchantId);
        Integer count = Long.valueOf(productDOMapper.countByExample(example)).intValue();
        return count;
    }

    @Override
    public List<ProductInfoBO> listProduct(ListProductParam listProductParam) {
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());
        RowBounds rowBounds = new RowBounds(listProductParam.getOffset(), listProductParam.getPageSize());
        List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        return ProductConverter.convertInfoBO(productDOS);
    }

    @Override
    @Transactional
    public void updateAverageDailySalesById(Long id, BigDecimal averageDailySales) {
        ProductDO productDO = new ProductDO();
        productDO.setId(id);
        productDO.setAverageDailySales(averageDailySales);
        productDOMapper.updateByPrimaryKeySelective(productDO);
    }

    @Override
    @Transactional
    public void executeProductAverageDailySales(Integer pageSize) {
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());

        int currentPage = 0;
        List<ProductDO> productDOS;
        RowBounds rowBounds;
        while (true) {
            currentPage++;
            rowBounds = new RowBounds((currentPage - 1) * pageSize, pageSize);
            productDOS = productDOMapper.selectByExampleWithRowbounds(example, rowBounds);
            if (productDOS == null || productDOS.isEmpty()) {
                return;
            }

            for (ProductDO productDO : productDOS) {
                int days = DateUtil.daysOfTwo(productDO.getGmtCreate());
                int salesVolume = productDO.getTotalSalesVolume();
                double averageDailySales = 0;
                if (days > 0) {
                    averageDailySales = (double) salesVolume / days;
                }
                ProductDO productDO1 = new ProductDO();
                productDO1.setId(productDO.getId());
                productDO1.setAverageDailySales(BigDecimal.valueOf(averageDailySales));
                productDOMapper.updateByPrimaryKeySelective(productDO1);
            }
        }
    }

    @Override
    public void rebuildProductIndex(Integer pageSize) {
        ListProductParam listProductParam = new ListProductParam();
        listProductParam.setPageSize(pageSize);
        int currentPage = 0;

        while (true) {
            currentPage ++;
            listProductParam.setCurrentPage(currentPage);
            ProductDOExample example = new ProductDOExample();
            example.createCriteria().andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());
            RowBounds rowBounds = new RowBounds(listProductParam.getOffset(), listProductParam.getPageSize());
            List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(example, rowBounds);
            if (productDOS == null || productDOS.isEmpty()) {
                return ;
            }

            Collection<SolrInputDocument> documents = new ArrayList<>();
            for (ProductDO productDO : productDOS) {
                SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO);
                documents.add(document);
            }
            solrService.addSolrDocsList(documents, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }
    }

    @Override
    public void delInvalidProductIndex() {
        ListProductParam listProductParam = new ListProductParam();
        listProductParam.setPageSize(1000);
        int currentPage = 0;

        while (true) {
            currentPage ++;
            listProductParam.setCurrentPage(currentPage);
            ProductDOExample example = new ProductDOExample();
            example.createCriteria().andStatusNotEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());
            RowBounds rowBounds = new RowBounds(listProductParam.getOffset(), listProductParam.getPageSize());
            List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(example, rowBounds);
            if (productDOS == null || productDOS.isEmpty()) {
                return ;
            }

            List<String> ids = new ArrayList<>();
            for (ProductDO productDO : productDOS) {
                ids.add(String.valueOf(productDO.getId()));
            }
            solrService.delSolrDocsByIds(ids, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }
    }

    @Override
    public Page<ProductQueryBO> listAllProduct(ListProductParam listProductParam) {
        ProductDOExample example = new ProductDOExample();
        Criteria criteria = example.createCriteria();
        if (listProductParam.getStatusEnum() != null) {
            criteria.andStatusEqualTo(listProductParam.getStatusEnum().getVal());
        }
        if (StringUtils.isNotEmpty(listProductParam.getName())) {
            criteria.andNameLike("%" + listProductParam.getName() + "%");
        }
        if (StringUtils.isNotEmpty(listProductParam.getSortName()) && StringUtils.isNotEmpty(listProductParam.getSortOrder())) {
            example.setOrderByClause("gmt_create " + listProductParam.getSortOrder());
        }

        // 查询总数
        RowBounds rowBounds = new RowBounds(listProductParam.getOffset(), listProductParam.getPageSize());
        Page<ProductQueryBO> page = new Page<>();
        page.setTotalCount(Long.valueOf(productDOMapper.countByExample(example)).intValue());
        page.setCurrentPage(listProductParam.getCurrentPage());
        List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);

        List<ProductQueryBO> productBOS = new ArrayList<ProductQueryBO>();

        for (ProductDO productDO : productDOS) {

            ProductModelDOExample  modelExample = new ProductModelDOExample();
            modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
            // 查询商品型号
            List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
            List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
            for (ProductModelDO productModelDO : productModelDOS) {
                ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
                ProductModelBOS.add(productModelBO);
            }
            String specJson = JSON.toJSONString(ProductModelBOS);
            String category = productCategoryService.getFullName(productDO.getCategoryId());
            ProductQueryBO productBO = ProductConverter.convertQueryBO(productDO);
            productBO.setSpec(specJson);

            productBO.setCategory(category);

            productBOS.add(productBO);
        }
        page.setRecords(productBOS);

        return page;
    }

    @Override
    public List<ProductBO> listProductByIds(List<Long> ids) {
        List<ProductDOView> productDOViewList = productDOMapperExtend.listProductByIds(ids);
        return ProductConverter.convertBOS(productDOViewList);
    }

    @Override
    @Transactional
    public void soldOutProductByMerchantId(Long merchantId) {
        ProductDOExample example2 = new ProductDOExample();
        example2.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.getVal());
        List<ProductDO> productDOS = productDOMapper.selectByExample(example2);
        if (!productDOS.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (ProductDO product : productDOS) {
                ids.add(String.valueOf(product.getId()));
            }
            solrService.delSolrDocsByIds(ids, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        }

        ProductDO productDO = new ProductDO();
        productDO.setStatus(ProductStatusEnum.PRODUCT_STATUS_DOWN.getVal());
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        productDOMapper.updateByExampleSelective(productDO, example);
    }

	@Override
	public ProductRelateAdInfoBO selectProductRelateAdInfo(Long id) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
		ProductRelateAdInfoBO bo = new ProductRelateAdInfoBO();
		bo.setName(productDO.getName());
		bo.setImgUrl(productDO.getFeatureImage());
		return bo;
	}

	@Override
	public void downOperatorById(Long id, String remark) {
		ProductDO product = new ProductDO();
		product.setId(id);
		product.setGmtDown(new Date());
		product.setGmtModified(new Date());
		product.setStatus(ProductStatusEnum.PRODUCT_STATUS_DOWN.getVal());
		product.setRemark(remark);
		productDOMapper.updateByPrimaryKeySelective(product);
		solrService.delSolrDocsById(id, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
		
	}

}
