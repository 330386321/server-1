package com.lawu.eshop.product.srv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductDataParam_bak;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.service.ProductService;
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
	 * @param ids
	 *            商品ID字符串
	 * @param productStatus
	 *            目标修改的状态
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
	public Result updateProductStatus(@RequestParam String ids, @RequestParam ProductStatusEnum productStatus) {
		int counts = productService.updateProductStatus(ids, productStatus);
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
	 */
	@RequestMapping(value = "selectProductById", method = RequestMethod.GET)
	public Result<ProductInfoDTO> selectProductById(@RequestParam Long productId) {
		if (productId == null) {
			return successCreated(ResultCode.ID_EMPTY);
		}

		// 商品基本信息
		ProductInfoBO productBO = productService.selectProductById(productId);
		if (productBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		ProductInfoDTO productDTO = ProductConverter.convertInfoDTO(productBO);

		return successCreated(productDTO);
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
		return successCreated(productDTO);
	}

	/**
	 * 添加、编辑商品
	 *
	 * @param productId
	 * @param product
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveProduct_bak", method = RequestMethod.POST)
	public Result saveProduct_bak(@RequestBody @Valid EditProductDataParam_bak product,
			BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		productService.eidtProduct_bak(product);
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 添加、编辑商品
	 *
	 * @param productId
	 * @param product
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveProduct", method = RequestMethod.POST)
	public Result saveProduct(@RequestBody @Valid EditProductDataParam product,
			BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		productService.eidtProduct(product);
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
     * 操作库存
     * @param productId
     * @param num	加减数量数量
     * @param flag	M-减、A-加
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "editTotalInventory", method = RequestMethod.POST)
	public Result editTotalInventory(@RequestParam Long productId,@RequestParam int num,@RequestParam String flag) {
		productService.editTotalInventory(productId,num,flag);
		return successCreated(ResultCode.SUCCESS);
	}

	/**
     * 操作销量
     * @param productId
     * @param num	加减数量数量
     * @param flag	M-减、A-加
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "editTotalSaleVolume", method = RequestMethod.POST)
	public Result editTotalSaleVolume(@RequestParam Long productId,@RequestParam int num,@RequestParam String flag) {
		productService.editTotalSaleVolume(productId,num,flag);
		return successCreated(ResultCode.SUCCESS);
	}
}
