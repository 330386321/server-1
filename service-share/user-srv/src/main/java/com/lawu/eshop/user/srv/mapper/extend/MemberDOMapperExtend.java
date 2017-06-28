package com.lawu.eshop.user.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.extend.InviterUserDOView;
import com.lawu.eshop.user.srv.domain.extend.MemberDOView;

/**
 * @author zhangyong
 * @date 2017/6/5.
 */
public interface MemberDOMapperExtend {

    int delUserGtPush(Long memberId);
    
    List<MemberDOView> getMemberByIds(List<Long> memberIds);

	List<MemberDO> selectByExampleWithRowbounds(InviterUserDOView view);


}
