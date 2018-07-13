package org.trustel.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.util.StringUtils;

public class DBSegmentHelper
{
    /**
     * 构造oracle特有三层嵌套分页单表数据SQL结构
     * @param sql 原查询SQL
     * @param firstIndex 开始取出记录索引
     * @param maxSize 取得记录数
     * @return
     */
    public static String buildOraclePagableSQL(String sql,String[] fields, int firstIndex,
            int maxSize)
    {
        StringBuilder psb = new StringBuilder("SELECT ");
        
        appendFieldsToStringBuilder(fields, psb,null);
        
        psb.append(" FROM (SELECT ");
/*        psb.append(" FROM (SELECT rownum,");
*/        
        appendFieldsToStringBuilder(fields, psb,"t");
        
        psb.append(",rownum as r_ FROM (").append(sql).append(
                ") t WHERE rownum<=").append(firstIndex + maxSize).append(
                ") WHERE r_>").append(firstIndex);
        System.out.println("build Oracle page SQL:" + psb);
        return psb.toString();
    }
    public static void appendFieldsToStringBuilder(String[] fields,
            StringBuilder psb,String tableAlias)
    
    {
        if(null ==tableAlias ) { tableAlias="";}
        
        if(!tableAlias.endsWith(".") && !StringUtils.isEmpty(tableAlias)) { 
            tableAlias = tableAlias.trim();
            tableAlias+=".";
        }
        
        psb.append(' ');
        
        if(null == fields || fields.length == 0){
            psb.append(tableAlias).append("* ") ;
        }else{
            for(String f:fields){
                psb.append(tableAlias).append(f).append(',');
            }
            psb.deleteCharAt(psb.length() - 1);
        }
    }
    public static String buildOraclePagableSQL(String sql, int firstIndex,
            int maxSize)
    {
       return buildOraclePagableSQL(sql,null,firstIndex,maxSize);
    }

    /**
     * 将属性名转换成下划线分割的属性名和相应的值构成键值对，
     * 如bean{ myName =“cks”} => Map<String,Object>{my_name:'cks'}
     * @param bean 对象实例
     * @return Map结构键值对
     */
    public static Map<String, Object> underscoreBeanPropNameMap(Object bean)
    {
        Map<String, Object> pvsMap = new HashMap<String, Object>();
        
        if(bean == null){ return pvsMap ;}
        
        BeanInfo bi = null;
        try
        {
            bi = Introspector.getBeanInfo(bean.getClass(), Object.class);
            PropertyDescriptor[] pds = bi.getPropertyDescriptors();
            
            for (PropertyDescriptor p : pds)
            {
                char[] pncs = p.getDisplayName().toCharArray();
                StringBuilder tmpNameSB = new StringBuilder();
                for(int i = 0 ;i <pncs.length;i++){
                
                    if(pncs[i] <= 'Z' && pncs[i] >= 'A'){
                        tmpNameSB.append('_').append(Character.toLowerCase(pncs[i]));
                    }
                    else
                    {
                        tmpNameSB.append(pncs[i]);
                    }
                }
                //没有getter方法的属性不是持久化字段
                if(null == p.getReadMethod()){
                    continue;
                }
                Object val = p.getReadMethod().invoke(bean, new Object[0]);
                pvsMap.put(tmpNameSB.toString(), val);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            Introspector.flushFromCaches(bean.getClass());
        }
        return pvsMap;
    }

    public static Date parseDataStr(String dateStr)
    {
        if(dateStr==null || "".equals(dateStr.trim())){
            return Calendar.getInstance().getTime();
        }
        Date result = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        try
        {
            result = sdf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        //System.out.println(dateStr + " -> " + result);
        return result;
    }
    
    
//    public static DbFieldsSegment buildDbFieldsSegment(Object bean,FieldNameConvertor cvt,boolean incNullVal){
//         return new DbFieldsSegment(bean,cvt,incNullVal).parse(bean);
//    }
//    public static DbFieldsSegment buildDbFieldsSegment(Object bean){
//        return buildDbFieldsSegment(bean,null,false);
//    }
    public static List<Object> adaptSQLTypeValus(List<Object> args)
    {
          List<Object> sqlTypesValus = new ArrayList<Object>();
          
                   
          return sqlTypesValus;
    }
   
 
}
