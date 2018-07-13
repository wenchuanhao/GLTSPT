package com.cdc.dc.report.accountReport.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.account.model.AccountImportInfo;
import com.cdc.dc.report.accountReport.common.Common;
import com.cdc.dc.report.accountReport.service.ILaungchAgingService;
import com.cdc.util.DateUtil;
/**
 * 报账经分实现类
 * @author xms
 *
 */
public class LaungchAgingServiceImpl implements ILaungchAgingService{
	
	private IEnterpriseService enterpriseService;
	SimpleDateFormat fs = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

	private static Map<String,String> linkMap = new HashMap<String,String>();
	static{
		linkMap.put(Common.BZJF_LING_FQBZ, "fqbz_time");
		linkMap.put(Common.BZJF_LING_YWSP, "ywsp_time");
		linkMap.put(Common.BZJF_LING_WTDJZG, "wtdjzg_time");
		linkMap.put(Common.BZJF_LING_NJCWSP, "njcwsp_time");
		linkMap.put(Common.BZJF_LING_JDLDSP, "jdldsp_time");
		linkMap.put(Common.BZJF_LING_SCWSP, "scwsp_time");
		linkMap.put(Common.BZJF_LING_CNZF, "cnzf_time");
	}
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List queryLinkAging(String linkName, String startTime, String endTime,String type) {
		StringBuilder sb = new StringBuilder();
		String str= "";
		if(linkName.equals(Common.BZJF_LING_FQBZ)){
			//modify by ywc 发起报账时间=提交电子报账单时间 -发票时间
			str = " sum(calc_workday(a.reach_sement,to_date(d.SUBMITER_DATE, 'yyyy-mm-dd hh24:mi:ss'))) / count(1) ";
		} else if(linkName.equals(Common.BZJF_LING_YWSP)){
			//modify by ywc 业务审批时间 = 取晚（大）值(部门领导审批完成时间，纸质提交财务时间)-提交电子报账单完成时间
			str = " sum(calc_workday(to_date(d.SUBMITER_DATE, 'yyyy-mm-dd hh24:mi:ss'),case when to_date(d.BMLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss') - a.page_submit_date > 0  then to_date(d.BMLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss') else a.page_submit_date end )) /count(1)";
		} else if(linkName.equals(Common.BZJF_LING_WTDJZG)){
			//str = "sum(a.rectify_time) / count(1)";
			//modify by ywc 最后一条问题的整改时间  - 第一个问题的通知时间（创建时间）
			str = " sum((select calc_workday(min(t.start_time),max(t.end_time)) from problem_list t where t.account_order_id = a.order_id )) / count(1)";
		} else if(linkName.equals(Common.BZJF_LING_NJCWSP)){
			String tempstr = " sum(case  when (select count(*)   from problem_list p  where p.end_time is not null   and p.account_order_id = a.order_id  ) > 0 then   case when ((decode(sign(to_date(d.zbkjapprove_date, 'yyyy-mm-dd hh24:mi:ss') -   to_date(d.CWJLAPPROVE_DATE,                                        'yyyy-mm-dd hh24:mi:ss')),                           -1,                           to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),                           to_date(d.zbkjapprove_date, 'yyyy-mm-dd hh24:mi:ss'))) >                  (trunc(decode(sign(to_date(d.zbkjapprove_date,                                              'yyyy-mm-dd hh24:mi:ss') -                                      to_date(d.CWJLAPPROVE_DATE,                                              'yyyy-mm-dd hh24:mi:ss')),                                 -1,                                 to_date(d.CWJLAPPROVE_DATE,                                         'yyyy-mm-dd hh24:mi:ss'),                                 to_date(d.zbkjapprove_date,                                         'yyyy-mm-dd hh24:mi:ss')),                          'mm') + 4) and                  (select max(p.end_time)                      from problem_list p                     where p.end_time is not null                       and p.account_order_id = a.order_id) <                  (trunc(add_months(decode(sign(to_date(d.zbkjapprove_date,                                                         'yyyy-mm-dd hh24:mi:ss') -                                                 to_date(d.CWJLAPPROVE_DATE,                                                         'yyyy-mm-dd hh24:mi:ss')),                                            -1,                                            to_date(d.CWJLAPPROVE_DATE,                                                    'yyyy-mm-dd hh24:mi:ss'),                                            to_date(d.zbkjapprove_date,                                                    'yyyy-mm-dd hh24:mi:ss')),                                     -1),                          'mm') + 26)) then              calc_workday((select max(p.end_time)                             from problem_list p                            where p.end_time is not null                              and p.account_order_id = a.order_id),                           decode(sign(to_date(d.zbkjapprove_date,                                               'yyyy-mm-dd hh24:mi:ss') -                                       to_date(d.CWJLAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss')),                                  -1,                                  to_date(d.CWJLAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss'),                                  to_date(d.zbkjapprove_date,                                          'yyyy-mm-dd hh24:mi:ss'))) -               calc_workday((trunc(add_months(decode(sign(to_date(d.zbkjapprove_date,                                                                 'yyyy-mm-dd hh24:mi:ss') -                                                         to_date(d.CWJLAPPROVE_DATE,                                                                 'yyyy-mm-dd hh24:mi:ss')),                                                    -1,                                                    to_date(d.CWJLAPPROVE_DATE,                                                            'yyyy-mm-dd hh24:mi:ss'),                                                    to_date(d.zbkjapprove_date,                                                            'yyyy-mm-dd hh24:mi:ss')),                                             -1),                                  'mm') + 26),                           (trunc(decode(sign(to_date(d.zbkjapprove_date,                                                      'yyyy-mm-dd hh24:mi:ss') -                                              to_date(d.CWJLAPPROVE_DATE,                                                      'yyyy-mm-dd hh24:mi:ss')),                                         -1,                                         to_date(d.CWJLAPPROVE_DATE,                                                 'yyyy-mm-dd hh24:mi:ss'),                                         to_date(d.zbkjapprove_date,                                                 'yyyy-mm-dd hh24:mi:ss')),                                  'mm') + 4))             else              calc_workday((select max(p.end_time)                             from problem_list p                            where p.end_time is not null                              and p.account_order_id = a.order_id),                           decode(sign(to_date(d.zbkjapprove_date,                                               'yyyy-mm-dd hh24:mi:ss') -                                       to_date(d.CWJLAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss')),                                  -1,                                  to_date(d.CWJLAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss'),                                  to_date(d.zbkjapprove_date,                                          'yyyy-mm-dd hh24:mi:ss')))           end          else           case             when ((decode(sign(to_date(d.zbkjapprove_date,                                        'yyyy-mm-dd hh24:mi:ss') -                                to_date(d.CWJLAPPROVE_DATE,                                        'yyyy-mm-dd hh24:mi:ss')),                           -1,                           to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),                           to_date(d.zbkjapprove_date, 'yyyy-mm-dd hh24:mi:ss'))) >                  (trunc(decode(sign(to_date(d.zbkjapprove_date,                                              'yyyy-mm-dd hh24:mi:ss') -                                      to_date(d.CWJLAPPROVE_DATE,                                              'yyyy-mm-dd hh24:mi:ss')),                                 -1,                                 to_date(d.CWJLAPPROVE_DATE,                                         'yyyy-mm-dd hh24:mi:ss'),                                 to_date(d.zbkjapprove_date,                                         'yyyy-mm-dd hh24:mi:ss')),                          'mm') + 4) and                  (decode(sign(to_date(d.BMLDAPPROVE_DATE,                                        'yyyy-mm-dd hh24:mi:ss') -                                a.page_submit_date),                           -1,                           a.page_submit_date,                           to_date(d.BMLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'))) <                  (trunc(add_months(decode(sign(to_date(d.zbkjapprove_date,                                                         'yyyy-mm-dd hh24:mi:ss') -                                                 to_date(d.CWJLAPPROVE_DATE,                                                         'yyyy-mm-dd hh24:mi:ss')),                                            -1,                                            to_date(d.CWJLAPPROVE_DATE,                                                    'yyyy-mm-dd hh24:mi:ss'),                                            to_date(d.zbkjapprove_date,                                                    'yyyy-mm-dd hh24:mi:ss')),                                     -1),                          'mm') + 26)) then              calc_workday(decode(sign(to_date(d.BMLDAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss') -                                       a.page_submit_date),                                  -1,                                  a.page_submit_date,                                  to_date(d.BMLDAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss')),                           decode(sign(to_date(d.zbkjapprove_date,                                               'yyyy-mm-dd hh24:mi:ss') -                                       to_date(d.CWJLAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss')),                                  -1,                                  to_date(d.CWJLAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss'),                                  to_date(d.zbkjapprove_date,                                          'yyyy-mm-dd hh24:mi:ss'))) -              calc_workday((trunc(add_months(decode(sign(to_date(d.zbkjapprove_date,                                                                 'yyyy-mm-dd hh24:mi:ss') -                                                         to_date(d.CWJLAPPROVE_DATE,                                                                 'yyyy-mm-dd hh24:mi:ss')),                                                    -1,                                                    to_date(d.CWJLAPPROVE_DATE,                                                            'yyyy-mm-dd hh24:mi:ss'),                                                    to_date(d.zbkjapprove_date,                                                            'yyyy-mm-dd hh24:mi:ss')),                                             -1),                                  'mm') + 26),                           (trunc(decode(sign(to_date(d.zbkjapprove_date,                                                      'yyyy-mm-dd hh24:mi:ss') -                                              to_date(d.CWJLAPPROVE_DATE,                                                      'yyyy-mm-dd hh24:mi:ss')),                                         -1,                                         to_date(d.CWJLAPPROVE_DATE,                                                 'yyyy-mm-dd hh24:mi:ss'),                                         to_date(d.zbkjapprove_date,                                                 'yyyy-mm-dd hh24:mi:ss')),                                  'mm') + 4))             else              calc_workday(decode(sign(to_date(d.BMLDAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss') -                                       a.page_submit_date),                                  -1,                                  a.page_submit_date,                                  to_date(d.BMLDAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss')),                           decode(sign(to_date(d.zbkjapprove_date,                                               'yyyy-mm-dd hh24:mi:ss') -                                       to_date(d.CWJLAPPROVE_DATE,                                               'yyyy-mm-dd hh24:mi:ss')),                                  -1,                                  to_date(d.CWJLAPPROVE_DATE,                                          'yyyy-mm-dd hh24:mi:ss'),                                  to_date(d.zbkjapprove_date,                                          'yyyy-mm-dd hh24:mi:ss')))           end        end ) / count(1) ";
			if(type.equals("部门")){
				sb.append("select a.department, " + tempstr);
				sb.append(" from  account_tab a  ");
				sb.append("  left join import_flow_info d on a.order_id=d.order_id  left join (select t1.order_id, max(t3.end_time) max_end_time  from account_tab t1");
				sb.append(" join import_flow_info t2 on t1.order_id = t2.order_id join problem_list t3 on t1.order_id = t3.account_order_id");
				sb.append(" group by t1.order_id) t4 on a.order_id=t4.order_id where a.RECORD_FLAT='2' and a.TDTIME='0' and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm')   and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm')  group by a.department");
			}else if(type.equals("费用")){
				sb.append("select c.type_name,c.parent_type_name," + tempstr);
				sb.append(" from  account_tab a  left join rules_type c");
				sb.append(" on a.cos_id=c.type_id left join import_flow_info d on a.order_id=d.order_id  left join (select t1.order_id, max(t3.end_time) max_end_time  from account_tab t1");
				sb.append(" join import_flow_info t2 on t1.order_id = t2.order_id join problem_list t3 on t1.order_id = t3.account_order_id");
				sb.append(" group by t1.order_id) t4 on a.order_id=t4.order_id where a.RECORD_FLAT='2' and a.TDTIME='0' and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm')   and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm')  group by c.type_name,c.parent_type_name");
			}else if(type.equals("问题类型")){
				sb.append(" select e.parameter_value," + tempstr);
				sb.append(" from  account_tab a left join problem_list b on a.order_id=b.account_order_id  left join rules_type c");
				sb.append(" on a.cos_id=c.type_id left join import_flow_info d on a.order_id=d.order_id  left join (select t1.order_id, max(t3.end_time) max_end_time  from account_tab t1");
				sb.append(" join import_flow_info t2 on t1.order_id = t2.order_id join problem_list t3 on t1.order_id = t3.account_order_id");
				sb.append(" group by t1.order_id) t4 on a.order_id=t4.order_id  left join sys_parameter e on b.type=e.parameter_code");
				sb.append(" where a.RECORD_FLAT='2' and b.type is not null  and a.TDTIME='0' and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm')   and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm')  group by e.parameter_value");
			}
		}else if(linkName.equals(Common.BZJF_LING_JDLDSP)){
			str = " sum(calc_workday(decode(sign(to_date(d.zbkjapprove_date,'yyyy-mm-dd hh24:mi:ss') - to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss')), -1,to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),to_date(d.zbkjapprove_date,'yyyy-mm-dd hh24:mi:ss')),to_date(d.NJLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'))) / count(1)";
		}else if(linkName.equals(Common.BZJF_LING_SCWSP)){
			str = "sum(calc_workday(nvl(to_date(d.NJLDAPPROVE_DATE,'yyyy-mm-dd hh24:mi:ss') , decode(sign(to_date(d.zbkjapprove_date,'yyyy-mm-dd hh24:mi:ss') - to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss')), -1,to_date(d.CWJLAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),to_date(d.zbkjapprove_date,'yyyy-mm-dd hh24:mi:ss')) ),to_date(d.SCWAPPROVE_DATE,'yyyy-mm-dd hh24:mi:ss'))) / count(1)";
		}else if(linkName.equals(Common.BZJF_LING_CNZF)){
			str = "sum(calc_workday(to_date(d.SCWAPPROVE_DATE,'yyyy-mm-dd hh24:mi:ss'),to_date(d.CNFKAPPROVE_DATE,'yyyy-mm-dd hh24:mi:ss'))) / count(1)";
		}
		if(!linkName.equals(Common.BZJF_LING_NJCWSP)){
			if(type.equals("部门")){
				sb.append(" select a.department,"+str+" from  account_tab a  ");
				sb.append("  left join ");
				sb.append(" import_flow_info d on a.order_id=d.order_id where a.RECORD_FLAT='2' and  ");
				sb.append(" to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm')   and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm')  ");
				sb.append(" and a.TDTIME='0' group by a.department ");
			}else if(type.equals("费用")){
				sb.append(" select c.type_name,c.parent_type_name,"+str+" from  account_tab a ");
				sb.append(" left join rules_type c on a.cos_id=c.type_id left join import_flow_info d on a.order_id=d.order_id where a.RECORD_FLAT='2' and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm') ");
				sb.append("  and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm') and a.TDTIME='0' group by c.type_name,c.parent_type_name ");
			}else if(type.equals("问题类型")){
				sb.append(" select e.parameter_value,"+str+" from  account_tab a left join problem_list b  ");
				sb.append("  on a.order_id=b.account_order_id ");
				sb.append(" left join import_flow_info d on a.order_id=d.order_id left join sys_parameter e on b.type=e.parameter_code ");
				sb.append("  where a.RECORD_FLAT='2' and  ");
				sb.append(" to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')>=to_date('"+startTime+"','yyyy-mm') and b.type is not null   and to_date(to_char(to_date(d.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm'),'yyyymm')<=to_date('"+endTime+"','yyyy-mm')  ");
				sb.append(" and a.TDTIME='0' group by e.parameter_value ");
			}
		}
		
		Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		return qeury.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAgingData(String startDate, String endDate, String link,
			String dimensionKey, String dimensionValue, int compareType) {
		List<String> dateList = DateUtil.getTimePeriods(startDate,endDate,compareType,6);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select d.*, case when nvl(lag(d.num_avg) over(order by d.dt),0) != 0 then round((d.num_avg-lag(d.num_avg) over(order by d.dt))/lag(d.num_avg) over(order by d.dt)*100,2) || '%' else '0%' end from ( ");
		
		for(int i = 0; i < dateList.size(); i++){
			String[] dateArr = dateList.get(i).split("-");
			
			if(i != 0){
				sb.append(" union all ");
			}
			
			if(StringUtils.isNotBlank(dimensionKey)){
				//部门维度
				if("department".equals(dimensionKey)){
					sb.append("select cast(t3.dt as varchar2(13)) dt,t3.department_id,t3.department, ")
					.append("       nvl(t5.num_count, 0) num_count,nvl(t5.num_sum, 0) num_sum,nvl(t5.num_avg, 0) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t1.department_id,t1.department ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("         where t1.department_id = '"+dimensionValue+"') t3 ")
					.append("  left join (select t4.department_id,count(1) num_count,sum(t4."+linkMap.get(link)+") num_sum,round(avg(t4."+linkMap.get(link)+"),2) num_avg ")
					.append("               from view_all_link_aging t4 ")
					.append("              where to_char(t4.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                and to_char(t4.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                and t4.department_id = '"+dimensionValue+"' ")
					.append("              group by t4.department_id, t4.department) t5 ")
					.append("    on t3.department_id = t5.department_id ");
				}
				//一级费用维度
				else if("parent_cos".equals(dimensionKey)){
					sb.append("select cast(t3.dt as varchar2(13)) dt,t3.parent_type_id,t3.parent_type_name, ")
					.append("       nvl(t5.num_count, 0) num_count,nvl(t5.num_sum, 0) num_sum,nvl(t5.num_avg, 0) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t2.parent_type_id,t2.parent_type_name ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("         where t2.parent_type_id = '"+dimensionValue+"') t3 ")
					.append("  left join (select t4.parent_type_id,count(1) num_count,sum(t4."+linkMap.get(link)+") num_sum,round(avg(t4."+linkMap.get(link)+"),2) num_avg ")
					.append("               from view_all_link_aging t4 ")
					.append("              where to_char(t4.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                and to_char(t4.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                and t4.parent_type_id = '"+dimensionValue+"' ")
					.append("              group by t4.parent_type_id, t4.parent_type_name) t5 ")
					.append("    on t3.parent_type_id = t5.parent_type_id ");
				}
				//二级费用维度
				else if("cos".equals(dimensionKey)){
					sb.append("select cast(t3.dt as varchar2(13)) dt,t3.type_id,t3.type_name, ")
					.append("       nvl(t5.num_count, 0) num_count,nvl(t5.num_sum, 0) num_sum,nvl(t5.num_avg, 0) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t2.type_id,t2.type_name ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("         where t2.type_id = '"+dimensionValue+"') t3 ")
					.append("  left join (select t4.type_id,count(1) num_count,sum(t4."+linkMap.get(link)+") num_sum,round(avg(t4."+linkMap.get(link)+"),2) num_avg ")
					.append("               from view_all_link_aging t4 ")
					.append("              where to_char(t4.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                and to_char(t4.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                and t4.type_id = '"+dimensionValue+"' ")
					.append("              group by t4.type_id, t4.type_name) t5 ")
					.append("    on t3.type_id = t5.type_id ");
				}
				//问题类型维度，且该环节为问题整改环节
				else if("problem".equals(dimensionKey) && Common.BZJF_LING_WTDJZG.equals(link)){
					sb.append("select cast(t4.dt as varchar2(13)) dt,t4.parameter_code,t4.parameter_name, ")
					.append("       nvl(t6.num_total, 0) num_total,nvl(t6.num_sum, 0) num_sum,nvl(t6.num_avg, 0) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t4.parameter_code,t4.parameter_name ")
					.append("          from sys_parameter t4 ")
					.append("         where t4.parameter_code = '"+dimensionValue+"') t4 ")
					.append("  left join (select t5.parameter_code,t5.parameter_name, ")
					.append("                    count(1) num_total,sum(t5.set_time) num_sum,round(avg(t5.set_time), 2) num_avg ")
					.append("               from (select distinct t1.order_id,t1.payment_time,t3.id,t4.parameter_code,t4.parameter_name,nvl(t3.set_time, 0) set_time ")
					.append("                       from account_tab t1 ")
					.append("                       join import_flow_info t2 on t1.order_id = t2.order_id ")
					.append("                       join problem_list t3 on t1.order_id = t3.account_order_id ")
					.append("                       join sys_parameter t4 on t3.type = t4.parameter_code ")
					.append("                      where t1.record_flat = '2' and t1.tdtime = '0' ")
					.append("                        and to_char(to_date(t2.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("                        and to_char(to_date(t2.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t5 ")
					.append("              group by t5.parameter_code, t5.parameter_name) t6 ")
					.append("    on t4.parameter_code = t6.parameter_code ");

				}
				//问题类型维度
				else if("problem".equals(dimensionKey)){
					sb.append("select cast(t2.dt as varchar2(13)) dt, t2.parameter_code, t2.parameter_name, ")
					.append("       nvl(t7.num_count, 0) num_count, nvl(t7.num_sum, 0) num_sum, nvl(t7.num_avg, 0) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt, t1.parameter_code, t1.parameter_name ")
					.append("          from sys_parameter t1 where t1.parameter_code = '"+dimensionValue+"') t2 ")
					.append("  left join (select t6.parameter_code, count(1) num_count, sum(t6."+linkMap.get(link)+") num_sum, avg(t6."+linkMap.get(link)+") num_avg ")
					.append("               from (select distinct t3.*, t5.parameter_code, t5.parameter_name ")
					.append("                       from view_all_link_aging t3 ")
					.append("                       join problem_list t4 on t3.order_id = t4.account_order_id ")
					.append("                       join sys_parameter t5 on t4.type = t5.parameter_code ")
					.append("                      where to_char(t3.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                        and to_char(t3.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                        and t5.parameter_code = '"+dimensionValue+"') t6 ")
					.append("              group by t6.parameter_code, t6.parameter_name) t7 ")
					.append("    on t2.parameter_code = t7.parameter_code ");
				}
				
			}
		}
		
		sb.append(" ) d");
		
		System.out.println(sb.toString());
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		List<Object[]> list = query.list();
		
		list.remove(0);
		
		return list;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProblemOrderData(String startDate, String endDate,
			String link, String dimensionKey, String dimensionValue,
			int compareType) {
		List<String> dateList = DateUtil.getTimePeriods(startDate,endDate,compareType,6);
		StringBuilder sb = new StringBuilder();
		sb.append("select d.*, case when d.dj_total != 0 then round(d.wtdj_total/d.dj_total*100,2) else 0 end wtdjzb, ")
		.append("  case when zwtdj_total != 0 then round(d.wtdj_total/d.zwtdj_total*100,2) else 0 end zwtdzb, ")
		.append("  case when nvl(lag(d.wtdj_total) over(order by d.dt),0) != 0 ")
		.append("    then round((d.wtdj_total-lag(d.wtdj_total) over(order by d.dt))/lag(d.wtdj_total) over(order by d.dt)*100,2)||'%' ")
		.append("    else '0%' end tbhb from (");
		
		for(int i = 0; i < dateList.size(); i++){
			String[] dateArr = dateList.get(i).split("-");
			
			if(i != 0){
				sb.append(" union all ");
			}
			
			if(StringUtils.isNotBlank(dimensionKey)){
				//部门维度
				if("department".equals(dimensionKey)){
					sb
					.append("select cast(t4.dt as varchar2(13)) dt,t4.department_id,t4.department,t4.dj_total,t4.wtdj_total,t7.zwtdj_total ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t1.department_id,t1.department, ")
					.append("                        nvl(t3.dj_total, 0) dj_total,nvl(t3.wtdj_total, 0) wtdj_total ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("          left join (select t3.department_id,count(1) dj_total,sum(t3.flag) wtdj_total ")
					.append("                      from view_all_order t3 ")
					.append("                     where to_char(t3.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                       and to_char(t3.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                     group by t3.department_id) t3 ")
					.append("            on t1.department_id = t3.department_id ")
					.append("         where t1.department_id = '"+dimensionValue+"') t4 ")
					.append("  join (select '"+dateList.get(i)+"' dt, count(distinct t1.order_id) zwtdj_total ")
					.append("          from account_tab t1 ")
					.append("          join import_flow_info t5 on t1.order_id = t5.order_id ")
					.append("          join problem_list t6 on t1.order_id = t6.account_order_id ")
					.append("         where t1.record_flat = '2' and t1.tdtime = '0' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t7 ")
					.append("    on t4.dt = t7.dt ");
				}
				//一级费用维度
				else if("parent_cos".equals(dimensionKey)){
					sb.append("select cast(t4.dt as varchar2(13)) dt,t4.parent_type_id,t4.parent_type_name,t4.dj_total,t4.wtdj_total,t7.zwtdj_total ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t2.parent_type_id,t2.parent_type_name, ")
					.append("                        nvl(t3.dj_total, 0) dj_total,nvl(t3.wtdj_total, 0) wtdj_total ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("          left join (select t3.parent_type_id,count(1) dj_total,sum(t3.flag) wtdj_total ")
					.append("                      from view_all_order t3 ")
					.append("                     where to_char(t3.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                       and to_char(t3.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                     group by t3.parent_type_id) t3 ")
					.append("            on t2.parent_type_id = t3.parent_type_id ")
					.append("         where t2.parent_type_id = '"+dimensionValue+"') t4 ")
					.append("  join (select '"+dateList.get(i)+"' dt, count(distinct t1.order_id) zwtdj_total ")
					.append("          from account_tab t1 ")
					.append("          join import_flow_info t5 on t1.order_id = t5.order_id ")
					.append("          join problem_list t6 on t1.order_id = t6.account_order_id ")
					.append("         where t1.record_flat = '2' and t1.tdtime = '0' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t7 ")
					.append("    on t4.dt = t7.dt ");
				}
				//二级费用维度
				else if("cos".equals(dimensionKey)){
					sb.append("select cast(t4.dt as varchar2(13)) dt,t4.type_id,t4.type_name,t4.dj_total,t4.wtdj_total,t7.zwtdj_total ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t2.type_id,t2.type_name, ")
					.append("                        nvl(t3.dj_total, 0) dj_total,nvl(t3.wtdj_total, 0) wtdj_total ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id ")
					.append("          left join (select t3.type_id,count(1) dj_total,sum(t3.flag) wtdj_total ")
					.append("                      from view_all_order t3 ")
					.append("                     where to_char(t3.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                       and to_char(t3.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                     group by t3.type_id) t3 ")
					.append("            on t2.type_id = t3.type_id ")
					.append("         where t2.type_id = '"+dimensionValue+"') t4 ")
					.append("  join (select '"+dateList.get(i)+"' dt, count(distinct t1.order_id) zwtdj_total ")
					.append("          from account_tab t1 ")
					.append("          join import_flow_info t5 on t1.order_id = t5.order_id ")
					.append("          join problem_list t6 on t1.order_id = t6.account_order_id ")
					.append("         where t1.record_flat = '2' and t1.tdtime = '0' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("           and to_char(to_date(t5.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t7 ")
					.append("    on t4.dt = t7.dt ");
				}
				//问题类型维度
				else if("problem".equals(dimensionKey)){
					sb.append("select cast(t7.dt as varchar2(13)) dt,t7.parameter_code,t7.parameter_name,t7.dj_total,t7.wtdj_total,t8.zwtdj_total ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t1.parameter_code,t1.parameter_name, nvl(t6.total, 0) dj_total,nvl(t6.total, 0) wtdj_total ")
					.append("          from sys_parameter t1 ")
					.append("          left join (select t6.parameter_code, count(1) total ")
					.append("                      from (select distinct t2.order_id, t5.parameter_code ")
					.append("                              from account_tab t2 ")
					.append("                              join import_flow_info t3 on t2.order_id = t3.order_id ")
					.append("                              join problem_list t4 on t2.order_id = t4.account_order_id ")
					.append("                              join sys_parameter t5 on t4.type = t5.parameter_code ")
					.append("                             where t2.record_flat = '2' and t2.tdtime = '0' ")
					.append("                               and to_char(to_date(t3.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("                               and to_char(to_date(t3.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t6 ")
					.append("                     group by t6.parameter_code) t6 ")
					.append("            on t1.parameter_code = t6.parameter_code ")
					.append("         where t1.parameter_code = '"+dimensionValue+"') t7 ")
					.append("  join (select '"+dateList.get(i)+"' dt, count(distinct t2.order_id) zwtdj_total ")
					.append("          from account_tab t2 ")
					.append("          join import_flow_info t3 on t2.order_id = t3.order_id ")
					.append("          join problem_list t4 on t2.order_id = t4.account_order_id ")
					.append("         where t2.record_flat = '2' and t2.tdtime = '0' ")
					.append("           and to_char(to_date(t3.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') >= '"+dateArr[0]+"' ")
					.append("           and to_char(to_date(t3.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymm') <= '"+dateArr[1]+"') t8 ")
					.append("    on t7.dt = t8.dt ");
				}
			}
		}
		
		sb.append(" ) d");
		
		System.out.println(sb.toString());
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		List<Object[]> list = query.list();
		
		list.remove(0);
		
		return list;
	}
	

	@Override
	public List<Object[]> queryTHbi(String linkName, String startTime,
			String endTime, String type,int compareType,int num) {
		
		List<String> dateList = DateUtil.getTimePeriods(startTime,endTime,compareType,num);
		
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(type)){
			//部门维度
			if("部门".equals(type)){
				sb.append("select distinct d.*, case when nvl(lag(d.num_avg) over(order by d.department,d.dt),0) != 0 then round((d.num_avg-lag(d.num_avg) over(order by d.department,d.dt))/lag(d.num_avg) over(order by d.department,d.dt)*100,2) || '%' else '0%' end from ( ");
			}else if("费用".equals(type)){
				sb.append("select distinct d.*, case when nvl(lag(d.num_avg) over(order by d.parent_type_name,d.dt),0) != 0 then round((d.num_avg-lag(d.num_avg) over(order by d.parent_type_name,d.dt))/lag(d.num_avg) over(order by d.parent_type_name,d.dt)*100,2) || '%' else '0%' end from ( ");
			}else if("问题类型".equals(type)){
				sb.append("select distinct d.*, case when nvl(lag(d.num_avg) over(order by d.parameter_name desc,d.dt),0) != 0 then round((d.num_avg-lag(d.num_avg) over(order by d.parameter_name desc,d.dt))/lag(d.num_avg) over(order by d.parameter_name desc,d.dt)*100,2) || '%' else '0%' end from ( ");
			}
		}
		
		for(int i = 0; i < dateList.size(); i++){
			String[] dateArr = dateList.get(i).split("-");
			
			if(i != 0){
				sb.append(" union all ");
			}
			
			if(StringUtils.isNotBlank(type)){
				//部门维度
				if("部门".equals(type)){
					sb.append("select cast(t3.dt as varchar2(13)) dt,t3.department_id,t3.department, ")
					.append("       nvl(t5.num_count, 0) num_count,nvl(t5.num_sum, 0) num_sum,round(nvl(t5.num_avg, 0),1) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t1.department_id,t1.department ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id where t2.status='1' ")
					.append("         ) t3 ")
					.append("  left join (select t4.department_id,count(1) num_count,sum(t4."+linkMap.get(linkName)+") num_sum,avg(t4."+linkMap.get(linkName)+") num_avg ")
					.append("               from view_all_link_aging t4 ")
					.append("              where to_char(t4.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                and to_char(t4.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("             group by t4.department_id, t4.department) t5 ")
					.append("    on t3.department_id = t5.department_id ");
				}
				//费用维度
				else if("费用".equals(type)){
					sb.append("select cast(t3.dt as varchar2(13)) dt,t3.parent_type_id,t3.parent_type_name, ")
					.append("       nvl(t5.num_count, 0) num_count,nvl(t5.num_sum, 0) num_sum,round(nvl(t5.num_avg, 0),1) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt,t2.parent_type_id,t2.parent_type_name ")
					.append("          from account_tab t1 join rules_type t2 on t1.cos_id = t2.type_id where t2.status='1' ")
					.append("         ) t3 ")
					.append("  left join (select t4.parent_type_id,count(1) num_count,sum(t4."+linkMap.get(linkName)+") num_sum,avg(t4."+linkMap.get(linkName)+") num_avg ")
					.append("               from view_all_link_aging t4 ")
					.append("              where to_char(t4.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                and to_char(t4.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("               group by t4.parent_type_id, t4.parent_type_name) t5 ")
					.append("    on t3.parent_type_id = t5.parent_type_id ");
				}
				
				//问题类型维度
				else if("问题类型".equals(type)){
					sb.append("select cast(t2.dt as varchar2(13)) dt, t2.parameter_code, t2.parameter_name, ")
					.append("       nvl(t7.num_count, 0) num_count, nvl(t7.num_sum, 0) num_sum, round(nvl(t7.num_avg, 0),1) num_avg ")
					.append("  from (select distinct '"+dateList.get(i)+"' dt, t1.parameter_code, t1.parameter_name ")
					.append("          from sys_parameter t1 where t1.parameter_type_id='16060416150887900001' ) t2 ")
					.append("  left join (select t6.parameter_code, count(1) num_count, sum(t6."+linkMap.get(linkName)+") num_sum, avg(t6."+linkMap.get(linkName)+") num_avg ")
					.append("               from (select distinct t3.*, t5.parameter_code, t5.parameter_name ")
					.append("                       from view_all_link_aging t3 ")
					.append("                       join problem_list t4 on t3.order_id = t4.account_order_id ")
					.append("                       join sys_parameter t5 on t4.type = t5.parameter_code ")
					.append("                      where t5.parameter_type_id='16060416150887900001' and  to_char(t3.payment_time, 'yyyyMM') >= '"+dateArr[0]+"' ")
					.append("                        and to_char(t3.payment_time, 'yyyyMM') <= '"+dateArr[1]+"' ")
					.append("                        ) t6 ")
					.append("              group by t6.parameter_code, t6.parameter_name) t7 ")
					.append("    on t2.parameter_code = t7.parameter_code ");
				}
				
			}
		}
		
		if(StringUtils.isNotBlank(type)){
			//部门维度
			if("部门".equals(type)){
				sb.append(" ) d  order by d.department,d.dt ");
			}else if("费用".equals(type)){
				sb.append(" ) d  order by d.parent_type_name,d.dt ");
			}else if("问题类型".equals(type)){
				sb.append(" ) d  order by d.parameter_name desc,d.dt ");
			}
		}
		System.out.println(sb.toString());
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		List<Object[]> list = query.list();
		
		return list;
	}


	@Override
	public String getDepIdByName(String depNmae) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct a.organization_id from sys_organization a where a.org_name='"+depNmae+"' ");
	    Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		return (String) qeury.uniqueResult();
	}


	@Override
	public List<Object[]> getCostIdByName(String costName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct a.type_id,a.parent_type_id from rules_type a where a.bus_types='BZ' and a.status='1' and a.type_name='"+costName+"'");
	    Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
	    List<Object[]> result = qeury.list();
		return result;
		
	}


	@Override
	public String getProTypeByName(String proName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct b.parameter_code from  sys_parameter b  where b.parameter_type_id='16060416150887900001' and b.parameter_name='"+proName+"'");
	    Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		return (String) qeury.uniqueResult();
	}


	@Override
	public List<AccountImportInfo> queryFlowInfoByTime(String startDate, String endDate) {
		QueryBuilder query = new QueryBuilder(AccountImportInfo.class,"tt");
		query.where(" (select recordFlat from AccountInfo t where t.orderId=tt.orderId)='2' and (select tDTime from AccountInfo t where t.orderId=tt.orderId)='0' and " +
				" to_date(to_char(to_date(column59, 'yyyy-mm-dd hh24:mi:ss'),'yyyymmdd'),'yyyymmdd') between to_date('"+startDate+"','yyyymm') and to_date('"+endDate+"','yyyymmdd') ");
		query.orderBy("column59", false);
		return (List<AccountImportInfo>) enterpriseService.query(query, 0);
	}
}
