package com.lawu.eshop.common.exception;

/**
 * 错误操作异常<p>
 * 用户的异常操作
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
public class WrongOperationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WrongOperationException(String message) {
        super(message);
    }
}
