package com.cdc.dc.project.zxm.service;

import com.cdc.dc.project.zxm.model.Zxm;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IZxmService {
	
	//public Object getEntity(Class clazz, String id);
	
	public ItemPage findZxm(Zxm zxm) throws Exception;
	
	public Zxm findZxmById(String id) throws Exception;

	public Zxm save(Zxm zxm) throws Exception;
	
	public Zxm update(Zxm zxm) throws Exception;

	public void delete(String id) throws Exception;
}
