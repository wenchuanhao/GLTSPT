package com.cdc.dc.project.fxk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.fxk.model.GcFxkContent;
import com.cdc.dc.project.fxk.model.GcFxkStage;
import com.cdc.dc.project.fxk.service.IFxkService;
import com.trustel.common.ItemPage;
/**
 * 
 * @author zengkai
 * @date 2016-8-19 上午8:37:51
 */
public class FxkServiceImpl implements IFxkService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public void delData() {
		QueryBuilder query1 = new QueryBuilder(GcFxkStage.class);
		enterpriseService.delete(query1);
		QueryBuilder query2 = new QueryBuilder(GcFxkContent.class);
		enterpriseService.delete(query2);
	}

	@Override
	public void saveData(List list, int index) {
		if(list == null || list.size() <= 0) return;
		int length = list.size();
		List<GcFxkStage> stageList = new ArrayList<GcFxkStage>();
		List<GcFxkContent> contentList = new ArrayList<GcFxkContent>();
		//取第一行为父阶段,其余为子阶段
		String[] strs = (String[]) list.get(0);
		GcFxkStage gcfxkParent = new GcFxkStage();
		String parentId = UUID.randomUUID().toString();
		gcfxkParent.setStageId(parentId);
		gcfxkParent.setSeq(String.valueOf(index + 1));
		if(strs != null && strs.length >= 5 && StringUtils.isNotEmpty(strs[0])
				&& StringUtils.isEmpty(strs[1])
				&& StringUtils.isEmpty(strs[2])
				&& StringUtils.isEmpty(strs[3])
				&& StringUtils.isEmpty(strs[4])){
			gcfxkParent.setStageName(strs[0]);
		}
		gcfxkParent.setLevelNum(FxkForm.FXK_LEVEL1);
		gcfxkParent.setCreateTime(new Date());
		stageList.add(gcfxkParent);
		//子阶段序列标记 
		int j = 1;
		String childId = "";//子阶段ID
		//遍历数据，从第三行开始
		for (int i = 2; i < length; i++) {
			String[] contents = (String[]) list.get(i);
			//获取数据，子阶段
			if(contents != null && contents.length >= 5 && StringUtils.isNotEmpty(contents[0])
					&& StringUtils.isEmpty(contents[1])
					&& StringUtils.isEmpty(contents[2])
					&& StringUtils.isEmpty(contents[3])
					&& StringUtils.isEmpty(contents[4])){
				GcFxkStage gcfxkChild = new GcFxkStage();
				childId = UUID.randomUUID().toString();
				gcfxkChild.setStageId(childId);
				gcfxkChild.setSeq(String.valueOf(j));
				gcfxkChild.setStageName(contents[0]);
				gcfxkChild.setParentId(parentId);
				gcfxkChild.setLevelNum(FxkForm.FXK_LEVEL2);
				gcfxkChild.setCreateTime(new Date());
				stageList.add(gcfxkChild);
				j++;
			}else if(contents != null && contents.length >= 5){
				GcFxkContent content = new GcFxkContent();
				content.setStageId(childId);
				content.setSeq(contents[0]);
				content.setRiskFactor(contents[1]);//风险因素
				content.setRiskAfter(contents[2]);//风险导致的后果	
				content.setRiskMeasure(contents[3]);//风险应对措施	
				content.setRiskType(contents[4]);//风险类别
				content.setCreateTime(new Date());
				contentList.add(content);
			}
		}
		//阶段保存
		enterpriseService.save(stageList);
		//内容保存
		enterpriseService.save(contentList);
	}

	@Override
	public ItemPage findFxk(FxkForm fxk) {
		QueryBuilder query = new QueryBuilder(GcFxkStage.class);
		query.where("levelNum",FxkForm.FXK_LEVEL1,QueryAction.EQUAL);
		query.orderBy("seq");
		return enterpriseService.query(query, fxk);
	}

	@Override
	public ItemPage findFxkView(FxkForm fxk) {
		Class<?>[] pojos = {GcFxkContent.class,GcFxkStage.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.stageId = b.stageId ");
		query.where(" b.parentId ",fxk.getId(),QueryAction.EQUAL);
		query.where(" a.stageId ",fxk.getStageId(),QueryAction.EQUAL);
		if(StringUtils.isNotEmpty(fxk.getContent())){
			query.where(" (a.riskFactor like '%"+fxk.getContent()+"%' or  a.riskAfter like '%"+fxk.getContent()+"%' or a.riskMeasure like '%"+fxk.getContent()+"%' or a.riskType like '%"+fxk.getContent()+"%' )");
		}
		query.orderBy("b.seq,to_number(a.seq)");
		return enterpriseService.query(query, fxk);
	}

	@Override
	public List<GcFxkStage> queryGcFxkStageList(String id) {
		QueryBuilder query = new QueryBuilder(GcFxkStage.class);
		query.where("parentId",id,QueryAction.EQUAL);
		query.orderBy("seq");
		return (List<GcFxkStage>) enterpriseService.query(query, 0);
	}
}
