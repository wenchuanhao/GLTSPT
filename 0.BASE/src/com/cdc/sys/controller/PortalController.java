package com.cdc.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysModule;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.sys.service.IDefaultLoginService;
import com.cdc.sys.service.IMatterQueryService;
import com.cdc.sys.service.IMyCollectService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.DataCache;
import com.trustel.common.ItemPage;

/**
 * 系统功能入口
 * @author weif
 *
 */
@Controller
@RequestMapping(value = "/core/portal/*")
public class PortalController extends CommonController {
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IMyCollectService collectService;
	@Autowired
	private IMatterQueryService matterQueryService;
	@Autowired
	private ISysOrganizationService sysOrganizationService;
	@Autowired
	private ISysPrivilegesService privilegesService;
	@Autowired
	private IDefaultLoginService defaultLoginService;
	

	/**
	 * 
	 * @author 
	 * @date 2016-6-2 下午4:13:06
	 * @param request	void
	 */
	private void common(HttpServletRequest request){
        SysUser sysUser = getVisitor(request);
        request.setAttribute("sysUser", sysUser);
        
		List<SysModule> oneMenu = DataCache.getInstance().getOneMenu();
		Map<String, List<SysModule>> twoMenus = DataCache.getInstance().getTwoMenu();
		Map<String, List<SysModule>> threeMenus = DataCache.getInstance().getThreeMenu();
		Map<String, List<SysModule>> fourMenus = DataCache.getInstance().getFourMenu();
		
		List<String> privilges = getVisitor(request).getPrivileges();
		List<SysModule> oneList = new ArrayList<SysModule>();
		List<SysModule> twoList = null;
		List<SysModule> threeList = null;
		List<SysModule> fourList = null;
		//1
		for (SysModule oneM : oneMenu){
			if (privilges.contains(oneM.getModuleCode()) && "1".equals(oneM.getIsMenu())) { //拥有的菜单
					twoList = new ArrayList<SysModule>();
					//2
					for (SysModule twoM : twoMenus.get(oneM.getModuleCode())) {
						if (privilges.contains(twoM.getModuleCode())) {
							threeList = new ArrayList<SysModule>();
							//3
							for (SysModule threeM : threeMenus.get(twoM.getModuleCode())) {
								if (privilges.contains(threeM.getModuleCode())) {
									fourList = new ArrayList<SysModule>();
									List<SysModule> fourMenu = fourMenus.get(threeM.getModuleCode());
									for (SysModule fourM : fourMenu){
										if (privilges.contains(fourM.getModuleCode())){
											fourList.add(fourM);
										}
									}
									threeM.getNextList().clear();
									threeM.getNextList().addAll(fourList);
									threeList.add(threeM);
								}
							}
							twoM.getNextList().clear();
							twoM.getNextList().addAll(threeList);	
							twoList.add(twoM);
						} // end if
					}
					oneM.getNextList().clear();
					oneM.getNextList().addAll(twoList);
					oneList.add(oneM);
			}// end if
		}
		request.setAttribute("menuList", oneList);
		
		
		oneList = new ArrayList<SysModule>();
		for (SysModule oneM : oneMenu){
			if (privilges.contains(oneM.getModuleCode()) && "0".equals(oneM.getIsMenu())) { //拥有的菜单
					twoList = new ArrayList<SysModule>();
					//2
					for (SysModule twoM : twoMenus.get(oneM.getModuleCode())) {
						if (privilges.contains(twoM.getModuleCode())) {
							threeList = new ArrayList<SysModule>();
							//3
							for (SysModule threeM : threeMenus.get(twoM.getModuleCode())) {
								if (privilges.contains(threeM.getModuleCode())) {
									fourList = new ArrayList<SysModule>();
									List<SysModule> fourMenu = fourMenus.get(threeM.getModuleCode());
									for (SysModule fourM : fourMenu){
										if (privilges.contains(fourM.getModuleCode())){
											fourList.add(fourM);
										}
									}
									threeM.getNextList().clear();
									threeM.getNextList().addAll(fourList);
									threeList.add(threeM);
								}
							}
							twoM.getNextList().clear();
							twoM.getNextList().addAll(threeList);	
							twoList.add(twoM);
						} // end if
					}
					oneM.getNextList().clear();
					oneM.getNextList().addAll(twoList);
					oneList.add(oneM);
			}// end if
		}
		request.setAttribute("menuList0", oneList);
	}
	
	/**
	 * portal 部分
	 * @return
	 */
	@RequestMapping(value = "portal", method = RequestMethod.GET)
	public String portal(HttpServletRequest request) {

		String url = request.getParameter("url");
		if(!StringUtils.isNotEmpty(url)){
			url = "core/portal/main";
		}
		request.setAttribute("url", url);
		common(request);
		
		return "core/main/portal";
	}
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main8(HttpServletRequest request) {
		
		common(request);
		
		List<Object[]> list = collectService.getAdvertisement();
		
		request.setAttribute("list", list);
		
		return "core/main/main";
	}
	
