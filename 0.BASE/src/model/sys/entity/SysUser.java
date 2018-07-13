package model.sys.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysUser entity<br>
 * 
 * @author sunsf
 */

public class SysUser implements java.io.Serializable {

    /**使用状态    账号安全等级：正常*/
    public static final String STATUS_FREEZE_NORMAL = "0";

    /**使用状态    账号安全等级：禁用*/
    public static final String STATUS_FREEZE_DISABLE = "2";

    /**使用状态    账号安全等级：失效  ---> 待激活*/
    public static final String STATUS_FREEZE_FAIL = "1";

    /**使用状态    账号安全等级：冻结*/
    public static final String STATUS_FREEZE_FAILCOUNT = "3";

    
    /**
     * 账号状态：启用 ---> 正常
     */
    public static final String STATUS_ACTIVATE_ENABLE = "1";

    /**
     * 账号状态：禁用（离职等原因） ---> 已注销
     */
    public static final String STATUS_ACTIVATE_DIS = "0";

    
    
	private String userId;//用户id
	private String userName;//用户名
	private String account;//登录账号
	private String password;//密码
	private String mobile;//用户手机号码
	private String email;//邮箱
	private String isReceiveSms;//是否接受SMS（0 ：否   1：是）
	private String userDefaultRole;//用户默认角色
	private String isActivate;//用户状态（0：禁用 1：启用）
	private String organizationId;//用户所属组织ID
	private String createrId;//创建人
	private Date createTime;//创建时间
	private int	orderNum;//新加的字段：排序字段
	private String freezeStatus;//
	private List<String> privileges = new ArrayList<String>();
	private String dNames;//假字段用来显示部门3级
	//是否展现非IE的提示
	private String isIeTip;
	private String passwordOld;//旧密码
	private String passwordNew;//新密码
	private Date lastUpdateTime;//最近更新时间

    private Long loginFailCount;//连续登陆错误次数
    private Date lastLoginFailDate;//最后登陆失败时间

    private Boolean isDongjie;//是否冻结(非数据库字段)
	
    private String employee;  //员工编号:用户身份唯一标识，2位公司代号+8位员工编号
	private String fullName;  //用户全名
	private String ouGuid;  //Portal组织ID
	private Long ouId;   //组织ID
	private String jobType;  //人员库类型-HR
	private String userType;  //人员库类型-PORTAL:Employee：正式员SocialEmployee：社会化员工;空字符串或NULL时：代维人员或者其他
	private String workPhone;  //工作电话
	private String telePhone;  //
	private String address;   //联系地址
	private Date userBirthday;  //生日
	private String sex;   //性别 1：男，2：女
	private String title;  //职位
	private String orderId;  //排序ID
	private Date userJoininDate;  //加入公司日期
	private String userNation;   //民族
	private String userReligion;  //政治面貌
	private Date userQuitDate;  //离开公司日期
	private Date changeTime;  //LDAP修改日期:用户资料在LDAP中变动的时间
	private Date lastUpdateDate;  //最后修改日期;
	private String isFromWeb;  //是否来源webservice 1：是  0或空：不是
	private String commandRoleId;//工程指令发起角色/组织id
	private String commandRoleName;//工程指令角色/组织名称

	public SysUser() {
	}

    public Date getLastLoginFailDate() {
        return lastLoginFailDate;
    }

    public void setLastLoginFailDate(Date lastLoginFailDate) {
        this.lastLoginFailDate = lastLoginFailDate;
    }

    public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsReceiveSms() {
		return isReceiveSms;
	}

	public void setIsReceiveSms(String isReceiveSms) {
		this.isReceiveSms = isReceiveSms;
	}

	public String getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
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

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public String getUserDefaultRole() {
		return userDefaultRole;
	}

	public void setUserDefaultRole(String userDefaultRole) {
		this.userDefaultRole = userDefaultRole;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	

	
	public String getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public String getdNames() {
		return dNames;
	}

	public void setdNames(String dNames) {
		this.dNames = dNames;
	}

	public String getIsIeTip() {
		return isIeTip;
	}

	public void setIsIeTip(String isIeTip) {
		this.isIeTip = isIeTip;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

    public Long getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Long loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Boolean getIsDongjie() {
        return (getLastLoginFailDate() != null && getLastLoginFailDate().after(DateTime.now().withTimeAtStartOfDay().toDate()) && getLoginFailCount() >= 5);
    }

    public void setIsDongjie(Boolean isDongjie) {
        this.isDongjie = isDongjie;
    }

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getUserJoininDate() {
		return userJoininDate;
	}

	public void setUserJoininDate(Date userJoininDate) {
		this.userJoininDate = userJoininDate;
	}

	public String getUserNation() {
		return userNation;
	}

	public void setUserNation(String userNation) {
		this.userNation = userNation;
	}

	public String getUserReligion() {
		return userReligion;
	}

	public void setUserReligion(String userReligion) {
		this.userReligion = userReligion;
	}

	public Date getUserQuitDate() {
		return userQuitDate;
	}

	public void setUserQuitDate(Date userQuitDate) {
		this.userQuitDate = userQuitDate;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
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

	public String getCommandRoleId() {
		return commandRoleId;
	}

	public void setCommandRoleId(String commandRoleId) {
		this.commandRoleId = commandRoleId;
	}

	public String getCommandRoleName() {
		return commandRoleName;
	}

	public void setCommandRoleName(String commandRoleName) {
		this.commandRoleName = commandRoleName;
	}
    
}