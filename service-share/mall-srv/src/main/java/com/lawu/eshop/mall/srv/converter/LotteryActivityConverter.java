package com.lawu.eshop.mall.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.common.constants.MemberGradeEnum;
import com.lawu.eshop.mall.constants.LotteryActivityStatusEnum;
import com.lawu.eshop.mall.dto.LotteryActivityDTO;
import com.lawu.eshop.mall.dto.LotteryActivityOperatorDTO;
import com.lawu.eshop.mall.srv.bo.LotteryActivityBO;
import com.lawu.eshop.mall.srv.domain.LotteryActivityDO;
import com.lawu.eshop.utils.DateUtil;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
public class LotteryActivityConverter {

    /**
     * BO转换
     *
     * @param activityDO
     * @return
     */
    public static LotteryActivityBO converBO(LotteryActivityDO activityDO) {
        if (activityDO == null) {
            return null;
        }

        LotteryActivityBO activityBO = new LotteryActivityBO();
        activityBO.setId(activityDO.getId());
        activityBO.setPrizeName(activityDO.getPrizeName());
        activityBO.setPrizePrice(activityDO.getPrizePrice());
        activityBO.setPrizeNumber(activityDO.getPrizeNumber());
        activityBO.setImagePath(activityDO.getImagePath());
        activityBO.setBeginTime(activityDO.getBeginTime());
        activityBO.setEndTime(activityDO.getEndTime());
        activityBO.setGrade(activityDO.getGrade());
        activityBO.setStatus(activityDO.getStatus());
        return activityBO;
    }

    /**
     * DTO转换
     *
     * @param activityBO
     * @return
     */
    public static LotteryActivityDTO converDTO(LotteryActivityBO activityBO) {
        if (activityBO == null) {
            return null;
        }

        LotteryActivityDTO activityDTO = new LotteryActivityDTO();
        activityDTO.setLotteryActivityId(activityBO.getId());
        activityDTO.setPrizeName(activityBO.getPrizeName());
        activityDTO.setPrizePrice(activityBO.getPrizePrice());
        activityDTO.setPrizeNumber(activityBO.getPrizeNumber());
        activityDTO.setImagePath(activityBO.getImagePath());
        activityDTO.setEndTimeDays(DateUtil.daysOfTwo(new Date(), activityBO.getEndTime()));
        activityDTO.setGradeEnum(MemberGradeEnum.getEnum(activityBO.getGrade()));
        activityDTO.setStatusEnum(LotteryActivityStatusEnum.getEnum(activityBO.getStatus()));
        activityDTO.setLotteryCount(activityBO.getLotteryCount());
        activityDTO.setLotteryNumber(activityBO.getLotteryNumber());
        return activityDTO;
    }

    /**
     * BO转换
     *
     * @param activityDOS
     * @return
     */
    public static List<LotteryActivityBO> converBOS(List<LotteryActivityDO> activityDOS) {
        List<LotteryActivityBO> activityBOS = new ArrayList<>();
        if (activityDOS == null || activityDOS.isEmpty()) {
            return activityBOS;
        }

        for (LotteryActivityDO activityDO : activityDOS) {
            activityBOS.add(converBO(activityDO));
        }
        return activityBOS;
    }

    /**
     * DTO转换
     *
     * @param activityBOS
     * @return
     */
    public static List<LotteryActivityDTO> converDTOS(List<LotteryActivityBO> activityBOS) {
        List<LotteryActivityDTO> activityDTOS = new ArrayList<>();
        if (activityBOS == null || activityBOS.isEmpty()) {
            return activityDTOS;
        }

        for (LotteryActivityBO activityBO : activityBOS) {
            activityDTOS.add(converDTO(activityBO));
        }
        return activityDTOS;
    }

    /**
     * DTO转换
     *
     * @param activityBO
     * @return
     */
    public static LotteryActivityOperatorDTO converOperatorDTO(LotteryActivityBO activityBO) {
        if (activityBO == null) {
            return null;
        }

        LotteryActivityOperatorDTO activityOperatorDTO = new LotteryActivityOperatorDTO();
        activityOperatorDTO.setId(activityBO.getId());
        activityOperatorDTO.setPrizeName(activityBO.getPrizeName());
        activityOperatorDTO.setPrizePrice(activityBO.getPrizePrice());
        activityOperatorDTO.setPrizeNumber(activityBO.getPrizeNumber());
        activityOperatorDTO.setImagePath(activityBO.getImagePath());
        activityOperatorDTO.setBeginTime(activityBO.getBeginTime());
        activityOperatorDTO.setEndTime(activityBO.getEndTime());
        activityOperatorDTO.setGradeEnum(MemberGradeEnum.getEnum(activityBO.getGrade()));
        activityOperatorDTO.setStatusEnum(LotteryActivityStatusEnum.getEnum(activityBO.getStatus()));
        activityOperatorDTO.setStatusDes(LotteryActivityStatusEnum.getEnum(activityBO.getStatus()).getName());
        return activityOperatorDTO;
    }

    /**
     * DTO转换
     *
     * @param activityBOS
     * @return
     */
    public static List<LotteryActivityOperatorDTO> converOperatorDTOS(List<LotteryActivityBO> activityBOS) {
        List<LotteryActivityOperatorDTO> activityOperatorDTOS = new ArrayList<>();
        if (activityBOS == null || activityBOS.isEmpty()) {
            return activityOperatorDTOS;
        }

        for (LotteryActivityBO activityBO : activityBOS) {
            activityOperatorDTOS.add(converOperatorDTO(activityBO));
        }
        return activityOperatorDTOS;
    }

}
