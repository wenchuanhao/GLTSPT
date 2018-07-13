package com.cdc.dc.project.zxmht.service;

import com.cdc.dc.project.zxmht.model.HtKz;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IHtKzService {
	
	//public Object getEntity(Class clazz, String id);
	
	public ItemPage findHtKz(HtKz htKz) throws Exception;
	
	public HtKz findHtKzById(String id) throws Exception;

	public HtKz save(HtKz htKz) throws Exception;
	
	public HtKz update(HtKz htKz) throws Exception;

	public void delete(String id) throws Exception;
}
