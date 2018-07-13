package model.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: 组织机构
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-23 下午7:39:14
 * @UpdateRemark:
 * @Version: V1.0
 */

public class SysOrganization implements Serializable {

    /**组织类型:公司*/
    public static final String ORG_TYP_COMPANY = "company";
    /**组织类型:部门*/
    public static final String ORG_TYP_DEPT = "dept";

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String organizationId;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 排序
	 */
	private float orgOrder;
	/**
	 * 上级组织
	 */
	private String parentId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建人
	 */
	private String createrId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 标示
	 */
	private String flag;

    /**组织类型*/
    private String orgType = this.ORG_TYP_DEPT;

    /**组织层级*/
    private Long orgLevel;
    
    private String ouGuid;  //Portal组织ID
    
    private Long ouId;  //组织ID
    
    private String orgState;  //组织状态0：停用   1：启用
    
    //private String ouName;  //组织名称
    
    //private String parentOuguid;  //上级组织ID
    
    private String ouFullname;  //组织全称
    
    //private Long oulevel;  //组织层次
    
    //private String ouOrder;  //组织排序
    
    private Date lastUpdateDate;  //最后修改日期
    
    private String isFromWeb;  //是否来源webservice 1：是  0或空：不是

    public Long getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Long orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public SysOrganization() {
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public float getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(float orgOrder) {
		this.orgOrder = orgOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

//	public String getTemporaryCreateTime() {
//		return temporaryCreateTime;
//	}
//
//	public void setTemporaryCreateTime(String temporaryCreateTime) {
//		this.temporaryCreateTime = temporaryCreateTime;
//	}
//
//	public float getSeq() {
//		return seq;
//	}
//
//	public void setSeq(float seq) {
//		this.seq = seq;
//	}
//
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOuGuid() {
		return ouGuid;
	}

	public void setOuGuid(String ouGuid) {
		this.ouGuid = ouGuid;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	public String getOuFullname() {
		return ouFullname;
	}

	public void setOuFullname(String ouFullname) {
		this.ouFullname = ouFullname;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getIsFromWeb() {
		return isFromWeb;
	}

	public void setIsFromWeb(String isFromWeb) {
		this.isFromWeb = isFromWeb;
	}
	
	
}