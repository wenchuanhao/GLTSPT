package model.sys.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @Description: 系统模块实体类
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-22 上午10:20:33
 * @UpdateRemark:
 * @Version: V1.0
 */
public class SysModule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String moduleId;
	/**
	 * 模块编码（类名）
	 */

	private String moduleCode;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 上级模块的编码
	 */
	private String parentCode;
	
	/**
	 * 是否是菜单
	 */
	private String isMenu;
	
	/**
	 * 菜单图片路径
	 */
	private String imgName;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建人ID
	 */
	private String createrId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建人名称
	 */
	private String createName;
	
	/**
	 * 请求路径
	 */
	private String url;
	
	/**
	 * 显示顺序
	 */
	private int seq;
	
	/**
	 * 菜单级别
	 */
	private int menuLevel;
	
	private String status;
	
	private String description;
	
	/**
	 * 当前菜单的下一级菜单
	 */
	private List<SysModule> nextList = new ArrayList<SysModule>();
	
	public SysModule() {
		
	}
	


	public SysModule(String moduleId, String moduleCode, String moduleName,
			String parentCode, String isMenu, String remark, String createrId,
			Date createTime, String createName, String url, int seq,
			int menuLevel, String status, String description) {
		super();
		this.moduleId = moduleId;
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.parentCode = parentCode;
		this.isMenu = isMenu;
		this.remark = remark;
		this.createrId = createrId;
		this.createTime = createTime;
		this.createName = createName;
		this.url = url;
		this.seq = seq;
		this.menuLevel = menuLevel;
		this.status = status;
		this.description = description;
	}



	public String getImgName() {
		return imgName;
	}



	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public List<SysModule> getNextList() {
		return nextList;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getIsMenu() {
		return this.isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public int getMenuLevel() {
		return menuLevel;
	}
	
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean equals(SysModule obj) {
		if(obj != null && obj.getModuleId() != null && obj.getModuleId().equals(this.getModuleId())){
			return true;
		} else {
			return false;
		}
	}

}