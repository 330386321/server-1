package com.lawu.eshop.user.srv.controller;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.MemberProductStoreDTO;
import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.*;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.service.*;
import com.lawu.eshop.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家门店 Created by Administrator on 2017/3/24.
 */
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController extends BaseController {

	@Autowired
	private MerchantStoreInfoService merchantStoreInfoService;

	@Autowired
	private MerchantStoreService merchantStoreService;

	@Autowired
	private MerchantAuditService merchantAuditService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MerchantStoreImageService merchantStoreImageService;
	
	@Autowired
	private MerchantStoreProfileService merchantStoreProfileService;

	/**
	 * 门店信息查询
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "selectMerchantStoreByMId", method = RequestMethod.GET)
	public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long merchantId) {

		MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(merchantId);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		String path=merchantStoreImageService.selectLogoPath(merchantId);
		MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.convertStoreDTO(merchantStoreBO);
		merchantStoreDTO.setLogoUrl(path);
		return successGet(merchantStoreDTO);

	}

	/**
	 * 门店信息查询
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
	public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id) {

		MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStore(id);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.coverDTO(merchantStoreBO);

		return successGet(merchantStoreDTO);

	}

	/**
	 * 新增门店信息
	 *
	 * @param merchantId
	 *            商家id
	 * @param merchantStoreParam
	 *            门店信息（）
	 * @return
	 */
	@RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
	public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {

		// 判断门店是否存在
		MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
		if (merchantStoreInfoBO != null) {
			return successCreated(ResultCode.RECORD_EXIST);
		}
		// 判断该营业执照是否存在相同记录
		if (!StringUtils.isEmpty(merchantStoreParam.getRegNumber())) {
			MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(), 1);
			if (merchantStoreProfileBO != null) {
				return successCreated(ResultCode.REG_NUMBER_RECORD_EXIST);
			}
		}

		// 判断该身份证号是否存在相同记录
		if (!StringUtils.isEmpty(merchantStoreParam.getOperatorCardId())) {
			MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getOperatorCardId(), 2);
			if (merchantStoreProfileBO != null) {
				return successCreated(ResultCode.ID_CARD_RECORD_EXIST);
			}
		}

		merchantStoreInfoService.saveMerchantStoreInfo(merchantId, merchantStoreParam);

		return successCreated();
	}

	/**
	 * 修改门店信息
	 *
	 * @param merchantId
	 *            商家id
	 * @param merchantStoreParam
	 *            门店信息
	 * @return
	 */
	@RequestMapping(value = "updateMerchantStoreInfo/{merchantStoreId}", method = RequestMethod.PUT)
	public Result updateMerchantStoreInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
		MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStore(merchantStoreId);
		if (merchantStoreInfoBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		merchantStoreInfoService.updateMerchantStoreInfo(merchantId, merchantStoreParam, merchantStoreId);

		return successCreated();
	}

	/**
	 * 根据商家ID获取商家门店的名称
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "shoppingCart/{merchantId}", method = RequestMethod.GET)
	public Result<MerchantInfoForShoppingCartDTO> getMerchantInfoForShoppingCart(@PathVariable("merchantId") Long merchantId) {

		MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);

		if (merchantStoreInfoBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		MerchantInfoForShoppingCartDTO rtn = MerchantStoreConverter.convert(merchantStoreInfoBO);
		return successGet(rtn);
	}

	/**
	 * 增加门店审核信息记录
	 *
	 * @param merchantStoreId
	 * @param merchantId
	 * @param merchantStoreParam
	 * @return
	 */
	@RequestMapping(value = "saveMerchantStoreAuditInfo/{merchantStoreId}", method = RequestMethod.POST)
	public Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
		// 查询是否存在未审核记录
		MerchantStoreAuditBO auditBO = merchantAuditService.getMerchantAuditInfoByUncheck(merchantId, MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_UNCHECK.val);
		if (auditBO != null) {
			return successCreated(ResultCode.MERCHANT_STORE_AUDIT_EXIST);
		}
		merchantStoreInfoService.saveMerchantStoreAuditInfo(merchantId, merchantStoreParam, merchantStoreId);
		return successCreated();
	}

	/**
	 * 根据商家ID查询该商家是否支持七天无理由退货
	 *
	 * @param merchantId
	 * @return
	 * @author Yangqh
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "findIsNoReasonReturnById", method = RequestMethod.GET)
	public Result findIsNoReasonReturnById(@RequestParam Long merchantId) {

		if (merchantId == null || merchantId == 0L) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		MerchantStoreInfoBO storeBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
		if (storeBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successCreated(storeBO.getIsNoReasonReturn());
	}

	/**
	 * 商家是否支持七天退货、商家的用户编号、当前用户是否是商家的粉丝
	 *
	 * @param param
	 */
	@RequestMapping(value = "shoppingOrderFindUserInfo", method = RequestMethod.PUT)
	public Result<ShoppingOrderFindUserInfoDTO> shoppingOrderFindUserInfo(@RequestBody ShoppingOrderFindUserInfoParam param) {

		List<ShoppingOrderFindMerchantInfoBO> shoppingOrderFindUserInfoBOList = merchantStoreInfoService.shoppingOrderFindUserInfo(param);
		
		MemberBO memberBO = memberService.getMemberById(param.getMemberId());
		
		return successGet(MerchantStoreConverter.convert(shoppingOrderFindUserInfoBOList, memberBO));
	}

	/**
	 * 根据门店ID查询门店详细信息
	 *
	 * @param id
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "storeDetail/{id}", method = RequestMethod.GET)
	public Result<StoreDetailDTO> storeDetail(@PathVariable Long id, @RequestParam Long memberId) {
		StoreDetailBO storeDetailBO = merchantStoreInfoService.getStoreDetailById(id, memberId);
		if (storeDetailBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successGet(MerchantStoreConverter.convertDTO(storeDetailBO));
	}

	/**
	 * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Yangqh
	 */
	@RequestMapping(value = "findCashUserInfo/{id}", method = RequestMethod.GET)
	public CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id) throws Exception {
		CashUserInfoBO cashUserInfoBO = merchantStoreInfoService.findCashUserInfo(id);
		if (cashUserInfoBO == null) {
			return null;
		}
		CashUserInfoDTO dto = new CashUserInfoDTO();
		BeanUtil.copyProperties(cashUserInfoBO, dto);
		return dto;
	}

	/**
	 * 买单查询商家名称和门店图片
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "findStoreNameAndImgByMerchantId/{merchantId}")
	public MerchantStoreDTO findStoreNameAndImgByMerchantId(@PathVariable("merchantId") Long merchantId) {
		if (merchantId == null) {
			return null;
		}
		MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.findStoreNameAndImgByMerchantId(merchantId);
		if (merchantStoreInfoBO == null) {
			return null;
		}
		MerchantStoreDTO merchantStoreDTO = new MerchantStoreDTO();
		merchantStoreDTO.setName(merchantStoreInfoBO.getName());
		merchantStoreDTO.setStoreUrl(merchantStoreInfoBO.getStoreUrl());
		return merchantStoreDTO;
	}

	/**
	 * 申请实体店铺
	 *
	 * @return
	 */
	@RequestMapping(value = "applyPhysicalStore/{merchantId}", method = RequestMethod.PUT)
	public Result applyPhysicalStore(@PathVariable(value = "merchantId") Long merchantId, @RequestBody ApplyStoreParam param) {
		if (merchantId == null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
		if (merchantStoreBO == null) {
			return successCreated(ResultCode.NOT_FOUND_DATA);
		}
		param.setName(merchantStoreBO.getName());
		param.setPrincipalName(merchantStoreBO.getPrincipalName());
		param.setPrincipalMobile(merchantStoreBO.getPrincipalMobile());
		param.setIndustryPath(merchantStoreBO.getIndustryPath());
		param.setIndustryName(merchantStoreBO.getIndustryName());
		param.setAddress(merchantStoreBO.getAddress());
		param.setManageType(MerchantStoreTypeEnum.ENTITY_MERCHANT);
		param.setIntro(merchantStoreBO.getIntro());
		param.setRegionPath(merchantStoreBO.getRegionPath());
		param.setRegionName(merchantStoreBO.getRegionName());
		// 添加审核记录
		Integer row = merchantStoreInfoService.applyPhysicalStore(merchantId, merchantStoreBO.getMerchantStoreId(), param);
		if (row < 0) {
			return successCreated(ResultCode.MERCHANT_STORE_AUDIT_EXIST);
		}
		return successCreated(ResultCode.SUCCESS);
	}

	/**
	 * 加入7天退货保障
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "updateNoReasonReturn/{merchantId}")
	public Result updateNoReasonReturn(@PathVariable("merchantId") Long merchantId) {
		MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(merchantId);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.MERCHANT_STORE_NO_EXIST);
		}
		merchantStoreService.updateNoReasonReturn(merchantStoreBO.getId());
		return successCreated();
	}

	/**
	 * 要购物门店详情
	 *
	 * @param id
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "shoppingStoreDetail/{id}", method = RequestMethod.GET)
	public Result<ShoppingStoreDetailDTO> shoppingStoreDetail(@PathVariable Long id, @RequestParam Long memberId) {
		ShoppingStoreDetailBO shoppingStoreDetailBO = merchantStoreInfoService.getShoppingStoreDetailById(id, memberId);
		if (shoppingStoreDetailBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successGet(MerchantStoreConverter.convertDTO(shoppingStoreDetailBO));
	}

	/**
	 * 根据门店ID查询门店信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getMerchantStore/{id}", method = RequestMethod.GET)
	public Result<MerchantStoreDTO> getMerchantStore(@PathVariable Long id) {
		MerchantStoreBO merchantStoreBO = merchantStoreService.getMerchantStoreById(id);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successGet(MerchantStoreConverter.convertStoreDTO(merchantStoreBO));
	}

	@RequestMapping(value = "selectAllMerchantStore", method = RequestMethod.POST)
	public Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(@RequestBody MerchantStoreParam param) {
		List<MerchantStoreBO> boList = merchantStoreService.selectAllMerchantStore(param);
		List<MerchantStorePlatDTO> list = new ArrayList<>();
		if (!boList.isEmpty()) {
			for (MerchantStoreBO merchantStoreBO : boList) {
				MerchantStorePlatDTO dto = new MerchantStorePlatDTO();
				dto.setMerchantStoreId(merchantStoreBO.getId());
				dto.setName(merchantStoreBO.getName());
				list.add(dto);
			}
		}
		return successGet(list);
	}

	/**
	 * 用户端商品详情页面查询，店铺信息
	 * 
	 * @param merchantId
	 * @return
	 * @author yangqh
	 */
	@RequestMapping(value = "getMemberProductDetailStore", method = RequestMethod.GET)
	public Result<MemberProductStoreDTO> getMemberProductDetailStore(@RequestParam Long merchantId) {
		if (merchantId == null) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		MerchantStoreInfoBO storeBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
		if (storeBO == null) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		List<MerchantStoreImageBO> logo = merchantStoreImageService.listMerchantStoreImageByType(merchantId, MerchantStoreImageEnum.STORE_IMAGE_LOGO);
		MemberProductStoreDTO dto = new MemberProductStoreDTO();
		dto.setStoreId(storeBO.getMerchantStoreId());
		dto.setStoreName(storeBO.getName());
		dto.setSupportEleven(storeBO.getIsNoReasonReturn());
		if(logo == null || logo.isEmpty()){
			dto.setLogo("");
		}else{
			dto.setLogo(logo.get(0).getPath());
		}
		
		return successCreated(dto);
	}
	
	/**
	 * 查询店铺类型
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getManageType", method = RequestMethod.GET)
	public Result<ManageTypeEnum> getManageType(@RequestParam Long merchantId) {
		if (merchantId == null) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		ManageTypeEnum type=merchantStoreProfileService.getManageType(merchantId);
		return successCreated(type);
	}

	/**
	 * 查询所有审核通过的实体店铺
	 *
	 * @return
	 */
	@RequestMapping(value = "listMerchantStore", method = RequestMethod.POST)
	public Result<List<MerchantStoreDTO>> listMerchantStore(@RequestBody ListMerchantStoreParam listMerchantStoreParam) {
		List<MerchantStoreBO> merchantStoreBOS = merchantStoreService.listMerchantStore(listMerchantStoreParam);
		if (merchantStoreBOS == null || merchantStoreBOS.isEmpty()) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		return successGet(MerchantStoreConverter.convertStoreDTO(merchantStoreBOS));
	}

	/**
	 * 更新门店统计数据，同时更新solr
	 *
	 * @param id
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "updateStoreStatistics/{id}", method = RequestMethod.PUT)
	public Result updateStoreStatistics(@PathVariable Long id, @RequestBody StoreStatisticsParam param) {
		MerchantStoreBO merchantStoreBO = merchantStoreService.getMerchantStoreById(id);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		merchantStoreService.updateStoreStatisticsById(id, param);
		return successCreated();
	}

	/**
	 * 更新门店索引
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStoreIndex/{id}", method = RequestMethod.PUT)
	public Result updateStoreIndex(@PathVariable Long id) {
		merchantStoreService.updateStoreIndex(id);
		return successCreated();
	}

	/**
	 * 重建门店索引
	 * @return
	 */
	@RequestMapping(value = "rebuildStoreIndex", method = RequestMethod.GET)
	public Result rebuildStoreIndex() {
		merchantStoreService.rebuildStoreIndex();
		return successCreated();
	}

	/**
	 * 删除无效的门店索引
	 * @return
	 */
	@RequestMapping(value = "delInvalidStoreIndex", method = RequestMethod.GET)
	public Result delInvalidStoreIndex() {
		merchantStoreService.delInvalidStoreIndex();
		return successCreated();
	}

	/**
	 * 查询门店name，门店照
	 * @param merchantIds
	 * @return
	 * @author zhangyong
	 */
	@RequestMapping(value = "getPayOrderStoreInfo", method = RequestMethod.GET)
	public Result<List<PayOrderStoreInfoDTO>> getPayOrderStoreInfo(@RequestParam("merchantIds") List<Long> merchantIds) {
		List<PayOrderStoreInfoBO> storeInfoBOS = merchantStoreInfoService.getPayOrderStoreInfo(merchantIds);
		if(storeInfoBOS == null){
			return successGet(new ArrayList<PayOrderStoreInfoDTO>());
		}
		List<PayOrderStoreInfoDTO> list = new ArrayList<PayOrderStoreInfoDTO>();
		for(PayOrderStoreInfoBO storeInfoBO :storeInfoBOS){
			PayOrderStoreInfoDTO storeInfoDTO = new PayOrderStoreInfoDTO();
			storeInfoDTO.setName(storeInfoBO.getName());
			storeInfoDTO.setStoreUrl(storeInfoBO.getStoreUrl());
			storeInfoDTO.setMerchantId(storeInfoBO.getMerchantId());
			list.add(storeInfoDTO);
		}
		return successGet(list);
	}

	/**
	 * 要买单人气推荐门店信息
	 * @param merchantStoreIds
	 * @return
	 * @author zhangyong
	 */
	@RequestMapping(method = RequestMethod.GET, value = "getMerchantStoreByIds")
	public Result<List<StoreSolrInfoDTO>> getMerchantStoreByIds(@RequestParam("merchantStoreIds") List<Long> merchantStoreIds) {

		List<StoreSolrInfoBO> bos = merchantStoreInfoService.getMerchantStoreByIds(merchantStoreIds);
		if (bos == null) {
			return successGet(new ArrayList<StoreSolrInfoDTO>());
		}
		List<StoreSolrInfoDTO> storeSolrInfoDTOS = MerchantStoreConverter.coverStoreSolrDTOS(bos);

		return successGet(storeSolrInfoDTOS);
	}
	
	@RequestMapping(value = "getAdMerchantStoreByIds", method = RequestMethod.GET)
	public Result<List<MerchantAdInfoDTO>> getAdMerchantStoreByIds(@RequestParam("merchantIds") List<Long> merchantIds) {
    	 List<MerchantAdInfoBO>  list=merchantStoreService.getAdMerchantStoreByIds(merchantIds);
    	 List<MerchantAdInfoDTO> dtoList=new ArrayList<>();
    	 for (MerchantAdInfoBO merchantAdInfoBO : list) {
    		 MerchantAdInfoDTO dto=new MerchantAdInfoDTO();
    		 dto.setManageTypeEnum(ManageTypeEnum.getEnum(merchantAdInfoBO.getManageType()));
    		 dto.setMerchantId(merchantAdInfoBO.getMerchantId());
    		 dto.setMerchantStoreId(merchantAdInfoBO.getMerchantStoreId());
    		 dto.setName(merchantAdInfoBO.getName());
    		 dto.setPath(merchantAdInfoBO.getPath());
    		 dtoList.add(dto);
		}
		return successGet(dtoList);
	}

	/**
	 * 根据商家id查询商家门店id
	 *
	 * @param merchantId
	 * @return
	 * @author Sunny
	 * @date 2017年6月27日
	 */
	@RequestMapping(value = "merchantStoreId/{merchantId}", method = RequestMethod.GET)
	public Result<Long> getMerchantStoreById(@PathVariable("merchantId") Long merchantId) {
		MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(merchantId);
		if (merchantStoreBO == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		return successGet(merchantStoreBO.getId());
	}

	/**
	 * 判断门店是否存在
	 * @param id
	 * @return
	 * @author zhangy
	 */
	@RequestMapping(value = "merchantStoreIsExist/{id}", method = RequestMethod.GET)
	public MerchantStoreStatusDTO merchantStoreIsExist(@PathVariable("id") Long id) {
		MerchantStoreStatusBO merchantStoreStatusBO = merchantStoreService.merchantStoreIsExist(id);
		MerchantStoreStatusDTO merchantStoreStatusDTO = new MerchantStoreStatusDTO();
		if (merchantStoreStatusBO.isExist()) {
			merchantStoreStatusDTO.setExist(merchantStoreStatusBO.isExist());
			merchantStoreStatusDTO.setStatus(merchantStoreStatusBO.getStatus());
		} else {
			merchantStoreStatusDTO.setExist(merchantStoreStatusBO.isExist());
		}
		return merchantStoreStatusDTO;
	}
	/**
	 * 查询logo图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getLogoUrlByStoreId/{id}", method = RequestMethod.GET)
	public String getLogoUrlByStoreId(@PathVariable("id") Long id){

		return merchantStoreImageService.selectLogoUrlByStoreId(id);
	}

	/**
	 * 查询store图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getStoreUrlByStoreId/{id}", method = RequestMethod.GET)
	public String getStoreUrlByStoreId(@PathVariable("id") Long id){

		return merchantStoreImageService.getStoreUrlByStoreId(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "getPayOrderDetailStoreInfo")
	public PayOrderMerchantStoreInfoDTO getPayOrderDetailStoreInfo(@RequestParam("merchantId") Long merchantId){

		PayOrderStoreInfoBO payOrderStoreInfoBO = merchantStoreInfoService.getPayOrderDetailStoreInfo(merchantId);
		if(payOrderStoreInfoBO == null){
			return null;
		}
		PayOrderMerchantStoreInfoDTO payOrderMerchantStoreInfoDTO = new PayOrderMerchantStoreInfoDTO();
		payOrderMerchantStoreInfoDTO.setStoreUrl(payOrderStoreInfoBO.getStoreUrl());
		payOrderMerchantStoreInfoDTO.setMerchantStoreId(payOrderStoreInfoBO.getMerchantStoreId());
		payOrderMerchantStoreInfoDTO.setPrincipalMobile(payOrderStoreInfoBO.getPrincipalMobile());
		payOrderMerchantStoreInfoDTO.setAddress(payOrderStoreInfoBO.getAddress());
		payOrderMerchantStoreInfoDTO.setName(payOrderStoreInfoBO.getName());
		payOrderMerchantStoreInfoDTO.setRegionName(payOrderStoreInfoBO.getRegionName());
		payOrderMerchantStoreInfoDTO.setUserNum(payOrderStoreInfoBO.getUserNum());
		return payOrderMerchantStoreInfoDTO;
	}


}

