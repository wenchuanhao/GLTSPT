package com.trustel.algorithm;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class CrossTable {
	public final static String ROW_HEAD = "row_head";
	
	private Hashtable hDimensions;
	private Hashtable contents;
	
	public abstract Object nullObject();
	
	public abstract Object increase(Object base, Object value);
	
	protected CrossTable() {
		hDimensions = new Hashtable();
		contents = new Hashtable();
	}
	
	public void put (String hDimension, String vDimension, Object value) {
		hDimensions.put(hDimension, hDimension);
		
		Hashtable row = (Hashtable) contents.get(vDimension);
		if (row == null) {
			row = new Hashtable();
			row.put(ROW_HEAD, vDimension);
		}
		
		Object content = row.get(hDimension);
		if (content == null)
			row.put(hDimension, value);
		else
			row.put(hDimension, increase(content, value));
		
		contents.put(vDimension, row);
	}
	
	public List getColumnHeads() {
		List columns = new ArrayList();		
		Enumeration en = hDimensions.keys();
		
		while(en.hasMoreElements())
			columns.add(en.nextElement());
		
		return columns;
	}
	
	public List getRows() {
		List rows = new ArrayList();
		
		Enumeration en = contents.keys();
		while(en.hasMoreElements())
			rows.add(contents.get(en.nextElement()));
		
		return rows;
	} 
	
	public Object getValue(Object row, Object columnHead) {
		Object value = ((Map) row).get(columnHead);
		
		return value == null ? nullObject() : value;
	}
}
