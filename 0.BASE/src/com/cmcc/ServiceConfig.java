package com.cmcc;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;

import org.trustel.util.DateUtils;

public class ServiceConfig {
	    public static String sourcesystemid="GLTSPT_NFJDIAP";
	    public static String sourcesystemname="南方基地管理提升平台";
	    public static String userid="GLTSPT_NFJDIAP";
	    public static String username="南方基地管理提升平台";
	    public static XMLGregorianCalendar submitdate= DateUtils.dateToXmlDate(new Date());
	    public static BigDecimal pagesize= new BigDecimal(1);
	    public static BigDecimal currentpage=new BigDecimal(1);
	    public static BigDecimal totalrecord=new BigDecimal(1);
	    public static String provincecode="1";
	    public static String environmentname="1";

}
