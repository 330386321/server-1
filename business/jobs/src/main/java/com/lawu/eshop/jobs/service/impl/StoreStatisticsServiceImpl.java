package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.CommentMerchantService;
import com.lawu.eshop.jobs.service.MerchantStoreService;
import com.lawu.eshop.jobs.service.StoreStatisticsService;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
@Service
public class StoreStatisticsServiceImpl implements StoreStatisticsService {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private CommentMerchantService commentMerchantService;

    @Override
    public void executeStoreStatistics() {
        ListMerchantStoreParam listMerchantStoreParam = new ListMerchantStoreParam();
        listMerchantStoreParam.setStatus(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val);
        listMerchantStoreParam.setManageType(ManageTypeEnum.ENTITY.val);
        listMerchantStoreParam.setPageSize(1000);
        int currentPage = 0;

        Result<List<MerchantStoreDTO>> result;
        while (true){
            currentPage ++;
            listMerchantStoreParam.setCurrentPage(currentPage);
            result = merchantStoreService.listMerchantStore(listMerchantStoreParam);
            if (result == null || result.getRet() != ResultCode.SUCCESS) {
                return;
            }

            for (MerchantStoreDTO merchantStoreDTO : result.getModel()) {
                Result<CommentGradeDTO> commentGradeDTOResult = commentMerchantService.getCommentAvgGrade(merchantStoreDTO.getMerchantId());
                if (commentGradeDTOResult.getRet() == ResultCode.SUCCESS) {
                    StoreStatisticsParam param = new StoreStatisticsParam();
                    param.setAverageConsumeAmount(BigDecimal.valueOf(commentGradeDTOResult.getModel().getAverageConsumeAmount()));
                    param.setAverageScore(BigDecimal.valueOf(commentGradeDTOResult.getModel().getAvgGrade()));
                    param.setFeedbackRate(BigDecimal.valueOf(commentGradeDTOResult.getModel().getGoodGrad()));
                    merchantStoreService.updateStoreStatisticsById(merchantStoreDTO.getMerchantStoreId(), param);
                }
            }
        }
    }
}
