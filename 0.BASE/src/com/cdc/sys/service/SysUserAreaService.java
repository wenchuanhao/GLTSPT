package com.cdc.sys.service;

import java.util.List;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import model.sys.entity.SysUserArea;

public class SysUserAreaService implements ISysUserAreaService{
	
	private IEnterpriseService enterpriseService;
	
	
	public void addSysUserArea(SysUserArea model) throws Exception {
		enterpriseService.save(model);
		
	}

	public void delSysUserArea(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUserArea.class);
		query.where("userId", userId);
		enterpriseService.delete(query);
		
	}
	/**
	 * 根据用户id和角色id删除用户所属的区域
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	public void delSysUserArea(String userId,String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysUserArea.class);
		query.where("userId", userId);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
	}
	@SuppressWarnings("unchecked")
	public List<SysUserArea> getSysUserAreaByUserId(String userId)throws Exception {
		QueryBuilder query = new QueryBuilder(SysUserArea.class);
		query.where("userId", userId);
		query.orderBy("ranks", true);
		List<?> list = enterpriseService.query(query, 0);
		if(list!=null){
			return (List<SysUserArea>)list;
		}
		return null;
	}
	/**
	 * 根据用户id和角色id去查看区域
	 */
	@SuppressWarnings("unchecked")
	public List<SysUserArea> getSysUserAreaByUserId(String userId,String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysUserArea.class);
		query.where("userId", userId,QueryAction.EQUAL);
		query.where("roleId", roleId,QueryAction.EQUAL);
		query.orderBy("ranks", true);
		List<?> list = enterpriseService.query(query, 0);
		if(list!=null){
			return (List<SysUserArea>)list;
		}
		return null;
	}
	
	
	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	

}
