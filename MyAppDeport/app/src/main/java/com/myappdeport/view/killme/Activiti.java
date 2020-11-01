package com.myappdeport.view.killme;

public class Activiti {
    private String title;
    private String Description;
    private String dateNum;
    private String dateDay;
    private String km;

    public Activiti(String title, String description, String dateNum, String dateDay, String km) {
        this.title = title;
        Description = description;
        this.dateNum = dateNum;
        this.dateDay = dateDay;
        this.km = km;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateNum() {
        return dateNum;
    }

    public void setDateNum(String dateNum) {
        this.dateNum = dateNum;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }
}
