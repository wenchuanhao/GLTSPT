package com.cdc.dc.project.attach.service;

import com.cdc.dc.project.attach.model.GcAttJy;
import com.cdc.dc.project.attach.model.GcAttach;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IGcAttachService {
	
	public ItemPage findGcAttach(GcAttach attach) throws Exception;
	
	public GcAttach findGcAttachById(String id) throws Exception;

	public GcAttach save(GcAttach attach) throws Exception;
	
	public GcAttach update(GcAttach attach) throws Exception;
	
	public GcAttach saveOrUpdate(GcAttach attach) throws Exception;

	public void delete(String id) throws Exception;
	
	public ItemPage findGcAttJy(GcAttJy attach) throws Exception;
	
	public GcAttJy findGcAttJyById(String id) throws Exception;

	public GcAttJy save(GcAttJy attach) throws Exception;
	
	public GcAttJy update(GcAttJy attach) throws Exception;
	
	public GcAttJy saveOrUpdate(GcAttJy attach) throws Exception;

	public void deleteGcAttJy(String id) throws Exception;
}
