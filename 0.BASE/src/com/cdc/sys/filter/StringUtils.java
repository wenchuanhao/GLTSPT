package com.cdc.sys.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtils {

    public static String subfix = "...";

    public static String valueOf(String source, int maxlength) {
        return valueOf(source, "", maxlength);
    }

    public static String valueOf(String source) {
        return valueOf(source, "");
    }

    public static String valueOf(String source, String defaultValue) {
        return valueOf(source, defaultValue, 0);
    }

    public static String valueOf(String source, String defaultValue, int maxlength) {
        if (source == null || source.trim().equals(""))
            return defaultValue;

        if (maxlength < 1)
            return source.trim();
        source = source.trim();
        if (source.length() > maxlength + 1)
            source = source.substring(0, maxlength) + subfix;

        return source;
    }

    /**
     * 将字符串转为HTML串，当源串为NULL或空时返回&nbsp;
     * 
     * @param source
     * @return
     */
    public static String null2HTML(String source) {
        return null2HTML(source, 0);

    }

    /**
     * 当字串超过长度时截短显示，为NULL时显示一个空格
     * 
     * @param source
     * @param len
     * @return
     */
    public static String null2HTML(String source, int len) {
        return valueOf(source, "&nbsp;", len).trim();
    }

    /**
     * 当字串超过指定长度时截断
     * 
     * @param source
     *            源串
     * @param max
     *            最大长度
     * @return 限制长度串
     */
    public static String null2Str(String source, int max) {
        if (source == null)
            return "";
        byte[] b = source.getBytes();
        if (b.length > max)
            source = new String(b, 0, max);
        return source;
    }

    /**
     * 返回以0作前缀的指定长度的序列号
     */
    public static String fixLength(String value, int fixLen) {
        return fixLength(value, fixLen, '0');
    }

    /**
     * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
     */
    public static String fixLength(String value, int fixLen, char fixChar) {
        String stmp = "";
        for (int i = 0; i < fixLen - value.length(); i++) {
            stmp = stmp + fixChar;
        }

        return stmp + value;
    }

    /**
     * 获取文件扩展名
     * 
     * @return
     */
    public static String getFileExtName(String fileName) {
        int index = fileName.lastIndexOf(".");
        // 文件扩展名
        String extName = "";
        if (index >= 0) {
            extName = fileName.substring(index);
        }
        return extName;
    }

    /**
     * 获取文件名（不带扩展名）
     * 
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }

    /**
     * 获取文件名（不带扩展名）,超过长度截取
     * 
     * @param fileName
     *            文件名
     * @param maxLength
     *            最大长度
     * @return
     */
    public static String getFileName(String fileName, int maxLength) {
        String name = getFileName(fileName);
        return stringSubString(name, maxLength);
    }

    /**
     * 判断char是否为中文 如果是中文返回true 英文返回false
     * 
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // GENERAL_PUNCTUATION 判断中文的“号
        // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 截取固定长度的字符串，中文占两位、英文占一位，其他语言不在本方法内 1、如果为空直接返回"" 2、非空情况就遍历截取
     * 
     * @param strName
     * @param len
     */
    public static String stringSubString(String strName, int subLen) {
        // 1、如果为空直接返回""
        if (strName == null || strName.trim().length() == 0 || subLen < 0) {
            return "";
        }
        StringBuffer retValue = new StringBuffer("");
        char[] ch = strName.trim().toCharArray();
        int len = 0;
        for (int i = 0; i < ch.length; i++) {
            if (isChinese(ch[i])) {// 是中文+2
                len += 2;
            } else {
                ++len;
            }
            if (len < subLen) {
                retValue.append(ch[i]);
            } else if (len == subLen) {
                retValue.append(ch[i]);
                return retValue.toString();
            } else if (len > subLen) {
                return retValue.toString();
            }
        }
        return retValue.toString();

    }
    
	/**
	 * 数字字符串转为数字
	 */
	public static Integer StringToDigital(String para){
		if(para == null) return 0;
		String str = "^-?[1-9]\\d*$";
		Pattern p = Pattern.compile(str); 
		if(!p.matcher(para).matches()){
			return 0;
		}
		return Integer.parseInt(para.trim());
	}
	
	/**
	 * 去掉空格(对"" null 返回NULL)
	 * @param para
	 * @return
	 */
	public static String removeBlank(String para){
		if(para==null || "".equals(para)) return null;
		return para.trim();
	}
	
	/**
	 * 把yyyy-mm-dd 字符串转为date类型
	 * @param yyymmdd
	 * @return
	 */
	public Date StringToDate(String yyymmdd){
		if(yyymmdd==null || "".equals(yyymmdd.trim())){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			 date = sdf.parse(yyymmdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 替换特殊字符
	 * 针对 appScan 检查
	 * 跨站点脚本编制，SQL 盲注，通过框架钓鱼，链接注入（便于跨站请求伪造）。
	 * @param value
	 * @return
	 */
	public static String filterDangerString(String value) {
		if (value == null) {
			return null;
		}
		
		/*value = value.replaceAll(";", " ").replaceAll("&","&amp;").replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;").replaceAll("\n","<br>").replaceAll("\"", "&quot;")
		.replaceAll("\'", "&acute;").replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");*/
		//value = value.replaceAll("\\|", "");

		value = value.replaceAll("&", "&amp;");
//新增开始
		value = value.replaceAll("onclick", " ");
		value = value.replaceAll("onblur", " ");
		value = value.replaceAll("onerror", " ");
		value = value.replaceAll("alert", " ");
		value = value.replaceAll("src", " ");
		value = value.replaceAll("window", " ");
		value = value.replaceAll("img", " ");
		value = value.replaceAll("prompt", " ");
//		value = value.replaceAll("and", " ");
//		value = value.replaceAll("view", " ");
//		value = value.replaceAll("create", " ");
//		value = value.replaceAll("drop", " ");
//		value = value.replaceAll("truncate", " ");
//		value = value.replaceAll("update", " ");
//		value = value.replaceAll("select", " ");
//		value = value.replaceAll("union", " ");
//		value = value.replaceAll("or", " ");
			//		value = value.replaceAll("=", " ");
//		value = value.replaceAll(",", " ");
//新增结束		
//		value = value.replaceAll(";", " ");

//		value = value.replaceAll("@", "");

//		value = value.replaceAll("'", "&apos;");
//		value = value.replaceAll("\'", "&apos;");

		value = value.replaceAll("\"", "&quot;");

//		value = value.replaceAll("\\'", "");

		value = value.replaceAll("\\\"", "");

		value = value.replaceAll("<", "&lt;");

		value = value.replaceAll(">", "&gt;");

//		value = value.replaceAll("\\(", "");

//		value = value.replaceAll("\\)", "");

//		value = value.replaceAll("\\+", "");

		value = value.replaceAll("\r", "");

		value = value.replaceAll("\n", "");

		value = value.replaceAll("script", "");
		
		value = value.replaceAll("%27", "");
		value = value.replaceAll("%22", "&quot;");
		value = value.replaceAll("%3E", "");
		value = value.replaceAll("%3C", "");
		value = value.replaceAll("%3D", "");
		value = value.replaceAll("%2F", "");
		value = value.replaceAll("%2522", "&quot;");
		return value;
	}
	
	
	/**
	 * 返回字符数
	 * @param str 
	 * @param filter
	 * @return
	 */
	public static int getCountByFilter(String str, String filter){
		int cnt = 0;
        int offset = 0;
        while((offset = str.indexOf(filter, offset)) != -1){
            offset = offset + filter.length();
            cnt++;
        }
        return cnt;
	} 
}
