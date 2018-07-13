package com.cdc.dc.divsion.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.trustel.util.DateUtils;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.divsion.model.DivisionRecord;
import com.cdc.dc.divsion.model.DivisionResult;
import com.cdc.dc.divsion.service.IDivsionElecService;
import com.cdc.system.core.util.SpringHelper;

/**
 * 行政经分水电煤接口同步
 * @author lxl
 * @date 2016-10-24
 */
public class SyncDivsionTask {
	/** 用水 */
	public static final String INTER_TYPE_WATER = "1";
	/** 用电 */
	public static final String INTER_TYPE_ELEC = "2";
	/** 用煤 */
	public static final String INTER_TYPE_GAS = "3";
	
//	public static String tokenPath = "http://10.251.84.24:8080/api/TokenService/GetToken?appID=njwy_api&appSecret=0a261b2ff58d485a977bfc38a094e470";
//	public static String waterPath = "http://10.251.84.24:8080/api/WaterMeterService/GetWaterMeterData";
//	public static String elecPath = "http://10.251.84.24:8080/api/ElectricityMeterService/GetElectricityMeterData";
//	public static String gasPath = "http://10.251.84.24:8080/api/GasMeterService/GetGasMeterData";
	
	public static final Map<String, String> monthMap = new HashMap<String, String>();
	
	private IDivsionElecService divsionService;
	public void setDivsionService(IDivsionElecService divsionService) {
		this.divsionService = divsionService;
	}
	
	private final static Logger LOG = LogManager.getLogger(SyncDivsionTask.class.getName());
	
