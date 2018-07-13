package com.cdc.dc.metadata.yuan.model;


/**
 * YuanTableColumnManage entity. @author MyEclipse Persistence Tools
 */

public class YuanTableColumnManage implements java.io.Serializable {

	// Fields

	private String id;
	private String tableId;
	private String columnName;
	private String nullable;
	private String dataType;
	private Integer dataLength;
	private String dataFormat;
	private Integer showOrder;

	// Constructors

	/** default constructor */
	public YuanTableColumnManage() {
	}

	/** full constructor */
	public YuanTableColumnManage(String tableId, String columnName,
			String nullable, String dataType, Integer dataLength,
			String dataFormat, Integer showOrder) {
		this.tableId = tableId;
		this.columnName = columnName;
		this.nullable = nullable;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.dataFormat = dataFormat;
		this.showOrder = showOrder;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getNullable() {
		return this.nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDataLength() {
		return this.dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

}