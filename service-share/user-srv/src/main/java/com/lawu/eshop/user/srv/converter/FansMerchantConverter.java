package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class FansMerchantConverter {

    /**
     * BO转换
     *
     * @param fansMerchantDO
     * @return
     */
    public static FansMerchantBO convertBO(FansMerchantDO fansMerchantDO) {
        if (fansMerchantDO == null) {
            return null;
        }

        FansMerchantBO fansMerchantBO = new FansMerchantBO();
        fansMerchantBO.setMemberId(fansMerchantDO.getMemberId());
        return fansMerchantBO;
    }

    /**
     * BO转换
     *
     * @param fansMerchantDOViewList
     * @return
     */
    public static List<FansMerchantBO> convertBO(List<FansMerchantDOView> fansMerchantDOViewList) {
        if (fansMerchantDOViewList.isEmpty()) {
            return null;
        }

        List<FansMerchantBO> fansMerchantBOS = new ArrayList<FansMerchantBO>(fansMerchantDOViewList.size());
        for (FansMerchantDOView fansMerchantDOView : fansMerchantDOViewList) {
            FansMerchantBO fansMerchantBO = new FansMerchantBO();
            fansMerchantBO.setRegionPath(fansMerchantDOView.getRegionPath());
            fansMerchantBO.setSex(fansMerchantDOView.getSex());
            fansMerchantBO.setAccount(fansMerchantDOView.getAccount());
            fansMerchantBO.setMemberId(fansMerchantDOView.getMemberId());
            fansMerchantBO.setAge(fansMerchantDOView.getAge());
            fansMerchantBO.setNum(fansMerchantDOView.getNum());
            fansMerchantBOS.add(fansMerchantBO);
        }
        return fansMerchantBOS;
    }

    /**
     * DTO转换
     *
     * @param fansMerchantBOList
     * @return
     */
    public static List<FansMerchantDTO> convertDTO(List<FansMerchantBO> fansMerchantBOList) {
        if (fansMerchantBOList.isEmpty()) {
            return null;
        }

        List<FansMerchantDTO> fansMerchantDTOS = new ArrayList<FansMerchantDTO>(fansMerchantBOList.size());
        for (FansMerchantBO fansMerchantBO : fansMerchantBOList) {
            FansMerchantDTO fansMerchantDTO = new FansMerchantDTO();
            fansMerchantDTO.setAge(fansMerchantBO.getAge());
            fansMerchantDTO.setMemberId(fansMerchantBO.getMemberId());
            fansMerchantDTO.setAccount(fansMerchantBO.getAccount());
            fansMerchantDTO.setRegionPath(fansMerchantBO.getRegionPath());
            fansMerchantDTO.setUserSexEnum(UserSexEnum.getEnum(fansMerchantBO.getSex()));
            fansMerchantDTO.setNum(fansMerchantBO.getNum());
            fansMerchantDTOS.add(fansMerchantDTO);
        }
        return fansMerchantDTOS;
    }
}
