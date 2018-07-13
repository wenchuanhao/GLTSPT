package com.cdc.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.sys.entity.SysModule;
import model.sys.entity.SysRolePrivilges;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.UserSetingMapping;
import model.sys.entity.ViewWordAll;
import model.sys.entity.ViewWordAllRead;

import org.hibernate.SQLQuery;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.BackLogForm;
import com.cdc.sys.form.QuickForm;
import com.trustel.common.ItemPage;


//我的工作台的实现
public class MatterQueryService implements IMatterQueryService {
	
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService service) {
		this.enterpriseService = service;
	}
	 //按条件查询我的所有的待办事项和待阅事项
	public ItemPage queryMatterList(int flag,BackLogForm backlogform,
			String backLogIdsStr,String actorId,String type) {
		if( "".equals(backLogIdsStr))
		{
			backLogIdsStr="-1";
		}
		Class<?>[] pojos = null;
		String where="";
		if(type.equals("1")){//按条件获取待办工单
			pojos=new Class<?>[]{ViewWordAll.class};
			where="a.id in("+backLogIdsStr+")";
			where="a.actorId ='"+actorId+"'";
		}else{//按条件获取待阅工单
			pojos=new Class<?>[]{ViewWordAllRead.class};
			where="a.id in("+backLogIdsStr+")";
			where="a.actorId ='"+actorId+"'";
		}
		BackLogForm from=new BackLogForm();
		QueryBuilder builder =new QueryBuilder(pojos);
		builder.where(where);
	    if(flag != 5){
	    	 builder.where("a.flag",String.valueOf(flag),QueryAction.EQUAL);
	    }
	   
	   // builder.where("a.flowInstanceId=b.instanceId");
	   // builder.where("d.workId",backLogIdsStr);  ,FlowInstance.class,FlowLog.class
	    builder.orderBy("a.createDate",false);
        ItemPage itemPage = enterpriseService.query(builder,from);
	     return itemPage;
	}
	
	//根据当前用户找到当前角色Id然后找到角色权限
	public List queryUserRoleList(String userId) {
        StringBuilder querySql = new StringBuilder();
        querySql.append("select t4.MODULE_NAME,t4.MODULE_CODE,t4.PARENT_CODE,t3.id from (select distinct t.MODULE_NAME,t.MODULE_CODE,t.PARENT_CODE,t.SEQ from sys_module t,sys_role_user t1,sys_role_privilges t2 ");
        querySql.append("where 1=1 ");
        querySql.append("and t2.module_code = t.module_code ");
        querySql.append("and t1.role_id = t2.role_id ");
        querySql.append("and t1.user_id = ?) t4 left join user_seting_maping t3 on t4.MODULE_CODE = t3.MENU_CODE and t3.USER_ID=? order by t4.SEQ asc");
        SQLQuery sqlQuery = enterpriseService.getSessions().createSQLQuery(querySql.toString());
        sqlQuery.setString(0, userId);
        sqlQuery.setString(1, userId);
        //System.out.println(querySql);
        return sqlQuery.list();
	}
	//获取用户设置的快捷菜单
	public ItemPage queryUserSelectMenu(String userId) {
		Class<?>[] pojos = new Class<?>[]{UserSetingMapping.class,SysModule.class,SysRolePrivilges.class,SysRoleUser.class};
		QueryBuilder builder =new QueryBuilder(pojos);
		builder.select("distinct(b)");
		builder.where("a.userId = d.userId");
		builder.where("d.roleId = c.roleId");
		builder.where("c.moduleCode =b.moduleCode");
		builder.where("a.menuCode =c.moduleCode");
		builder.where("a.userId",userId);
		
		ItemPage itemPage = enterpriseService.query(builder,new QuickForm());
		return itemPage;
	}
	//保存选中的模块数据
	public void saveUserSelectMenu(UserSetingMapping usm) {
		enterpriseService.save(usm);
		
		
	}
	
	public int flushUserMenuSetting(String[] menuCodes,String userId){
		int i=0;
		UserSetingMapping usm;
		QueryBuilder builder =new QueryBuilder("UserSetingMapping");
		builder.where("userId",userId);
		enterpriseService.delete(builder);
		if(menuCodes != null){
			for(String menuCode : menuCodes){
				usm=new UserSetingMapping();
				usm.setUserId(userId);
				usm.setMenuCode(menuCode);
				usm.setCreatetime(new Date());
				enterpriseService.save(usm);
				i++;
			}
		}
		
		return i;
	}
	//获取待办待阅的条数
	public Map<String, ItemPage> queryMatterCount(int flag,
			BackLogForm backlogform, String backLogIdsStr, String actorId,
			String type) {
		if("".equals(backLogIdsStr)){
			backLogIdsStr="-1";
		}
		Class<?>[] pojos = null;
		QueryBuilder builder =null;
		String where="";
		Map<String,ItemPage> map=new HashMap<String,ItemPage>();
		//待办事项
		pojos=new Class<?>[]{ViewWordAll.class};
		where="a.id =("+backLogIdsStr+")";
		where="a.actorId ='"+actorId+"'";
		builder=new QueryBuilder(pojos);
		builder.where(where);
	    if(flag != 5){
	      builder.where("a.flag",String.valueOf(flag),QueryAction.EQUAL);
	    }
	    builder.orderBy("a.createDate",false);
	    //System.out.println(where.toString()+"-------");
	    ItemPage itemPage = enterpriseService.query(builder,backlogform);
	    /**
	    List list = enterpriseService.query(builder,0);
	    Set set = new HashSet();
		set.addAll(itemPage.getItems());
		List newlist = new ArrayList();
		newlist.addAll(set);
		Set set2 = new HashSet();
		set2.addAll(list);
		List newlist2 = new ArrayList();
		newlist2.addAll(set2);
		itemPage = new ItemPage(newlist,newlist2.size(),backlogform.getPageIndex(),backlogform.getPageSize());
		**/
	    map.put("1",itemPage);
	    //待阅事项
		pojos=new Class<?>[]{ViewWordAllRead.class};
		builder=new QueryBuilder(pojos);
		builder.where(where);
	    if(flag != 5){
	    builder.where("a.flag",String.valueOf(flag),QueryAction.EQUAL);
	    }
	    builder.orderBy("a.createDate",false);
	    itemPage = enterpriseService.query(builder,backlogform);
	    
	    map.put("2", itemPage);
	    return map;
	}
}
