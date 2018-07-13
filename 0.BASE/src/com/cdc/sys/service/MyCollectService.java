package com.cdc.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.sys.entity.MyCollect;

import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

public class MyCollectService implements IMyCollectService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void addCollect(MyCollect collect) throws RuntimeException {
          enterpriseService.save(collect);
	}
	
	public List<MyCollect> getCollect(String userId)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(MyCollect.class);
		builder.orderBy("collectTime", false);
		builder.where("userId", userId);
		builder.where("collectStatus", "1");
//		return (List<MyCollect>)enterpriseService.query(builder, 5);
		return null;
	}
	
	
	/**
	 * 根据当前登录帐号 查询是否已经被收藏
	 * @param userId
	 * @return
	 * @throws RuntimeException
	 */
	public List<MyCollect> getIsExsitCollect(String userId)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(MyCollect.class);
		builder.where("userId", userId);
		return (List<MyCollect>)enterpriseService.query(builder, 0);
	}
	
	/**
	 * 根据当前登录帐号、查询名称 查询是否已经被收藏
	 * @param userId
	 * @return
	 * @throws RuntimeException
	 */
	public List<MyCollect> getIsExsitCollectName(String userId,String collectName,String collectType)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(MyCollect.class);
		builder.where("userId", userId);
		builder.where("collectName",collectName.trim());
		builder.where("collectType",collectType);
		return (List<MyCollect>)enterpriseService.query(builder, 0);
	}
	
	
	/**
	 * 取消收藏
	 * @param collectId
	 * @throws RuntimeException
	 */
	public void canelCollect(String collectId)throws RuntimeException{
		MyCollect collect=(MyCollect) enterpriseService.getById(MyCollect.class, collectId);
		collect.setCollectStatus("0");
		collect.setCollectTime(new Date());
		enterpriseService.updateObject(collect);
		
	}
	
	/**
	 * 修改收藏
	 * @param collectId
	 * @throws RuntimeException
	 */
	public void updateCollect(String collectName)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(MyCollect.class);
		builder.where("collectName",collectName.trim());
		String sql="update MyCollect set collectStatus=1";
		enterpriseService.execute(sql+builder.getWhere(),builder.getValues());
		
	}
	
	@Override
	public List<Object[]> getAdvertisement(){
		
		String sql ="select a.file_id,a.file_path,a.create_time,a.create_username,b.pic_title,b.pic_url,b.pic_sort from RULES_FILE_UPLOAD a,DC_PICCONFIG b where a.rules_infoid = b.config_id and b.status = '1' and a.status='1' order by b.pic_sort,b.create_time";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<Object[]> result = new ArrayList<Object[]>();
		if(list != null){
			for (Object[] object : list) {
				Object[] obj = new Object[7];
				String fileId = (String) object[0];
				String filePath = (String) object[1];
				Date createTime = (Date) object[2];
				String createUsername = (String) object[3];
				String picTitle = (String) object[4];
				String picUrl = (String) object[5];
				String picSort = (String) object[6];
				
				obj[0] = fileId;
				obj[1] = filePath;
				obj[2] = createTime;
				obj[3] = createUsername;
				obj[4] = picTitle;
				obj[5] = picUrl;
				obj[6] = picSort;
				
				result.add(obj);
			}
		}
		return result;
	}

}
