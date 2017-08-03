package com.lawu.eshop.merchant.api.mock.service;

<<<<<<< Upstream, based on branch 'develop' of git@192.168.1.24:eshop/server.git
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackagePurchaseNotesDTO;
import com.lawu.eshop.merchant.api.service.DiscountPackagePurchaseNotesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/8/3.
 */
@Service
public class MockDiscountPackagePurchaseNotesService extends BaseController implements DiscountPackagePurchaseNotesService {
    @Override
    public Result<List<DiscountPackagePurchaseNotesDTO>> list() {
        return successGet();
    }
=======
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackagePurchaseNotesDTO;
import com.lawu.eshop.merchant.api.service.DiscountPackagePurchaseNotesService;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockDiscountPackagePurchaseNotesService extends BaseController implements DiscountPackagePurchaseNotesService {

	@Override
	public Result<List<DiscountPackagePurchaseNotesDTO>> list() {
		return successGet();
	}
>>>>>>> db30afd feat(merchant-api):修改更新和保存优惠套餐接口的上传方式
}
