package com.cdc.dc.metadata.yuan.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.trustel.service.IEnterpriseService;

import com.cdc.system.core.util.SpringHelper;

/**
 * YuanTableManage entity. @author MyEclipse Persistence Tools
 */

public class YuanTableManage implements java.io.Serializable {

	// Fields

	private String id;
	private String tableName;
	private String parentId;
	private String datasourceId;
	private Date updateDate;
	private String updateUserName;
	private String updateUserId;
	private Date createDate;
	private String createUserName;
	private String createUserId;
	private Integer showOrder;

	// Constructors

	/** default constructor */
	public YuanTableManage() {
	}

	/** full constructor */
	public YuanTableManage(String tableName, String parentId,
			String datasourceId, Date updateDate, String updateUserName,
			String updateUserId, Date createDate, String createUserName,
			String createUserId, Integer showOrder) {
		this.tableName = tableName;
		this.parentId = parentId;
		this.datasourceId = datasourceId;
		this.updateDate = updateDate;
		this.updateUserName = updateUserName;
		this.updateUserId = updateUserId;
		this.createDate = createDate;
		this.createUserName = createUserName;
		this.createUserId = createUserId;
		this.showOrder = showOrder;
	}

	// Property accessors
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 表别名
	 * @author WEIFEI
	 * @date 2016-7-15 下午1:10:03
	 * @return	String
	 */
	public String getTableAlias(){
		
		if (this.parentId == null || this.parentId.equals("ROOT")) {
			return this.tableName;
		}
		String comments = (String) this.getTable().get("comments");
		return (comments == null) == true ? this.tableName:comments;
	}
	
