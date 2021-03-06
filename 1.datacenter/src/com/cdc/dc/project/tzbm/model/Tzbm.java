package com.cdc.dc.project.tzbm.model;
// default package

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;


/**
 * Tzbm entity. @author MyEclipse Persistence Tools
 */

public class Tzbm extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;
     private String column01;
     private String column02;
     private String column03;
     private String column04;
     private String column05;
     private BigDecimal column06;
     private String column07;
     private BigDecimal column08;
     private BigDecimal column09;
     private String column10;
     private BigDecimal column11;
     private BigDecimal column12;
     private String column13;
     private String column14;
     private String column15;
     private String column16;
     private Date column17;
     private String column18;
     private String column19;
     private String isNew;
     private Date newDate;     
     private String deleteFlag;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;
     
     private Date beginCreateTime;
     private Date endCreateTime;
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
     public List<Zxm> getZxmList(){
    	 if(this.id == null || this.id.equals("")){
    		 return new ArrayList<Zxm>();
    	 }
    	 
    	 IZxmService zxmService = (IZxmService)SpringHelper.getBean("zxmService");
     	try {
     		Zxm zxm = new Zxm();
     		zxm.setColumn07(this.id);
     		zxm.setPageSize(Integer.MAX_VALUE - 1);
     		
        	ItemPage itemPage = zxmService.findZxm(zxm);
    		
 			return (List<Zxm>) itemPage.getItems();
 		} catch (Exception e) {
 			return new ArrayList<Zxm>();
 		}
     }
     
     public Jsxm getJsxm(){
    	 IJsxmService jsxmService = (IJsxmService)SpringHelper.getBean("jsxmService");
     	try {
 			return (Jsxm)jsxmService.findJsxmById(this.column04);
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
    	 
    	 if(column13 == null || column13.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column04s = column13.split(",");
    	 
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
     
     public String getColumn14Name(){
    	 List<SysUser> list = this.getList14();
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
      * 投资项目督办人
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:43
      * @return	List<SysUser>
      */
     public List<SysUser> getList14(){
    	 
    	 if(column14 == null ||  column14.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column05s = column14.split(",");
    	 
    	 for (int i = 0; i < column05s.length; i++) {
    		 if(!column05s[i].equals("")){
    		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column05s[i]);
    		 if (sysuser != null) {
    			 list.add(sysuser);
			}}
		 }
    	 return list;
     }
     
     
     public String getColumn19Name(){
    	 List<SysUser> list = this.getList19();
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
      * 管理员
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:43
      * @return	List<SysUser>
      */
     public List<SysUser> getList19(){
    	 
    	 if(column19 == null ||  column19.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column19s = column19.split(",");
    	 
    	 for (int i = 0; i < column19s.length; i++) {
    		 if(!column19s[i].equals("")){
    		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column19s[i]);
    		 if (sysuser != null) {
    			 list.add(sysuser);
			}}
		 }
    	 return list;
     } 
     
    public String getId() {
        return this.id;
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

    public BigDecimal getColumn06() {
        return this.column06;
    }
    
    public void setColumn06(BigDecimal column06) {
        this.column06 = column06;
    }

    public String getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(String column07) {
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

    public String getColumn10() {
        return this.column10;
    }
    
    public void setColumn10(String column10) {
        this.column10 = column10;
    }

    public BigDecimal getColumn11() {
        return this.column11;
    }
    
    public void setColumn11(BigDecimal column11) {
        this.column11 = column11;
    }

    public BigDecimal getColumn12() {
        return this.column12;
    }
    
    public void setColumn12(BigDecimal column12) {
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

    public Date getColumn17() {
        return this.column17;
    }
    
    public void setColumn17(Date column17) {
        this.column17 = column17;
    }

    public String getColumn18() {
        return this.column18;
    }
    
    public void setColumn18(String column18) {
        this.column18 = column18;
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

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}
   








}