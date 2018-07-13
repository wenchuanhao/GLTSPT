package com.cdc.dc.project.jsxm.model;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;


/**
 * Jsxm entity. @author MyEclipse Persistence Tools
 */

public class Jsxm extends PageQueryForm  implements java.io.Serializable {


    // Fields    

     private String id;
     private String column01;
     private String column02;
     private String column03;
     private String column04;
     private String column05;
     private String column06;
     private Date column07;
     private String column08;
     private String column09;
     private String column10;
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
      * 投资编码列表
      * @author WEIFEI
      * @date 2016-8-7 下午5:07:47
      * @return	List<Tzbm>
      */
     public List<Tzbm> getTzbmList(){
    	 
    	 if(this.id == null || this.id.equals("")){
    		 return new ArrayList<Tzbm>();
    	 }
    	 
    	 ITzbmService tzbmService = (ITzbmService)SpringHelper.getBean("tzbmService");
     	try {
     		Tzbm tzbm = new Tzbm();
     		tzbm.setColumn04(this.id);//建设项目编号
     		tzbm.setPageSize(Integer.MAX_VALUE - 1);
     		
        	ItemPage itemPage = tzbmService.findTzbm(tzbm);
    		
 			return (List<Tzbm>) itemPage.getItems();
 		} catch (Exception e) {
 			return new ArrayList<Tzbm>();
 		}
     }
     
     public String getColumn04Name(){
    	 List<SysUser> list = this.getList04();
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
      * 建设总控
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:34
      * @return	List<SysUser>
      */
     public List<SysUser> getList04(){
    	 
    	 if(column04 == null || column04.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column04s = column04.split(",");
    	 
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
     
     public String getColumn05Name(){
    	 List<SysUser> list = this.getList05();
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
      * 审核人
      * @author WEIFEI
      * @date 2016-8-15 下午4:37:43
      * @return	List<SysUser>
      */
     public List<SysUser> getList05(){
    	 
    	 if(column05 == null ||  column05.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column05s = column05.split(",");
    	 
    	 for (int i = 0; i < column05s.length; i++) {
    		 if(!column05s[i].equals("")){
    		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column05s[i]);
    		 if (sysuser != null) {
    			 list.add(sysuser);
			}}
		 }
    	 return list;
     }
     
     public String getColumn10Name(){
    	 List<SysUser> list = this.getList10();
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
     public List<SysUser> getList10(){
    	 
    	 if(column10 == null ||  column10.equals("")){
    		 return new ArrayList<SysUser>();
    	 }
    	 IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	 List<SysUser> list = new ArrayList<SysUser>();
    	 
    	 String [] column10s = column10.split(",");
    	 
    	 for (int i = 0; i < column10s.length; i++) {
    		 if(!column10s[i].equals("")){
    		 SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, column10s[i]);
    		 if (sysuser != null) {
    			 list.add(sysuser);
			}}
		 }
    	 return list;
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

    public Date getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(Date column07) {
        this.column07 = column07;
    }

    public String getColumn08() {
        return this.column08;
    }
    
    public void setColumn08(String column08) {
        this.column08 = column08;
    }

    public String getColumn09() {
        return this.column09;
    }
    
    public void setColumn09(String column09) {
        this.column09 = column09;
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

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}
   








}