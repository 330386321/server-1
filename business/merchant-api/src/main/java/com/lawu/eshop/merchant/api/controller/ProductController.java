package com.lawu.eshop.merchant.api.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductImagePrefix;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import util.UploadFileUtil;

/**
 * @author Yangqh
 * @date 2017/3/11
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Audit(date = "2017-04-01", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "分页查询商品", notes = "分页查询商品，[]。(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "selectProduct", method = RequestMethod.POST)
	public Result<Page<ProductQueryDTO>> selectProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ProductQuery query) {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
		ProductDataQuery queryData = new ProductDataQuery();
		queryData.setProductStatus(query.getProductStatus());
		queryData.setMerchantId(merchantId);
		queryData.setName(query.getName());
		queryData.setProductSortFieldEnum(query.getProductSortFieldEnum());
		queryData.setOrderType(query.getOrderType());
		queryData.setApp(query.isApp());
		Result<Page<ProductQueryDTO>> page = productService.selectProduct(queryData);
		return successCreated(page);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1002]。(杨清华)", httpMethod = "PUT")
	@Authorization
	@RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
	public Result updateProductStatus(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "商家ID(多个英文逗号分开)") String ids,
			ProductStatusEnum productStatus) {

		return productService.updateProductStatus(ids, productStatus);
	}

	@ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[]，（杨清华）", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
	public Result<ProductEditInfoDTO> selectEditProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {

		return productService.selectEditProductById(productId);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "添加、编辑商品", notes = "添加、编辑商品接口，合并成一个接口，新增时productId传0，[]，（杨清华）", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "saveProduct", method = RequestMethod.POST)
	public Result saveProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam EditProductParam product) throws IOException, ServletException {

		HttpServletRequest request = getRequest();
		Long productId = product.getProductId();
		if ((productId == null || productId == 0L || productId < 0)
				&& (request.getParts() == null || request.getParts().isEmpty() || request.getParts().size() < 1)) {
			return successCreated(ResultCode.IMAGE_IS_NULL);
		}

		String imageContents = product.getImageContents();
		imageContents = URLDecoder.decode(imageContents);
		List<Object> imageContentsList = JSONArray.parseArray(imageContents, Object.class);
		if (imageContentsList == null || imageContentsList.isEmpty() || imageContentsList.size() < 1) {
			return successCreated(ResultCode.FAIL, "商品详情图片描述不能为空");
		}
		int imageContentsSize = imageContentsList.size();

		StringBuffer featureImageStr = new StringBuffer();
		StringBuffer productImageStr = new StringBuffer();
		Map<String, List<String>> detailImageMap = new HashMap<String, List<String>>();
		Collection<Part> parts;
		try {
			parts = request.getParts();
			for (Part part : parts) {
				Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_PRODUCT, part);
				String resultFlag = map.get("resultFlag");
				if (!"0".equals(resultFlag)) {
					return successCreated(resultFlag);
				}

				String imgUrl = map.get("imgUrl");
				if ("".equals(imgUrl)) {
					logger.error("上传商品图片失败，上传文件方法返回路径为空(productId={})", productId);
					return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
				}

				String fileName = part.getName();
				if (fileName.contains(ProductImagePrefix.featureImage)) {
					featureImageStr.append(imgUrl).append(",");

				} else if (fileName.contains(ProductImagePrefix.productIamge)) {
					productImageStr.append(imgUrl).append(",");

				} else if (fileName.contains(ProductImagePrefix.productDetailImage)) {
					for (int i = 1; i <= imageContentsSize; i++) {
						if (fileName.contains(ProductImagePrefix.productDetailImage+"-" + i)) {
							List<String> images = detailImageMap.get(ProductImagePrefix.productDetailImage+"-" + i);
							if (images == null || images.isEmpty()) {
								images = new ArrayList<String>();
							}
							images.add(imgUrl);
							detailImageMap.put(ProductImagePrefix.productDetailImage+"-" + i, images);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("上传商品图片异常，失败(productId={})", productId);
			return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
		}

		String featureImage = "";
		String productImage = "";
		if (productId == null || productId == 0L || productId < 0) {
			featureImage = featureImageStr.toString();
			productImage = productImageStr.toString();
			if ("".equals(productImage)) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
			}
			if (detailImageMap == null || detailImageMap.isEmpty()) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL);
			}

			if ("".equals(featureImage)) {
				featureImage = productImage.split(",")[0];
			}
		} else {
			if ("".equals(product.getBackProductImageUrls()) && "".equals(productImage)) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
			}
			if ("".equals(product.getBackProductDetailImageUrls())
					&& (detailImageMap == null || detailImageMap.isEmpty())) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL);
			}

			if (!"".equals(product.getBackProductImageUrls())) {
				productImage = product.getBackProductImageUrls() + "," + productImage;
			}

			if (!"".equals(product.getBackProductDetailImageUrls())) {
				// 将回显图片url直接存入增量图片前面
				Map<String, Object> retMap = JSON.parseObject(product.getBackProductDetailImageUrls(),
						new TypeReference<Map<String, Object>>() {
						});
				Iterator itr = retMap.keySet().iterator();
				while (itr.hasNext()) {
					String key = itr.next().toString();
					Object obj = retMap.get(key);
					List<String> backList = (List<String>) obj;
					if (backList != null && !backList.isEmpty() && backList.size() > 0) {
						List<String> eList = detailImageMap.get(key);
						for (int i = 0; i < backList.size(); i++) {
							eList.add(i, backList.get(i));
						}
					}
				}
			}
		}
		productImage = productImage.substring(0, productImage.lastIndexOf(","));

		EditProductDataParam dataProduct = new EditProductDataParam();
		dataProduct.setProductId(productId);
		dataProduct.setMerchantId(UserUtil.getCurrentUserId(getRequest()));
		dataProduct.setName(product.getName());
		dataProduct.setCategoryId(product.getCategoryId());
		dataProduct.setContent(product.getContent());
		dataProduct.setSpec(URLDecoder.decode(product.getSpec()));
		dataProduct.setImageContents(imageContents);
		dataProduct.setFeatureImage(featureImage);
		dataProduct.setProductImages(productImage);
		dataProduct.setDetailImageMap(detailImageMap);
		dataProduct.setIsAllowRefund(product.getIsAllowRefund());

		return productService.saveProduct(dataProduct);

	}

}
