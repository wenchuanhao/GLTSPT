package com.cdc.dc.account.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysUser;

import com.cdc.dc.account.common.ComtreeTransitionDTO;
import com.cdc.dc.account.form.AccountConfigForm;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.form.FlowInfoForm;
import com.cdc.dc.account.model.AccountConfig;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.AccountInvoice;
import com.cdc.dc.account.model.FlowInfo;
import com.cdc.dc.account.model.ImportTaxContract;
import com.cdc.dc.account.model.PersonalOpinion;
import com.cdc.dc.account.model.ProblemList;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.sys.form.DicFailImportObject;
import com.trustel.common.ItemPage;

public interface IAccountService {
	
	
	/**
	 * 查询发起报账时长、业务审批时长
	 * @param orderid
	 * @return
	 */
	public String queryAging(String orderid);
	
	/**
	 * 查询报账单据状态为初审会计LIST
	 * @return
	 */
	public List<AccountInfo> listQueryAccount();
	
	/**
	 * 查询报账管理员
	 * @return
	 */
	public String queryTrialIsAdmin(String userid);
	
	/**
	 * 查询组织架构
	 * @param id
	 * @return
	 */
	public List<ComtreeTransitionDTO> getDepartment(String id);
	
	/**
	 * 查询费用类型
	 */
	public List<ComtreeTransitionDTO> getCostType(String id);
	
	/**
	 * 查询部门
	 * @param value
	 * @return
	 */
	public List<Object[]> queryDepartment(String value);
	
	/**
	 * 根据哪些用户有信息补录权限
	 */
	public List<Object[]> querySysUser(String pageCode);
	
	/**
	 * 根据userid查找部门
	 * @param userid
	 * @return
	 */
	public String queryDepartmentById(String userid);
	
	/**
	 * 根据ID查找会计
	 * @param costTypeId
	 * @param depId TODO
	 */
	public List<AccountConfig> queryAccountById(String costTypeId, String depId) throws Exception;
	
	/**
	 * 查询费用类型
	 *  
	 */
	public List<RulesType> queryCostType() throws Exception;
	
	/**
	 * 根据ID查询费用类型
	 *  
	 */
	public RulesType queryCostTypeById(String costTypeid) throws Exception;
	
	/**
	 * 查询报账量化单据
	 * @param 
	 */
	public ItemPage queryAccountItemPage(String type,AccountForm form,Map<String,String> map, SysUser visitor) throws Exception;
	
	/**
	 * 查询报账量化单据
	 * @param 
	 */
	public List queryAccountList(String type,AccountForm form,Map<String,String> map, SysUser visitor) throws Exception;
	
	
	/**
	 * 查询导出数据
	 * @param type
	 * @param form
	 * @param map
	 * @param visitor
	 * @return
	 */
	public List<AccountInfo> queryExportData(String type,AccountForm form,Map<String,String> map, SysUser visitor);
	
	/**
	 * 根据ids查询导出数据
	 * @param ids
	 * @return
	 */
	public List<AccountInfo> queryExportDate(String ids);
	
	/**
	 * 保存报账量化单
	 */
	public void addAccount(AccountInfo accountInfo) throws Exception;
	
	/**
	 * 检查是否超时（报账单）
	 * @param id
	 * @return
	 */
	public List<AccountInfo> checkIsOutTime(String id);
	
	/**
	 * 查询初审录入单
	 */
	public ItemPage accountTrialQuery( AccountForm accountForm,String type,SysUser user,String people) throws Exception;
	
	/**
	 * 查询初审录入单
	 */
	public List accountTrialQueryList( AccountForm accountForm,String type,SysUser user,String people) throws Exception;
	
	/**
	 * 保存问题详情
	 */
	public String savePersonalOpinion(String content, String creator, Date createDate, String outline);
	
	/**
	 * 查询问题详情列表
	 */
	public List queryPersonalOpinionList(String userId);
	
	/**
	 * 保存新增问题
	 */
	public void saveProblem(ProblemList problemList);
	
	/**
	 * 删除常用审批意见
	 */
	public void deletePersonalOpinion(String personalOpinionId);
	
	/**
	 * 根据ID查询个人常用意见
	 */
	public PersonalOpinion queryPersonalOpinionById(String personalOpinionId);
	
	/**
     * 获取指定人员的审核意见列表
     * @param user
     * @return
     */
    public List<PersonalOpinion> getPersonalOption(SysUser user);
	
    /**
     * 根据单号查询工单列表
     * @param map TODO
     */
    public List queryAccountWorkorderByIds(AccountForm accountForm,String people,String userId,String type) throws Exception; 
    
    /**
	 * 保存初审会计配置
	 */
	public void saveTrialConfig(AccountConfig accountConfig);
	
	/**
	 * 查找用户
	 */
	public List<SysUser> queSysUserById(String id);
	
	/**
	 * 根据ID查找初审会计配置
	 */
	public AccountConfig findTrialConfigById(String  id);
	
	/**
	 * 查找初审会计列表
	 */
	public ItemPage queryTrialConfigList(AccountConfigForm accountConfig);
	
