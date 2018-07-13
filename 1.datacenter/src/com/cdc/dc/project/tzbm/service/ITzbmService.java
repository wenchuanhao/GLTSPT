package com.cdc.dc.project.tzbm.service;

import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.model.TzbmYear;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface ITzbmService {
	
	public ItemPage findTzbm(Tzbm tzbm) throws Exception;
	
	public Tzbm findTzbmById(String id) throws Exception;

	public Tzbm save(Tzbm tzbm) throws Exception;
	
	public Tzbm update(Tzbm tzbm) throws Exception;

	public void delete(String id) throws Exception;
	
	public ItemPage findTzbmYear(TzbmYear tzbmYear) throws Exception;
	
	public TzbmYear findTzbmYearById(String id) throws Exception;

	public TzbmYear save(TzbmYear tzbmYear) throws Exception;
	
	public TzbmYear update(TzbmYear tzbmYear) throws Exception;

	public void deleteTzbmYear(String id) throws Exception;
}
