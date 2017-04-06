package com.lawu.eshop.product.srv.service.impl;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductImgTypeEnum;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.ProductRecommendParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.*;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductModelConverter;
import com.lawu.eshop.product.srv.converter.ProductRecommendConverter;
import com.lawu.eshop.product.srv.domain.*;
import com.lawu.eshop.product.srv.domain.extend.ProductDOView;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductImageDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.ProductDOMapperExtend;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import com.lawu.eshop.product.srv.service.ProductService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDOMapper productDOMapper;

    @Autowired
    private ProductModelDOMapper productModelDOMapper;

    @Autowired
    private ProductImageDOMapper productImageDOMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductDOMapperExtend productDOMapperExtend;

    @Override
    public Page<ProductQueryBO> selectProduct(ProductDataQuery query) {
        ProductDOExample example = new ProductDOExample();
        example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
                .andNameLike("%" + query.getName() + "%")
                .andStatusEqualTo(query.getStatus().val);

        //查询总数
        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
        Page<ProductQueryBO> page = new Page<>();
        page.setTotalCount(productDOMapper.countByExample(example));
        page.setCurrentPage(query.getCurrentPage());
        List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);

        List<ProductQueryBO> productBOS = new ArrayList<ProductQueryBO>();

        ProductModelDOExample modelExample = null;
        for (ProductDO productDO : productDOS) {

            modelExample = new ProductModelDOExample();
            modelExample.createCriteria().andProductIdEqualTo(productDO.getId())
                    .andStatusEqualTo(true);
            //查询商品型号
            List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
            List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
            for (ProductModelDO productModelDO : productModelDOS) {
                ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
                ProductModelBOS.add(productModelBO);
            }
            String specJson = JSON.toJSONString(ProductModelBOS);

            ProductQueryBO productBO = ProductConverter.convertQueryBO(productDO);
            productBO.setSpec(specJson);

            String category = productCategoryService.getFullName(productDO.getCategoryId());
            productBO.setCategory(category);

            productBOS.add(productBO);
        }
        page.setRecords(productBOS);

        return page;
    }

    @Override
    @Transactional
    public int updateProductStatus(String ids, ProductStatusEnum productStatus) {
        int rows = 0;
        String idArray[] = ids.split(",");
        ProductDOExample examle = new ProductDOExample();
        for (int i = 0; i < idArray.length; i++) {
            examle.clear();
            ProductDO productDO = new ProductDO();
            productDO.setId(Long.valueOf(idArray[i]));
            productDO.setStatus(productStatus.val);
            productDO.setGmtModified(new Date());
            int row = productDOMapper.updateByPrimaryKeySelective(productDO);
            rows = rows + row;
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

        //查询商品型号
        ProductModelDOExample modelExample = new ProductModelDOExample();
        modelExample.createCriteria().andProductIdEqualTo(productDO.getId())
                .andStatusEqualTo(true);
        List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);

        List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
        Integer totalSales = 0;
        boolean rangePrice = true;
        if (productModelDOS.size() == 1) {
            rangePrice = false;
        }
        double max = productModelDOS.get(0).getPrice().doubleValue();
        double min = max;
        for (ProductModelDO productModelDO : productModelDOS) {
            ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
            ProductModelBOS.add(productModelBO);

            Integer salesVolume = productModelDO.getSalesVolume();
            totalSales = totalSales + salesVolume;

            double price = 0;
            if (rangePrice) {
                price = productModelDO.getPrice().doubleValue();
                if (max < price) {
                    max = price;
                }
                if (min > price) {
                    min = price;
                }
            }
        }
        String specJson = JSON.toJSONString(ProductModelBOS);
        productInfoBO.setSpec(specJson);
        productInfoBO.setTotalSales(totalSales);
        productInfoBO.setPriceMax(String.valueOf(max));
        productInfoBO.setPriceMin(String.valueOf(min));

        //查询商品图片
        ProductImageDOExample imageExample = new ProductImageDOExample();
        imageExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
        List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
        List<String> imagesHead = new ArrayList<String>();
        List<String> imagesDetail = new ArrayList<String>();
        for (ProductImageDO image : imageDOS) {
            if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_HEAD.val.byteValue()) {
                imagesHead.add(image.getImagePath());
            } else if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val.byteValue()) {
                imagesDetail.add(image.getImagePath());
            }
        }
        String imagesHeadJson = JSON.toJSONString(imagesHead);
        String imagesDetailJson = JSON.toJSONString(imagesDetail);
        productInfoBO.setImagesHeadUrl(imagesHeadJson);
        productInfoBO.setImageDetailUrl(imagesDetailJson);

        return productInfoBO;
    }

    @Override
    public ProductEditInfoBO selectEditProductById(Long productId) {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        if (productDO == null) {
            return null;
        }

        ProductEditInfoBO productEditInfoBO = ProductConverter.convertEditInfoBO(productDO);

        //查询商品型号
        ProductModelDOExample modelExample = new ProductModelDOExample();
        modelExample.createCriteria().andProductIdEqualTo(productDO.getId())
                .andStatusEqualTo(true);
        List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);

        List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
        for (ProductModelDO productModelDO : productModelDOS) {
            ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
            ProductModelBOS.add(productModelBO);
        }
        String specJson = JSON.toJSONString(ProductModelBOS);
        productEditInfoBO.setSpec(specJson);

        //查询型号图片
        ProductImageDOExample imageExample = new ProductImageDOExample();
        imageExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
        List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
        List<String> images = new ArrayList<String>();
        for (ProductImageDO image : imageDOS) {
            images.add(image.getImagePath());
        }
        String iamgesJson = JSON.toJSONString(images);
        productEditInfoBO.setImagesUrl(iamgesJson);

        return productEditInfoBO;
    }

    @Override
    @Transactional
    public void eidtProduct(Long productId, EditProductDataParam product) {

        boolean isEdit = true;
        if (productId == 0L || productId == null || productId < 0) {
            //保存商品信息
            ProductDO productDO = ProductConverter.convertDO(product, 0L);
            productDOMapper.insertSelective(productDO);
            productId = productDO.getId();
            isEdit = false;
        } else {
            //修改商品基本信息
            ProductDO productDO = ProductConverter.convertDO(product, productId);
            ProductDOExample example = new ProductDOExample();
            example.createCriteria().andIdEqualTo(productId);
            productDOMapper.updateByExampleSelective(productDO, example);
        }

        if (isEdit) {
            //删除商品型号信息
            ProductModelDOExample modelExample = new ProductModelDOExample();
            modelExample.createCriteria().andProductIdEqualTo(productId);
            ProductModelDO modelDO = new ProductModelDO();
            modelDO.setStatus(false);
            modelDO.setGmtModified(new Date());
            productModelDOMapper.updateByExampleSelective(modelDO, modelExample);
        }
        //新增型号信息
        String spec = product.getSpec();
        List<ProductModelBO> speclist = JSON.parseArray(spec, ProductModelBO.class);
        ProductModelDO pmDO = null;
        for (ProductModelBO dataBO : speclist) {
            pmDO = new ProductModelDO();
            pmDO.setMerchantId(product.getMerchantId());
            pmDO.setProductId(productId);
            pmDO.setName(dataBO.getName());
            pmDO.setSalesVolume(dataBO.getSalesVolume());
            pmDO.setOriginalPrice(dataBO.getOriginalPrice());
            pmDO.setPrice(dataBO.getPrice());
            pmDO.setInventory(Integer.valueOf(dataBO.getInventory()));
            pmDO.setGmtCreate(new Date());
            pmDO.setGmtModified(new Date());
            productModelDOMapper.insertSelective(pmDO);
        }

        if (isEdit) {
            //删除产品图片
            ProductImageDOExample imageExample = new ProductImageDOExample();
            imageExample.createCriteria().andProductIdEqualTo(productId);
            ProductImageDO imageDO = new ProductImageDO();
            imageDO.setStatus(false);
            imageDO.setGmtModified(new Date());
            productImageDOMapper.updateByExampleSelective(imageDO, imageExample);
        }
        //保存商品滚动图片信息
        ProductImageDO pcDO = null;
        String imageUrl = product.getProductImage();
        String[] imageUrls = imageUrl.split(",");
        for (int i = 0; i < imageUrls.length; i++) {
            pcDO = new ProductImageDO();
            pcDO.setProductId(Long.valueOf(productId));
            pcDO.setImagePath(imageUrls[i]);
            pcDO.setGmtCreate(new Date());
            pcDO.setGmtModified(new Date());
            pcDO.setStatus(true);
            pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_HEAD.val);
            productImageDOMapper.insert(pcDO);
        }
        //保存商品详情图片
        String imageDetailUrl = product.getProductDetailImage();
        String[] imageDetailUrls = imageDetailUrl.split(",");
        for (int i = 0; i < imageDetailUrls.length; i++) {
            pcDO = new ProductImageDO();
            pcDO.setProductId(Long.valueOf(productId));
            pcDO.setImagePath(imageUrls[i]);
            pcDO.setGmtCreate(new Date());
            pcDO.setGmtModified(new Date());
            pcDO.setStatus(true);
            pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val);
            productImageDOMapper.insert(pcDO);
        }
    }

    @Override
    public List<ProductRecommendBO> listProductBycategoryId(ProductRecommendParam productRecommendParam) {
        List<ProductDOView> productDOViewList = productDOMapperExtend.listProductBycategoryId(productRecommendParam);
        return ProductRecommendConverter.convertBO(productDOViewList);
    }

}
