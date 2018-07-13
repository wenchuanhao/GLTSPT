package com.cmcc;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmcc.mss.msgheader.MsgHeader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 描述:webservice实现公共类 .
 * User: fuJ
 * Date: 16-05-18
 */
public abstract class JaxbParseBase {

    private final Logger log = LoggerFactory.getLogger(JaxbParseBase.class);


    /**
     * 根据xml字符串转化为相应的bean对象
     * @param clazz
     * @param xmlStr
     * @param <T>
     * @return
     */
    protected <T> T xmlToBean(Class<T> clazz,String xmlStr){

        log.info(xmlStr);
        StringReader stringReader = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xmlStr));
        } catch (JAXBException e) {
            log.error(String.format("xml转换bean失败:[%s]",xmlStr),e);
        } finally {
            IOUtils.closeQuietly(stringReader);
        }
        return null;
    }

    /**
     * 根据实体bean对象转为xml字符串
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> String beanToXml(T clazz,String encoding){
        StringWriter stringWriter = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz.getClass());
            Marshaller marshaller = jc.createMarshaller();
            //决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //xml的编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            stringWriter = new StringWriter();
            marshaller.marshal(clazz, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            log.error(String.format("bean转换xml失败:[%s]",clazz),e);
        } finally {
            IOUtils.closeQuietly(stringWriter);
        }
        return null;
    }


    protected String generateVerifyByEntity(Object entity){

        StringBuilder sourceStr = new StringBuilder();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields){
            System.out.println(field.getType());
        }
        return null;

    }


    public void parseParams(Element element,StringBuilder containStr){

        List attributes = element.attributes();
        for(int j=0;attributes!= null && j<attributes.size();j++){
            Attribute attribute = (Attribute)attributes.get(j);
            containStr.append(String.format(attribute.getValue()));
        }



        List elements = element.elements();
        if (elements.size() <= 0) {
            containStr.append(element.getText());
        } else {
            for (int i=0;i<elements.size();i++){
                Element element_ = (Element)elements.get(i);
                parseParams(element_,containStr);
            }
        }
    }
    
    public MsgHeader initMsgHeader(MsgHeader msgHeader){
    	if(null==msgHeader){
    		msgHeader=new  MsgHeader();
    	}
    	if(null==msgHeader.getSOURCESYSTEMID()||msgHeader.getSOURCESYSTEMID().trim().equals("")){
    		msgHeader.setSOURCESYSTEMID(ServiceConfig.sourcesystemid);
    	}
    	if(null==msgHeader.getSOURCESYSTEMNAME()||msgHeader.getSOURCESYSTEMNAME().trim().equals("")){
    		msgHeader.setSOURCESYSTEMNAME(ServiceConfig.sourcesystemname);
    	}
    	if(null==msgHeader.getSUBMITDATE()){
    		msgHeader.setSUBMITDATE(ServiceConfig.submitdate);
    	}
    	if(null==msgHeader.getPAGESIZE()){
    		msgHeader.setPAGESIZE(ServiceConfig.pagesize);
    	}
    	if(null==msgHeader.getCURRENTPAGE()){
    		msgHeader.setCURRENTPAGE(ServiceConfig.currentpage);
    	}
    	if(null==msgHeader.getTOTALRECORD()){
    		msgHeader.setTOTALRECORD(ServiceConfig.totalrecord);
    	}
    	if(null==msgHeader.getPROVINCECODE()||msgHeader.getPROVINCECODE().trim().equals("")){
    		msgHeader.setPROVINCECODE(ServiceConfig.provincecode);
    	}
    	if(null==msgHeader.getENVIRONMENTNAME()||msgHeader.getENVIRONMENTNAME().trim().equals("")){
    		msgHeader.setENVIRONMENTNAME(ServiceConfig.environmentname);
    	}
    	if(null==msgHeader.getUSERID()||msgHeader.getUSERID().trim().equals("")){
    		msgHeader.setUSERID(ServiceConfig.userid);
    	}
    	if(null==msgHeader.getUSERNAME()||msgHeader.getUSERNAME().trim().equals("")){
    		msgHeader.setUSERNAME(ServiceConfig.username);
    	}
    	return msgHeader;
    	
    }
   
}
