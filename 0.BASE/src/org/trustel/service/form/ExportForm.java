package org.trustel.service.form;

import org.trustel.util.ParameterFactory;

import java.io.Serializable;

public class ExportForm implements IExportForm,Serializable {

	private int exportSize = 0;

	private int skipSize = 0;

	private String exp;

	public int getExportSize() {
		return exportSize <= 0 ? ParameterFactory.getInt("SYSTEM_EXPORT_ROWS",
				10000) : exportSize;
	}

	/**
	 * 导出时通常需要指定跳过记录数。如导出第二页时，需要跳过第一页
	 */
	public int getSkipSize() {
		return skipSize;
	}

	/**
	 * 目前仅支持导出EXCEL格式
	 */
	public boolean isExport() {
		return "EXCEL".equalsIgnoreCase(exp);
	}

	public void setExp(String export) {
		this.exp = export;
	}

	public void setExportSize(int exportSize) {
		this.exportSize = exportSize;
	}

}
