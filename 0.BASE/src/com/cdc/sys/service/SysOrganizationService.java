package com.cdc.sys.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysOrganizationTemp;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.SysOrganizationForm;
import com.trustel.common.ItemPage;

public class SysOrganizationService implements ISysOrganizationService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public ItemPage queryOrganization(SysOrganizationForm form)
			throws Exception {
		QueryBuilder query = new QueryBuilder(
				"SysOrganization s ,SysOrganization p ");
//		QueryBuilder query = new QueryBuilder(
//				"SysOrganization s ,SysOrganization p ,SysUser u");
		query.where("s.parentId = p.organizationId");
		query.where("s.organizationId","80df8fa55ca4048ac2314dab1a52d75e",QueryAction.NOEQUAL);
//		query.where("s.createrId = u.userId");
		if (null != form) {
			if (null != form.getOrgCode() && !form.getOrgCode().equals(""))
				query.where("s.orgCode", form.getOrgCode(), QueryAction.LIKE);
			if (null != form.getOrgName() && !form.getOrgName().equals(""))
				query.where("s.orgName", form.getOrgName(), QueryAction.LIKE);
//			if (null != form.getParentId() && !form.getParentId().equals(""))
//				query.where("s.parentId in("+getOrgByPId(form.getParentId())+")");
				query.where("s.parentId",form.getParentId(),QueryAction.EQUAL);
			if (null != form.getCreateTime()
					&& !form.getCreateTime().equals(""))
				query.where("s.createTime", form.getCreateTime(),
						QueryAction.LIKE);
		}
		query.orderBy("s.orgOrder",true);
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}

	public void addOrganization(SysOrganization organization) throws Exception {
        if (organization != null) {
            SysOrganization parentOrg = (SysOrganization) enterpriseService.getById(SysOrganization.class,organization.getParentId());
            if (parentOrg != null) {
                organization.setOrgLevel(parentOrg.getOrgLevel()!=null ? parentOrg.getOrgLevel()+1 : 1);
            }
        }
		enterpriseService.save(organization);
	}

	public void modifyOrganization(SysOrganization organization)
			throws Exception {
        if (organization != null) {
            SysOrganization parentOrg = (SysOrganization) enterpriseService.getById(SysOrganization.class,organization.getParentId());
            if (parentOrg != null) {
                organization.setOrgLevel(parentOrg.getOrgLevel()!=null ? parentOrg.getOrgLevel()+1 : 1);
            }
        }
		enterpriseService.updateObject(organization);
	}

	public SysOrganization queryOrgById(String organizationId) throws Exception {
		return (SysOrganization) enterpriseService.getById(
				SysOrganization.class, organizationId);
	}

	public void deleteOrganization(String organizationId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("organizationId", organizationId);
		enterpriseService.delete(query);

	}

	public SysOrganization getOrgById(String orgId) throws Exception {
		return (SysOrganization) enterpriseService.getById(
				SysOrganization.class, orgId);
	}
	
	/**
	 * 查询公司及以下组织
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public String getAllOrgById(String orgId) throws Exception{
		SysOrganization  sysOrganization=null;
		sysOrganization=(SysOrganization) enterpriseService.getById(
				SysOrganization.class, orgId);
		String org=null;
		if(sysOrganization!=null){
			org=">>"+sysOrganization.getOrgName();
		}
		SysOrganization sysOrganizationTemp=null;
		while(sysOrganization.getParentId()!=""&&sysOrganization.getParentId()!=null&&sysOrganization.getOrgLevel()>3){
			sysOrganizationTemp=(SysOrganization) enterpriseService.getById(SysOrganization.class,sysOrganization.getParentId());
			org=">>"+sysOrganizationTemp.getOrgName()+org;
			sysOrganization=sysOrganizationTemp;
		}			
		return org;
	}
	
	
	@SuppressWarnings("unchecked")
	public float getOrderNumber(String orgId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId", orgId, QueryAction.EQUAL);
		List<SysOrganization> list = (List<SysOrganization>) enterpriseService.query(query, 0);
		float t=1;
		for(SysOrganization org:list){
			if(org.getOrgOrder()>t){
				t=org.getOrgOrder();
			}
		}
		if(t==1){
			SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, orgId);
			 return org.getOrgOrder()*2+1;
		}else{
			return (float) (t+1);
		}
	}
	/**
	 * 根据Id获取seq
	 * @param orgId
	 * @return
	 */
	public float getSeqById(String orgId)throws Exception{
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, orgId);
//		return org.getSeq();
		return (float)0;
	}
	@SuppressWarnings("unchecked")
	public List<SysOrganization> queryAll() throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.orderBy("orgOrder");
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}
	/**
	 * 获取组织机构————排除root
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> queryAllNoRoot()throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		//String flag="85D176CCBC8942F3B54DE050BE748A58";
		String flag="80df8fa55ca4048ac2314dab1a52d75e";
		builder.where("parentId",flag.trim());
		builder.where("organizationId",flag.trim(),QueryAction.NOEQUAL);
		List<SysOrganization> listOrg =(List<SysOrganization>) enterpriseService.query(builder, 0);
		return listOrg;
	}
	/**
	 * 获取组织机构————排除root的所有组织
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getAllByNoRoot()throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		//String flag="ROOT";
		String flag="80df8fa55ca4048ac2314dab1a52d75e";
		builder.where("organizationId",flag.trim(),QueryAction.NOEQUAL);
		List<SysOrganization> listOrg =(List<SysOrganization>) enterpriseService.query(builder, 0);
		return listOrg;
	}
	public SysOrganization queryOrgByName(String orgName,String parentId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("orgName", orgName, QueryAction.EQUAL);
		query.where("parentId", parentId, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysOrganization) list.get(0);
		return null;
	}
	public void moveDown(String id) throws Exception {
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);
		if(org!=null){
			List<SysOrganization> list=queryNextLevelOrgByOrgId(org.getParentId());
					for(SysOrganization subOrg:list){
						if(subOrg.getOrgOrder()>org.getOrgOrder()){
							float t=org.getOrgOrder();
							org.setOrgOrder(subOrg.getOrgOrder());
							subOrg.setOrgOrder(t);
							enterpriseService.updateObject(subOrg);
							enterpriseService.updateObject(org);
							break;
						}
					}
		}
	}
	//用来移动组织的向下级越级移动
	public void moveDown2(String id,String pid) throws Exception {
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);//子组织
		SysOrganization porg=(SysOrganization) enterpriseService.getById(SysOrganization.class, pid);//父组织
		List<SysOrganization> list = queryNextLevelOrgByOrgId(pid);
		int k=0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getOrganizationId().equals(id)){
				k=i;
				break;
			}
		}
		if(k==0){
			k=1;
		}else{
			k=k-1;
		}
		
		SysOrganization org1=list.get(k);
		org.setParentId(org1.getOrganizationId());
		org.setOrgOrder(getOrderNumber(org1.getOrganizationId()));
//        org.setOrgLevel(org1.getOrgLevel()+1);
		enterpriseService.updateObject(org);
		
		//重新更新当前的 level
		org.setOrgLevel( this.getOrgCurLevel(org.getOrganizationId()) );
		enterpriseService.updateObject(org);
		
        updateOrgLevel(org.getOrganizationId(),org.getOrgLevel());
		
	}
	public void moveUp(String id) throws Exception {
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);
		if(org!=null){
			List<SysOrganization> list = new ArrayList<SysOrganization>();
			if(org.getParentId().equals("80df8fa55ca4048ac2314dab1a52d75e")){
				list=queryNextLevelOrgByOrgId(org.getParentId());
			}else{
				list=queryNextLevelOrgByOrgIdInDoWn(org.getParentId());
			}
			
			 if(org.getParentId().equals("80df8fa55ca4048ac2314dab1a52d75e")){
				 if(list!=null && list.size()>0){
				    	int n=0;
				    	for(int i=0;i<list.size();i++){
				    		if(list.get(i).getOrganizationId().equals(id)){
				    			int k=0;
				    			if(i>=1){
				    				k=i-1;
				    			}
				    			
				    			n=k;
				    			break;
				    		}
				    	}
				    	
				    	
				    	SysOrganization orgs=list.get(n);
				    	float org1=orgs.getOrgOrder();
				    	float org2=org.getOrgOrder();
				    	org.setOrgOrder(org1);
				    	orgs.setOrgOrder(org2);
				    	enterpriseService.updateObject(org);
						enterpriseService.updateObject(orgs);
						return;
				    }
			 }else{
				 for(SysOrganization subOrg:list){
						if(subOrg.getOrgOrder()<org.getOrgOrder()){
							float t=org.getOrgOrder();
							org.setOrgOrder(subOrg.getOrgOrder());
							subOrg.setOrgOrder(t);
							enterpriseService.updateObject(subOrg);
							enterpriseService.updateObject(org);
							break;
						}
					}
			 }
				    
					
		}
	}
	//用来移动组织的向上级越级移动
	public void moveUp2(String id,String pid) throws Exception {
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);//子组织
		SysOrganization porg=(SysOrganization) enterpriseService.getById(SysOrganization.class, pid);//父组织
		org.setParentId(porg.getParentId());
		//org.setOrgOrder(porg.getOrgOrder()+1.0f);
		
		org.setOrgOrder(getOrderNumber(porg.getOrganizationId()));
//        org.setOrgLevel(org.getOrgLevel()-1);

		enterpriseService.updateObject(org);
		
		//重新更新当前的 level
		org.setOrgLevel( this.getOrgCurLevel(org.getOrganizationId()) );
		enterpriseService.updateObject(org);

        updateOrgLevel(org.getOrganizationId(),org.getOrgLevel());
		
	}
	//用来移动组织的向下级越级移动
	public void moveUp3(String id,String pid) throws Exception {
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);//子组织
		SysOrganization porg=(SysOrganization) enterpriseService.getById(SysOrganization.class, pid);//父组织
		org.setParentId(porg.getParentId());
		//org.setOrgOrder(porg.getOrgOrder()+1.0f);
		
		org.setOrgOrder(getOrderNumber(porg.getParentId())+10f);
//        org.setOrgLevel(porg.getOrgLevel()+1);
		enterpriseService.updateObject(org);

		//重新更新当前的 level
		org.setOrgLevel( this.getOrgCurLevel(org.getOrganizationId()) );
		enterpriseService.updateObject(org);
		
        updateOrgLevel(org.getOrganizationId(),org.getOrgLevel());
		
	}

    public void updateOrgLevel(String topOrgId,Long level){
        StringBuilder sql = new StringBuilder();
        sql.append("merge into sys_organization t2 ")
                .append("using (select t.organization_id as orgId,level as queryLevel from sys_organization t ")
                .append("start with t.parent_id='").append(topOrgId).append("' ")
                .append("connect by prior t.organization_id=t.parent_id) t1 ")
                .append("on (t2.organization_id = t1.orgId) ")
                .append("when matched then ")
                .append("  update set t2.org_level=").append(level).append("+t1.queryLevel");
        enterpriseService.getSessions().createSQLQuery(sql.toString()).executeUpdate();
    }

	@SuppressWarnings("unchecked")
	public List<SysOrganization> checkSubOrg(String oIds) throws Exception{
		QueryBuilder query=new QueryBuilder(SysOrganization.class);
			query.where("parentId in ("+oIds+")");
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}

	/**
	 * 根据组织ID获取该组织的下一级子组织
	 * 
	 * @param OrgId(从小到大)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysOrganization> queryNextLevelOrgByOrgId(String OrgId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId", OrgId, QueryAction.EQUAL);
		query.orderBy("orgOrder",true);
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}
	/**
	 * 根据组织ID获取该组织的下一级子组织
	 * 
	 * @param OrgId
	 * @return（从大到小）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysOrganization> queryNextLevelOrgByOrgIdInDoWn(String OrgId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId", OrgId, QueryAction.EQUAL);
		query.orderBy("orgOrder",false);
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}
	/**
	 * 递归获取某个组织所有子组织
	 * 
	 * @param orgList
	 *            所有组织
	 * @param SubList
	 *            子组织变量
	 * @param newList
	 *            存某个组织下的所有子组织
	 * @return
	 */
	public List<SysOrganization> recursive(List<SysOrganization> orgList,
			List<SysOrganization> SubList, List<SysOrganization> newList) {
		// 子组织
		List<SysOrganization> SuborgList = new ArrayList<SysOrganization>();
		for (SysOrganization org : orgList) {
			for (SysOrganization Suborg : SubList) {
				if (Suborg.getOrganizationId().equals(org.getParentId())) {
					newList.add(org);
					SuborgList.add(org);
				}
			}
		}
		if (SuborgList.size() != 0) {
			this.recursive(orgList, SuborgList, newList);
		}
		return newList;
	}
	public void deptNames(StringBuilder deptNamesa,String orgId) throws Exception{
		 
		if(orgId != null && !orgId.equals("80df8fa55ca4048ac2314dab1a52d75e")){
			SysOrganization sysOrganization = getOrgById(orgId);
			if(deptNamesa == null || deptNamesa.equals("")){
				deptNamesa.append(sysOrganization.getOrgName());
			}else{
				String newNames="未知";
				if(sysOrganization!=null && sysOrganization.getOrgName()!=null && !sysOrganization.getOrgName().trim().equals("")){
					newNames=sysOrganization.getOrgName();
				}
				deptNamesa.append(">>").append(newNames);
			}
			if(sysOrganization!=null && sysOrganization.getParentId()!=null){
				deptNames(deptNamesa,sysOrganization.getParentId());
			}
			
		}
	}
	/**
	 * 获得平级，平级子级或自己子级的组织id
	 */
	@SuppressWarnings("unchecked")
	public String getOrgIdsByOrgId(String orgId,String pid) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService.query(query, 0);
		if(orgId != null && !orgId.equals("") && orgId.equals("80df8fa55ca4048ac2314dab1a52d75e")){
			String orgIdN = "'" + orgId + "',";
			for (SysOrganization org : orgList) {
				orgIdN += "'" + org.getOrganizationId() + "',";
			}
			return orgIdN.substring(0,orgIdN.length()-1);
		}
	//	QueryBuilder querystr = new QueryBuilder(SysOrganization.class);//获取平级组织Id
		//querystr.where("parentId", pid, QueryAction.EQUAL);
		//List<SysOrganization> bList = (List<SysOrganization>) enterpriseService.query(querystr, 0);
		
		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
		List<SysOrganization> newList = new ArrayList<SysOrganization>();
		String orgIds = "";
		orgIds = "'" + orgId + "',";
