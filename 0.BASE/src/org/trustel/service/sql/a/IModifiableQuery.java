package org.trustel.service.sql.a;

import java.util.List;

public interface IModifiableQuery extends IPrepareQuery {
	
	public String getSQL();

	public String merger(IQuery builder, List<ICondition> conditions);

	public List<ICondition> value2Conditions();

}
