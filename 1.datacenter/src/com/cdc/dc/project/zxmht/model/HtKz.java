package com.cdc.dc.project.zxmht.model;
// default package

import java.math.BigDecimal;
import java.util.Date;

import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.system.core.util.SpringHelper;


/**
 * HtKz entity. @author MyEclipse Persistence Tools
 */

public class HtKz extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;
     private String column01;
     private String column02;
     private BigDecimal column03;
     private BigDecimal column04;
     private String column05;
     private String column06;
     private BigDecimal column07;
     private BigDecimal column08;
     private BigDecimal column09;
     private BigDecimal column10;
     private BigDecimal column11;
     private Date column12;
     private String column13;
     private String deleteFlag;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;

     private String htName;
     private Date beginCreateTime;
     private Date endCreateTime;
     private String ks;			//科室

     public String getKs() {
		return ks;
	}

	public void setKs(String ks) {
		this.ks = ks;
	}

	/**
      * 负责人
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:34
      * @return	List<SysUser>
      */
     public SysUser getColumn13SysUser(){
 	   	 if(column13 == null || column13.equals("")){
 	  		 return new SysUser();
 	  	 }
 	  	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
 	  	SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column13);
 		 if (sysuser == null) {
 			 return new SysUser();
 		 }
 		 
 		 return sysuser;
     }
     
    public ZxmHt getZxmHt(){
    	
    	IZxmHtService zxmHtService = (IZxmHtService)SpringHelper.getBean("zxmHtService");
    	
    	try {
			return (ZxmHt)zxmHtService.findZxmHtById(this.column01);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new ZxmHt();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getColumn01() {
        return this.column01;
    }
    
    public void setColumn01(String column01) {
        this.column01 = column01;
    }

    public String getColumn02() {
        return this.column02;
    }
    
    public void setColumn02(String column02) {
        this.column02 = column02;
    }

    public BigDecimal getColumn03() {
        return this.column03;
    }
    
    public void setColumn03(BigDecimal column03) {
        this.column03 = column03;
    }

    public BigDecimal getColumn04() {
        return this.column04;
    }
    
    public void setColumn04(BigDecimal column04) {
        this.column04 = column04;
    }

    public String getColumn05() {
        return this.column05;
    }
    
    public void setColumn05(String column05) {
        this.column05 = column05;
    }

    public String getColumn06() {
        return this.column06;
    }
    
    public void setColumn06(String column06) {
        this.column06 = column06;
    }

    public BigDecimal getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(BigDecimal column07) {
        this.column07 = column07;
    }

    public BigDecimal getColumn08() {
        return this.column08;
    }
    
    public void setColumn08(BigDecimal column08) {
        this.column08 = column08;
    }

    public BigDecimal getColumn09() {
        return this.column09;
    }
    
    public void setColumn09(BigDecimal column09) {
        this.column09 = column09;
    }

    public BigDecimal getColumn10() {
        return this.column10;
    }
    
    public void setColumn10(BigDecimal column10) {
        this.column10 = column10;
    }

    public BigDecimal getColumn11() {
        return this.column11;
    }
    
    public void setColumn11(BigDecimal column11) {
        this.column11 = column11;
    }

    public Date getColumn12() {
        return this.column12;
    }
    
    public void setColumn12(Date column12) {
        this.column12 = column12;
    }

    public String getColumn13() {
        return this.column13;
    }
    
    public void setColumn13(String column13) {
        this.column13 = column13;
    }

    public String getDeleteFlag() {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }
    
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return this.updateUserName;
    }
    
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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


	public String getHtName() {
		return htName;
	}


	public void setHtName(String htName) {
		this.htName = htName;
	}
   








}