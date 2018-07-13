package model.sys.entity;

public enum HolidayInfoStatus{

    workday("上班"),holiday("放假");

    private String info;

    private HolidayInfoStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
