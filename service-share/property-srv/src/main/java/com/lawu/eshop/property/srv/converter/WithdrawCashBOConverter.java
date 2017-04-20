package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.property.constants.CashChannelEnum;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.dto.WithdrawCashStatusDTO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDO;

/**
 * 用户提现转换
 * @author Sunny
 * @date 2017/04/20
 *
 */
public class WithdrawCashBOConverter {
	
	/**
	 * WithdrawCashDO 转 WithdrawCashBO
	 * 
	 * @param withdrawCashDO
	 * @return
	 */
	public static WithdrawCashBO convert(WithdrawCashDO withdrawCashDO) {
		WithdrawCashBO rtn = null;
		
        if (withdrawCashDO == null || withdrawCashDO.getId() == null || withdrawCashDO.getId() <= 0) {
            return rtn;
        }
        
        rtn = new WithdrawCashBO();
        
        BeanUtils.copyProperties(withdrawCashDO, rtn);
        rtn.setStatus(CashStatusEnum.getEnum(withdrawCashDO.getStatus()));
        rtn.setUserType(UserTypeEnum.getEnum(withdrawCashDO.getUserType()));
        rtn.setChannel(CashChannelEnum.getEnum(withdrawCashDO.getChannel()));
        
        return rtn;
    }
	
	/**
	 * WithdrawCashDO List 转换为 WithdrawCashBO List
	 * 
	 * @param withdrawCashDOList
	 * @return
	 */
	public static List<WithdrawCashBO> convert(List<WithdrawCashDO> withdrawCashDOList) {
		List<WithdrawCashBO> rtn = null;
		
        if (withdrawCashDOList == null || withdrawCashDOList.isEmpty()) {
            return rtn;
        }
        
        rtn = new ArrayList<WithdrawCashBO>();
        
        for (WithdrawCashDO item : withdrawCashDOList) {
        	rtn.add(convert(item));
        }
        
        return rtn;
    }
	
	/**
	 * WithdrawCashBO 转 WithdrawCashStatusDTO
	 * 
	 * @param withdrawCashBO
	 * @return
	 */
	public static WithdrawCashStatusDTO convert(WithdrawCashBO withdrawCashBO) {
		WithdrawCashStatusDTO rtn = null;
		
        if (withdrawCashBO == null || withdrawCashBO.getId() == null || withdrawCashBO.getId() <= 0) {
            return rtn;
        }
        
        rtn = new WithdrawCashStatusDTO();
        
        BeanUtils.copyProperties(withdrawCashBO, rtn);
        
        return rtn;
    }
	
	/**
	 * WithdrawCashBO List 转换为 WithdrawCashStatusDTO List
	 * 
	 * @param withdrawCashDOList
	 * @return
	 */
	public static List<WithdrawCashStatusDTO> convertWithdrawCashStatusDTOList(List<WithdrawCashBO> withdrawCashBOList) {
		List<WithdrawCashStatusDTO> rtn = null;
		
        if (withdrawCashBOList == null || withdrawCashBOList.isEmpty()) {
            return rtn;
        }
        
        rtn = new ArrayList<WithdrawCashStatusDTO>();
        
        for (WithdrawCashBO item : withdrawCashBOList) {
        	rtn.add(convert(item));
        }
        
        return rtn;
    }

}
