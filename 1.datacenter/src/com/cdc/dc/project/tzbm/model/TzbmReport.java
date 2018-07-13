package com.cdc.dc.project.tzbm.model;
// default package

import java.util.ArrayList;
import java.util.List;

import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.system.core.util.SpringHelper;


/**
 * Tzbm entity. @author MyEclipse Persistence Tools
 */

public class TzbmReport extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;	//投资编码ID
     private String A;	//投资编号
     private String B;	//项目名称
     private String C;	//项目总投资
     private String D;	//资本开支目标
     private String E;	//至上年度安排投资计划
     private String F;		//累计签订
     private String G;	//累计完成
     private String H;	//年度资本开支
     private String I;		//本年度资本开支百分比
     private String J;		//年度转资目标
     private String K;	//本年累计转资
     private String L;		//累计付款
     private String M;	//负责人
     private String N;	//计划任务书
     private String O;	//建设任务书
     
     public Jsxm getJsxm(){
    	 IJsxmService jsxmService = (IJsxmService)SpringHelper.getBean("jsxmService");
     	try {
 			return (Jsxm)jsxmService.findJsxmById(this.id);
 		} catch (Exception e) {
 			return new Jsxm();
 		}
     }

     public String getColumn13Name(){
    	 List<SysUser> list = this.getList13();
    	 String name = "";
    	 for (SysUser sysUser : list) {
    		 name += sysUser.getUserName()+"，";
    	 }
    	 if (!name.equals("")) {
    		 name = name.substring(0, name.length()-1);
		}
    	 return name;
     }
     
     /**
      * 投资项目联系人
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:34
      * @return	List<SysUser>
      */
     public List<SysUser> getList13(){
    	 
    	 if(M == null || M.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column04s = M.split(",");
    	 
    	 for (int i = 0; i < column04s.length; i++) {
    		 if(!column04s[i].equals("")){
        		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column04s[i]);
        		 if (sysuser != null) {
        			 list.add(sysuser);
    			}
    		 }
		 }
    	 return list;
     }

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

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getE() {
		return E;
	}

	public void setE(String e) {
		E = e;
	}

	public String getF() {
		return F;
	}

	public void setF(String f) {
		F = f;
	}

	public String getG() {
		return G;
	}

	public void setG(String g) {
		G = g;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getI() {
		return I;
	}

	public void setI(String i) {
		I = i;
	}

	public String getJ() {
		return J;
	}

	public void setJ(String j) {
		J = j;
	}

	public String getK() {
		return K;
	}

	public void setK(String k) {
		K = k;
	}

	public String getL() {
		return L;
	}

	public void setL(String l) {
		L = l;
	}

	public String getM() {
		return M;
	}

	public void setM(String m) {
		M = m;
	}

	public String getN() {
		return N;
	}

	public void setN(String n) {
		N = n;
	}

	public String getO() {
		return O;
	}

	public void setO(String o) {
		O = o;
	}
     
     
    






}