	public void syncDivsion() {
		LOG.info("==============>> 同步物业管理平台数据接口开始");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			int startYear = 2015;
			int endYear = DateUtils.getYear(new Date(), null);
			List<String> yearsList = getYearsList(startYear, endYear);
			
			//先清空数据
			JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
			jdbcTemplate.execute("truncate table DIVISION_RESULT");
			
			syscWater(httpClient, yearsList);
			syscElec(httpClient, yearsList);
			syscGas(httpClient, yearsList);
		} catch (Exception e) {
			LOG.info("同步失败", e);
		}
		LOG.info("==============>> 同步物业管理平台数据接口结束");
	}

	/**
	 * 同步南区天然气用量统计接口
	 * @param httpClient
	 * @param yearList
	 */
	private void syscGas(CloseableHttpClient httpClient, List<String> yearList) {
		String token = getToken(httpClient);
		if(StringUtils.isNotEmpty(token)){
			for(String year : yearList){
				String gasPath = DCConfig.getProperty("GAS_PATH");
				String url = gasPath + "?year=" + year + "&Token=" + token;
				List<DivisionResult> resultList = new ArrayList<DivisionResult>();
				DivisionRecord record = new DivisionRecord();
				
				try {
					HttpGet httpGet = new HttpGet(url);
					String response = httpClient.execute(httpGet, new BasicResponseHandler());
					JSONObject jsonObject = JSONObject.fromObject(response);
					if(jsonObject!=null && jsonObject.size()>0){
						//记录日志
						record.setRequestUrl(url);
						record.setRequestYear(year);
						record.setRequestToken(token);
						record.setErrorCode(jsonObject.getString("ErrorCode"));
						record.setErrorMessage(jsonObject.getString("ErrorMessage"));
						record.setCreateTime(new Date());
						divsionService.saveEntity(record);
						
						JSONArray jsonArray = jsonObject.getJSONArray("Data");
						if(jsonArray != null){
							for(int i = 0; i < jsonArray.size(); i++){
								jsonObject = jsonArray.getJSONObject(i);
								String name = jsonObject.getString("Name");
								JSONObject monthsData = jsonObject.getJSONObject("MonthsData");
								if(monthsData != null){
									for(Object month : monthsData.keySet()){
										DivisionResult result = new DivisionResult();
										result.setRecordId(record.getRecordId());
										result.setType(INTER_TYPE_GAS);
										result.setYear(year);
										result.setMonth(monthMap.get(month));
										result.setName(name);
										result.setValue(monthsData.getDouble(String.valueOf(month)));
										result.setCreateTime(new Date());
										resultList.add(result);
									}
								}
							}
							divsionService.save(resultList);
						}
					}
				} catch (Exception e) {
					LOG.info("同步南区天然气用量统计接口失败", e);
				}
			}
		}
	}

	/**
	 * 同步办公生活区总用电量统计数据接口
	 * @param httpClient
	 * @param yearList
	 * @throws Exception
	 */
	private void syscElec(CloseableHttpClient httpClient, List<String> yearList) throws Exception {
		String token = getToken(httpClient);
		if(StringUtils.isNotEmpty(token)){
			for(String year : yearList){
				String elecPath = DCConfig.getProperty("ELEC_PATH");
				String url = elecPath + "?year=" + year + "&Token=" + token;
				List<DivisionResult> resultList = new ArrayList<DivisionResult>();
				DivisionRecord record = new DivisionRecord();
				try {
					HttpGet httpGet = new HttpGet(url);
					String response = httpClient.execute(httpGet, new BasicResponseHandler());
					JSONObject jsonObject = JSONObject.fromObject(response);
					if(jsonObject!=null && jsonObject.size()>0){
						//记录日志
						record.setRequestUrl(url);
						record.setRequestYear(year);
						record.setRequestToken(token);
						record.setErrorCode(jsonObject.getString("ErrorCode"));
						record.setErrorMessage(jsonObject.getString("ErrorMessage"));
						record.setCreateTime(new Date());
						divsionService.saveEntity(record);
						
						JSONArray jsonArray = jsonObject.getJSONArray("Data");
						if(jsonArray != null){
							for(int i = 0; i < jsonArray.size(); i++){
								jsonObject = jsonArray.getJSONObject(i);
								String EstatesName = jsonObject.getString("EstatesName");
								JSONArray systems = jsonObject.getJSONArray("Systems");
								if(systems!=null && systems.size()>0){
									for(int j = 0; j < systems.size(); j++){
										JSONObject system = systems.getJSONObject(j);
										String systemName = system.getString("SystemName");
										JSONObject monthsData = system.getJSONObject("MonthsData");
										if(monthsData != null){
											for(Object month : monthsData.keySet()){
												DivisionResult result = new DivisionResult();
												result.setRecordId(record.getRecordId());
												result.setType(INTER_TYPE_ELEC);
												result.setYear(year);
												result.setMonth(monthMap.get(month));
												result.setName(EstatesName);
												result.setSystemName(systemName);
												result.setValue(monthsData.getDouble(String.valueOf(month)));
												result.setCreateTime(new Date());
												resultList.add(result);
											}
										}
									}
								}
							}
							divsionService.save(resultList);
						}
					}
				} catch (Exception e) {
					LOG.info("同步办公生活区总用电量统计数据接口失败", e);
				}
			}
		}
	}

	/**
	 * 同步用水统计数据接口
	 * @param httpClient
	 * @param yearList
	 * @throws Exception
	 */
	private void syscWater(CloseableHttpClient httpClient, List<String> yearList) throws Exception {
		String token = getToken(httpClient);
		if(StringUtils.isNotEmpty(token)){
			for(String year : yearList){
				String waterPath = DCConfig.getProperty("WATER_PATH");
				String url = waterPath + "?year=" + year + "&Token=" + token;
				List<DivisionResult> resultList = new ArrayList<DivisionResult>();
				DivisionRecord record = new DivisionRecord();
				try {
					HttpGet httpGet = new HttpGet(url);
					String response = httpClient.execute(httpGet, new BasicResponseHandler());
					JSONObject jsonObject = JSONObject.fromObject(response);
					if(jsonObject!=null && jsonObject.size()>0){
						//记录日志
						record.setRequestUrl(url);
						record.setRequestYear(year);
						record.setRequestToken(token);
						record.setErrorCode(jsonObject.getString("ErrorCode"));
						record.setErrorMessage(jsonObject.getString("ErrorMessage"));
						record.setCreateTime(new Date());
						divsionService.saveEntity(record);
						
						JSONArray jsonArray = jsonObject.getJSONArray("Data");
						if(jsonArray != null){
							for(int i = 0; i < jsonArray.size(); i++){
								jsonObject = jsonArray.getJSONObject(i);
								String name = jsonObject.getString("Name");
								JSONObject monthsData = jsonObject.getJSONObject("MonthsData");
								if(monthsData != null){
									for(Object month : monthsData.keySet()){
										DivisionResult result = new DivisionResult();
										result.setRecordId(record.getRecordId());
										result.setType(INTER_TYPE_WATER);
										result.setYear(year);
										result.setMonth(monthMap.get(month));
										result.setName(name);
										result.setValue(monthsData.getDouble(String.valueOf(month)));
										result.setCreateTime(new Date());
										resultList.add(result);
									}
								}
							}
							divsionService.save(resultList);
						}
					}
				} catch (Exception e) {
					LOG.info("同步用水统计数据接口失败", e);
				}
			}
		}
	}

	/**
	 * 获取临时授权令牌
	 * @param httpClient
	 * @return
	 * @throws Exception
	 */
	private String getToken(CloseableHttpClient httpClient) {
		String token = null;
		String tokenPath = DCConfig.getProperty("TOKEN_PATH");
		HttpGet httpGet = new HttpGet(tokenPath);
		DivisionRecord record = new DivisionRecord();
		try {
			String response = httpClient.execute(httpGet, new BasicResponseHandler());
			JSONObject jsonObject = JSONObject.fromObject(response);
			if(jsonObject!=null && jsonObject.size()>0){
				token = jsonObject.getString("Data");
				record.setRequestToken(token);
				record.setErrorCode(jsonObject.getString("ErrorCode"));
				record.setErrorMessage(jsonObject.getString("ErrorMessage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			record.setRequestUrl(tokenPath);
			record.setCreateTime(new Date());
			divsionService.saveEntity(record);
		}
		return token;
	}
	
	private static List<String> getYearsList(int startYear, int endYear){
		List<String> list = new ArrayList<String>();
		while(startYear <= endYear){
			list.add(String.valueOf(startYear));
			startYear++;
		}
		return list;
	}
	
	static{
		monthMap.put("January", "01");
		monthMap.put("February", "02");
		monthMap.put("March", "03");
		monthMap.put("April", "04");
		monthMap.put("May", "05");
		monthMap.put("June", "06");
		monthMap.put("July", "07");
		monthMap.put("August", "08");
		monthMap.put("September", "09");
		monthMap.put("October", "10");
		monthMap.put("November", "11");
		monthMap.put("December", "12");
	}
}
