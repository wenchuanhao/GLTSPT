package com.cdc.dc.project.zxmht.model;
// default package

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.sys.entity.SysUser;

import org.springframework.jdbc.core.JdbcTemplate;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.dc.project.zxmht.service.IHtKzService;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;


/**
 * ZxmHt entity. @author MyEclipse Persistence Tools
 */

public class ZxmHt  extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;
     private String column01;
     private String column02;
     private String column03;
     private String column04;
     private String column05;
     private String column06;
     private String column07;
     private String column08;
     private BigDecimal column09;
     private BigDecimal column10;
     private BigDecimal column11;
     private String column12;
     private String column13;
     private String column14;
     private String column15;
     private String column16;
     private String column17;
     private String column18;
     private Date column19;
     private String column20;
     private String column21;
     private String column22;
     private String column23;
     private String deleteFlag;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;
     private String xmType;
     
     private Date beginCreateTime;
     private Date endCreateTime;
     private String rlStatus;
     private String ids;
     private String dept;		//部门
     private String ks;			//科室
     private String related;	//与我相关
     
     public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

     public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getKsName() {
		return  GcUtils.getKsName(ks);
	}
	public String getKs() {
		return ks;
	}

	public void setKs(String ks) {
		this.ks = ks;
	}
     public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 累计形象进度/MIS接收金额
	 * @author WEIFEI
	 * @date 2016-10-21 下午4:33:26
	 * @return	String
	 */
	public String getHtKzcolumn07(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		List<Map<String, Object>>  list = jdbcTemplate.queryForList("select sum(column_07)column_07 from gc_htkz where column_01 = '"+this.id+"' group by column_01");
		if(list.size() >0){
			return  (String)(list.get(0).get("column_07")).toString();
		}
		return "0.0";
	}
	
	/**
	 * 累计转资金额
	 * @author WEIFEI
	 * @date 2016-10-21 下午4:33:31
	 * @return	String
	 */
	public String getHtKzcolumn09(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		List<Map<String, Object>>  list = jdbcTemplate.queryForList("select sum(column_09)column_09 from gc_htkz where column_01 = '"+this.id+"' group by column_01");
		if(list.size() >0){
			return  (String)(list.get(0).get("column_09")).toString();
		}
		return "0.0";
	}
	
	/**
	 * 累计付款金额
	 * @author WEIFEI
	 * @date 2016-10-21 下午4:33:36
	 * @return	String
	 */
	public String getHtKzcolumn11(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		List<Map<String, Object>>  list = jdbcTemplate.queryForList("select sum(column_11)column_11 from gc_htkz where column_01 = '"+this.id+"' group by column_01");
		if(list.size() >0){
			return  (String)(list.get(0).get("column_11")).toString();
		}
		return "0.0";
	}
	
    /**
     * 合同开支列表
     * @author WEIFEI
     * @date 2016-8-7 下午3:05:24
     * @return	List<HtKz>
     */
    public List<HtKz> getHtKzList(){
   	 
   	 if(this.id == null || this.id.equals("")){
   		 return new ArrayList<HtKz>();
   	 }
   	 
   	 IHtKzService htKzService = (IHtKzService)SpringHelper.getBean("htKzService");
    	try {
    	HtKz htKz = new HtKz();
    	htKz.setColumn01(this.id);//项目合同编号
    	htKz.setPageSize(Integer.MAX_VALUE - 1);
       	ItemPage itemPage = htKzService.findHtKz(htKz);
       	List<HtKz> list = (List<HtKz>)itemPage.getItems();
			return list;
		} catch (Exception e) {
			return new ArrayList<HtKz>();
		}
    }
	
     public List<ZxmHtAttach> getHtAttach(){
    	 if(this.id == null || this.id.equals("")){
    		 return new ArrayList<ZxmHtAttach>();
    	 }
    	 IZxmHtService zxmHtService = (IZxmHtService)SpringHelper.getBean("zxmHtService");
     	try {
     		ZxmHtAttach zxmHt = new ZxmHtAttach();
        	zxmHt.setParentId(this.id);//子项目编号
        	zxmHt.setPageSize(Integer.MAX_VALUE - 1);
        	ItemPage itemPage = zxmHtService.findZxmHtAttach(zxmHt);
        	List<ZxmHtAttach> list = (List<ZxmHtAttach>)itemPage.getItems();
 			return list;
 		} catch (Exception e) {
 			return new ArrayList<ZxmHtAttach>();
 		}
     }
     
    public Zxm getZxm(){
    	
    	IZxmService zxmService = (IZxmService)SpringHelper.getBean("zxmService");
    	try {
			return (Zxm)zxmService.findZxmById(this.column04);
		} catch (Exception e) {
			return new Zxm();
		}
    }
    
    public String getColumn20Name(){
		 return getColumn20SysUser().getUserName();
   }
    
    /**
     * 合同归属人
     * @author WEIFEI
     * @date 2016-8-15 下午4:37:34
     * @return	List<SysUser>
     */
    public SysUser getColumn20SysUser(){
	   	 if(column20 == null || column20.equals("")){
	  		 return new SysUser();
	  	 }
	  	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
	  	SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column20);
		 if (sysuser == null) {
			 return new SysUser();
		 }
		 
		 return sysuser;
    }
    
    
    public String getColumn21Name(){
   	 List<SysUser> list = this.getList21();
   	 String name = "";
   	 for (SysUser sysUser : list) {
   		 name += sysUser.getUserName()+"，";
   	 }
   	 if (!name.equals("")) {
   		 name = name.substring(0, name.length()-1);
		}
   	 return name;
    }
    
    public String getColumn21Id(){
      	 List<SysUser> list = this.getList21();
      	 String name = "";
      	 for (SysUser sysUser : list) {
      		 name += sysUser.getUserId()+",";
      	 }
      	 if (!name.equals("")) {
      		 name = name.substring(0, name.length()-1);
   		}
      	 return name;
       }
    
    /**
     * 管理员
     * @author WEIFEI
     * @date 2016-8-15 下午4:37:43
     * @return	List<SysUser>
     */
    public List<SysUser> getList21(){
   	 
   	 if(column21 == null ||  column21.equals("")){
   		 return new ArrayList<SysUser>();
   	 }
   	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
   	 List<SysUser> list = new ArrayList<SysUser>();
   	 
   	 String [] column21s = column21.split(",");
   	 
   	 for (int i = 0; i < column21s.length; i++) {
   		 if(!column21s[i].equals("")){
   		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column21s[i]);
   		 if (sysuser != null) {
   			 list.add(sysuser);
			}}
		 }
   	 return list;
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

    public String getColumn03() {
        return this.column03;
    }
    
    public void setColumn03(String column03) {
        this.column03 = column03;
    }

    public String getColumn04() {
        return this.column04;
    }
    
    public void setColumn04(String column04) {
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

    public String getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(String column07) {
        this.column07 = column07;
    }

    public String getColumn08() {
        return this.column08;
    }
    
    public void setColumn08(String column08) {
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

    public String getColumn12() {
        return this.column12;
    }
    
    public void setColumn12(String column12) {
        this.column12 = column12;
    }

    public String getColumn13() {
        return this.column13;
    }
    
    public void setColumn13(String column13) {
        this.column13 = column13;
    }

    public String getColumn14() {
        return this.column14;
    }
    
    public void setColumn14(String column14) {
        this.column14 = column14;
    }

    public String getColumn15() {
        return this.column15;
    }
    
    public void setColumn15(String column15) {
        this.column15 = column15;
    }

    public String getColumn16() {
        return this.column16;
    }
    
    public void setColumn16(String column16) {
        this.column16 = column16;
    }

    public String getColumn17() {
        return this.column17;
    }
    
    public void setColumn17(String column17) {
        this.column17 = column17;
    }

    public String getColumn18() {
        return this.column18;
    }
    
    public void setColumn18(String column18) {
        this.column18 = column18;
    }

    public Date getColumn19() {
        return this.column19;
    }
    
    public void setColumn19(Date column19) {
        this.column19 = column19;
    }

    public String getColumn20() {
        return this.column20;
    }
    
    public void setColumn20(String column20) {
        this.column20 = column20;
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


	public String getXmType() {
		return xmType;
	}


	public void setXmType(String xmType) {
		this.xmType = xmType;
	}

	public String getColumn21() {
		return column21;
	}

	public void setColumn21(String column21) {
		this.column21 = column21;
	}

	public String getRlStatus() {
		return rlStatus;
	}

	public void setRlStatus(String rlStatus) {
		this.rlStatus = rlStatus;
	}

	public String getColumn22() {
		return column22;
	}

	public void setColumn22(String column22) {
		this.column22 = column22;
	}

	public String getColumn23() {
		return column23;
	}

	public void setColumn23(String column23) {
		this.column23 = column23;
	}

}