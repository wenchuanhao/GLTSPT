package org.trustel.service.sql.a;


public class DefaultColumn implements IColumn {
	private String alais;
	private String name;
	private int columnIndex;
	private int type;

	public DefaultColumn(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public DefaultColumn(String name, int type, int columnIndex) {
		this.name = name;
		this.columnIndex = columnIndex;
		this.type = type;
	}

	public DefaultColumn(String name, int type, int columnIndex, String alais) {
		this.name = name;
		this.columnIndex = columnIndex;
		this.type = type;
		this.alais = alais;
	}

	public String getAlais() {
		return alais;
	}

	public String getName() {
		return name;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public int getType() {
		return type;
	}

}
