package com.lawu.eshop.statistics.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.statistics.service.CommentMerchantService;
import com.lawu.eshop.statistics.service.MerchantStoreService;
import com.lawu.eshop.statistics.service.StoreStatisticsService;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
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
        Result<List<MerchantStoreDTO>> result = merchantStoreService.listMerchantStore();
        if (result.getRet() != ResultCode.SUCCESS) {
            return;
        }

        for (MerchantStoreDTO merchantStoreDTO : result.getModel()) {
            Result<CommentGradeDTO> commentGradeDTOResult = commentMerchantService.getCommentAvgGrade(merchantStoreDTO.getMerchantId());
            if (commentGradeDTOResult.getRet() == ResultCode.SUCCESS) {
                StoreStatisticsParam param = new StoreStatisticsParam();
                param.setAverageConsumeAmount(new BigDecimal(commentGradeDTOResult.getModel().getAverageConsumeAmount()));
                param.setAverageScore(new BigDecimal(commentGradeDTOResult.getModel().getAvgGrade()));
                param.setFeedbackRate(new BigDecimal(commentGradeDTOResult.getModel().getGoodGrad()));
                merchantStoreService.updateStoreStatisticsById(merchantStoreDTO.getMerchantStoreId(), param);
            }
        }
    }
}
