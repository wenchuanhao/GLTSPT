package com.cdc.sys.form;

import org.trustel.service.form.PageQueryForm;

/**
 * 节假日管理查询form
 */
public class HolidayQueryForm  extends PageQueryForm {

    private String year;

    private String month;

    private String createUser;

    private String sourceType;

    private String targetType;

    private String queryStartCreateDate;

    private String queryEndCreateDate;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getQueryStartCreateDate() {
        return queryStartCreateDate;
    }

    public void setQueryStartCreateDate(String queryStartCreateDate) {
        this.queryStartCreateDate = queryStartCreateDate;
    }

    public String getQueryEndCreateDate() {
        return queryEndCreateDate;
    }

    public void setQueryEndCreateDate(String queryEndCreateDate) {
        this.queryEndCreateDate = queryEndCreateDate;
    }
}
