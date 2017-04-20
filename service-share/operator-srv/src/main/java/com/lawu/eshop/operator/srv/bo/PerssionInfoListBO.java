package com.lawu.eshop.operator.srv.bo;

import java.util.Map;
import java.util.Set;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
public class PerssionInfoListBO {

    private Set<Map<String,String>> perssionInfo;

    public Set<Map<String, String>> getPerssionInfo() {
        return perssionInfo;
    }

    public void setPerssionInfo(Set<Map<String, String>> perssionInfo) {
        this.perssionInfo = perssionInfo;
    }
}
