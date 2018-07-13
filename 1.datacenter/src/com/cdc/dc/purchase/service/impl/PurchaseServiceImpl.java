package com.cdc.dc.purchase.service.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import bsh.StringUtil;

import com.cdc.dc.account.model.AccountConfig;
import com.cdc.dc.purchase.form.PurchaseDataColumnForm;
import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.Purchase;
import com.cdc.dc.purchase.model.PurchaseColumn;
import com.cdc.dc.purchase.model.PurchaseInfo;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class PurchaseServiceImpl implements IPurchaseService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findJsxm(Purchase purchase) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Purchase.class);
		
		Date beginCreateTime = purchase.getBeginCreateTime();
		Date endCreateTime = purchase.getEndCreateTime();
		
		//创建开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where("column07",beginCreateTime,QueryAction.GT);
		}
		
		//创建结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			query.where("column07",endCreateTime,QueryAction.LE);
		}
		
		//ids查询
		String ids = purchase.getIds();
		if (ids != null && !ids.equals("")) {
			String idS = "";
			for (int i = 0; i < ids.split(",").length; i++) {
				if(!ids.split(",")[i].equals("")){
					idS += ",'"+ids.split(",")[i]+"'";
				}
			}
			query.where(" id in ("+idS.substring(1)+")");
		}
		query.where(" deleteFlag = 'N' ");
		query.orderBy("column07 desc");
		
		return enterpriseService.query(query, purchase);
	}

	@Override
	public Purchase findJsxmById(String id) throws Exception {
		
		return (Purchase)enterpriseService.getById(Purchase.class, id);
	}

	@Override
	public Purchase save(Purchase purchase) throws Exception {
		
		enterpriseService.save(purchase);
		
		return purchase;
	}

	@Override
	public Purchase update(Purchase purchase) throws Exception {
		
		enterpriseService.updateObject(purchase);
		
		return purchase;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Purchase.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public ItemPage queryPurchase(PurchaseForm purchaseForm) throws Exception {
		
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String sql ="select t.*,substr(t.column_ar,1,7)                                          flowmonth," +
		        "calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd')) atime,"+
				"(case when calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd'))<"+
		        "((select t.parameter_value from sys_parameter t where parameter_type_id= (select t.parameter_type_id from sys_parameter_type t where parameter_type_code='JF_THRESHOLD') and t.parameter_code='XQ_TH' )) then "+
				" '正常' else '超时提醒' end)                                                        a_alert,"+
		        "calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_ab,'yyyy-mm-dd'))  ctime,"+
				"round((to_number(column_aw)- to_number(column_n)),2)                              smoney,"+
		        "round((to_number(column_aw)- to_number(column_n))/to_number(column_n) ,2)         frate,"+
				"calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))  dtime,"+
		        "(case when calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))<"+
				"((select t.parameter_value from sys_parameter t where parameter_type_id= (select t.parameter_type_id from sys_parameter_type t where parameter_type_code='JF_THRESHOLD') and t.parameter_code='CG_TH' )) then "+
		        " '正常' else '超时提醒' end)                                                       e_alert,"+
				"calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))  ftime"+
				" from JF_PURCHASE_info t where 1=1 ";
		        
				if(StringUtils.isNotEmpty(purchaseForm.getColumnB())){
					sql += " and t.column_b like '%" + purchaseForm.getColumnB().trim() + "%'";
				}
				if(StringUtils.isNotEmpty(purchaseForm.getColumnC())){
					sql += " and t.column_c like '%" + purchaseForm.getColumnC().trim() + "%'";
				}
				if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
					sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ")";
				}
				if(StringUtils.isNotEmpty(purchaseForm.getColumnN())){
					sql += " and t.column_n like '%" + purchaseForm.getColumnN().trim() + "%'";
				}
				if(StringUtils.isNotEmpty(purchaseForm.getColumnJ())){
					sql += " and t.column_j like '%" + purchaseForm.getColumnJ().trim() + "%'";
				}
				sql += " order by t.update_time desc";
		
		int length = jdbcTemplate.queryForObject("select count(1) from ( " + sql + " )", Integer.class);
		//分页
		sql = "select * from ( select t.*, rownum rn from ( " 
				+ sql
				+ ") t where rownum <= "
				+ purchaseForm.getPageIndex()*purchaseForm.getPageSize()
				+ " ) t where t.rn > "
				+ (purchaseForm.getPageIndex()-1)*purchaseForm.getPageSize();
				
    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
//    	int length = rows.size();
//    	//开始索引
//		int beginIndex = (purchaseForm.getPageIndex() - 1) * purchaseForm.getPageSize();
//		//结束索引
//		int endIndex = purchaseForm.getPageIndex() * purchaseForm.getPageSize() > length ? length : purchaseForm.getPageIndex() * purchaseForm.getPageSize();
//    	
//		List pageList = new ArrayList(purchaseForm.getPageSize());
//		
//		for (int i = beginIndex; i < endIndex; i++) {
//			pageList.add(rows.get(i));
//		}
		//生成分页结果
		ItemPage itemPage = new ItemPage(rows, length, purchaseForm.getPageIndex(),purchaseForm.getPageSize());	
		return itemPage;
	}
    
	
    /**
     * 记录表JF_purchase_column
     */
	@Override
	public PurchaseColumn savePurchaseColumn(PurchaseColumn purchaseColumn) throws Exception {		 
		enterpriseService.save(purchaseColumn);
		return purchaseColumn;
	}
   
	/**
	 * 查询表 JF_purchase_column
	 */
	@Override
	public List<Map<String, Object>> queryPurchaseColumn() throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
    	List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from JF_purchase_column where isDelete ='1' order by column_order");
		return rows;
	}
    
	/**
	 * 表单非覆盖提交
	 */
	@Override
	public void uncoverPurchaseByExcel(List data) throws Exception {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");		
		String sql ="select count(1) from JF_PURCHASE_info where column_aj=? and rownum<2" ;
	    final List filterData = new ArrayList();
		//先过滤掉表中已经存在的记录
		for(int i=0;i<data.size();i++){
			PurchaseInfo purchaseInfo = (PurchaseInfo) data.get(i);
			int j = jdbcTemplate.queryForObject(  
                    sql, new Object[] { purchaseInfo.getColumnAj() }, Integer.class);  
			
			//库里无该条对应记录
			if(j==0){
				filterData.add(purchaseInfo);
			}
		}
		
		//把过滤好的excel记录插入表中
		String insert_sql = getsqlString();
		int[] jj = jdbcTemplate.batchUpdate(insert_sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				return filterData.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				PurchaseInfo purchaseInfo = (PurchaseInfo) filterData.get(i);
				//69个？号
				ps.setString(1,purchaseInfo.getUnits());
				ps.setString(2,purchaseInfo.getOperator());
				ps.setString(3,purchaseInfo.getColumnA());
				ps.setString(4,purchaseInfo.getColumnB());
				ps.setString(5,purchaseInfo.getColumnC());
				ps.setString(6,purchaseInfo.getColumnD());
				ps.setString(7,purchaseInfo.getColumnE());
				ps.setString(8,purchaseInfo.getColumnF());
				ps.setString(9,purchaseInfo.getColumnG());
				ps.setString(10,purchaseInfo.getColumnH());
				ps.setString(11,purchaseInfo.getColumnI());
				ps.setString(12,purchaseInfo.getColumnJ());
				ps.setString(13,purchaseInfo.getColumnK());
				ps.setString(14,purchaseInfo.getColumnL());
				ps.setString(15,purchaseInfo.getColumnM());
				ps.setString(16,purchaseInfo.getColumnN());
				ps.setString(17,purchaseInfo.getColumnO());
				ps.setString(18,purchaseInfo.getColumnP());
				ps.setString(19,purchaseInfo.getColumnQ());
				ps.setString(20,purchaseInfo.getColumnR());
				ps.setString(21,purchaseInfo.getColumnS());
				ps.setString(22,purchaseInfo.getColumnT());
				ps.setString(23,purchaseInfo.getColumnU());
				ps.setString(24,purchaseInfo.getColumnV());
				ps.setString(25,purchaseInfo.getColumnW());
				ps.setString(26,purchaseInfo.getColumnX());
				ps.setString(27,purchaseInfo.getColumnY());
				ps.setString(28,purchaseInfo.getColumnZ());
				ps.setString(29,purchaseInfo.getColumnAa());
				ps.setString(30,purchaseInfo.getColumnAb());
				ps.setString(31,purchaseInfo.getColumnAc());
				ps.setString(32,purchaseInfo.getColumnAd());
				ps.setString(33,purchaseInfo.getColumnAe());
				ps.setString(34,purchaseInfo.getColumnAf());
				ps.setString(35,purchaseInfo.getColumnAg());
				ps.setString(36,purchaseInfo.getColumnAh());
				ps.setString(37,purchaseInfo.getColumnAi());
				ps.setString(38,purchaseInfo.getColumnAj());
				ps.setString(39,purchaseInfo.getColumnAk());
				ps.setString(40,purchaseInfo.getColumnAl());
				ps.setString(41,purchaseInfo.getColumnAm());
				ps.setString(42,purchaseInfo.getColumnAn());
				ps.setString(43,purchaseInfo.getColumnAo());
				ps.setString(44,purchaseInfo.getColumnAp());
				ps.setString(45,purchaseInfo.getColumnAq());
				ps.setString(46,purchaseInfo.getColumnAr());
				ps.setString(47,purchaseInfo.getColumnAs());
				ps.setString(48,purchaseInfo.getColumnAt());
				ps.setString(49,purchaseInfo.getColumnAu());
				ps.setString(50,purchaseInfo.getColumnAv());
				ps.setString(51,purchaseInfo.getColumnAw());
				ps.setString(52,purchaseInfo.getColumnAx());
				ps.setString(53,purchaseInfo.getColumnAy());
				ps.setString(54,purchaseInfo.getColumnAz());
				ps.setString(55,purchaseInfo.getColumnBa());
				ps.setString(56,purchaseInfo.getColumnBb());
				ps.setString(57,purchaseInfo.getColumnBc());
				ps.setString(58,purchaseInfo.getColumnBd());
				ps.setString(59,purchaseInfo.getColumnBe());
				ps.setString(60,purchaseInfo.getColumnBf());
				ps.setString(61,purchaseInfo.getColumnBg());
				ps.setString(62,purchaseInfo.getColumnBh());
				ps.setString(63,purchaseInfo.getColumnBi());
				ps.setString(64,purchaseInfo.getColumnBj());
				ps.setString(65,purchaseInfo.getColumnBk());
				ps.setString(66,purchaseInfo.getColumnBl());
				ps.setString(67,purchaseInfo.getColumnBm());
				ps.setString(68,purchaseInfo.getColumnBn());
				ps.setString(69,purchaseInfo.getColumnBo());			
			}
			
		});
		
	}
	
	/**
	 * excel覆盖提交
	 */
	public void coverPurchaseByExcel(final List<String> list,final List data) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		//先删除库里已经存在的记录
		String sql = "delete from JF_PURCHASE_info where column_aj=?";
		int[] ii = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setString(1, list.get(i));				
			}
			
		});  
		//把数据插入到表中
		String insert_sql = getsqlString();
		int[] jj = jdbcTemplate.batchUpdate(insert_sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				return data.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				PurchaseInfo purchaseInfo = (PurchaseInfo) data.get(i);
				//69个？号
				ps.setString(1,purchaseInfo.getUnits());
				ps.setString(2,purchaseInfo.getOperator());
				ps.setString(3,purchaseInfo.getColumnA());
				ps.setString(4,purchaseInfo.getColumnB());
				ps.setString(5,purchaseInfo.getColumnC());
				ps.setString(6,purchaseInfo.getColumnD());
				ps.setString(7,purchaseInfo.getColumnE());
				ps.setString(8,purchaseInfo.getColumnF());
				ps.setString(9,purchaseInfo.getColumnG());
				ps.setString(10,purchaseInfo.getColumnH());
				ps.setString(11,purchaseInfo.getColumnI());
				ps.setString(12,purchaseInfo.getColumnJ());
				ps.setString(13,purchaseInfo.getColumnK());
				ps.setString(14,purchaseInfo.getColumnL());
				ps.setString(15,purchaseInfo.getColumnM());
				ps.setString(16,purchaseInfo.getColumnN());
				ps.setString(17,purchaseInfo.getColumnO());
				ps.setString(18,purchaseInfo.getColumnP());
				ps.setString(19,purchaseInfo.getColumnQ());
				ps.setString(20,purchaseInfo.getColumnR());
				ps.setString(21,purchaseInfo.getColumnS());
				ps.setString(22,purchaseInfo.getColumnT());
				ps.setString(23,purchaseInfo.getColumnU());
				ps.setString(24,purchaseInfo.getColumnV());
				ps.setString(25,purchaseInfo.getColumnW());
				ps.setString(26,purchaseInfo.getColumnX());
				ps.setString(27,purchaseInfo.getColumnY());
				ps.setString(28,purchaseInfo.getColumnZ());
				ps.setString(29,purchaseInfo.getColumnAa());
				ps.setString(30,purchaseInfo.getColumnAb());
				ps.setString(31,purchaseInfo.getColumnAc());
				ps.setString(32,purchaseInfo.getColumnAd());
				ps.setString(33,purchaseInfo.getColumnAe());
				ps.setString(34,purchaseInfo.getColumnAf());
				ps.setString(35,purchaseInfo.getColumnAg());
				ps.setString(36,purchaseInfo.getColumnAh());
				ps.setString(37,purchaseInfo.getColumnAi());
				ps.setString(38,purchaseInfo.getColumnAj());
				ps.setString(39,purchaseInfo.getColumnAk());
				ps.setString(40,purchaseInfo.getColumnAl());
				ps.setString(41,purchaseInfo.getColumnAm());
				ps.setString(42,purchaseInfo.getColumnAn());
				ps.setString(43,purchaseInfo.getColumnAo());
				ps.setString(44,purchaseInfo.getColumnAp());
				ps.setString(45,purchaseInfo.getColumnAq());
				ps.setString(46,purchaseInfo.getColumnAr());
				ps.setString(47,purchaseInfo.getColumnAs());
				ps.setString(48,purchaseInfo.getColumnAt());
				ps.setString(49,purchaseInfo.getColumnAu());
				ps.setString(50,purchaseInfo.getColumnAv());
				ps.setString(51,purchaseInfo.getColumnAw());
				ps.setString(52,purchaseInfo.getColumnAx());
				ps.setString(53,purchaseInfo.getColumnAy());
				ps.setString(54,purchaseInfo.getColumnAz());
				ps.setString(55,purchaseInfo.getColumnBa());
				ps.setString(56,purchaseInfo.getColumnBb());
				ps.setString(57,purchaseInfo.getColumnBc());
				ps.setString(58,purchaseInfo.getColumnBd());
				ps.setString(59,purchaseInfo.getColumnBe());
				ps.setString(60,purchaseInfo.getColumnBf());
				ps.setString(61,purchaseInfo.getColumnBg());
				ps.setString(62,purchaseInfo.getColumnBh());
				ps.setString(63,purchaseInfo.getColumnBi());
				ps.setString(64,purchaseInfo.getColumnBj());
				ps.setString(65,purchaseInfo.getColumnBk());
				ps.setString(66,purchaseInfo.getColumnBl());
				ps.setString(67,purchaseInfo.getColumnBm());
				ps.setString(68,purchaseInfo.getColumnBn());
				ps.setString(69,purchaseInfo.getColumnBo());			
			}
			
		});  
	}
	
	/**
	 * 拼装sql
	 * @return
	 */
	private String getsqlString(){
		StringBuffer sb = new StringBuffer();
		sb.append("insert into JF_PURCHASE_info(ID,UNITS,OPERATOR,CREATE_TIME,UPDATE_TIME,COLUMN_A,COLUMN_B,COLUMN_C,COLUMN_D,COLUMN_E,COLUMN_F,COLUMN_G,COLUMN_H,COLUMN_I,COLUMN_J,COLUMN_K,COLUMN_L,COLUMN_M,COLUMN_N,COLUMN_O,COLUMN_P,COLUMN_Q,COLUMN_R,COLUMN_S,COLUMN_T,COLUMN_U,COLUMN_V,COLUMN_W,COLUMN_X,COLUMN_Y,COLUMN_Z,COLUMN_AA,COLUMN_AB,COLUMN_AC,COLUMN_AD,COLUMN_AE,COLUMN_AF,COLUMN_AG,COLUMN_AH,COLUMN_AI,COLUMN_AJ,COLUMN_AK,COLUMN_AL,COLUMN_AM,COLUMN_AN,COLUMN_AO,COLUMN_AP,COLUMN_AQ,COLUMN_AR,COLUMN_AS,COLUMN_AT,COLUMN_AU,COLUMN_AV,COLUMN_AW,COLUMN_AX,COLUMN_AY,COLUMN_AZ,COLUMN_BA,COLUMN_BB,COLUMN_BC,COLUMN_BD,COLUMN_BE,COLUMN_BF,COLUMN_BG,COLUMN_BH,COLUMN_BI,COLUMN_BJ,COLUMN_BK,COLUMN_BL,COLUMN_BM,COLUMN_BN,COLUMN_BO) ");
		//去掉最后一个逗号
		sb.append("values(");
		sb.append("sys_guid(),").append("?,").append("?,").append("sysdate,").append("sysdate,");
        for(int i=0;i<67;i++)
		{
			sb.append("?").append(",");
		}
        sb.deleteCharAt(sb.length()-1).append(")");
		return sb.toString();
	}
	
	/**
	 * 获取对应表元数据
	 * @param tableName
	 * @return
	 */
	public  List<String> getTableMetadatas(String tableName){
		List<String> metaList = new ArrayList<String>();
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select * from "+ tableName;
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);  
		SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();  
		int columnCount = sqlRsmd.getColumnCount();  
		for (int i = 1; i <= columnCount; i++) { 
			//System.out.println(sqlRsmd.getColumnName(i));
			metaList.add(sqlRsmd.getColumnName(i)); 
		}  
		return metaList;
		
	}
   
	/**
	 * 页面新增表单提交
	 */
	public void addPurchase(final List<String[]> list, final String account) throws Exception {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		final List<String> tableMetadata = getTableMetadatas("JF_PURCHASE_info");
		String insert_sql = getsql(tableMetadata);
		
		int[] ii = jdbcTemplate.batchUpdate(insert_sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				String[] data = list.get(i);
				final List<String> tableList1 = getTableDatas("column_name","JF_PURCHASE_column");
				final List<String> tableList2 = getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
				ps.setString(1,account);
				//后面有68个？号
				for(int j=2;j<tableMetadata.size()-3-tableList1.size()+tableList2.size();j++)
				{
					//System.out.println(data.get(j-1));
					ps.setString(j, data[j-2]);
										
				}				
			}
			
		});
		
	}
	
	/**
	 * 拼接sql 和excel导入的sql不同
	 * @param tableMetadatas
	 * @return
	 */
	private String getsql(List<String> tableMetadatas){
		StringBuffer sb = new StringBuffer();
		final List<String> tableList1 = getTableDatas("column_name","JF_PURCHASE_column");
		final List<String> tableList2 = getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
		final List<String> tableList3 = getTableDatas("column_type","JF_PURCHASE_column where isDelete='1'");
		sb.append("insert into JF_PURCHASE_info(");
		//由于excel表单模板固定只有67列另外表有4个其他属性
		for(int i=0;i<tableMetadatas.size()-tableList1.size();i++)
		{
			sb.append(tableMetadatas.get(i)).append(",");
		}
		for(int i=0;i<tableList2.size();i++)
		{
			sb.append(tableList2.get(i)).append(",");
		}
		//去掉最后一个逗号
		sb.deleteCharAt(sb.length()-1).append(") values(");
		sb.append("sys_guid(),").append("'NJ',").append("?,").append("sysdate,").append("sysdate,");
        for(int i=5;i<72;i++)
		{
			sb.append("?").append(",");
		}
        for(int i=72;i<tableMetadatas.size()-tableList1.size()+tableList2.size();i++){
        	//字符串
        	if("1".equals(tableList3.get(i-72))){
        		sb.append("?").append(",");
        	}
        	//时间
        	if("2".equals(tableList3.get(i-72))){
        		sb.append("to_date(?,'yyyy-mm-dd')").append(",");
        	}
        } 
        sb.deleteCharAt(sb.length()-1).append(")");
		return sb.toString();		
	}

	@Override
	/**
	 * 根据id获取记录
	 */
	public Map<String, Object> queryPurchaseByid(String id) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
    	Map<String, Object> row = jdbcTemplate.queryForMap("select * from JF_purchase_info where id =?",id);
		return row;
	}
    
	/**
	 * 根据id删除记录
	 */
	@Override
	public void deletePurchaseByid(String id) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql ="delete from JF_purchase_info where id =?";
		jdbcTemplate.update(sql, id);
		
	}
    
	/**
	 * 根据id修改记录
	 */
	@Override
	public void updatePurchaseByid(final String id, final List<String[]> list,final String account) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		final List<String> tableMetadata = getTableMetadatas("JF_PURCHASE_info");
        String update_sql = getUpdateSql(tableMetadata);
		
		int[] ii = jdbcTemplate.batchUpdate(update_sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				String[] data = list.get(i);
				final List<String> tableList1 = getTableDatas("column_name","JF_PURCHASE_column");
				final List<String> tableList2 = getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
				ps.setString(1,account);
				//后面有68个？号
				for(int j=2;j<tableMetadata.size()-3-tableList1.size()+tableList2.size();j++)
				{
					//System.out.println(data.get(j-1));
					ps.setString(j, data[j-2]);
										
				}
				ps.setString(tableMetadata.size()-3-tableList1.size()+tableList2.size(),id);
			}
			
		});
		
		
	}

	private String getUpdateSql(List<String> tableMetadata) {
		StringBuffer sb = new StringBuffer();
		final List<String> tableList1 = getTableDatas("column_name","JF_PURCHASE_column");
		final List<String> tableList2 = getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
		final List<String> tableList3 = getTableDatas("column_type","JF_PURCHASE_column where isDelete='1'");
		sb.append("update JF_PURCHASE_info set operator=?,update_time=sysdate,");
		//表有5个其他属性
		
		for(int i=5;i<72;i++)
		{
			sb.append(tableMetadata.get(i)).append("=?,");
		}
        for(int i=72;i<tableMetadata.size()-tableList1.size()+tableList2.size();i++){
        	//字符串
        	if("1".equals(tableList3.get(i-72))){
        		sb.append(tableMetadata.get(i)).append("=?,");
        	}
        	//时间
        	if("2".equals(tableList3.get(i-72))){
        		
        		sb.append(tableMetadata.get(i)).append("=").append("to_date(?,'yyyy-mm-dd')").append(",");
        	}
        } 
		
		//去掉最后一个逗号
		sb.deleteCharAt(sb.length()-1).append(" where id = ?");

		return sb.toString();
	}
	
	@Override
	public ItemPage findItem(PurchaseForm purchaseForm, boolean flag) {
		QueryBuilder query = new QueryBuilder(PurchaseInfo.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//开始时间
		if(null != purchaseForm.getBeginCreateTime() &&  null == purchaseForm.getEndCreateTime()){			
			purchaseForm.setBeginTime(sdf.format(purchaseForm.getBeginCreateTime()));
			query.where("( to_date(columnO,'yyyy-mm-dd') >= to_date('"+purchaseForm.getBeginTime()+"','yyyy-mm-dd') or  nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) >= to_date('"+purchaseForm.getBeginTime()+"','yyyy-mm-dd'))");
		}
		//结束时间
		if(null == purchaseForm.getBeginCreateTime() &&  null != purchaseForm.getEndCreateTime()){
			purchaseForm.setEndTime(sdf.format(purchaseForm.getEndCreateTime()));
			query.where("( to_date(columnO,'yyyy-mm-dd') <= to_date('"+purchaseForm.getEndTime()+"','yyyy-mm-dd') or  nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) <= to_date('"+purchaseForm.getEndTime()+"','yyyy-mm-dd'))");
		}
		if(null != purchaseForm.getBeginCreateTime() &&  null != purchaseForm.getEndCreateTime()){
			purchaseForm.setBeginTime(sdf.format(purchaseForm.getBeginCreateTime()));
			purchaseForm.setEndTime(sdf.format(purchaseForm.getEndCreateTime()));
			query.where("( (to_date(columnO,'yyyy-mm-dd') >= to_date('"+purchaseForm.getBeginTime()+"','yyyy-mm-dd') and  to_date(columnO,'yyyy-mm-dd') <= to_date('"+purchaseForm.getEndTime()+"','yyyy-mm-dd') ) " +
					" or  " +
					" (nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) >= to_date('"+purchaseForm.getBeginTime()+"','yyyy-mm-dd') and nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) <= to_date('"+purchaseForm.getEndTime()+"','yyyy-mm-dd')) )");
		}
		if( null == purchaseForm.getBeginCreateTime() &&  null == purchaseForm.getEndCreateTime()){
			query.where("( (to_date(columnO,'yyyy-mm-dd') >= trunc(sysdate,'yyyy') and  to_date(columnO,'yyyy-mm-dd') <= trunc(sysdate,'dd') ) " +
					" or  " +
					" (nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and nvl(to_date(columnAs,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd')) )  ");
		}
		
		if(StringUtils.isNotEmpty(purchaseForm.getIds())){
			query.where(" id in ("+purchaseForm.getIds()+") ");
		}
		query.where("columnB", purchaseForm.getColumnB(), QueryAction.LIKE);//经办人
		query.where("columnC", purchaseForm.getColumnC(), QueryAction.LIKE);//需求部门
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			query.where(" columnC in (" + purchaseForm.getManageAparts().trim() + ") ");//需求部门
			//sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		
		if(flag){
			query.where("columnBl", "是", QueryAction.EQUAL);//是否取消
		}else{
			query.where(" (columnBl != '是' or columnBl is null) ");//是否取消
		}
		return enterpriseService.query(query, purchaseForm);
	}


	@Override
	public Long queryTimeBetweenDate(String beginTime, String endTime) {
		String sql ="";
		if(StringUtils.isEmpty(beginTime) && StringUtils.isNotEmpty(endTime)){
			beginTime = "to_char(sysdate,'yyyy-mm-dd')";
			sql = "select calc_workday(to_date("+beginTime+",'yyyy-MM-dd'),to_date('"+endTime+"','yyyy-MM-dd')) from dual";
		}
		else if(StringUtils.isEmpty(endTime) && StringUtils.isNotEmpty(beginTime)){
			endTime = "to_char(sysdate,'yyyy-mm-dd')";
			sql = "select calc_workday(to_date('"+beginTime+"','yyyy-MM-dd'),to_date("+endTime+",'yyyy-MM-dd')) from dual";

		}
		else if(StringUtils.isEmpty(endTime) && StringUtils.isEmpty(beginTime)){
			beginTime = "to_char(sysdate,'yyyy-mm-dd')";
			endTime = "to_char(sysdate,'yyyy-mm-dd')";
			sql = "select calc_workday(to_date("+beginTime+",'yyyy-MM-dd'),to_date("+endTime+",'yyyy-MM-dd')) from dual";

		}
		else{
			sql = "select calc_workday(to_date('"+beginTime+"','yyyy-MM-dd'),to_date('"+endTime+"','yyyy-MM-dd')) from dual";

		}
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List list = query.list();
		if(list != null && list.size() > 0){
			return ((BigDecimal) list.get(0)).longValue();
		}
		return 0L;
	}
    
	/**
	 * 查询此电子采购项目编号是否存在
	 */
	@Override
	public int queryPurchaseByItemid(String itemId) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql ="select count(*) from JF_purchase_info where column_aj =? and rownum<2";
		int i = jdbcTemplate.queryForObject(sql, new Object[] { itemId }, Integer.class);
		return i;
	}
   
	/**
	 * 查询采购角色配置
	 */
	@Override
	public ItemPage queryPurchaseRole(PurchaseForm purchaseForm) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(PurchaseRole.class);
        if(StringUtils.isNotEmpty(purchaseForm.getRoleName()))	{
        	query.where("roleName",purchaseForm.getRoleName(),QueryAction.LIKE);
        }
        if(StringUtils.isNotEmpty(purchaseForm.getManageName()))	{
        	query.where("manageName",purchaseForm.getManageName(),QueryAction.LIKE);
        }
        query.orderBy("create_time",false);
		return enterpriseService.query(query, purchaseForm);
	}
    
	/**
	 * 新增采购角色配置
	 */
	@Override
	public void SavePurchaseRole(PurchaseForm purchaseForm, String orgIds,String orgNames) {
		PurchaseRole purchaseRole =  new PurchaseRole();
		purchaseRole.setManageAcount(purchaseForm.getUserId());
		purchaseRole.setRoleName(purchaseForm.getRoleName());
		purchaseRole.setManageApartId(orgIds);
		purchaseRole.setManageName(purchaseForm.getManageName());
		purchaseRole.setManageApartMent(orgNames);
		purchaseRole.setCreate_time(new Date());
		purchaseRole.setCreate_id(purchaseForm.getOperatorName());
		enterpriseService.save(purchaseRole);
	}

	
	/**
	 * 数据列配置
	 */
	@Override
	public ItemPage queryDataColumn(PurchaseDataColumnForm form) throws Exception{
		QueryBuilder query = new QueryBuilder(PurchaseColumn.class);
		
		//数据列名
		if (StringUtils.isNotEmpty(form.getColumn_name())) {
			query.where("column_name", form.getColumn_name(), QueryAction.LIKE);
		}
		//字段名
		if (StringUtils.isNotEmpty(form.getColumn_cname())) {
			query.where("column_cname", form.getColumn_cname(), QueryAction.LIKE);
		}		
		//文本类型
		if (StringUtils.isNotEmpty(form.getColumn_type())) {
			query.where("column_type", form.getColumn_type(), QueryAction.EQUAL);
		}
		query.where("column_length", form.getColumn_length(), QueryAction.EQUAL);
		query.where("isDelete","1",QueryAction.EQUAL);
		
		query.orderBy("column_order",false);
		
		return enterpriseService.query(query, form);
	}
	
	/**
	 * 根据id删除数据列
	 */
	@Override
	public void deleteDataColumn(PurchaseColumn purchaseColumn){
		purchaseColumn.setIsDelete("0");
		enterpriseService.updateObject(purchaseColumn);
	}
	
	@Override
	public Object getEntity(Class clazz, String id) {
		Session session = enterpriseService.getSessions();
		Object obj = enterpriseService.getById(clazz,id);
		session.clear();
		session = null;
		return  obj;
	}
	
	public  List<String> getTableDatas(String columnName, String tableName){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+columnName+" from "+ tableName +" order by column_order";
		List <Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		List<String> list2 = new ArrayList<String>();
		for (Map<String, Object> m : list){
			for (String k : m.keySet()){
				list2.add((String) m.get(k));
			}
		}
		return list2;
	}
	@Override
	public void delPurchaseRoleByid(String id) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(PurchaseRole.class);
		query.where("id",id,QueryAction.EQUAL);
		enterpriseService.delete(query);		
	}

	@Override
	public PurchaseRole queryPurchaseRoleByid(String id) {
        if(null == id) {
        	return null;
        }        		
		return (PurchaseRole) enterpriseService.getById(PurchaseRole.class, id);
	}

	@Override
	public void updatePurchaseRole(PurchaseForm purchaseForm, String orgIds,String orgNames) {
		PurchaseRole purchaseRole =  new PurchaseRole();
		purchaseRole.setId(purchaseForm.getId());
		purchaseRole.setManageAcount(purchaseForm.getUserId());
		purchaseRole.setRoleName(purchaseForm.getRoleName());
		purchaseRole.setManageApartId(orgIds);
		purchaseRole.setManageName(purchaseForm.getManageName());
		purchaseRole.setManageApartMent(orgNames);
		purchaseRole.setCreate_time(new Date());
		purchaseRole.setCreate_id(purchaseForm.getOperatorName());
		enterpriseService.updateObject(purchaseRole);
		
	}
	
	/**
	 * 批量删除
	 * @param id
	 */
	public void delpurchaseRoleBybatch(String ids) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(PurchaseRole.class);
		
		query.where("id",ids.split("\\|"));
		enterpriseService.delete(query);
		
	}
    /**
     * 根据分管人账号查询
     * @return 
     */
	@Override
	public List<PurchaseRole> queryPurchaseRoleByAcount(String manageAcount) {
		QueryBuilder query = new QueryBuilder(PurchaseRole.class);
		query.where("instr(manageAcount,'"+manageAcount+"')>0");
		
		return (List<PurchaseRole>) enterpriseService.query(query,0);
		
	}

	@Override
	public List<SysOrganization> queryAllColumnC(String username) {
		List<SysOrganization> result = new ArrayList<SysOrganization>();
		String sql = "select distinct(column_c) from JF_PURCHASE_INFO ";
		if(StringUtils.isNotEmpty(username)){
			sql += " where column_b='"+username+"' ";
		}
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List<String> list = query.list();
		if(list != null && list.size() > 0){
			for (String str : list) {
				SysOrganization e = new SysOrganization();
				e.setOrgName(str);
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public void addUserRoleByCode(SysUser sysUser, String rulecode) throws Exception {
		//根据默认角色编号查询角色信息
		SysRole sysrole = null;
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleCode", rulecode, QueryAction.EQUAL);
		List<SysRole> list = (List<SysRole>) enterpriseService.query(query, 0);
		if (list != null && list.size() > 0){
			sysrole = list.get(0);
		}
		if(sysrole == null){
			return;
		}
		

		if(sysUser != null && StringUtils.isNotEmpty(sysUser.getUserId()) && StringUtils.isNotEmpty(sysrole.getRoleId())){
			//重复判断
			SysRoleUser model = getRoleUsersByUserId(sysUser.getUserId(),sysrole.getRoleId());
			if(model == null){
				model = new SysRoleUser();
				model.setRoleId(sysrole.getRoleId());
				model.setUserId(sysUser.getUserId());
				enterpriseService.save(model);
			}
		}
	
	}
	
	public SysRoleUser getRoleUsersByUserId(String userId,String roleId) throws Exception{
		//查询角色id配置
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		query.where("roleId", roleId, QueryAction.EQUAL);
		List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
		if (list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
