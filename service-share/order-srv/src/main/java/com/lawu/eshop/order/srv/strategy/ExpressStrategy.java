package com.lawu.eshop.order.srv.strategy;

import org.apache.commons.lang.StringUtils;

import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;
import com.lawu.eshop.order.srv.bo.ShipperBO;
import com.lawu.eshop.order.srv.utils.express.kdniao.constants.StateEnum;

/**
 * 快递策略
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public interface ExpressStrategy {
	
	/**
	 * 查询物流轨迹
	 * @param expCode
	 * @param expNo
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月4日
	 */
	ExpressInquiriesDetailBO inquiries(String expCode, String expNo);
	
	/**
	 * 识别快递单号
	 * @param expNo
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月4日
	 */
	ExpressRecognitionDetailBO recognition(String expNo);
	
	/**
	 * 如果快递公司编码为空，自动识别快递单号的快递公司编码，然后再查询物流轨迹
	 * @param expCode
	 * @param expNo
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月25日
	 */
	default ExpressInquiriesDetailBO recognitionWithInquiries(String expCode, String expNo) {
		ExpressInquiriesDetailBO rtn = null;
		if (StringUtils.isNotBlank(expCode)) {
			rtn = inquiries(expCode, expNo);
		}
		/*
		 *  1.如果通过用户选择的快递公司编号查询不到
		 *  2.接口没有传入快递公司编号
		 *  通过快递单号查询快递公司编号
		 */
		if (rtn == null || StateEnum.NO_INFO.equals(rtn.getState())) {
			ExpressRecognitionDetailBO expressRecognitionDetailBO = recognition(expNo);
			if (expressRecognitionDetailBO != null && expressRecognitionDetailBO.getShippers() != null) {
				// 根据可能的快递公司编码，由可信度从高到低遍历查询
				for (ShipperBO shipper : expressRecognitionDetailBO.getShippers()) {
					rtn = inquiries(shipper.getShipperCode(), expNo);
					// 如果返回结果有物流轨迹，则跳出循环
					if (!StateEnum.NO_INFO.equals(rtn.getState())) {
						// 放入真实的快递公司编码
						break;
					}
				}
			}
		}
		return rtn;
	}
}
