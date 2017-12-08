package com.lawu.eshop.member.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.bizctrl.AbstractBusinessDecisionService;
import com.lawu.concurrentqueue.bizctrl.BusinessExecuteException;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.AdService;

/**
 * 
 * 
 * @Description
 * @author zhangrc
 * @date 2017年12月8日
 */
@SuppressWarnings("rawtypes")
@Service("adBusinessDecisionServiceImpl")
public class AdBusinessDecisionServiceImpl extends AbstractBusinessDecisionService<Result> {
    
    @Autowired
    private AdService adService;
    
    @Override
    public Integer queryInventory(Object id) {
        Result<Integer> result = adService.getInventory((Long) id);
        if (result.getRet() != ResultCode.SUCCESS) {
            return -1;
        }
        return result.getModel();
    }

    @Override
    public Result sellOut() {
        Result rs = new Result();
        rs.setRet(ResultCode.AD_CLICK_PUTED);
        rs.setMsg(ResultCode.get(rs.getRet()));
        return rs;
    }

    @Override
    public Result fail(BusinessExecuteException e) {
        Result rs = new Result();
        if (e.getRet() != 0) {
            rs.setRet(e.getRet());
            rs.setMsg(e.getMsg());
        } else {
            rs.setRet(ResultCode.FAIL);
            rs.setMsg(ResultCode.get(rs.getRet()));
        }
        return rs;
    }

}
