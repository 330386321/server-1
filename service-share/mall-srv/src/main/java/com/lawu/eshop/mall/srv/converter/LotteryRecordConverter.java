package com.lawu.eshop.mall.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.mall.dto.LotteryRecordDTO;
import com.lawu.eshop.mall.srv.bo.LotteryRecordBO;
import com.lawu.eshop.mall.srv.domain.LotteryActivityDO;

/**
 * @author meishuquan
 * @date 2017/11/24.
 */
public class LotteryRecordConverter {

    /**
     * BO转换
     *
     * @param activityDO
     * @return
     */
    public static LotteryRecordBO converBO(LotteryActivityDO activityDO) {
        if (activityDO == null) {
            return null;
        }

        LotteryRecordBO recordBO = new LotteryRecordBO();
        recordBO.setPrizeName(activityDO.getPrizeName());
        recordBO.setPrizePrice(activityDO.getPrizePrice());
        recordBO.setImagePath(activityDO.getImagePath());
        recordBO.setEndTime(activityDO.getEndTime());
        return recordBO;
    }

    /**
     * DTO转换
     *
     * @param recordBO
     * @return
     */
    public static LotteryRecordDTO converDTO(LotteryRecordBO recordBO) {
        if (recordBO == null) {
            return null;
        }

        LotteryRecordDTO recordDTO = new LotteryRecordDTO();
        recordDTO.setPrizeName(recordBO.getPrizeName());
        recordDTO.setPrizePrice(recordBO.getPrizePrice());
        recordDTO.setImagePath(recordBO.getImagePath());
        recordDTO.setEndTime(recordBO.getEndTime());
        recordDTO.setLotteryNumber(recordBO.getLotteryNumber());
        recordDTO.setLotteryAccountList(recordBO.getLotteryAccountList());
        return recordDTO;
    }

    /**
     * DTO转换
     *
     * @param recordBOS
     * @return
     */
    public static List<LotteryRecordDTO> converDTOS(List<LotteryRecordBO> recordBOS) {
        List<LotteryRecordDTO> recordDTOS = new ArrayList<>();
        if (recordBOS == null || recordBOS.isEmpty()) {
            return recordDTOS;
        }

        for (LotteryRecordBO recordBO : recordBOS) {
            recordDTOS.add(converDTO(recordBO));
        }
        return recordDTOS;
    }

}
