package org.trustel.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BooleanConverter;

 public class DbFieldsSegment{
        
        private String fieldsHolder;
        private String fieldAliasesHolder;
        private Object[] values;
        private FieldNameConvertor cvt;
        private Object orginBean;
        private boolean incNullVal = false;
        
        @Override
        public String toString()
        {
           
            return super.toString();
        }
        
        public DbFieldsSegment(){}
        
        public DbFieldsSegment parse(Object bean){
            
            try
            {
                BeanInfo bi = Introspector.getBeanInfo(bean.getClass(), Object.class);
                PropertyDescriptor[] pds = bi.getPropertyDescriptors();
                StringBuilder asb  = new StringBuilder();
                StringBuilder sb  = new StringBuilder();
                ArrayList<Object> valList = new ArrayList<Object>();
                for(PropertyDescriptor pd : pds){
                    String fn = pd.getDisplayName();
                    Object val = null;
                    if(pd.getReadMethod() != null){
                        val = pd.getReadMethod().invoke(bean, new Object[0]);
                    }else{
                        continue;
                    }
                    
                    if(cvt != null ){
                        fn = cvt.convert(fn);
                        System.out.println("Get alais name:"+fn);
                    }
                    if(null != fn && !"".equals(fn) 
                            && ( incNullVal ? true : null != val)){
                        asb.append(fn).append(',');
                        valList.add(val);
                        sb.append(fn).append(',');
                        System.out.println("find value:"+val);
                    }
                }
                
                if(asb.length() > 0)
                {
                    asb.deleteCharAt(asb.length() -1 );
                }
            
                if(sb.length() > 0)
                {
                    sb.deleteCharAt(sb.length() -1 );
                }
        
                fieldsHolder = asb.toString();
                fieldAliasesHolder = sb.toString();
                values = valList.toArray();
                orginBean = bean;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return this;
        }
        
        
        public DbFieldsSegment(Object orginBean, FieldNameConvertor cvt, boolean incNullVal)
        {
            super();
            this.cvt = cvt;
            this.orginBean = orginBean;
            this.incNullVal = incNullVal;
            this.parse(orginBean);
        }

        /**
         * ������field�����ɣ����ŷָ����ִ�����f1,f2,f3......
         */
        public String getFieldsHolder()
        {
            return fieldsHolder;
        }

        
        public void setFieldsHolder(String fieldsHolder)
        {
            this.fieldsHolder = fieldsHolder;
        }

        /**
         * ���ֶ���һһ��Ӧ���ֶ�ֵ
         * @return �ֶ�ֵ����
         */
        public Object[] getValues()
        {
            return values;
        }

        public void setValues(Object[] values)
        {
            this.values = values;
        }

        public FieldNameConvertor getCvt()
        {
            return cvt;
        }

        public void setCvt(FieldNameConvertor cvt)
        {
            this.cvt = cvt;
        }

        public Object getOrginBean()
        {
            return orginBean;
        }

        public void setOrginBean(Object orginBean)
        {
            this.orginBean = orginBean;
        }

        public boolean isIncNullVal()
        {
            return incNullVal;
        }

        public void setIncNullVal(boolean incNullVal)
        {
            this.incNullVal = incNullVal;
        }
        
        
        public static interface FieldNameConvertor{
            String convert(String oldName);
        }
        
        /**
         * �������ֶ�ռλ���ŷָ����ʺ�ռλ��ϣ��� ?,?,?......
         */
        public String getQmarkHolders(){
            StringBuilder sb = new StringBuilder();
            for(int i = 0 ; i < values.length ; i++ ){
                sb.append("?,");
            }
            
            return sb.substring(0, sb.length() - 2);
        }

        public String getFieldAliasesHolder()
        {
            return fieldAliasesHolder;
        }

        public void setFieldAliasesHolder(String fieldAliasesHolder)
        {
            this.fieldAliasesHolder = fieldAliasesHolder;
        }
        
        public static String[] getFieldNames(Class<?> beanClz){
            return getFieldNames(beanClz,false,null);
        }
        public static String[] getFieldNames(Class<?> beanClz,boolean ignoreNullField,Object bean){
            List<String> fields = new ArrayList<String>();
            
            try
            {
                BeanInfo bi = Introspector.getBeanInfo(beanClz,Object.class);
                PropertyDescriptor[] pds = bi.getPropertyDescriptors();
                
                for (int i = 0; i < pds.length; i++)
                {
                    Method fm = pds[i].getReadMethod();
                    if( ignoreNullField && null != bean 
                            && null != fm 
                            && null != fm.invoke(bean, new Object[0]) ){
                        
                            fields.add(pds[i].getDisplayName());
                            
                    }else{
                        
                        fields.add(pds[i].getDisplayName());
                        
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return fields.toArray(new String[0]);
        }

        /**
         * ����SQL�����ֶ��б?��set field1 = ? , field2 = ?  
         * @param sqlBuilder ���ַ�
         * @param bean �־û���bean
         * @param ignoreFields �����ֶΣ���ʽ��".field1.field2"
         * @param ignoreNull �Ƿ���Կ�ֵ�ֶ�
         * @param vals ����SQL����ֵ�ļ���
         */
        public static void buildSetFieldsFragment(
                StringBuilder sqlBuilder,
                Object bean,
                String ignoreFields,
                boolean ignoreNull,
                List<Object> vals)
        {
            buildFieldsFragment(" set " ,
                    sqlBuilder,
                    bean,
                    ignoreFields,
                    ",", 
                    ignoreNull, 
                    vals
            );
        }
        
        /**
         * ����SQL��ѯ�ֶ��б?��where������ field1 = value1 and field2 = value1 
         * @param sqlBuilder ���ַ�
         * @param bean �־û���bean
         * @param ignoreFields �����ֶΣ���ʽ��".field1.field2"
         * @param ignoreNull �Ƿ���Կ�ֵ�ֶ�
         * @param vals ����SQL����ֵ�ļ���
         */
        public static void buildWhereFieldsFragment(StringBuilder sqlBuilder,
                Object bean, String ignoreFields,boolean ignoreNull,List<Object> vals)
        {
            buildFieldsFragment(" where 1=1 and " ,
                    sqlBuilder,
                    bean,
                    ignoreFields,
                    " and ", 
                    ignoreNull, 
                    vals
            );
        }
        
        /**
         * ����SQL���ºͲ�ѯ�ֶ��б?��where������ field1 = value1 and field2 = value1,��set field1 = ? , field2 = ?  
         * @param prefix ǰ���ַ�
         * @param sqlBuilder ���ַ�
         * @param bean �־û���bean
         * @param ignoreFields �����ֶΣ���ʽ��".field1.field2"
         * @param splitChars �ֶ���ֵ�ָ��ִ�,��"."
         * @param ignoreNull �Ƿ���Կ�ֵ�ֶ�
         * @param vals ����SQL����ֵ�ļ���
         */
        public static void buildFieldsFragment(
                String prefix,
                StringBuilder sqlBuilder,
                Object bean,
                String ignoreFields,
                String splitChars,
                boolean ignoreNull
                ,List<Object> vals)
        {
            sqlBuilder.append(prefix);
            PropertyDescriptor[] pds = null;
            try
            {
                pds = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
           
            for(int i = pds.length - 1 ; i >= 0 ; i--) {
                Object value = null ;
                PropertyDescriptor pd = pds[i];
                Method rm = pd.getReadMethod() ;
                
                try
                {
                    if(null !=  rm ){
                        value = rm.invoke( bean, new Object[0] ) ;
                        
                        if( (ignoreNull && value == null) 
                                || (null != ignoreFields && ignoreFields.indexOf( pd.getDisplayName() ) > -1) )
                        {
                            continue ;
                        }
                        
                        sqlBuilder.append(pd.getDisplayName());
                        String strVal = ConvertUtils.convert(value);
                        //���ֵΪ%��ͷ�ͽ�β��ʹ��ģ���ѯ
                        if(strVal != null && strVal.startsWith("%") && strVal.endsWith("%")){
                            sqlBuilder.append(" like ?");
                        }
                        else
                        {
                            sqlBuilder.append("=?");
                        }
                        
                        sqlBuilder.append(splitChars);
              //        System.out.println("fieldName:" + pd.getDisplayName() + " Value:" + strVal);
                                               
                        vals.add(value);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
            }
            int scPos = sqlBuilder.lastIndexOf(splitChars);
            int endPos =scPos + splitChars.length() ;
            if(endPos > scPos && scPos >=0 ){
               // System.out.println("���ĩβ�ָ��ִ���"+sqlBuilder.subSequence(scPos, endPos));
                sqlBuilder.delete(scPos, endPos);
            }
        }
        
        /**
         * ����SQL����еġ��־û�ȫ���ֶΡ�(field1,field2,field3...)VALUES(?,?,?...)
         * @param sqlBuilder ǰ��sql���
         * @param bean �����־û��Ķ���
         * @param ignoreNull �Ƿ���Կ�ֵ�ֶ�
         * @param vals �б?����ȡ���ʺ�ռλ����ڵ�ֵ
         */
        public static void buildFieldsAndValues(StringBuilder sqlBuilder,
                Object bean, boolean ignoreNull,List<Object> vals)
        {
            buildFieldsAndValues(sqlBuilder, bean, ignoreNull, null, vals);
            
        }
       
        /**
         * ����SQL����е�(field1,field2,field3...)VALUES(?,?,?...)
         * @param sqlBuilder ǰ��sql���
         * @param bean �����־û��Ķ���
         * @param ignoreNull �Ƿ���Կ�ֵ�ֶ�
         * @param ignoreFields �����ֶΣ���ʽ��".field1.field2"
         * @param vals �б?����ȡ���ʺ�ռλ����ڵ�ֵ
         */
        public static void buildFieldsAndValues(StringBuilder sqlBuilder,
            Object bean, boolean ignoreNull,String ignoreFields,List<Object> vals){
            sqlBuilder.append(" ( ");
            PropertyDescriptor[] pds = null;
            try
            {
                pds = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            int fieldCount = 0;
            for(int i = pds.length - 1 ; i >= 0 ; i--) {
                Object value = null ;
                PropertyDescriptor pd = pds[i];
                Method rm = pd.getReadMethod() ;
                
                try
                {
                    if(null !=  rm ){
                        value = rm.invoke( bean, new Object[0] ) ;
                        if(ignoreNull && value == null || 
                                ( ignoreFields != null && ignoreFields.indexOf("."+pd.getDisplayName())> -1 ))
                        {
                            continue ;
                        }
                        fieldCount ++;
                        sqlBuilder.append(pd.getDisplayName()).append(',');
                        vals.add(value);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
               
            }
            sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
            sqlBuilder.append(" ) VALUES ( ");
            
            for(int i = fieldCount ; i > 0 ; i-- ){
                sqlBuilder.append("? ,");
            }
            
            sqlBuilder.setCharAt(sqlBuilder.length() -1 , ')');
    }
   
    static{
        
        Converter bcn = new BooleanConverter();
        ConvertUtils.register(bcn, Boolean.class);
        ConvertUtils.register(bcn, Boolean.TYPE);
        
    }
}       
   