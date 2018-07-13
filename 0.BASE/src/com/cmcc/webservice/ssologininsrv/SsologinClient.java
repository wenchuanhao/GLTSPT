package com.cmcc.webservice.ssologininsrv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrv;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrvRequest;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrvResponse;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

/**
 * 描述: 单点登录接口
 */
public class SsologinClient extends JaxbParseBase{
	
	private SBIAPSSOLoginInSrv_Service sBIAPSSOLoginInSrv_Service;
	
	
	public SBIAPSSOLoginInSrv_Service getsBIAPSSOLoginInSrv_Service() {
		return sBIAPSSOLoginInSrv_Service;
	}

	public void setsBIAPSSOLoginInSrv_Service(
			SBIAPSSOLoginInSrv_Service sBIAPSSOLoginInSrv_Service) {
		this.sBIAPSSOLoginInSrv_Service = sBIAPSSOLoginInSrv_Service;
	}


	private SBIAPSSOLoginInSrv sBIAPSSOLoginInSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public SsologinClient() {
		super();
	}
	
	public void init(){
		sBIAPSSOLoginInSrv =sBIAPSSOLoginInSrv_Service.getSBIAPSSOLoginInSrvPort(); 
    }


	@SuppressWarnings("unchecked")
    public SBIAPSSOLoginInSrvResponse Ssologin(SBIAPSSOLoginInSrvRequest sBIAPSSOLoginInSrvRequest){
		try {
			if(null==sBIAPSSOLoginInSrvRequest){
				return null;
			}{
				MsgHeader msgHeader=sBIAPSSOLoginInSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				sBIAPSSOLoginInSrvRequest.setMsgHeader(msgHeader);
				log.info("请求参数:{ip:"+sBIAPSSOLoginInSrvRequest.getIP()+",loginId:"+sBIAPSSOLoginInSrvRequest.getLOGINID()
	        			+",msgHeader:"+sBIAPSSOLoginInSrvRequest.getMsgHeader()+"}");
	        	return sBIAPSSOLoginInSrv.process(sBIAPSSOLoginInSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	/*public static void main(String[] args) throws Exception {  
		       //这个是用cxf 客户端访问cxf部署的webservice服务  
		       //千万记住，访问cxf的webservice必须加上namespace ,否则通不过  
		      //现在又另外一个问题，传递过去的参数服务端接收不到  
		       JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();  
		       org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8080/facelook/services/facelookWebService?wsdl");  
		        //url为调用webService的wsdl地址  
		       QName name=new QName("http://server.webservice.facelook.com/","getAlbumList");  
		       //namespace是命名空间，methodName是方法名  
		       String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"  
		                + "     <facelook>"  
		                + "        <condition>"  
		               + "            <name>家</name>"  
		                 + "            <description></description>"  
		               + "            <pageno></pageno>"  
		                 + "            <pagesize></pagesize>"  
		                + "        </condition>"  
		               + "     </facelook>";  
		       //paramvalue为参数值  
		        Object[] objects=client.invoke(name,xmlStr);  
		      //调用web Service//输出调用结果  
		        System.out.println(objects[0].toString());  
	}
*/
	
	
}
