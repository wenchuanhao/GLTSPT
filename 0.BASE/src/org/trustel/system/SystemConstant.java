package org.trustel.system;

/**
 * 
 * 类 名：系统常量
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-04-21 14:56
 * 
 * 数据源：
 * 
 * 描 述：
 * 
 * 在SESSION中的变量命名以SESSION开始，放在请求中的变量以ATTRIBUTE开始，区分ACTION的变量以ACTION开始
 * 
 */

public class SystemConstant {

	public final static String SESSION_VISITOR = "VISITOR";

	public final static String SESSION_MESSAGE = "MESSAGE";

	public final static String ATTRIBUTE_MESSAGE = "MESSAGE";

	public final static String ATTRIBUTE_ITEMPAGE = "ITEMPAGE";

	public final static String ATTRIBUTE_INSTANCE = "INSTANCE";

	public final static String ATTRIBUTE_LIST = "LIST_DATA";// ListAllAttributeName

	public final static String ACTION_DELETE = "DELETE";
	public final static String DEFAULT_PROJECT = "DEFAULTPROJECT";

    /**强制修改密码**/
    public final static String ATTRIBUTE_FORCE_UPDATE_PASSWORD = "FORCE_UPDATE_PASSWORD";
    /**
     * 问题交互,是否可回复的标识
     */
    public final static String IS_ANSWER_FLAG = "IS_ANSWER_FLAG";

    //测试问题，适配问题 的查询流程列表form的状态
    public final static String QUERY_FORM_STATUS = "QUERY_FORM_STATUS";

}
