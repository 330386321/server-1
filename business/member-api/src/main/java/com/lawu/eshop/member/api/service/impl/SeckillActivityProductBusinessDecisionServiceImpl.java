package com.lawu.eshop.member.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.bizctrl.BusinessDecisionService;
import com.lawu.concurrentqueue.bizctrl.BusinessExecuteException;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.SeckillActivityProductService;

/**
 * 
 * 
 * @author jiangxinjun
 * @createDate 2017年11月30日
 * @updateDate 2017年11月30日
 */
@SuppressWarnings("rawtypes")
@Service
public class SeckillActivityProductBusinessDecisionServiceImpl implements BusinessDecisionService<Result> {
    
    @Autowired
    private SeckillActivityProductService seckillActivityProductService;
    
    @Override
    public Integer queryInventory(Object id) {
        Result<Integer> result = seckillActivityProductService.getInventory((Long) id);
        if (result.getRet() != ResultCode.SUCCESS) {
            return -1;
        }
        return result.getModel();
    }

    @Override
    public Result sellOut() {
        Result rs = new Result();
        rs.setRet(ResultCode.INVENTORY_SHORTAGE);
        rs.setMsg(ResultCode.get(rs.getRet()));
        return rs;
    }

    @Override
    public Result fail(BusinessExecuteException e) {
        Result rs = new Result();
        rs.setRet(e.getRet());
        rs.setMsg(e.getMsg());
        return rs;
    }

}