	@RequestMapping(value = "ietip", method = {RequestMethod.GET,RequestMethod.POST})
	public String ieTip(HttpServletRequest request) {
		String operateType = request.getParameter("operateType");
		String isIeTip = request.getParameter("isIeTip");
		if("1".equals(operateType)){
			SysUser user=getVisitor(request);
			user.setIsIeTip(isIeTip);
			try {
				sysUserService.modifySysUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "core/main/ie8tip";
	}

	/**
	 * header 部分
	 * 
	 * @return
	 */
	@RequestMapping(value = "header", method = RequestMethod.GET)
	public String header(HttpServletRequest request) {
		
		List<SysModule> oneMenu = DataCache.getInstance().getOneMenu();
		Map<String, List<SysModule>> twoMenus = DataCache.getInstance().getTwoMenu();
		Map<String, List<SysModule>> threeMenus = DataCache.getInstance().getThreeMenu();
		Map<String, List<SysModule>> fourMenus = DataCache.getInstance().getFourMenu();
		
		List<String> privilges = getVisitor(request).getPrivileges();
		List<SysModule> menuList = new ArrayList<SysModule>();
		//1
		for (SysModule oneM : oneMenu){
			if (privilges.contains(oneM.getModuleCode())) { //拥有的菜单
					List<SysModule> twoMenu = twoMenus.get(oneM.getModuleCode());
					//2
					for (SysModule twoM : twoMenu) {
						if (privilges.contains(twoM.getModuleCode())) {
							List<SysModule> threeMenu = threeMenus.get(twoM.getModuleCode());
							//3
							for (SysModule threeM : threeMenu) {
								if (privilges.contains(threeM.getModuleCode())) {
									List<SysModule> fourMenu = fourMenus.get(threeM.getModuleCode());
									for (SysModule fourM : fourMenu){
										if (privilges.contains(fourM.getModuleCode())){
											threeM.getNextList().add(fourM);
										}
									}
									twoM.getNextList().add(threeM);
								}
							}
							oneM.getNextList().add(twoM);
						}// end if
					}
					menuList.add(oneM);
			}// end if
		}
		request.setAttribute("menuList", menuList);

        request.setAttribute("myWorkplace", privilges.contains("MyWorkSpaceController"));
        SysUser sysUser = getVisitor(request);
        request.setAttribute("sysUser", sysUser);
		return "core/main/header";
}

	/**
	 * footer 部分
	 * @return
	 */
	@RequestMapping(value = "footer", method = RequestMethod.GET)
	public String footer(HttpServletRequest request) {
		System.out.println();
		
		return "core/main/footer";
	}
	
	public static void main(String[] args) {
		System.out.println(getDate(new Date()));
	}
	
	//获取周几的方法
	public static String getDate(Date dt){
		String[] days={"星期日","星期一","星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal=Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
		 w = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//:ss
		String time = formatter.format(cal.getTime());
		
		return time+" "+days[w];
		}

	/**
	 * sysM 部分
	 * @return
	 */
/*	@RequestMapping(value = "sysM", method = RequestMethod.GET)
	public String sysM(HttpServletRequest request) {
		String moduleCode = request.getParameter("moduleCode");
		request.setAttribute("moduleCode", moduleCode);
		return "core/main/sysM";
	}*/
	
	/**
	 * sysM_menu 部分
	 * @return
	 */
/*	@RequestMapping(value = "sysM_menu", method = RequestMethod.GET)
	public String sysMmenu(HttpServletRequest request) {
		String moduleCode = request.getParameter("moduleCode");
		setMenus(request, moduleCode);
		return "core/main/sysM_menu";
	}*/
	

	/*private void setMenus(HttpServletRequest request, String module) {
		Map<String, List<SysModule>> twoMenus = DataCache.getInstance().getTwoMenu();
		Map<String, List<SysModule>> threeMenus = DataCache.getInstance().getThreeMenu();
		Map<String, List<SysModule>> fourMenus = DataCache.getInstance().getFourMenu();
		List<SysModule> twoMenu = twoMenus.get(module);
		List<String> privilges = getVisitor(request).getPrivileges();
		List<MenuForm> menuForms = new ArrayList<MenuForm>();
		for (SysModule twoM : twoMenu) {
			if (privilges.contains(twoM.getModuleCode())) {
				MenuForm menuForm = new MenuForm();
				List<SysModule> threeMenu = threeMenus.get(twoM.getModuleCode());
				menuForm.setModule(twoM);
				List<MenuNode> menuNodes = new ArrayList<MenuNode>();
				for (SysModule threeM : threeMenu) {
					if (privilges.contains(threeM.getModuleCode())) {
						List<SysModule> fourMenuTemp = fourMenus.get(threeM.getModuleCode());
						List<SysModule> fourMenu = new ArrayList<SysModule>();
						MenuNode threeMenuNode = new MenuNode();
						for (SysModule fourM : fourMenuTemp)
							if (privilges.contains(fourM.getModuleCode()))
								fourMenu.add(fourM);
						threeMenuNode.setNextNode(fourMenu);
						threeMenuNode.setNowMenu(threeM);
						menuNodes.add(threeMenuNode);

					}
					menuForm.setMenuNodes(menuNodes);
				}
				menuForms.add(menuForm);
			}
		}
		request.setAttribute("menuForms", menuForms);
	}*/


	/**
	 * 
	 * 底部
	 * 
	 * @return
	 */
	@RequestMapping(value = "bottom", method = RequestMethod.GET)
	public String bottom(HttpServletRequest request) {
		return "core/main/bottom";
	}
		
}
