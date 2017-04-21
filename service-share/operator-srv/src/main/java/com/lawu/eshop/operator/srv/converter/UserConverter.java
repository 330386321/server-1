package com.lawu.eshop.operator.srv.converter;

import com.lawu.eshop.operator.constants.StatusEnum;
import com.lawu.eshop.operator.dto.UserListDTO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.bo.UserListBO;
import com.lawu.eshop.operator.srv.domain.UserDO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class UserConverter {

    public static UserBO convertBO(UserDO userDO) {
        if(userDO == null){
            return null;
        }
        UserBO userBO = new UserBO();
        userBO.setId(userDO.getId());
        userBO.setAccount(userDO.getAccount());
        userBO.setPassword(userDO.getPassword());
        userBO.setName(userDO.getName());
        return userBO;
    }

    public static UserListBO cover(UserDO userDO) {
        if(userDO == null){
            return null;
        }
        UserListBO userListBO = new UserListBO();
        userListBO.setId(userDO.getId());
        userListBO.setAccount(userDO.getAccount());
        userListBO.setName(userDO.getName());
        userListBO.setGmtCreate(userDO.getGmtCreate());
        userListBO.setStatusEnum(StatusEnum.getEnum(userDO.getStatus()));
        return userListBO;
    }

    public static List<UserListDTO> coverDTOS(List<UserListBO> records) {
        if(records.isEmpty()){
            return new ArrayList<>();
        }
        List<UserListDTO> userListDTOS = new ArrayList<UserListDTO>();
        for(UserListBO userListBO : records){
            UserListDTO userListDTO = new UserListDTO();
            userListDTO.setAccount(userListBO.getAccount());
            userListDTO.setId(userListBO.getId());
            if(StringUtils.isEmpty(userListBO.getName())){
                userListDTO.setName("");
            }else{
                userListDTO.setName(userListBO.getName());
            }
            userListDTO.setGmtCreate(userListBO.getGmtCreate());
            userListDTO.setStatusEnum(userListBO.getStatusEnum());
            userListDTOS.add(userListDTO);
        }
        return userListDTOS;
    }
}