package com.lawu.eshop.operator.api.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.DiscountPackageService;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.operator.api.service.MerchantFavoredService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.operator.api.service.TokenService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.AccountDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.MerchantStoreProfileDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.AccountFreezeParam;
import com.lawu.eshop.user.param.AccountParam;
import com.lawu.eshop.user.param.StoreIndexParam;
import com.lawu.eshop.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/5/16.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AdService adService;

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @Autowired
    private DiscountPackageService discountPackageService;

    @ApiOperation(value = "根据会员账号查询会员信息", notes = "根据会员账号查询会员信息。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("index:store")
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result<MemberDTO> getMember(@PathVariable @ApiParam(value = "会员账号") String account) {
        return memberService.getMemberByAccount(account);
    }

    @ApiOperation(value = "根据会员ID查询会员信息", notes = "根据会员ID查询会员信息。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("account:detail")
    @RequestMapping(value = "getMemberById/{id}", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberById(@PathVariable @ApiParam(value = "会员账号") Long id) {
        return memberService.findMember(id);
    }

    @ApiOperation(value = "根据会员账号查询会员列表（商家，会员）", notes = "根据会员账号查询会员列表（商家，会员）（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getAccountList", method = RequestMethod.GET)
    @PageBody
    public Result<Page<AccountDTO>> getAccountList(@ModelAttribute AccountParam param) {
        if (UserType.MEMBER.equals(param.getUserType())) {
            //用户
            return memberService.getAccountList(param);
        } else {
            return merchantService.getAccountList(param);
        }
    }

    @ApiOperation(value = "冻结账户", notes = "冻结账户（商家，会员）（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "freezeAccount", method = RequestMethod.POST)
    public Result freezeAccount(@ModelAttribute AccountFreezeParam param) {

        if (param.getNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
            //查询用户冻结状态
            Result<UserDTO> user = memberService.getMemberByNum(param.getNum());
            if (user.getModel() != null && user.getModel().getIsFreeze().equals(param.getNum())) {
                return successCreated(ResultCode.FAIL);
            }
            //修改用户冻结状态
            memberService.freezeAccount(param.getNum(), param.getIsFreeze(), StringUtils.isEmpty(param.getFreezeReason()) ? "解冻" : param.getFreezeReason());
            if (param.getIsFreeze()) {//冻结
                memberService.delUserGtPush(param.getId());
                tokenService.delMemberRelationshipByAccount(param.getAccount());//删除token
            }
            return successCreated();
        }
        //查询商家冻结状态
        Result<MerchantDTO> merchant = merchantService.getMerchantByNum(param.getNum());
        if (merchant.getModel() != null && merchant.getModel().getIsFreeze().equals(param.getIsFreeze())) {
            return successCreated(ResultCode.FAIL);
        }
        //冻结商家
        merchantService.freezeAccount(param.getNum(), param.getIsFreeze(), StringUtils.isEmpty(param.getFreezeReason()) ? "解冻" : param.getFreezeReason());
        Result<MerchantStoreProfileDTO> result = merchantService.getMerchantStoreProfileInfo(param.getId());
        if (ResultCode.MERCHANT_STORE_NO_EXIST == result.getRet()) {
            //未创建门店
            if (param.getIsFreeze()) {
                merchantService.delMerchantGtPush(param.getId());
                tokenService.delMerchantRelationshipByAccount(param.getAccount());//删除token
            }
            //下架并删除solr广告
            adService.soldOutAdByMerchantId(param.getId());

            return successCreated();
        }
        if (param.getIsFreeze()) {
            //冻结
            if (MerchantStoreTypeEnum.ENTITY_MERCHANT.equals(result.getModel().getTypeEnum())) {
                //实体店铺
                //删除solr信息
                merchantStoreService.delSolrDocsById(result.getModel().getMerchantStoreId());
            } else {
                //普通店铺
                //下架所有商品,删除solr商品
                productService.soldOutProductByMerchantId(param.getId());

            }
            // 下架，删除solr广告
            adService.soldOutAdByMerchantId(param.getId());

            merchantService.delMerchantGtPush(param.getId());
            tokenService.delMerchantRelationshipByAccount(param.getAccount());//删除token
        } else {
            //解冻
            // 添加solr门店信息-实体店铺
            if (MerchantStoreTypeEnum.ENTITY_MERCHANT.equals(result.getModel().getTypeEnum())) {
                Result<MerchantFavoredDTO> favoredDTOResult;
                Result<Page<DiscountPackageQueryDTO>> discountResult;
                List<StoreIndexParam> indexParamList = new ArrayList<>();
                StoreIndexParam storeIndexParam = new StoreIndexParam();
                favoredDTOResult = merchantFavoredService.findFavoredByMerchantId(param.getId());
                String favoredInfo = "";
                String favoreEndTime = "";
                double discountOrdinal = 1000;
                if (isSuccess(favoredDTOResult)) {
                    if (MerchantFavoredTypeEnum.TYPE_FULL.equals(favoredDTOResult.getModel().getTypeEnum())) {
                        favoredInfo = "买单每满" + favoredDTOResult.getModel().getReachAmount().intValue() + "减" + favoredDTOResult.getModel().getFavoredAmount().intValue() + "元";
                        discountOrdinal = (favoredDTOResult.getModel().getReachAmount().subtract(favoredDTOResult.getModel().getFavoredAmount())).divide(favoredDTOResult.getModel().getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        discountOrdinal = discountOrdinal * 1000 + 2;
                    } else if (MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.equals(favoredDTOResult.getModel().getTypeEnum())) {
                        favoredInfo = "买单满" + favoredDTOResult.getModel().getReachAmount().intValue() + "减" + favoredDTOResult.getModel().getFavoredAmount().intValue() + "元";
                        discountOrdinal = (favoredDTOResult.getModel().getReachAmount().subtract(favoredDTOResult.getModel().getFavoredAmount())).divide(favoredDTOResult.getModel().getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        discountOrdinal = discountOrdinal * 1000 + 3;
                    } else if (MerchantFavoredTypeEnum.TYPE_DISCOUNT.equals(favoredDTOResult.getModel().getTypeEnum())) {
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        favoredInfo = "买单" + numberFormat.format(favoredDTOResult.getModel().getDiscountRate()) + "折";
                        discountOrdinal = favoredDTOResult.getModel().getDiscountRate().divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        discountOrdinal = discountOrdinal * 1000 + 1;
                    }
                    favoreEndTime = DateUtil.getDateFormat(favoredDTOResult.getModel().getEntireEndTime());
                }

                //查询商家优惠套餐
                discountResult = discountPackageService.listForMember(param.getId());
                String discountPackage = "";
                if (isSuccess(discountResult) && !discountResult.getModel().getRecords().isEmpty()) {
                    discountPackage = discountResult.getModel().getRecords().get(0).getName();
                }

                storeIndexParam.setMerchantStoreId(result.getModel().getMerchantStoreId());
                storeIndexParam.setFavoreInfo(favoredInfo);
                storeIndexParam.setDiscountPackage(discountPackage);
                storeIndexParam.setDiscountOrdinal(discountOrdinal);
                storeIndexParam.setFavoreEndTime(favoreEndTime);
                indexParamList.add(storeIndexParam);

                merchantStoreService.rebuildStoreIndex(indexParamList);
            }
        }
        return successCreated();

    }

}
