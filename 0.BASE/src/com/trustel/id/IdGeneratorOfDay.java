package com.trustel.id;

import java.io.Serializable;
import java.text.NumberFormat;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.trustel.common.DateFunc;

public class IdGeneratorOfDay implements IdentifierGenerator {
	private static long sequence;
    private static String compareDay;
    private static NumberFormat numberFormat;
    
    static {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(4);
        numberFormat.setMaximumIntegerDigits(4);
    }
    
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		String currentDay = DateFunc.getCurrentDateString("yyMMdd");
        if (compareDay == null || compareDay.compareTo(currentDay) != 0) {
            compareDay = currentDay;
            sequence = 1;
        } else
            sequence++;

        return currentDay + numberFormat.format(sequence);
	}

}
