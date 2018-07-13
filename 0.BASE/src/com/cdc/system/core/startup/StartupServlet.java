package com.cdc.system.core.startup;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cdc.system.core.cache.DataCache;
import com.cdc.system.core.cache.RedisSessionManager;

/**
 * 
 * @Description: 加载系统启动需要预先加载的服务和数据
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:31:12
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class StartupServlet extends HttpServlet { 
	private Log logger = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);
		logger.info("========开始加载系统预加载数据==========");
		try {
			String webAppRootValue = servletConfig.getServletContext().getRealPath("/");
			System.setProperty("webAppRootKey", webAppRootValue);
			// 得到容器环境
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
			onLoadData(ctx,servletConfig.getServletContext());
		} catch (Exception e) {
			logger.error("========加载系统预加载数据失败，系统启动失败==========");
			logger.error(this, e);
		}
		logger.info("========加载系统预加载数据完成==========");
	}

	/**
	 * 加载数据
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected void onLoadData(WebApplicationContext context,ServletContext application) throws Exception {
		try {
			DefaultLoadService defaultLoadService = (DefaultLoadService) context.getBean("defaultLoadService");
			
			Properties properties;
			String content = null;
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("system.properties"));
			content = (String) properties.get("isNeedRedis");
			RedisSessionManager redisSessionManager = null;
			String preLoadStr=null;
			if(content.trim().equals("1")){
				redisSessionManager = (RedisSessionManager)context.getBean("redisSessionManager");
				DataCache.getInstance(redisSessionManager);
				preLoadStr = redisSessionManager.getString("loading");
			}
			
            //标识状态为装载中,防止同时进行
            if ("0".equals(preLoadStr) || preLoadStr == null){
                //标识为装载中
            	if(content.trim().equals("1")){
            		 redisSessionManager.putString("loading","1");
            	}
                defaultLoadService.loadParam();
                defaultLoadService.loadModule();
                defaultLoadService.loadRolePrivilges();
                //标识状态为装载完成
                if(content.trim().equals("1")){
                	redisSessionManager.putString("loading","0");
             	}
                
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
