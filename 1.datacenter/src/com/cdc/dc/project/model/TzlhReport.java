package com.cdc.dc.project.model;
// default package

import java.math.BigDecimal;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;


/**
 * 投资量化
 * @author WEIFEI
 * @date 2016-8-24 下午6:52:04
 */

public class TzlhReport extends PageQueryForm implements java.io.Serializable {

     private String id;			//ID
     private String A;			//项目编码
     private String B;			//项目名称
     private BigDecimal C;	//计划转资金额汇总（万元）
     private BigDecimal D;	//实际转资金额汇总（万元）
     private BigDecimal E;	//项目转资率
     private BigDecimal F;	//年度资本开支进度计划（万元）
     private BigDecimal G;	//年度完成资本开支（万元）
     private BigDecimal H;	//年度投资计划完成率
     private BigDecimal I;	//项目投资总额（万元）
     private BigDecimal J;	//合同金额（万元）
     private BigDecimal K;	//合同数量
     private BigDecimal L;	//合同完成率
     
     private String YF;			//月份
     private String ND;			//年度
     private String dept;		//部门
     private String ks;			//科室
     private String fzr;			//负责人
     private String yyyymm;	//yyyymm
     
     private Date beginCreateTime;
     private Date endCreateTime;
     
     
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public BigDecimal getC() {
		return C;
	}
	public void setC(BigDecimal c) {
		C = c;
	}
	public BigDecimal getD() {
		return D;
	}
	public void setD(BigDecimal d) {
		D = d;
	}
	public BigDecimal getE() {
		return E;
	}
	public void setE(BigDecimal e) {
		E = e;
	}
	public BigDecimal getF() {
		return F;
	}
	public void setF(BigDecimal f) {
		F = f;
	}
	public BigDecimal getG() {
		return G;
	}
	public void setG(BigDecimal g) {
		G = g;
	}
	public BigDecimal getH() {
		return H;
	}
	public void setH(BigDecimal h) {
		H = h;
	}
	public BigDecimal getI() {
		return I;
	}
	public void setI(BigDecimal i) {
		I = i;
	}
	public BigDecimal getJ() {
		return J;
	}
	public void setJ(BigDecimal j) {
		J = j;
	}
	public BigDecimal getK() {
		return K;
	}
	public void setK(BigDecimal k) {
		K = k;
	}
	public BigDecimal getL() {
		return L;
	}
	public void setL(BigDecimal l) {
		L = l;
	}
	public String getYF() {
		return YF;
	}
	public void setYF(String yF) {
		YF = yF;
	}
	public String getND() {
		return ND;
	}
	public void setND(String nD) {
		ND = nD;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getKs() {
		return ks;
	}
	public void setKs(String ks) {
		this.ks = ks;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public String getYyyymm() {
		return yyyymm;
	}
	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public Date getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

   
	

}