package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.dto.BankAccountOperatorDTO;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.bo.BankAccountOperatorBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BankDO;

/**
 * 银行卡信息转换
 * @author zhangrc
 * @date 2017/03/29
 *
 */
public class BankAccountConverter {
	/**
	 * DO转BO
	 * @param bankAccountDO
	 * @return
	 */
	public static BankAccountBO convertBO(BankAccountDO bankAccountDO,String bankName,String url) {
        if (bankAccountDO == null) {
            return null;
        }
        BankAccountBO bankAccountBO=new BankAccountBO();
        bankAccountBO.setId(bankAccountDO.getId());
        bankAccountBO.setBankName(bankName);
        bankAccountBO.setIconUrl(url);
        bankAccountBO.setAccountName(bankAccountDO.getAccountName());
        bankAccountBO.setAccountNumber(bankAccountDO.getAccountNumber());
        bankAccountBO.setSubBranchName(bankAccountDO.getSubBranchName());
        return bankAccountBO;
    }
	
	/**
	 * DOS 转BOS
	 * @param bankDOS
	 * @return
	 */
	public static List<BankAccountBO> convertBOS(List<BankAccountDO> bankAccountDOS ,List<BankDO> bankDOS) {
        if (bankAccountDOS == null) {
            return null;
        }
        List<BankAccountBO> BOS=new ArrayList<BankAccountBO>();
        for (BankAccountDO bankAccountDO: bankAccountDOS) {
        	String accountNumber=bankAccountDO.getAccountNumber();
        	String newAccountNumber =accountNumber.substring(accountNumber.length()-4, accountNumber.length());
        	bankAccountDO.setAccountNumber(newAccountNumber);
        	String bankName=null;
        	String url=null;
        	for (BankDO bankBO : bankDOS) {
				if(bankAccountDO.getBankId().equals(bankBO.getId())){
					bankName=bankBO.getName();
					url=bankBO.getIconUrl();
				}
				
			}
        	BOS.add(convertBO(bankAccountDO,bankName,url));
		}
        
        return BOS;
    }
	
	/**
	 * BO转DTO
	 * @param bankAccountBO
	 * @return
	 */
	public static BankAccountDTO convertDTO(BankAccountBO bankAccountBO ) {
        if (bankAccountBO == null) {
            return null;
        }
        BankAccountDTO bankAccountDTO=new BankAccountDTO();
        bankAccountDTO.setId(bankAccountBO.getId());
        bankAccountDTO.setAccountName(bankAccountBO.getAccountName());
        bankAccountDTO.setAccountNumber(bankAccountBO.getAccountNumber());
        bankAccountDTO.setBankName(bankAccountBO.getBankName());
        bankAccountDTO.setSubBranchName(bankAccountBO.getSubBranchName());
        bankAccountDTO.setIconUrl(bankAccountBO.getIconUrl());
        bankAccountDTO.setBankId(bankAccountBO.getBankId());
        return bankAccountDTO;
    }
	
	/**
	 * BOS 转DTOS
	 * @param bankAccountBOS
	 * @return
	 */
	public static List<BankAccountDTO> convertDTOS(List<BankAccountBO> bankAccountBOS ) {
        List<BankAccountDTO> DTOS=new ArrayList<BankAccountDTO>();
        if(bankAccountBOS==null){
        	return DTOS;
        }
        for (BankAccountBO bankAccountBO: bankAccountBOS) {
        	BankAccountDTO bankAccountDTO=new BankAccountDTO();
            bankAccountDTO.setId(bankAccountBO.getId());
            bankAccountDTO.setAccountName(bankAccountBO.getAccountName());
            bankAccountDTO.setAccountNumber(bankAccountBO.getAccountNumber());
            bankAccountDTO.setBankName(bankAccountBO.getBankName());
            bankAccountDTO.setSubBranchName(bankAccountBO.getSubBranchName());
            bankAccountDTO.setIconUrl(bankAccountBO.getIconUrl());
            bankAccountDTO.setBankId(bankAccountBO.getBankId());
            DTOS.add(bankAccountDTO);
		}
        return DTOS;
    }

	public static List<BankAccountOperatorBO> convertOperatorBOS(List<BankAccountDO> list) {
		if (list == null) {
            return null;
        }
        List<BankAccountOperatorBO> BOS=new ArrayList<BankAccountOperatorBO>();
        
        for (BankAccountDO bankAccountDO: list) {
        	
        	BankAccountOperatorBO bankAccountBO=new BankAccountOperatorBO();
              bankAccountBO.setId(bankAccountDO.getId());
              bankAccountBO.setAccountName(bankAccountDO.getAccountName());
              bankAccountBO.setAccountNumber(bankAccountDO.getAccountNumber());
              bankAccountBO.setSubBranchName(bankAccountDO.getSubBranchName());
              bankAccountBO.setAuditorId(bankAccountDO.getAuditorId());
              bankAccountBO.setRemark(bankAccountDO.getRemark());
              bankAccountBO.setAuditorTime(bankAccountDO.getAuditTime());
              bankAccountBO.setBankId(bankAccountDO.getBankId());
        	  BOS.add(bankAccountBO);
		}
		return BOS;
	}
	
	
	public static List<BankAccountOperatorDTO> convertOperatorDTOS(List<BankAccountOperatorBO> list) {
		if (list == null) {
            return null;
        }
        List<BankAccountOperatorDTO> dtos=new ArrayList<BankAccountOperatorDTO>();
        
		for (BankAccountOperatorBO bankAccountOperatorBO : list) {

			BankAccountOperatorDTO bankAccountDTO = new BankAccountOperatorDTO();
			bankAccountDTO.setId(bankAccountOperatorBO.getId());
			bankAccountDTO.setAccountName(bankAccountOperatorBO.getAccountName());
			bankAccountDTO.setAccountNumber(bankAccountOperatorBO.getAccountNumber());
			bankAccountDTO.setSubBranchName(bankAccountOperatorBO.getSubBranchName());
			bankAccountDTO.setAuditorId(bankAccountOperatorBO.getAuditorId());
			bankAccountDTO.setRemark(bankAccountOperatorBO.getRemark());
			bankAccountDTO.setAuditorTime(bankAccountOperatorBO.getAuditorTime());
			bankAccountDTO.setBankName(bankAccountOperatorBO.getBankName());
			dtos.add(bankAccountDTO);
			
		}
		return dtos;
	}

}
