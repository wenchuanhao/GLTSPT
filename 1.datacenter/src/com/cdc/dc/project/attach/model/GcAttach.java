package com.cdc.dc.project.attach.model;
// default package

import java.util.Date;
import java.util.List;

import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;


/**
 * GcAttach entity. @author MyEclipse Persistence Tools
 */

public class GcAttach extends PageQueryForm  implements java.io.Serializable {


    // Fields    
	/**
	 * 业务类型ID
	 */
	public static String TYPE_ID = null;
	/**
	 * 建设项目ID
	 */
	public static String JSXM_ID = null;

     private String id;
     private String parentId;
     private String column01;
     private Date column02;
     private String column03;
     private String column04;
     private String column05;
     private String column06;
     private String column07;
     private String column08;
     private String column09;
     private String column10;
     private String column11;
     private String jsxmId;
     private String deleteFlag;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;

     public List<GcAttJy> getListJy() throws Exception{
    	 
    	 IGcAttachService gcAttachService = (IGcAttachService)SpringHelper.getBean("gcAttachService");
    	 
    	 GcAttJy jy = new GcAttJy();
    	 jy.setColumn07("1");
    	 jy.setParentId(this.id);
    	 ItemPage itemPage = gcAttachService.findGcAttJy(jy);
    	 
    	 return (List<GcAttJy>)itemPage.getItems();
     }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getColumn01() {
        return this.column01;
    }
    
    public void setColumn01(String column01) {
        this.column01 = column01;
    }

    public Date getColumn02() {
        return this.column02;
    }
    
    public void setColumn02(Date column02) {
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

	public String getColumn06() {
		return column06;
	}

	public void setColumn06(String column06) {
		this.column06 = column06;
	}

	public String getColumn07() {
		return column07;
	}

	public void setColumn07(String column07) {
		this.column07 = column07;
	}

	public String getColumn08() {
		return column08;
	}

	public void setColumn08(String column08) {
		this.column08 = column08;
	}

	public String getColumn09() {
		return column09;
	}

	public void setColumn09(String column09) {
		this.column09 = column09;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getJsxmId() {
		return jsxmId;
	}

	public void setJsxmId(String jsxmId) {
		this.jsxmId = jsxmId;
	}

}