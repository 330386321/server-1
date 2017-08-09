package com.lawu.eshop.merchant.api.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.AdPlatformService;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductImagePrefix;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
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

	@Autowired
	private MerchantApiConfig merchantApiConfig;

	@Autowired
	private AdPlatformService adPlatformService;

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
		queryData.setIsApp(query.getIsApp());
		queryData.setCategoryId(query.getCategoryId());
		queryData.setCurrentPage(query.getCurrentPage());
		queryData.setPageSize(query.getPageSize());
		Result<Page<ProductQueryDTO>> page = productService.selectProduct(queryData);
		return successCreated(page);
	}

	@Audit(date = "2017-04-21", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1002]。(杨清华)", httpMethod = "PUT")
	@Authorization
	@RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
	public Result updateProductStatus(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "商家ID(多个英文逗号分开)") String ids,
			ProductStatusEnum productStatus) {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());

		//下架、删除商品时需要判断该商品是否存在广告位
		if(!productStatus.getVal().equals(ProductStatusEnum.PRODUCT_STATUS_UP.getVal())){
			List<String> idsList = Arrays.asList(ids.split(","));
			for(String id : idsList){
				Result<Boolean> result = adPlatformService.selectByProductIdAndStatus(Long.parseLong(id));
				if(result.getModel()){
					return successCreated(ResultCode.GOODS_PRODUCT_EXIST_ADFLAT);
				}
			}
		}

		return productService.updateProductStatus(ids, productStatus,merchantId);
	}

	@Audit(date = "2017-04-21", reviewer = "孙林青")
	@ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[]，（杨清华）", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
	public Result<ProductEditInfoDTO> selectEditProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {

		return productService.selectEditProductById(productId);

	}

	@Deprecated
	@Audit(date = "2017-04-25", reviewer = "孙林青")
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@ApiOperation(value = "添加、编辑商品", notes = "添加、编辑商品接口，合并成一个接口，[]，（杨清华）", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "saveProduct", method = RequestMethod.POST)
	public Result saveProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam EditProductParam product) throws IOException, ServletException {

		HttpServletRequest request = getRequest();
		Long productId = product.getProductId();
		if ((productId == null || productId == 0L || productId < 0)
				&& (request.getParts() == null || request.getParts().isEmpty())) {
			return successCreated(ResultCode.IMAGE_IS_NULL);
		}

		String imageContents = product.getImageContents();
		imageContents = URLDecoder.decode (imageContents);

		StringBuilder featureImageStr = new StringBuilder();
		StringBuilder productImageStr = new StringBuilder();
		StringBuilder productDetailImageStr = new StringBuilder();
		Map<String, List<String>> detailImageMap = new HashMap<String, List<String>>();
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
			for (Part part : parts) {
				Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_PRODUCT, part, merchantApiConfig.getImageUploadUrl());
				String resultFlag = map.get("resultFlag");
				if (!"0".equals(resultFlag)) {
					return successCreated(resultFlag);
				}

				String imgUrl = map.get("imgUrl");
				if ("".equals(imgUrl)) {
					continue;
					// logger.error("上传商品图片失败，上传文件方法返回路径为空(productId={})",productId);
					// return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
				}

				String fileName = part.getName();
				if (fileName.contains(ProductImagePrefix.featureImage)) {
					featureImageStr.append(imgUrl).append(",");

				} else if (fileName.contains(ProductImagePrefix.productImage)) {
					productImageStr.append(imgUrl).append(",");

				} else if (fileName.contains(ProductImagePrefix.productDetailImage)) {
					productDetailImageStr.append(imgUrl).append(",");

				}
			}
		} catch (IOException e) {
			logger.error("上传商品图片异常，失败(productId={})", productId,e);
			return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
		} catch (ServletException ex) {
            logger.info("Servlet异常，没有提交图片文件",ex);
        }
		
		String featureImage = featureImageStr.toString();
		String productImage = productImageStr.toString();
		String productDetailImage = productDetailImageStr.toString();
		if (productId == 0L) {
			if ("".equals(productImage)) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
			}
			if ("".equals(productDetailImage)) {
				//return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL);
			}

			if ("".equals(featureImage)) {
				featureImage = productImage.split(",")[0];
			}
		} else {
			String backImageUrls = product.getBackProductImageUrls();
			backImageUrls = URLDecoder.decode(backImageUrls);
			if ((backImageUrls == null || "[]".equals(backImageUrls) || "".equals(backImageUrls))
					&& "".equals(productImage)) {
				return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
			}
			if (!"".equals(backImageUrls) && !"[]".equals(backImageUrls)) {
				String image = StringUtil.getJsonListToString(backImageUrls);
				productImage = image + "," + productImage;
			}
			if ("".equals(featureImage)) {
				featureImage = productImage.split(",")[0];
			}

			String backDetailImageUrls = product.getBackProductDetailImageUrls();
			backDetailImageUrls = URLDecoder.decode(backDetailImageUrls);
			if (!"".equals(backDetailImageUrls) && !"[]".equals(backDetailImageUrls)) {
				productDetailImage = getUpdateLaterImageDetailUrl(productDetailImage, backDetailImageUrls);
			}
		}
		
		productImage = productImage.substring(0, productImage.lastIndexOf(','));
		if(!"".equals(productDetailImage)){
			productDetailImage = productDetailImage.substring(0, productDetailImage.lastIndexOf(','));
		}	
		
		EditProductDataParam dataProduct = new EditProductDataParam();
		dataProduct.setProductId(productId);
		dataProduct.setMerchantId(UserUtil.getCurrentUserId(getRequest()));
		dataProduct.setMerchantNum(UserUtil.getCurrentUserNum(getRequest()));
		dataProduct.setName(product.getName());
		dataProduct.setCategoryId(product.getCategoryId());
		dataProduct.setContent(product.getContent());
		dataProduct.setSpec(URLDecoder.decode(product.getSpec()));
		dataProduct.setImageContents((imageContents == null || "".equals(imageContents)) ? "[]" : imageContents);
		dataProduct.setFeatureImage(featureImage);
		dataProduct.setProductImages(productImage);
		dataProduct.setDetailImages(productDetailImage);
		dataProduct.setIsAllowRefund(product.getIsAllowRefund());
		dataProduct.setDeleteSpecIds(product.getDeleteSpecIds());
		dataProduct.setProductStatus(product.getProductStatus());

		return productService.saveProduct(dataProduct);

	}

	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "更新商品关键词", notes = "更新商品关键词。[1002]（梅述全）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@Authorization
	@RequestMapping(value = "updateKeywordsById/{id}", method = RequestMethod.PUT)
	public Result updateKeywordsById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
									 @PathVariable @ApiParam(required = true, value = "商品ID") Long id,
									 @RequestParam @ApiParam(required = true, value = "关键词") String keywords) {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
		return productService.updateKeywordsById(id, merchantId, keywords);
	}

	/**
	 *
	 * @param token
	 * @param product
	 * @return
	 * @since V2.4.0
	 * @throws IOException
	 * @throws ServletException
	 */
	@Audit(date = "2017-04-25", reviewer = "孙林青")
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@ApiOperation(value = "添加编辑商品", notes = "添加编辑商品接口，[]，（杨清华）", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "saveProductInfo", method = RequestMethod.POST)
	public Result saveProductInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam EditProductParam product) {

		String imageContents = product.getImageContents();
		imageContents = URLDecoder.decode (imageContents);

		String productImages = product.getBackProductImageUrls();
		productImages = URLDecoder.decode(productImages);
		if (productImages == null || "[]".equals(productImages) || "".equals(productImages)) {
			return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
		}
		productImages = StringUtil.getJsonListToString(productImages);

		String productDetailImages = product.getBackProductDetailImageUrls();
		productDetailImages = URLDecoder.decode(productDetailImages);
		productDetailImages = StringUtil.getJsonListToString(productDetailImages);

		String featureImage = productImages.split(",")[0];

		EditProductDataParam dataProduct = new EditProductDataParam();
		dataProduct.setProductId(product.getProductId());
		dataProduct.setMerchantId(UserUtil.getCurrentUserId(getRequest()));
		dataProduct.setMerchantNum(UserUtil.getCurrentUserNum(getRequest()));
		dataProduct.setName(product.getName());
		dataProduct.setCategoryId(product.getCategoryId());
		dataProduct.setContent(product.getContent());
		dataProduct.setSpec(URLDecoder.decode(product.getSpec()));
		dataProduct.setImageContents((imageContents == null || "".equals(imageContents)) ? "[]" : imageContents);
		dataProduct.setFeatureImage(featureImage);
		dataProduct.setProductImages(productImages);
		dataProduct.setDetailImages(productDetailImages);
		dataProduct.setIsAllowRefund(product.getIsAllowRefund());
		dataProduct.setDeleteSpecIds(product.getDeleteSpecIds());
		dataProduct.setProductStatus(product.getProductStatus());
		dataProduct.setKeywords(product.getKeywords());

		return productService.saveProduct(dataProduct);

	}

	/**
	 * 存在增量和修改的
	 * 
	 * @param productDetailImage
	 *            新上传图片（存在回显修改的）
	 * @param backProductDetailImageUrls
	 *            修改回显的url
	 * @return
	 */
	private String getUpdateLaterImageDetailUrl(String productDetailImage, String backProductDetailImageUrls) {
		if ("".equals(backProductDetailImageUrls) || "[]".equals(backProductDetailImageUrls)) {// 回显的全部删除完
			return productDetailImage;
		}
		List<String> backDetailImagesList = StringUtil.getJsonListToStringList(backProductDetailImageUrls);
		if (!backDetailImagesList.contains("")) {
			if(!"".equals(productDetailImage)){
				String []productDetailImages = productDetailImage.split(",");
				for(int i = 0 ; i < productDetailImages.length ; i++){
					backDetailImagesList.add(productDetailImages[i]);
				}
			}
		} else {
			List<Integer> indexs = new ArrayList<Integer>();// 回显图片修改过的下标
			for (int i = 0; i < backDetailImagesList.size(); i++) {
				if ("".equals(backDetailImagesList.get(i))) {
					indexs.add(i);
				}
			}
			List<String> detailImagesList1 = Arrays.asList(productDetailImage.split(","));
			List<String> detailImagesList = new ArrayList<String>();
			detailImagesList.addAll(detailImagesList1);
			for (int i = 0; i < indexs.size(); i++) {
				backDetailImagesList.set(indexs.get(i), detailImagesList.get(0));
				detailImagesList.remove(0);
			}
			backDetailImagesList.addAll(detailImagesList);
		}
		
		StringBuilder nimages = new StringBuilder();
		for (String image : backDetailImagesList) {
			nimages.append(image).append(",");
		}
		String images = nimages.toString();
		return images;
	}

	
}
