package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
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
	public static BankAccountBO convertBO(BankAccountDO bankAccountDO,String bankName) {
        if (bankAccountDO == null) {
            return null;
        }
        BankAccountBO bankAccountBO=new BankAccountBO();
        bankAccountBO.setId(bankAccountDO.getId());
        bankAccountBO.setBankName(bankName);
        bankAccountBO.setAccountName(bankAccountDO.getAccountName());
        String accountNumber=bankAccountDO.getAccountNumber();
        String newAccountNumber =accountNumber.substring(accountNumber.length()-4, accountNumber.length());
        bankAccountBO.setAccountNumber(newAccountNumber);
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
        	String bankName=null;
        	for (BankDO bankBO : bankDOS) {
				if(bankAccountDO.getBankId().equals(bankBO.getId())){
					bankName=bankBO.getName();
				}
			}
        	BOS.add(convertBO(bankAccountDO,bankName));
		}
        
        return BOS;
    }
	
	/**
	 * BO转DTO
	 * @param bankDO
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
        return bankAccountDTO;
    }
	
	/**
	 * BOS 转DTOS
	 * @param bankDOS
	 * @return
	 */
	public static List<BankAccountDTO> convertDTOS(List<BankAccountBO> bankAccountBOS ) {
        if (bankAccountBOS == null) {
            return null;
        }
        List<BankAccountDTO> DTOS=new ArrayList<BankAccountDTO>();
        for (BankAccountBO bankAccountBO: bankAccountBOS) {
        	DTOS.add(convertDTO(bankAccountBO));
		}
        
        return DTOS;
    }

}
