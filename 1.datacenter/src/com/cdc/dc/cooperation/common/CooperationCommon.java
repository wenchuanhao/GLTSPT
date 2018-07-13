package com.cdc.dc.cooperation.common;

import java.util.List;

import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;

/**
 * 制度创建
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
public class CooperationCommon {

	/**
	 * 数据源配置CooperationDatasourceType
	 */
	public final static String datasourceRoot_level =  "1";//最小级别
	
	public final static String datasourceRoot_parentId =  "ROOT";//root父节点
    
	public final static String datasourceStatus_Save =  "1";//已存
	public final static String datasourceStatus_Del =  "0";//删除

	public final static String datasourceSource_1st =  "1";//导入、年报表
	public final static String datasourceSource_2nd =  "2";//录入、月报表
	public final static String datasourceSource_3rd =  "3";//
	public final static String datasourceSource_4th =  "4";//删除
	
	/**
	 * 数据权限配置CooperationTypeRoleUser
	 */
	public final static String typeRoleUserStatus_Save =  "1";//已存
	public final static String typeRoleUserStatus_Del =  "0";//删除
	
	public final static String DICT_TYPE =  "COOPER_ROLE";//数据字典类型配置
	public final static String DS_QUERYER =  "1";//合交数据查询员
	public final static String DS_REMAINER =  "2";//合交数据审核员
	public final static String DS_IMPORTER =  "3";//合交数据录入员
	public final static String COOPER_ADMIN =  "4";//合交管理员
	public final static String YB_QUERYER =  "5";//合交报表查询员
	
	/**
	 * 数据记录表CooperationDatasourceRecords
	 */
    public final static String datasourceRecordsStatus_DEL =  "0";//已删除
    public final static String datasourceRecordsStatus_CG =  "1";//草稿
    public final static String datasourceRecordsStatus_SH =  "2";//审核中———提交状态
    public final static String datasourceRecordsStatus_TG =  "3";//已通过
    public final static String datasourceRecordsStatus_FZ =  "4";//废止（无此状态）
    public final static String datasourceRecordsStatus_XD =  "5";//已修订
    public final static String datasourceRecordsStatus_WTG =  "6";//不通过，退回
    public final static String datasourceRecordsStatus_TB =  "7";//同步成功
    public final static String datasourceRecordsStatus_WTB =  "8";//同步失败
    public final static String datasourceRecordsStatus_TBZ =  "9";//同步中
    
    /**
     * 数据源数据校验规则表YuanTableColumnManage
     */
    public final static String yTCMnullableNo =  "0";//不可为空
    public final static String yTCMnullableYes =  "1";//可为空
    
    public final static String yTCMdataTypeDate =  "Date";//时间类型
    public final static String yTCMdataTypeNumber =  "Number";//数值类型
    public final static String yTCMdataTypeString =  "String";//字符串类型
    
    
    public static String getIds(List<CooperationTypeRoleUser> list,List<CooperationDatasourceType> lists){
		String datasourceIds = "'";
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				if(i == 0){
					datasourceIds += list.get(i).getDatasourceId();
				}else{
					datasourceIds += "','"+list.get(i).getDatasourceId();
				}
			}
		}
		if(lists != null){
			for (int i = 0; i < lists.size(); i++) {
				if(i == 0){
					datasourceIds += lists.get(i).getParentDatasourceId();
				}else{
					datasourceIds += "','"+lists.get(i).getParentDatasourceId();
				}
			}
		}
		return datasourceIds + "'";
	}
    public static String getIds(List<CooperationDatasourceType> datasourceTypeList){
    	String datasourceIds = "'";
		if(datasourceTypeList != null){
			for (int i = 0; i < datasourceTypeList.size(); i++) {
				if(i == 0){
					datasourceIds += datasourceTypeList.get(i).getDatasourceId();
				}else{
					datasourceIds += "','"+datasourceTypeList.get(i).getDatasourceId();
				}
			}
		}
		return datasourceIds += "'";
    }
}