	public Map<String, Object> getTable(){
		
		if (this.tableName == null || this.tableName.equals("")) {
			return new HashMap<String, Object>();
		}
		
		if (this.parentId.equals("") || this.parentId.equals("ROOT")) {
			return new HashMap<String, Object>();
		}
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select b.table_name,b.comments,(select count(*) from "+this.tableName.toUpperCase()+") num_rows from user_tables a,user_tab_comments b where a.table_name = b.table_name and a.table_name = '"+this.tableName.toUpperCase()+"'");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < list.size(); i++) {
			map = list.get(i);
			break;
		}
		return map;
	}
	
	private String comments;
	private String column_name;
	private String data_type;
	private String data_length;
	private String data_precision;
	private String data_scale;
	private String nullable;
	private String data_default;
	
	private String cuurent_id;
	private String cuurent_table_id;
	private String cuurent_column_name;
	private String cuurent_data_type;
	private String cuurent_data_length;
	private String cuurent_nullable;
	private String cuurent_data_format;
	private String cuurent_show_order;
	
	
	public List<YuanTableManage> getTableColumn(){
		if (this.tableName == null || this.tableName.equals("")) {
			return new ArrayList<YuanTableManage>();
		}
		
		if (this.parentId.equals("") || this.parentId.equals("ROOT")) {
			return new ArrayList<YuanTableManage>();
		}
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select a.table_name,a.column_id,b.comments, a.column_name, a.data_type,a.data_length,a.data_precision, a.data_scale, a.nullable, a.data_default" +
				"	from user_tab_columns a,user_col_comments b where a.column_name = b.column_name and a.table_name = b.table_name and a.table_name='"+this.tableName.toUpperCase()+"'" +
						" and a.column_name!='CREATE_TIME' and a.column_name!='DATASOURCE_RECORDS_ID' and a.column_name!='NORMAL_ID' order by a.column_name ");
		
		List<YuanTableManage> list2 = new ArrayList<YuanTableManage>();
		
		Map<String, YuanTableColumnManage> tcMap = getTableColumnManage();
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			YuanTableManage vo = new YuanTableManage();
			vo.setComments((String)map.get("comments"));
			vo.setColumn_name((String)map.get("column_name"));
			vo.setData_type((String)map.get("data_type"));
			vo.setData_length(String.valueOf((BigDecimal)map.get("data_length") == null ? "":(BigDecimal)map.get("data_length")));
			vo.setData_precision(String.valueOf((BigDecimal)map.get("data_precision") == null ? "":(BigDecimal)map.get("data_precision")));
			vo.setData_scale(String.valueOf((BigDecimal)map.get("data_scale") == null ? "":(BigDecimal)map.get("data_scale")));
			vo.setNullable((String)map.get("nullable"));
			vo.setData_default((String)map.get("data_default") == null ? "":((String)map.get("data_default")).replace("null", ""));
			
			String cn = (String)map.get("column_name");
			YuanTableColumnManage ycm = tcMap.get(cn);
			if (ycm != null) {
				vo.setCuurent_id(ycm.getId());
				vo.setCuurent_table_id(ycm.getTableId());
				vo.setCuurent_column_name(ycm.getColumnName());
				vo.setCuurent_data_type(ycm.getDataType());
				vo.setCuurent_data_length(String.valueOf(ycm.getDataLength()));
				vo.setCuurent_nullable(ycm.getNullable());
				vo.setCuurent_data_format(ycm.getDataFormat());
				vo.setCuurent_show_order(String.valueOf(ycm.getShowOrder()));
			}
			list2.add(vo);
		}
		return list2;
	}
	
	
	public Map<String, YuanTableColumnManage> getTableColumnManage(){
		
		if (this.tableName == null || this.tableName.equals("")) {
			return new HashMap<String, YuanTableColumnManage>();
		}
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select t.* from yuan_table_column_manage t where t.table_id = '"+this.tableName+"'");
		
		Map<String, YuanTableColumnManage> listMap = new HashMap<String, YuanTableColumnManage>();
		
		
		for (int i = 0; i < list.size(); i++) {
			YuanTableColumnManage vo = new YuanTableColumnManage();
			Map<String, Object> map = list.get(i);
			
			String cuurentId = (String)map.get("ID");
			String tableId = (String)map.get("TABLE_ID");
			String columnName = (String)map.get("COLUMN_NAME");
			String nullable = (String)map.get("NULLABLE");
			String dataType = (String)map.get("DATA_TYPE");
			Integer dataLength = ((BigDecimal)map.get("DATA_LENGTH")).intValue();
			String dataFormat = (String)map.get("DATA_FORMAT");
			Integer showOrder = ((BigDecimal)map.get("SHOW_ORDER")).intValue();
			
			vo.setId(cuurentId);
			vo.setTableId(tableId);
			vo.setColumnName(columnName);
			vo.setNullable(nullable);
			vo.setDataType(dataType);
			vo.setDataLength(dataLength);
			vo.setDataFormat(dataFormat);
			vo.setShowOrder(showOrder);
			listMap.put(columnName, vo);
		}
		
		return listMap;
	}
	
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 获取父名称
	 * @author WEIFEI
	 * @date 2016-7-12 下午6:29:55
	 * @return	String
	 */
	public String getParentName(){
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		if (this.parentId == null || this.parentId.equals("")) {
			return "";
		}
		
		List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query("from YuanTableManage where id = '"+this.parentId+"'", 0);
	
		YuanTableManage yuanTableManage = new YuanTableManage();
		
		if (list.size() > 0) {
			yuanTableManage = list.get(0);
		}
		
		return yuanTableManage.getTableName();
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getData_length() {
		return data_length;
	}

	public void setData_length(String data_length) {
		this.data_length = data_length;
	}

	public String getData_precision() {
		return data_precision;
	}

	public void setData_precision(String data_precision) {
		this.data_precision = data_precision;
	}

	public String getData_scale() {
		return data_scale;
	}

	public void setData_scale(String data_scale) {
		this.data_scale = data_scale;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getData_default() {
		return data_default;
	}

	public void setData_default(String data_default) {
		this.data_default = data_default;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDatasourceId() {
		return this.datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getCuurent_data_type() {
		return cuurent_data_type;
	}

	public void setCuurent_data_type(String cuurent_data_type) {
		this.cuurent_data_type = cuurent_data_type;
	}

	public String getCuurent_data_length() {
		return cuurent_data_length;
	}

	public void setCuurent_data_length(String cuurent_data_length) {
		this.cuurent_data_length = cuurent_data_length;
	}

	public String getCuurent_nullable() {
		return cuurent_nullable;
	}

	public void setCuurent_nullable(String cuurent_nullable) {
		this.cuurent_nullable = cuurent_nullable;
	}

	public String getCuurent_data_format() {
		return cuurent_data_format;
	}

	public void setCuurent_data_format(String cuurent_data_format) {
		this.cuurent_data_format = cuurent_data_format;
	}

	public String getCuurent_id() {
		return cuurent_id;
	}

	public void setCuurent_id(String cuurent_id) {
		this.cuurent_id = cuurent_id;
	}

	public String getCuurent_table_id() {
		return cuurent_table_id;
	}

	public void setCuurent_table_id(String cuurent_table_id) {
		this.cuurent_table_id = cuurent_table_id;
	}

	public String getCuurent_column_name() {
		return cuurent_column_name;
	}

	public void setCuurent_column_name(String cuurent_column_name) {
		this.cuurent_column_name = cuurent_column_name;
	}

	public String getCuurent_show_order() {
		return cuurent_show_order;
	}

	public void setCuurent_show_order(String cuurent_show_order) {
		this.cuurent_show_order = cuurent_show_order;
	}

}