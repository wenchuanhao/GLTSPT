package com.cdc.system.core.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.sys.entity.SysModule;
import model.sys.entity.SysUser;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.cdc.sys.dict.model.SysParameter;

/**
 * 
 * @Description: 数据缓存的类
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:30:40
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class DataCache {

	private static DataCache INSTANCE;
	private String isNeedRedis;
	private List<SysModule> oneMenu = new ArrayList<SysModule>();
	private Map<String, List<SysModule>> twoMenu = new HashMap<String, List<SysModule>>();
	private Map<String, List<SysModule>> threeMenu = new HashMap<String, List<SysModule>>();
	private Map<String, List<SysModule>> fourMenu = new HashMap<String, List<SysModule>>();
	private Map<String, List<String>> sysPrivilges = new HashMap<String, List<String>>();
	private List<SysParameter> sysParameters = new ArrayList<SysParameter>();

	private Map<String, List<SysParameter>> parameters = new HashMap<String, List<SysParameter>>();

    private RedisSessionManager redisSessionManager;

	private DataCache() {
	}

	public static DataCache getInstance(){
		if (INSTANCE == null) {
            INSTANCE = new DataCache();
        }
		Properties properties = null;
		String content = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("system.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = (String) properties.get("isNeedRedis");
		INSTANCE.isNeedRedis=content;
		return INSTANCE;
	}

    public static DataCache getInstance(RedisSessionManager redisSessionManager)  {
        if (INSTANCE == null) {
            INSTANCE = new DataCache();
            INSTANCE.redisSessionManager = redisSessionManager;
        }
        Properties properties = null;
		String content = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("system.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = (String) properties.get("isNeedRedis");
		INSTANCE.isNeedRedis=content;
        return INSTANCE;
    }

	public List<SysModule> getOneMenu() {
		//return oneMenu;
		if(null!=isNeedRedis&&isNeedRedis.equals("1")){
			return redisSessionManager.getList("oneMenu");
		}else{
			return oneMenu;
		}
        
	}

	public void setOneMenu(List<SysModule> oneMenu) {
		//this.oneMenu = oneMenu;
       // redisSessionManager.putList("oneMenu",oneMenu);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	redisSessionManager.putList("oneMenu",oneMenu);
		}else{
			this.oneMenu = oneMenu;
		}
	}

	public Map<String, List<SysModule>> getTwoMenu() {
		//return twoMenu;
       // return redisSessionManager.getMap("twoMenu");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	 return redisSessionManager.getMap("twoMenu");
		}else{
			return twoMenu;
		}
	}

	public void setTwoMenu(Map<String, List<SysModule>> twoMenu) {
		//this.twoMenu = twoMenu;
        //redisSessionManager.putMap("twoMenu",twoMenu);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	redisSessionManager.putMap("twoMenu",twoMenu);
		}else{
			this.twoMenu = twoMenu;
		}
	}

	public Map<String, List<SysModule>> getThreeMenu() {
		//return threeMenu;
        //return redisSessionManager.getMap("threeMenu");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getMap("threeMenu");
		}else{
			return threeMenu;
		}
	}

	public void setThreeMenu(Map<String, List<SysModule>> threeMenu) {
		//this.threeMenu = threeMenu;
       // redisSessionManager.putMap("threeMenu",threeMenu);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	 redisSessionManager.putMap("threeMenu",threeMenu);
		}else{
			this.threeMenu = threeMenu;
		}
	}

	public Map<String, List<SysModule>> getFourMenu() {
		//return fourMenu;
       // return redisSessionManager.getMap("fourMenu");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getMap("fourMenu");
		}else{
			return fourMenu;
		}
	}

	public void setFourMenu(Map<String, List<SysModule>> fourMenu) {
		//this.fourMenu = fourMenu;
        //redisSessionManager.putMap("fourMenu",fourMenu);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	redisSessionManager.putMap("fourMenu",fourMenu);
		}else{
			this.fourMenu = fourMenu;
		}
	}

	public List<SysParameter> getSysParameters() {
		//return sysParameters;
        //return redisSessionManager.getList("sysParameters");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getList("sysParameters");
		}else{
			return sysParameters;
		}

	}

	public void setSysParameters(List<SysParameter> sysParameters) {
		//this.sysParameters = sysParameters;
       // redisSessionManager.putList("sysParameters",sysParameters);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	 redisSessionManager.putList("sysParameters",sysParameters);
		}else{
			this.sysParameters = sysParameters;
		}
	}

	public Map<String, List<SysParameter>> getParameters() {
		//return parameters;
       // return redisSessionManager.getMap("parameters");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getMap("parameters");
		}else{
			return parameters;
		}
	}

	public void setParameters(Map<String, List<SysParameter>> parameters) {
		//this.parameters = parameters;
        //redisSessionManager.putMap("parameters",parameters);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	redisSessionManager.putMap("parameters",parameters);
		}else{
			this.parameters = parameters;
		}
	}

	public Map<String, List<String>> getSysPrivilges() {
		//return sysPrivilges;
        // return redisSessionManager.getMap("sysPrivilges");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getMap("sysPrivilges");
		}else{
			return sysPrivilges;
		}
	}

	public void setSysPrivilges(Map<String, List<String>> sysPrivilges) {
		//this.sysPrivilges = sysPrivilges;
        //redisSessionManager.putMap("sysPrivilges",sysPrivilges);
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	redisSessionManager.putMap("sysPrivilges",sysPrivilges);
		}else{
			this.sysPrivilges = sysPrivilges;
		}
	}

	public void setSuperUsers(List<SysUser> superUsers) {
		 //redisSessionManager.putList("superUsers", superUsers);
		 if(null!=isNeedRedis&&isNeedRedis.equals("1")){
			 redisSessionManager.putList("superUsers", superUsers);
		 }else{
			
		 }
	}
	public List<SysUser> getSuperUsers() {
        //return redisSessionManager.getList("superUsers");
        if(null!=isNeedRedis&&isNeedRedis.equals("1")){
        	return redisSessionManager.getList("superUsers");
		 }else{
			return null;
		 }
	}

	public String getIsNeedRedis() {
		return isNeedRedis;
	}

	public void setIsNeedRedis(String isNeedRedis) {
		this.isNeedRedis = isNeedRedis;
	}	
	
}
