package com.cdc.dc.project.zb.service;

import java.util.List;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import com.cdc.dc.project.zb.model.GcZbAudits;
import com.cdc.dc.project.zb.model.Zb;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IZbService {
	
	//public Object getEntity(Class clazz, String id);
	
	public ItemPage findZb(Zb zb) throws Exception;
	
	public Zb findZbById(String id) throws Exception;

	public Zb save(Zb zb) throws Exception;
	
	public Zb update(Zb zb) throws Exception;

	public void delete(String id) throws Exception;

	public List<Object[]> searchProjectByCode(String code);

	public List<SysOrganization> querySysOrganizationList();

	public List<Zb> ajaxLoadHistory(String proCode, String userId);

	public List<Object[]> searchDepartmenByName(String code);

	/**
	 * 根据项目id和项目类型查找建设项目总控
	 * @param proId
	 * @param productType
	 * @return
	 */
	public List<SysUser> ajaxLoadAuditUser(String proId, String productType);

	public ItemPage findRemainZb(Zb zb);

	/**
	 * 审核通过
	 * @param zb
	 * @param sysUser
	 */
	public void ajaxPast(Zb zb, SysUser sysUser);

	/**
	 * 周报审核不通过
	 * @param zb
	 * @param auditDesc
	 * @param sysUser
	 */
	public void returnRemain(Zb zb, String auditDesc, SysUser sysUser);

	/**
	 * 根据周报id查询审核信息
	 * @param id
	 * @return
	 */
	public List<GcZbAudits> queryGcZbAuditsList(String id);

	/**
	 * 根据部门id查询部门室经理
	 * @param organizationId
	 * @return
	 */
	public List<SysUser> querySysUserByOrg(String organizationId);

	/**
	 * 周报项目选择
	 * @param zb
	 * @return
	 */
	public ItemPage selectList(Zb zb);
}
