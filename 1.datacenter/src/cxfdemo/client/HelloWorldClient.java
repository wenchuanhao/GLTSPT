package cxfdemo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**   
 * <p>��������  �ͻ��˵��ô���</p> 
 * <p>�޸��ˣ�����ί </p> 
 * <p>�޸�ʱ�䣺2014-5-12 ����09:29:21  </p> 
 * @version   
 */  
public class HelloWorldClient {  
  
      
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
    	/*HelloWorldImplService ss = new HelloWorldImplService();
    	HelloWorld  port = ss.getHelloWorldImplPort();
        String result = port.sayHi("Jaune");  
        System.out.println(result); */ 
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                 new String[] { "spring-cxf-client.xml" });  
   
         HelloWorld client = (HelloWorld) context.getBean("client");  
   
         String response = client.sayHi("Joe");  
         System.out.println("Response: " + response);  
         System.exit(0);  
    }  
  
}  