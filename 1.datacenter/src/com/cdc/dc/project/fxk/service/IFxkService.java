package com.cdc.dc.project.fxk.service;

import java.util.List;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.fxk.model.GcFxkStage;
import com.trustel.common.ItemPage;

/**
 * 
 * @author zengkai
 * @date 2016-8-19 上午8:37:51
 */
public interface IFxkService {

	/**
	 * 清空风险库所有导入的数据
	 */
	void delData();

	/**
	 * 保存Excel 单个 sheet中的数据
	 * @param list
	 */
	void saveData(List list,int index);

	/**
	 * 首页查询
	 * @param fxk
	 * @return
	 */
	ItemPage findFxk(FxkForm fxk);

	/**
	 * 风险库详情页面
	 * @param fxk
	 * @return
	 */
	ItemPage findFxkView(FxkForm fxk);

	/**
	 * 子阶段列表
	 * @param id
	 * @return
	 */
	List<GcFxkStage> queryGcFxkStageList(String id);

}
