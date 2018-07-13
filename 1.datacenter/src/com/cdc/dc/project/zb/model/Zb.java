package com.cdc.dc.project.zb.model;
// default package

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.system.core.util.SpringHelper;


/**
 * Zb entity. @author MyEclipse Persistence Tools
 */

public class Zb extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;
     private String column01;//周报名称
     private String column02;//项目编码
     private String column03;//本周工作
     private String column04;//下周计划
     private String column05;//关键点
     private String column06;//存在问题
     private String column07;//汇报人
     private String column07UserId;//汇报人id
     private Date column08;//汇报周期开始
     private Date column09;//汇报周期结束
     private String column10;//事项状态
     private String column11;//本周状态
     private String column12;//负责人
     private String column12UserId;//负责人id
     private Date column13;//汇报时间
     private String column14;//业务类型,项目类型
     private String deleteFlag;//删除标记
     private Date createDate;//创建时间
     private String updateUserId;//修改人id
     private String updateUserName;//修改人名称
     private Date updateDate;//修改时间
     private String createUserName;//创建人名称
     private String createUserId;//创建人id
     private String projectName; //项目名称
     private String column07Departmen;//汇报人科室id
     private String projectUserid;//项目创建人ID
     private String projectId; //项目ID
     private String column07DepartmenName;//汇报人科室名称
 	private String auditUserid;//审核人id
 	private String auditUsername;//审核人名称
 	private String zbStatus;//周报状态(0:未审核(未发布),1:审核通过(已发布),2:审核不通过)
     
     private String userRoles;//用户角色
     private String zbIds;//批量导出
     private String beginCreateTime;
     private String endCreateTime;
     
     public final static String zbStatus_SH =  "0";//0:未审核(未发布)
     public final static String zbStatus_FB =  "1";//1:审核通过(已发布)
     public final static String zbStatus_TH =  "2";//2:审核不通过（退回）
     public final static String zb_audit =  "ZB_AUDIT";//周报审核员角色
    // Constructors
     private String related;	//与我相关
     
     public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

    /** default constructor */
    public Zb() {
    }

    
    /** full constructor */
    public Zb(String column01, String column02, String column03, String column04, String column05, String column06, String column07, 
    		Date column08, Date column09, String column10, String column11, String column12, Date column13, String column14, 
    		String deleteFlag, Date createDate, String updateUserId, String updateUserName, Date updateDate, String createUserName, 
    		String createUserId, String column07UserId, String column12UserId,String projectName,String projectUserid,String projectId,String column07Departmen
    		,String auditUserid,String auditUsername,String zbStatus) {
        this.column01 = column01;
        this.column02 = column02;
        this.column03 = column03;
        this.column04 = column04;
        this.column05 = column05;
        this.column06 = column06;
        this.column07 = column07;
        this.column08 = column08;
        this.column09 = column09;
        this.column10 = column10;
        this.column11 = column11;
        this.column12 = column12;
        this.column13 = column13;
        this.column14 = column14;
        this.deleteFlag = deleteFlag;
        this.createDate = createDate;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateDate = updateDate;
        this.createUserName = createUserName;
        this.createUserId = createUserId;
        this.column07UserId = column07UserId;
		this.column12UserId = column12UserId;
		this.projectName = projectName;
		this.projectUserid = projectUserid;
		this.projectId = projectId;
		this.column07Departmen = column07Departmen;
		this.auditUserid = auditUserid;
		this.auditUsername = auditUsername;
		this.zbStatus = zbStatus;
    }

    public String getColumn12Name(){
    	IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
    	if(StringUtils.isNotEmpty(this.projectId)){
    		Jsxm jsxm = (Jsxm) enterpriseService.queryEntity(Jsxm.class, this.projectId);
    		if(jsxm != null){
    			return "建设总控";
    		}
    		Tzbm tzbm = (Tzbm) enterpriseService.queryEntity(Tzbm.class, this.projectId);
    		if(tzbm != null){
    			return "投资项目联系人";
    		}
    		Zxm zxm = (Zxm)enterpriseService.queryEntity(Zxm.class, this.projectId);
    		if(zxm != null){
    			return "子项目负责人";
    		}
    		ZxmHt zxmHt = (ZxmHt) enterpriseService.queryEntity(ZxmHt.class, this.projectId);
    		if(zxmHt != null){
    			return "合同归属人";
    		}
    	}
		return "负责人";
	}
   
    // Property accessors

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

    public Date getColumn08() {
        return this.column08;
    }
    
    public void setColumn08(Date column08) {
        this.column08 = column08;
    }

    public Date getColumn09() {
        return this.column09;
    }
    
    public void setColumn09(Date column09) {
        this.column09 = column09;
    }

    public String getColumn10() {
        return this.column10;
    }
    
    public void setColumn10(String column10) {
        this.column10 = column10;
    }

    public String getColumn11() {
        return this.column11;
    }
    
    public void setColumn11(String column11) {
        this.column11 = column11;
    }

    public String getColumn12() {
        return this.column12;
    }
    
    public void setColumn12(String column12) {
        this.column12 = column12;
    }

    public Date getColumn13() {
        return this.column13;
    }
    
    public void setColumn13(Date column13) {
        this.column13 = column13;
    }

    public String getColumn14() {
        return this.column14;
    }
    
    public void setColumn14(String column14) {
        this.column14 = column14;
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


	public String getColumn07UserId() {
		return column07UserId;
	}


	public void setColumn07UserId(String column07UserId) {
		this.column07UserId = column07UserId;
	}


	public String getColumn12UserId() {
		return column12UserId;
	}


	public void setColumn12UserId(String column12UserId) {
		this.column12UserId = column12UserId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getColumn07Departmen() {
		return column07Departmen;
	}


	public void setColumn07Departmen(String column07Departmen) {
		this.column07Departmen = column07Departmen;
	}

	
	
	public String getProjectUserid() {
		return projectUserid;
	}


	public void setProjectUserid(String projectUserid) {
		this.projectUserid = projectUserid;
	}


	public String getColumn07DepartmenName() {
		return column07DepartmenName;
	}

	public void setColumn07DepartmenName(String column07DepartmenName) {
		this.column07DepartmenName = column07DepartmenName;
	}

	public String getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getAuditUserid() {
		return auditUserid;
	}


	public void setAuditUserid(String auditUserid) {
		this.auditUserid = auditUserid;
	}


	public String getAuditUsername() {
		return auditUsername;
	}


	public void setAuditUsername(String auditUsername) {
		this.auditUsername = auditUsername;
	}


	public String getZbStatus() {
		return zbStatus;
	}


	public void setZbStatus(String zbStatus) {
		this.zbStatus = zbStatus;
	}


	public String getZbIds() {
		return zbIds;
	}


	public void setZbIds(String zbIds) {
		this.zbIds = zbIds;
	}


	public String getBeginCreateTime() {
		return beginCreateTime;
	}


	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}


	public String getEndCreateTime() {
		return endCreateTime;
	}


	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
   
}