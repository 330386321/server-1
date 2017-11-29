package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;
import com.lawu.eshop.utils.StringUtil;
import org.apache.commons.lang.StringUtils;

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
        fansMerchantBO.setMerchantId(fansMerchantBO.getMerchantId());
        return fansMerchantBO;
    }

    /**
     * BO转换
     *
     * @param fansMerchantDOViewList
     * @return
     */
    public static List<FansMerchantBO> convertBO(List<FansMerchantDOView> fansMerchantDOViewList) {
        List<FansMerchantBO> fansMerchantBOS = new ArrayList<FansMerchantBO>();
        if (fansMerchantDOViewList == null || fansMerchantDOViewList.isEmpty()) {
            return fansMerchantBOS;
        }

        for (FansMerchantDOView fansMerchantDOView : fansMerchantDOViewList) {
            FansMerchantBO fansMerchantBO = new FansMerchantBO();
            fansMerchantBO.setRegionPath(fansMerchantDOView.getRegionPath());
            fansMerchantBO.setRegionName(fansMerchantDOView.getRegionName());
            fansMerchantBO.setAccount(fansMerchantDOView.getAccount());
            fansMerchantBO.setMemberId(fansMerchantDOView.getMemberId());
            fansMerchantBO.setNum(fansMerchantDOView.getNum());
            fansMerchantBO.setGmtCreate(fansMerchantDOView.getGmtCreate());
            fansMerchantBO.setHeadimg(fansMerchantDOView.getHeadimg());
            fansMerchantBO.setLevel(fansMerchantDOView.getLevel());
            fansMerchantBO.setNickname(fansMerchantDOView.getNickname());
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
        List<FansMerchantDTO> fansMerchantDTOS = new ArrayList<FansMerchantDTO>();
        if (fansMerchantBOList == null || fansMerchantBOList.isEmpty()) {
            return fansMerchantDTOS;
        }

        for (FansMerchantBO fansMerchantBO : fansMerchantBOList) {
            FansMerchantDTO fansMerchantDTO = new FansMerchantDTO();
            fansMerchantDTO.setMemberId(fansMerchantBO.getMemberId());
            fansMerchantDTO.setAccount(StringUtil.hideUserAccount(fansMerchantBO.getAccount()));
            fansMerchantDTO.setRegionPath(fansMerchantBO.getRegionPath());
            fansMerchantDTO.setRegionName(fansMerchantBO.getRegionName());
            fansMerchantDTO.setNum(fansMerchantBO.getNum());
            fansMerchantDTO.setNickname(StringUtils.isEmpty(fansMerchantBO.getNickname()) ? "E店用户" : fansMerchantBO.getNickname());
            fansMerchantDTO.setHeadimg(fansMerchantBO.getHeadimg());
            fansMerchantDTO.setLevel(fansMerchantBO.getLevel());
            fansMerchantDTO.setGmtCreate(fansMerchantBO.getGmtCreate());
            fansMerchantDTOS.add(fansMerchantDTO);
        }
        return fansMerchantDTOS;
    }
}
