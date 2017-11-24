package com.lawu.eshop.product.srv.mapper.extend;

/**
 * ProductModelDOMapper扩展
 * 
 * @author Sunny
 * @date 2017年6月6日
 */
public interface SeckillActivityProductDOExtendMapper {

    /**
     * 根据id增加关注人数
     * 
     * @param id 主键
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
	void increaseAttentionCount(Long id);
}