//		for(SysOrganization org:bList){
//			orgIds+="'"+org.getOrganizationId()+"',";
//		}
		for (SysOrganization org : orgList) {
			//for(SysOrganization brg :bList){
				if (org.getParentId().equals(orgId)) {
					sublist.add(org);
					newList.add(org);
				}
			//}
		}
		if (sublist.size() != 0) {
			List<SysOrganization> allSubList = recursive(orgList, sublist,
					newList);
			for (SysOrganization org : allSubList) {
				orgIds += "'" + org.getOrganizationId() + "',";
			}
		}
		return orgIds.substring(0,orgIds.length()-1);
	}
	/**
	 * 获得当前级或自己子级的组织id
	 */
	@SuppressWarnings("unchecked")
	public String getOrgIdsByOrgIdNew(String orgId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService.query(query, 0);
		if(orgId != null && !orgId.equals("") && orgId.equals("80df8fa55ca4048ac2314dab1a52d75e")){
			String orgIdN = orgId + ",";
			for (SysOrganization org : orgList) {
				orgIdN += org.getOrganizationId() + ",";
			}
			return orgIdN.substring(0,orgIdN.length()-1);
		}
		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
		List<SysOrganization> newList = new ArrayList<SysOrganization>();
		String orgIds = "";
		orgIds = orgId + ",";
		for (SysOrganization org : orgList) {
				if (org.getParentId().equals(orgId)) {
					sublist.add(org);
					newList.add(org);
				}
		}
		if (sublist.size() != 0) {
			List<SysOrganization> allSubList = recursive(orgList, sublist,
					newList);
			for (SysOrganization org : allSubList) {
				orgIds += org.getOrganizationId() + ",";
			}
		}
		return orgIds.substring(0,orgIds.length()-1);
	}
	/**
	 * 根据组织Id获取其组织下的用户以及下属组织下的用户
	 */
	@SuppressWarnings("unchecked")
	public String getOrgByPId(String orgId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService
				.query(query, 0);
		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
		List<SysOrganization> newList = new ArrayList<SysOrganization>();
		String orgIds = "";
		orgIds = "'" + orgId + "',";
		for (SysOrganization org : orgList) {
			if (org.getParentId().equals(orgId)) {
				sublist.add(org);
				newList.add(org);
			}
		}
		if (sublist.size() != 0) {
			List<SysOrganization> allSubList = recursive(orgList, sublist,
					newList);
			for (SysOrganization org : allSubList) {
				orgIds += "'" + org.getOrganizationId() + "',";
			}
		}
		return orgIds.substring(0,orgIds.length()-1);
	}
	public String judgment(String id,String flag) throws Exception{
			SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);
			if(org!=null){
				if(flag.equals("up")){
					List<SysOrganization> list=queryNextLevelOrgByOrgId(org.getParentId());
						if(list.size()!=0){
							if(org.getOrgOrder()==list.get(0).getOrgOrder()){
								return "1";
							}else{
								return "0";
							}
						}else{
							return "0";
						}
				}else{
						List<SysOrganization> list=queryNextLevelOrgByOrgIdInDoWn(org.getParentId());
							if(list.size()!=0){
								if(org.getOrgOrder()==list.get(0).getOrgOrder()){
									return "2";
								}else{
									return "0";
								}
							}else{
								return "0";
							}
						}
					}
			return "0";
		}

	public List<SysOrganization> getOrgByIds(String ids) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("organizationId in ("+ids+")");
		query.orderBy("orgOrder",true);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService
				.query(query, 0);
		if(orgList != null && orgList.size() > 0) {
			return orgList;
		} else {
			return null;
		}
	}
	
	/**
	 * 查询是否是系统管理员
	 * @param userId
	 * @return
	 */
	public Boolean isAdmin(String userId){
		Boolean status=false;
		QueryBuilder query = new QueryBuilder("SysUser s1,SysRoleUser s2,SysRole s3");
		query.where("s1.userId ='"+userId.trim()+"'");
		query.where("s1.userId = s2.userId");
		query.where("s2.roleId = s3.roleId");
		query.where("s3.roleCode ='admin'");
		System.out.print(query.getHQL());
		List<?> list=enterpriseService.query(query, 0);
		if(list.size()>0){
			status=true;
		}
	    return status;
	}
	
	   //根据组织id获得当前父级id
	public SysOrganization getParentId(String orgId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("organizationId", orgId, QueryAction.EQUAL);
		List<?> list=enterpriseService.query(query, 0);
		
		return (SysOrganization) list.get(0);
	}

	
	//递归搜索 祖辈的 组织架构
	private SysOrganization recursiveAncestorOrg( Map<String, SysOrganization> allMap , 
			List<SysOrganization> level2List ,  SysOrganization  curOrg){
		//如果当前组织，就是第二级别的
		if(level2List.contains(curOrg)){
			return curOrg;
		}
		//
		SysOrganization parentOrg = allMap.get(curOrg.getParentId());
		if(level2List.contains(parentOrg)){
			return parentOrg;
		}else if(parentOrg==null || parentOrg==curOrg ){
			return null;
		}else{
			return recursiveAncestorOrg(allMap , level2List , parentOrg );
		}
	}
	//
	//根据组织ID获取第二个级别（例如中数通）的组织架构
	//
	public SysOrganization queryLevel2ByOrgId(String OrgId) throws Exception {

		//查询所有的组织不包括root
		List<SysOrganization> all= getAllByNoRoot();
		Map<String, SysOrganization> allMap= new HashMap<String, SysOrganization>();
		for (SysOrganization org : all) {
			allMap.put(org.getOrganizationId() , org);
		}
		//
		//查询所有第一级别 组织
		List<SysOrganization> level1List = new ArrayList<SysOrganization>();
		Map<String, SysOrganization> level1Map = new HashMap<String, SysOrganization>();
		for (SysOrganization org : all) {
			if( "80df8fa55ca4048ac2314dab1a52d75e".equals(org.getParentId()) ){
				level1List.add(org);
				level1Map.put(org.getOrganizationId() , org);
			}
		}
		//查询所有第二级别组织
		List<SysOrganization> level2List = new ArrayList<SysOrganization>();
		Map<String, SysOrganization> level2Map = new HashMap<String, SysOrganization>();
		for (SysOrganization org : all) {
			if( level1Map.get(org.getParentId()) != null ) {
				level2List.add(org);
				level2Map.put(org.getOrganizationId() , org);
			}
		}
		//
		//查询当前组织的二级组织
		SysOrganization curOrg = allMap.get(OrgId);
		SysOrganization target;		
		target = recursiveAncestorOrg(allMap , level2List , curOrg );
		if(target==null){
			target=curOrg;
		}
		return target;
	}
	
	
	
	/**
	 * 是否Root之下的一级组织架构
	 */
	public String isLevelOne(String id){
		SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, id);//子组织
		if("80df8fa55ca4048ac2314dab1a52d75e".equals(org.getParentId()) )
			return "1";
		else
			return "0";
	}
	
	/**
	 * 根据组织id，获取组织当前的tree的level
	 */
	public Long getOrgCurLevel(String id){
		Long level = null;
		Session session = enterpriseService.getSessions();
		String sql = "";
		sql += " select (level - 1) as lv  ";
		sql += " from sys_organization t ";
		sql += " where t.organization_id = '" +id+ "' ";
		sql += " start with t.organization_id = '80df8fa55ca4048ac2314dab1a52d75e' ";
		sql += " connect by t.parent_id = prior t.organization_id ";
		List list = session.createSQLQuery(sql).list();
		if( ! list.isEmpty() ){
			BigDecimal bigDecimal = (BigDecimal) list.get(0);
			level = bigDecimal.longValue();
		}
		return level;
	}
	
	 /*删除组织信息，以便同步用户*/
    public void  dropOrg(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_ORGANIZATION t where  t.IS_FROM_WEB='1'");
		query.executeUpdate();
    	
    }
    
    /*备份组织信息，以便同步用户*/
    public void  dropOrgBackup(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_ORGANIZATION_BACKUP");
		query.executeUpdate();
		Query query1 = enterpriseService.getSessions().createSQLQuery("insert into SYS_ORGANIZATION_BACKUP(ORGANIZATION_ID,ORG_NAME,ORG_ORDER,PARENT_ID,DESCRIPTION,CREATER_ID,CREATE_TIME,ORG_LEVEL,ORG_TYPE,OU_GUID,OU_ID,ORG_STATE,OU_FULLNAME,LAST_UPDATE_DATE,IS_FROM_WEB) select * from sys_organization");
		query1.executeUpdate();
    }
    
    /*组织临时信息，以便同步用户*/
    public void  dropOrgTemp(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_ORGANIZATION_TEMP");
		query.executeUpdate();
    }
    
    /*同步组织时，同步id字段*/
    public void  updateOrgId(){
    	Query query = enterpriseService.getSessions().createSQLQuery("update SYS_ORGANIZATION t set t.organization_id=t.ou_guid where t.organization_id!='16052415553777700001'");
		query.executeUpdate();
    }
    
    /*将临时表里数据同步到正式表*/
    public void  addOrgByTemp(){
    	Query query = enterpriseService.getSessions().createSQLQuery("insert into SYS_ORGANIZATION(ORGANIZATION_ID,ORG_NAME,ORG_ORDER,PARENT_ID,DESCRIPTION,CREATER_ID,CREATE_TIME,ORG_LEVEL,ORG_TYPE,OU_GUID,OU_ID,ORG_STATE,OU_FULLNAME,LAST_UPDATE_DATE,IS_FROM_WEB) "
         +" select * from SYS_ORGANIZATION_TEMP t start with organization_id='80df8fa55ca4048ac2314dab1a52d75e' connect by prior organization_id=parent_id");
		query.executeUpdate();
    }
	
    /*添加临时组织信息*/
    public void addOrganizationTemp(SysOrganizationTemp organization) throws Exception{
    	 if (organization != null) {
             SysOrganization parentOrg = (SysOrganization) enterpriseService.getById(SysOrganization.class,organization.getParentId());
             if (parentOrg != null) {
                 organization.setOrgLevel(parentOrg.getOrgLevel()!=null ? parentOrg.getOrgLevel()+1 : 1);
             }
         }
 		enterpriseService.save(organization);
    }
    
    /*临时同步组织时，同步id字段*/
    public void  updateOrgIdTemp(){
    	Query query = enterpriseService.getSessions().createSQLQuery("update SYS_ORGANIZATION_TEMP t set t.organization_id=t.ou_guid where t.organization_id!='16052415553777700001'");
		query.executeUpdate();
    }

	@Override
	public List<SysOrganization> getOrgAndSonById(String id) {
		List<SysOrganization> result = new ArrayList<SysOrganization>();
		if(StringUtils.isEmpty(id)){
			return null;
		}
		SysOrganization org = (SysOrganization) enterpriseService.getById(SysOrganization.class,id);
		if(org != null){
			//添加组织
			result.add(org);
			//查询子组织
			QueryBuilder query = new QueryBuilder(SysOrganization.class);
			query.where("parentId", org.getOrganizationId(), QueryAction.EQUAL);
			List<SysOrganization> list = (List<SysOrganization>) enterpriseService.query(query, 0);
			if(list != null && list.size() > 0){
				for (SysOrganization sysOrganization : list) {
					//递归添加子组织
					result.addAll(getOrgAndSonById(sysOrganization.getOrganizationId()));
				}
			}
		}
		return result;
	}
}
