package com.cdc.dc.project.jsxm.service;

import com.cdc.dc.project.jsxm.model.Jsxm;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IJsxmService {
	
	//public Object getEntity(Class clazz, String id);
	
	public ItemPage findJsxm(Jsxm jsxm) throws Exception;
	
	public Jsxm findJsxmById(String id) throws Exception;

	public Jsxm save(Jsxm jsxm) throws Exception;
	
	public Jsxm update(Jsxm jsxm) throws Exception;

	public void delete(String id) throws Exception;
}
