package com.trustel.common;

import java.io.File;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Created on 2008-2-4 2:42:49
 * @author xiaowei
 *
 */
public class StandUtil {

	/**
	 * Convert object to integer
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj){
		try{
			return Integer.parseInt(obj.toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Convert object to short
	 * @param obj
	 * @return
	 */
	public static short toShort(Object obj){
	    try{
			return Short.parseShort(obj.toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Convert object to long
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj){
		try{
			return Long.parseLong(obj.toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Convert object to double
	 * @param obj
	 * @return
	 */
	public static double toDouble(Object obj){
	    try{
			return Double.parseDouble(obj.toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Convert object to float
	 * @param obj
	 * @return
	 */
	public static float toFloat(Object obj){
		try{
			return Float.parseFloat(obj.toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Convert object to string
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj){
		try{
			return obj.toString();
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * Convert object to date
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj){
		try{
			return DateFormat.getDateInstance().parse(obj.toString());
		}catch(Exception e){
			return new Date();
		}
	}
	
	/**
	 * Convert object to boolean
	 * @param obj
	 * @return
	 */
	public static boolean toBoolean(Object obj){
	    try{
			return Boolean.parseBoolean(obj.toString());
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 获得数组最大值
	 * @param var
	 * @return
	 */
	public static int max(int[] var){
		if(var==null)
			return 0;
		int max = var[0];
		for(int i=1;i<var.length;i++){
			if(var[i]>max)
				max = var[i];
		}
		return max;
	}
	
	/**
	 * 获得数组最小值
	 * @param var
	 * @return
	 */
	public static int min(int[] var){
		if(var==null)
			return 0;
		int min = var[0];
		for(int i=1;i<var.length;i++){
			if(var[i]<min)
				min = var[i];
		}
		return min;
	}
	
	/**
	 * 把对象转换为Map
	 */
	public static Map makeMap(Object obj){
	    try{
            return BeanUtils.describe(obj);
        }catch(Exception e){
            print("Make Map Exception !");
        }
        return null;
	}

	/**
	 * 通过Map构造对象
	 * @param map
	 * @return
	 */
	public static Object makeBean(Object obj,Map map){
		try{
            BeanUtils.populate(obj,fetchMap(obj,map));
        }catch(Exception e){
            print("Make Bean Exception !");
        }
		return obj;
	}

	/**
	 * 获得对象的属性Map
	 * @param obj
	 * @param map
	 * @return
	 */
	public static Map fetchMap(Object obj,Map map){
		Map var = makeMap(obj);
		Map param = new HashMap();
		Collection list = map.keySet();
		if(list!=null&&list.size()>0){
			for(Iterator iter = list.iterator();iter.hasNext();){
				String key = (String)iter.next();
				if(var.containsKey(key)){
					param.put(key, map.get(key));
				}
			}
		}
		return param;
	}
	
	/**
	 * 计算两个数组之差
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static int[] subArray(int[] arr1,int[] arr2){
	    if(arr1==null||arr2==null){
	        return null;
	    }
	    int[] tmp = new int[arr1.length];
	    for(int i=0;i<tmp.length;i++){
	        tmp[i] = arr1[i];
	    }
	    int leng = Math.min(arr1.length,arr2.length);
	    for(int i=0;i<leng;i++){
	        tmp[i] -= arr2[i];
	    }
	    return tmp;
	}
	
	/**
	 * 计算两个数组之和
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static int[] addArray(int[] arr1,int[] arr2){
	    if(arr1==null||arr2==null){
	        return null;
	    }
	    int[] tmp = new int[arr1.length];
	    for(int i=0;i<tmp.length;i++){
	        tmp[i] = arr1[i];
	    }
	    int leng = Math.min(arr1.length,arr2.length);
	    for(int i=0;i<leng;i++){
	        tmp[i] += arr2[i];
	    }
	    return tmp;
	}
	
	/**
	 * 修正当前页面数
	 * @param count 记录总数
	 * @param page 当前页面
	 * @param size 每页记录数
	 * @return
	 */
	public static int correctPage(int count,int page,int size){
		int total = (int)Math.ceil((double)count/size)-1;
		int current = page;
		if(page<0){
			current = 0;
		}else if(page>total){
			current = total;
		}
		return current;
	}
	
	/**
	 * 打印信息到控制台
	 * @param msg
	 */
	public static void print(Map msg){
		print(msg.toString());
	}
	
	/**
	 * 打印信息到控制台
	 * @param msg
	 */
	public static void print(Object msg){
		System.out.println("\n- - - - - - - - Message - - - - - - - -");
		System.out.println(msg);
		System.out.println("- - - - - - - - - - - - - - - - - - - -\n");
	}
	
	/**
	 * 输出异常信息
	 * @param e
	 */
	public static void debug(Exception e){
	    System.out.println("\n- - - - - - - - Exception - - - - - - - -");
	    System.out.println("Class : "+e.getClass());
	    StackTraceElement[] msg = e.getStackTrace();
	    for(int i=0;i<msg.length;i++){
	        System.out.println(msg[i]);
	    }
	    System.out.println("- - - - - - - - - - - - - - - - - - - - -\n");
	}
	
	/**
	 * 构造sql查询语句(精确)
	 * @param sql
	 * @param map
	 * @return
	 */
	public static String definiteSQL(Map map){
		StringBuffer sql = new StringBuffer();
		Collection list = map.keySet();
		for(Iterator iter = list.iterator();iter.hasNext();){
			String key = (String)iter.next();
			if(map.get(key)!=null&&!"".equals(map.get(key))){
				sql.append(key+"='"+map.get(key)+"' and ");
			}
		}
		return sql.substring(0,sql.length()-4).toString();
	}
	
	/**
	 * 构造sql查询语句(模糊)
	 * @param sql
	 * @param map
	 * @return
	 */
	public static String indefiniteSQL(Map map){
		StringBuffer sql = new StringBuffer();
		Collection list = map.keySet();
		for(Iterator iter = list.iterator();iter.hasNext();){
			String key = (String)iter.next();
			if(map.get(key)!=null&&!"".equals(map.get(key))){
				sql.append(key+" like '%"+map.get(key)+"%'and ");
			}
		}
		return sql.substring(0,sql.length()-4).toString();
	}
	
	/**
	 * Fetch the suffix
	 * @param name
	 * @return
	 */
	public static String suffix(String name){
		int index = name.lastIndexOf('.');
		return name.substring(index);
	}
	
	/**
	 * Fetch the prefix
	 * @param name
	 * @return
	 */
	public static String prefix(String name){
		int index = name.lastIndexOf('.');
		return name.substring(0,index).replace('\\', File.separatorChar);
	}
	
	/**
	 * Fetch the directory
	 * @param path
	 * @return
	 */
	public static String folder(String path){
		int index = path.lastIndexOf('\\');
		return path.substring(0,index-1).replace('\\', File.separatorChar);
	}
	
	/**
	 * Fetch the file name
	 * @param path
	 * @return
	 */
	public static String file(String path){
		int index = path.lastIndexOf('\\');
		return path.substring(index+1,path.length());
	}
	
	/**
	 * 判断对象是否为空
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		if(obj==null)
			return true;
		if(obj instanceof String){
			return "".equals(obj)? true : false;
		}else if(obj instanceof Map){
			return ((Map)obj).isEmpty();
		}else if(obj instanceof Collection){
			return ((Collection)obj).isEmpty();
		}else{
			return false;
		}
	}
	
	/**
	 * 判断对象是否不为空
	 * @return
	 */
	public static boolean notEmpty(Object obj){
		return !isEmpty(obj);
	}
	
	/**
	 * 解析Date对象
	 * @param obj
	 * @return
	 */
	public static Date parseDate(Object obj){
		DateFormat format = DateFormat.getDateInstance();
		try{
			return format.parse(obj.toString());
		}catch(Exception e){
			return new Date();
		}
	}
}
