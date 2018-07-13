package com.cdc.sys.service;

import java.util.List;




import model.sys.entity.SysOrganization;
import model.sys.entity.SysOrganizationTemp;

import com.cdc.sys.form.SysOrganizationForm;
import com.trustel.common.ItemPage;

public interface ISysOrganizationService {
	public ItemPage queryOrganization(SysOrganizationForm form) throws Exception;

	public void addOrganization(SysOrganization organization) throws Exception;

	public void modifyOrganization(SysOrganization organization) throws Exception;

	public void deleteOrganization(String organizationId) throws Exception;

	public List<SysOrganization> queryAll() throws Exception;
	
	public List<SysOrganization> queryAllNoRoot() throws Exception;

	public SysOrganization getOrgById(String orgId) throws Exception;
	/**
	 * 查询公司及以下组织
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public String getAllOrgById(String orgId) throws Exception;
	
	public SysOrganization queryOrgByName(String orgName,String parentId) throws Exception;
	
	public List<SysOrganization> checkSubOrg(String oIds) throws Exception;
	
	public List<SysOrganization> getOrgByIds(String ids) throws Exception;
	
	public void moveUp(String id) throws Exception;
	public void moveUp2(String id,String pid) throws Exception;
	public void moveDown(String id) throws Exception;
	public void moveDown2(String id,String pid) throws Exception;
	public float getOrderNumber(String orgId)throws Exception;
	/**
	 * 根据组织ID获取该组织的下一级子组织
	 * 
	 * @param OrgId
	 * @return（从大到小）
	 * @throws Exception
	 */
	public List<SysOrganization> queryNextLevelOrgByOrgIdInDoWn(String OrgId)
			throws Exception;
	
	/**
	 * 根据组织ID获取该组织的下一级子组织
	 * 
	 * @param OrgId
	 * @return
	 * @throws Exception
	 */
	public List<SysOrganization> queryNextLevelOrgByOrgId(String OrgId)
			throws Exception;
	/**
	 * 根据Id获取seq
	 * @param orgId
	 * @return
	 */
	public float getSeqById(String orgId) throws Exception;
	
	public String judgment(String id,String flag) throws Exception;

	public List<SysOrganization> getAllByNoRoot()throws RuntimeException;
	
	/**
	 * 获得平级，平级子级或自己子级的组织id
	 */
	public String getOrgIdsByOrgId(String orgId,String pid) throws Exception;
	
	/**
	 * 获得当前级或自己子级的组织id
	 */
	public String getOrgIdsByOrgIdNew(String orgId) throws Exception;
	
	/**
	 * 查询是否是系统管理员
	 * @param userId
	 * @return
	 */
	public Boolean isAdmin(String userId);
	
	  //根据组织id获得当前父级id
	public SysOrganization getParentId(String orgId) throws Exception;
	 //由当前组织递归找根节点
	public void deptNames(StringBuilder deptNames,String orgId) throws Exception;
	
	
	/**
	 * 根据组织ID获取第二个级别（例如中数通）的组织架构
	 * @param OrgId
	 * @return
	 * @throws Exception
	 */
	public SysOrganization queryLevel2ByOrgId(String OrgId)
			throws Exception;

	
	/**
	 * 是否Root之下的一级组织架构
	 */
	public String isLevelOne(String id);
	
	
	/**
	 * 根据组织id，获取组织当前的tree的level
	 */
	public Long getOrgCurLevel(String id);
	
	
	 /*删除组织信息，以便同步用户*/
    public void  dropOrg();
    
    /*备份组织信息，以便同步用户*/
    public void  dropOrgBackup();
    
    /*组织临时信息，以便同步用户*/
    public void  dropOrgTemp();
    
    /*同步组织时，同步id字段*/
    public void  updateOrgId();
    
    /*将临时表里数据同步到正式表*/
    public void  addOrgByTemp();
    
    /*临时同步组织时，同步id字段*/
    public void  updateOrgIdTemp();
    
    /*添加临时组织信息*/
    public void addOrganizationTemp(SysOrganizationTemp organization) throws Exception;
    
    /**
     * 根据id获取组织及子组织
     * @param ids
     * @return
     */
    public List<SysOrganization> getOrgAndSonById(String id);
	
}
