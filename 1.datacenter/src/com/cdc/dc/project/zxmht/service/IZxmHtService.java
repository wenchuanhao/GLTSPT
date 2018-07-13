package com.cdc.dc.project.zxmht.service;

import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.model.ZxmHtAttach;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IZxmHtService {
	
	public ItemPage findZxmHt(ZxmHt zxmHt) throws Exception;
	
	public ZxmHt findZxmHtById(String id) throws Exception;

	public ZxmHt save(ZxmHt zxmHt) throws Exception;
	
	public ZxmHt update(ZxmHt zxmHt) throws Exception;

	public void delete(String id) throws Exception;
	
	public ItemPage findZxmHtAttach(ZxmHtAttach htAttach) throws Exception;

	public ZxmHtAttach findZxmHtAttachById(String id) throws Exception;
	
	public ZxmHtAttach saveZxmHtAttach(ZxmHtAttach htAttach) throws Exception;

	public void deleteZxmHtAttach(String id) throws Exception;
}
