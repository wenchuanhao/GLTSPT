package org.trustel.service;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.trustel.service.form.IPageQueryForm;
import org.trustel.service.hibernate.IHibernateService;
import org.trustel.service.jdbc.IJdbcService;

import com.trustel.common.ItemPage;

public interface IEnterpriseService extends IHibernateService, IJdbcService {

	public ItemPage findSqlToVo(String sql, List conditions, IPageQueryForm form, Class cls);

}
