package org.trustel.service.sql.a;

import java.util.List;


public interface IPrepareQuery {
	
	public String getPrepareSQL();

	public List<IField> getValues();

}