	/**
	 * 查找信息补录流程列表
	 */
	public ItemPage queryFlowInfoList(FlowInfoForm flowInfoForm);
	
	/**
	 * 查找信息补录流程列表
	 */
	public List<FlowInfo> queryFlowInfoById(String id) throws Exception;
	
	/**
	 * 检查初审会计是否重复
	 * @param config
	 * @return
	 */
	public List<AccountConfig> checkTrialConfig(AccountConfig config);
	
	/**
	 * 删除初审会计配置
	 * @param userId 
	 */
	public void delTrialConfig(String id, String userId);
	
	/**
	 * update初审会计配置
	 * @return 
	 */
	public void updateTrialConfig(AccountConfig config);
	
	/**
	 * update报账量化单
	 * @return 
	 */
	public void updateTrialAccount(AccountInfo info);
	
	/**
	 * 查询报账单
	 * @return 
	 */
	public AccountInfo findTrialAccountByid(String id);
	
	/**
	 * 查询报账退单次数
	 * @return 
	 */
	public List<AccountInfo> findTrialAccountTDTime(String id);
	
	/**
	 * 查询报账单
	 * @return 
	 */
	public ItemPage findTrialAccountByidForm(String id,AccountForm accountForm);
	
	/**
	 * 查找耗时类型
	 * @return
	 */
	public String queryHaoshiType(String id);
	
	/**
	 * 查询问题列表
	 * @return 
	 */
	public List<ProblemList> queryProblemList(String orderid);
	
	/**
	 * 修改问题列表list
	 * @param user TODO
	 * @return 
	 */
	public void updateProblem(ProblemList problemList, SysUser user);
	
	
	/**
	 * 根据orderID查找问题列表
	 * @return 
	 */
	public ProblemList queryProblem(String  id);
	
	/**
	 * 根据报账ID查询流程信息
	 * @param id
	 * @return
	 */
	public List<FlowInfo> queryAccountInfoById(String id);
	
	/**
	 * 信息补录查询列表列表
	 */
	public ItemPage queryInfoCollectList(AccountForm accountForm,String type,SysUser visitor,String people) throws Exception;
	
	/**
	 * 批量保存excel数据
	 * @param visitor
	 * @param file
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public List<DicFailImportObject> saveData(SysUser visitor, String file,
			HttpServletRequest request)
			throws RuntimeException;

	/**
	 * 税务模板信息导入
	 * @param visitor
	 * @param string
	 * @return
	 */
	public List<DicFailImportObject> saveSWData(SysUser visitor, String file);

	/**
	 * 查询税务信息列表
	 * @param flowInfoForm
	 * @return
	 */
	public ItemPage queryTaxInfoList(FlowInfoForm flowInfoForm);

	/**
	 * 根据单号查询税务合同信息
	 * @param orderid
	 * @return
	 */
	public ImportTaxContract queryImportTaxContractById(String orderid,String column03,String column04);

	/**
	 * 保存发票信息
	 * @param aInfo
	 */
	public void saveInvoice(AccountInfo aInfo);

	/**
	 * 根据报账单ID查找发票信息
	 * @param orderid
	 * @return
	 */
	public List<AccountInvoice> getInvoiceList(String id);

	/**
	 * 查询发票信息
	 * @param type 
	 * @param accountForm
	 * @param visitor 
	 * @param map 
	 * @return
	 */
	public ItemPage queryInvoice(String type, AccountForm accountForm, Map<String, String> map, SysUser visitor);
	
	/**
	 * 查询发票信息
	 * @param type 
	 * @param accountForm
	 * @param visitor 
	 * @param map 
	 * @return
	 */
	public List queryInvoiceList(String type, AccountForm accountForm, Map<String, String> map, SysUser visitor);

	/**
	 * 根据Id查找发票信息
	 * @param id
	 * @return
	 */
	public AccountInvoice getInvoiceById(String id);

	/**
	 * 保存发票信息
	 * @param invoice
	 */
	public void saveInvoice(AccountInvoice invoice);

	/**
	 * 更新发票信息
	 * @param invoice
	 */
	public void updateInvoice(AccountInvoice invoice);

	/**
	 * 批量删除发票信息
	 * @param ids
	 * @return 
	 */
	public long batchDelInvoice(String[] ids);

	/**
	 * 联系查询部门
	 * @param orgValue
	 * @return
	 */
	public List<Object[]> searchDepartmenByName(String orgValue);

	public List<ProblemList> queryProblemList(String accountOrderId, String status);

	/**
	 * 删除草稿问题
	 * @param orderid
	 * @param status
	 */
	public void delProblemList(String orderid,String userId, String status);

	public List<ProblemList> queryProblemList(String orderid, String userId, String status);
    
	/**
	 * 问题整改耗时
	 * @param orderid
	 * @return
	 */
	public String queryWtzgSum(String orderid);

	/**
	 * 导出问题单据
	 * @param accountForm
	 * @param type
	 * @param visitor
	 * @param people
	 * @return
	 */
	public List<AccountInfo> exportProblems(AccountForm accountForm, String type, SysUser visitor, String people);

	public List<ImportTaxContract> queryImportTaxContractListByOrderId(String orderid);

}
