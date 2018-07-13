package com.trustel.common;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * 
 */
public class OrderBy implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -193538642601448312L;
	/**
     * 排序字段
     */
    public String property;
    /**
     * 是否升序
     */
    public boolean ascend;
    
    public OrderBy(String property) {
        this.property = property;
        ascend = true;
    }
    
    public OrderBy(String property, boolean ascend) {
        this.property = property;
        this.ascend = ascend;
    }
    
    public String toString() {
    	return ascend ? property : property + " desc";
    }
}
