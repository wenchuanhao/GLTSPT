package com.cdc.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysModule;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.util.BatchUserUtil;
import org.trustel.util.DateUtils;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.form.SysModuleForm;
import com.cdc.system.core.startup.DefaultLoadService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;
import com.trustel.common.MD5Helper;

public class SysModuleService implements ISysModuleService {
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public ItemPage querySysModule(SysModuleForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(" SysModule s,SysModule p");
		query.where("s.parentCode = p.moduleCode");
		if (null != form) {
			if (null != form.getModuleCode() && !form.getModuleCode().equals(""))
				query.where("s.moduleCode", form.getModuleCode(), QueryAction.LIKE);
			if (null != form.getModuleName() && !form.getModuleName().equals(""))
				query.where("s.moduleName", form.getModuleName(), QueryAction.LIKE);
		}
		query.orderBy("s.parentCode");
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}

	public void addSysModule(SysModule sysModule) throws Exception {
		enterpriseService.save(sysModule);

	}

	public void modifySysModule(SysModule sysModule) throws Exception {
		enterpriseService.updateObject(sysModule);

	}

	public SysModule getSysModuleById(String moduleId) throws Exception {
		return (SysModule) enterpriseService.getById(SysModule.class, moduleId);
	}

	public void deleteSysModule(String moduleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysModule.class);
		query.where("moduleId", moduleId);
		enterpriseService.delete(query);
	}

	public List<SysModule> queryAll() throws Exception {
		QueryBuilder query = new QueryBuilder(SysModule.class);
		query.orderBy("seq");
		@SuppressWarnings("unchecked")
		List<SysModule> modules = ((List<SysModule>) enterpriseService.query(query, 0));
		return treeList(modules);
	}
	
	
	public void loadModule() throws Exception{
		DefaultLoadService defaultLoadService = (DefaultLoadService) SpringHelper.getBean("defaultLoadService");
		defaultLoadService.loadModule();
	}

	/**
	 * 组装树形
	 * @author WEIFEI
	 * @date 2016-4-21 下午2:39:30
	 * @param list
	 * @return
	 * @throws Exception	List<SysModule>
	 */
	private List<SysModule> treeList(List<SysModule> list) throws Exception{
		Map<String, SysModule> map = new HashMap<String, SysModule>();
		for (SysModule vo : list) {
			//if (vo.getIsMenu().equals("1")) {
				map.put(vo.getModuleCode(), vo);
			//}
		}
		List<SysModule> newList = new ArrayList<SysModule>();
		for (SysModule vo : list) {
			if (vo.getMenuLevel() == 1) {//vo.getIsMenu().equals("1") && 
				SysModule mapSM = map.get(vo.getModuleCode());
				newList.add(mapSM);
			}
			if (map.containsKey(vo.getParentCode())&& vo.getMenuLevel() > 1) {// && vo.getIsMenu().equals("1") 
				//当前目录的上一级目录
				SysModule parentSM = map.get(vo.getParentCode());
				//上级目录添加子目录
				parentSM.getNextList().add(vo);
			}
		}
		return newList;
	}
	
	public SysModule queryModuleByName(String moduleCode) throws Exception {
		QueryBuilder query = new QueryBuilder(SysModule.class);
		query.where("moduleCode", moduleCode, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysModule) list.get(0);
		return null;
	}
	
	/**
	 * * 根据用户账号和名字查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	@SuppressWarnings("unchecked")
	public SysModule getModuleByid(String account) throws Exception {
		QueryBuilder query = new QueryBuilder(SysModule.class);
		if(null!=account&& !account.equals("")){
			query.where("moduleCode",account);
		}else{
			query.where("moduleCode",1);
		}
		List <SysModule> list= (List<SysModule>) enterpriseService.query(query, 0);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * excel数据验证
	 * 
	 * @param visitor
	 * @param file
	 * @param request
	 * @param specificationId
	 * @return
	 * @throws RuntimeException
	 */

	public List<DicFailImportObject> validateWare(String file,
			HttpServletRequest request)
			throws RuntimeException {
		String userResult[][] = null;//用户信息

		DicFailImportObject dicFailImportObject = null;//记录一条错误的信息
		List<DicFailImportObject> listobject=null;//所有错误
		try {
			userResult = BatchUserUtil.getData(new File(file), 2, 0);
			listobject=new ArrayList<DicFailImportObject>();
			if(userResult==null || userResult.length==0){
//				DicFailImportObject dicFailImportObject0 = new DicFailImportObject();
//				listobject = new ArrayList<DicFailImportObject>();
				dicFailImportObject.setFailReason("至少要填入一条数据!");
				listobject.add(dicFailImportObject);
				return listobject;
			}
			//循环用户信息
			for (int i = 0; i < userResult.length; i++) {//除检查重复之外的其他数据检查机制
				
				if (null == userResult[i][0] ||  "".equals(userResult[i][0])) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单编码为空!");
					listobject.add(dicFailImportObject);
				}else{
					SysModule Model = getModuleByid(userResult[i][0]);
					if(Model != null){
						dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 3);
						dicFailImportObject.setFailReason("菜单编码已存在!");
						listobject.add(dicFailImportObject);
					} else {
						int count=0;
						for(int j=0;j<userResult.length;j++){
							if(userResult[i][0].equals(userResult[j][0])){
								count+=1;
							}
						}
						if(count>=2){
							dicFailImportObject = new DicFailImportObject();
							dicFailImportObject.setSheetIndex(0);
							dicFailImportObject.setRow(i + 3);
							dicFailImportObject.setFailReason(userResult[i][0]+"菜单编码在此份excle中重复存在"+count+"个!");
							listobject.add(dicFailImportObject);
						}
					}
				}
				
				if ("".equals(userResult[i][1]) || null == userResult[i][1]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单名称为空!");
					listobject.add(dicFailImportObject);
				} 
				
				if ("".equals(userResult[i][2]) || null == userResult[i][2]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("是否菜单为空!");
					listobject.add(dicFailImportObject);
				} 
				if ("".equals(userResult[i][3]) || null == userResult[i][3]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单等级为空!");
					listobject.add(dicFailImportObject);
				} 
				if ("".equals(userResult[i][3]) || null == userResult[i][3]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单等级为空!");
					listobject.add(dicFailImportObject);
				} 
				if ("".equals(userResult[i][4]) || null == userResult[i][4]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单父级编码为空!");
					listobject.add(dicFailImportObject);
				} 
				if ("".equals(userResult[i][6]) || null == userResult[i][6]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("菜单显示顺序为空!");
					listobject.add(dicFailImportObject);
				} 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listobject;
	}
	
	/**
	 * 批量添加菜单信息
	 * @param visitor
	 * @param file
	 * @param request
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public String saveModule(SysUser visitor,String file,HttpServletRequest request) throws Exception{
		boolean flag=false;
		String[][] result = null;
		SysModule module=null;
		String ids = "";

		try {
			result = BatchUserUtil.getData(new File(file), 2, 0);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}// 忽略前两行（标题行）
		for (int i = 0; i < result.length; i++) {
			module = new SysModule();
			module.setModuleCode(result[i][0]);
			module.setModuleName(result[i][1]);
			
			if (result[i][2].equals("是".trim())) {
				module.setIsMenu("1");
			}if (result[i][2].equals("否".trim())) {
				module.setIsMenu("0");
			}if (result[i][3].equals("0级".trim())) {
				module.setSeq(0);
			}if (result[i][3].equals("1级".trim())) {
				module.setSeq(1);
			}
			if (result[i][3].equals("2级".trim())) {
				module.setSeq(2);
			}
			if (result[i][3].equals("3级".trim())) {
				module.setSeq(3);
			}
			if (result[i][3].equals("4级".trim())) {
				module.setSeq(4);
			}
			module.setCreaterId(visitor.getUserId());
			module.setParentCode(result[i][4]);
			module.setUrl(result[i][5]);
			module.setDescription(result[i][7]);
    		module.setCreateTime((DateUtils.get(new Date(), "GM+8")));
    		
			try {
				ids= saveSysModule(module);
			} catch (Exception e) {
              e.printStackTrace();
			}
			
		}

		return ids;
	}
	
	/**
	 * 添加菜单__返回主键
	 */
	public String saveSysModule(SysModule sysModule) throws Exception {
		return (String) enterpriseService.save(sysModule);
	}
	
	@Override
	public List<DicFailImportObject> saveData(SysUser visitor, String file,
			HttpServletRequest request) throws RuntimeException {
		List<DicFailImportObject> listObject=null;
		DicFailImportObject object=new DicFailImportObject();
		try {
			listObject=this.validateWare(file, request);
			if(listObject.size()==0){
				String f=this.saveModule(visitor,file, request);
				if(f==null || f.equals("")){
					object.setFailReason("保存失败,请检查录入数据!");
					object.setSheetIndex(-1);
					listObject.add(object);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObject;
	}

}
