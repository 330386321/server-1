package com.lawu.eshop.operator.api.controller;

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
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.operator.api.service.TokenService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.AccountDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantStoreProfileDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.AccountFreezeParam;
import com.lawu.eshop.user.param.AccountParam;

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
public class MemberController extends BaseController{

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

    @ApiOperation(value = "根据会员账号查询会员信息", notes = "根据会员账号查询会员信息。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("index:store")
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result<MemberDTO> getMember(@PathVariable @ApiParam(value = "会员账号") String account) {
        return memberService.getMemberByAccount(account);
    }

    @ApiOperation(value = "根据会员账号查询会员列表（商家，会员）", notes = "根据会员账号查询会员列表（商家，会员）（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getAccountList", method = RequestMethod.GET)
    @PageBody
    public Result<Page<AccountDTO>> getAccountList(@ModelAttribute AccountParam param){
        if(UserType.MEMBER.equals(param.getUserType())){
            //用户
            return  memberService.getAccountList(param);
        }else{
            return  merchantService.getAccountList(param);
        }
    }

    @ApiOperation(value = "冻结账户", notes = "冻结账户（商家，会员）（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "freezeAccount", method = RequestMethod.PUT)
    public Result freezeAccount(@ModelAttribute AccountFreezeParam param){
        if (param.getNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
            //修改用户冻结状态
            memberService.freezeAccount(param.getNum(),param.getFreeze());
            if(param.getFreeze()){//冻结
                tokenService.delMemberRelationshipByAccount(param.getAccount());//删除token
            }
            return successCreated();
        }
        //冻结商家
        merchantService.freezeAccount(param.getNum(),param.getFreeze());
        Result<MerchantStoreProfileDTO> result = merchantService.getMerchantStoreProfileInfo(param.getId());
        if (ResultCode.MERCHANT_STORE_NO_EXIST == result.getRet()) {
            //未创建门店
            if (param.getFreeze()) {
                tokenService.delMerchantRelationshipByAccount(param.getAccount());//删除token
            }
            //下架并删除solr广告
            adService.soldOutAdByMerchantId(param.getId());

            return successCreated();
        }
        if (param.getFreeze()) {
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

            tokenService.delMerchantRelationshipByAccount(param.getAccount());//删除token
        }else{
            //解冻
            //TODO 添加solr门店信息
        }
        return successGet();

    }

}
