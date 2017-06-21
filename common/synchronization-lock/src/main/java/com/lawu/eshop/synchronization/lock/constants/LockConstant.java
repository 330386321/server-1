package com.lawu.eshop.synchronization.lock.constants;

public class LockConstant {
	
	/**
	 * 隐藏构造函数
	 */
	private LockConstant() {
		throw new IllegalAccessError("Utility class");
	}
	
    /**
     * 分布式事务同步锁前缀
     */
    public static final String DISTRIBUTED_SYNCHRONIZATION_LOCK = "SYN_LOCK_";
	
	/*******************************
	 * Model Name
	 *******************************/
    public enum LockModule {
    	/**
         * ad-srv模块
         */
    	LOCK_AD_SRV("AD_SRV"),
    	
        /**
         * mall-srv模块
         */
    	LOCK_MALL_SRV("MALL_SRV"),
    	
        /**
         * order-srv模块
         */
    	LOCK_ORDER_SRV("ORDER_SRV"),
    	
        /**
         * product-srv模块
         */
    	LOCK_PRODUCT_SRV("PRODUCT_SRV"),
    	
        /**
         * property-srv模块
         */
    	LOCK_PROPERTY_SRV("PROPERTY_SRV"),
    	
        /**
         * user-srv模块
         */
    	LOCK_USER_SRV("USER_SRV");
    	
    	private String name;
    	
    	LockModule(String name){
    		this.name = name;
    	}

		public String getName() {
			return name;
		}

    }

    /******************************************
     * Lock Name
     ******************************************/
    /*******	ad-srv模块LockName		**********/
    
    /*******	mall-srv模块LockName		**********/
    
    /*******	order-srv模块LockName 	**********/
    
    /*******	product-srv模块LockName	**********/
    
    /*******	property-srv模块LockName	**********/
    
    /*******	user-srv模块LockName		**********/
}
