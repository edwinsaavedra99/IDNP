package com.myappdeport.view.killme;

public class Food {
    private String title;
    private String descriptio_short;
    private String getDescriptio_long;

    public Food(String title, String descriptio_short, String getDescriptio_long) {
        this.title = title;
        this.descriptio_short = descriptio_short;
        this.getDescriptio_long = getDescriptio_long;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptio_short() {
        return descriptio_short;
    }

    public void setDescriptio_short(String descriptio_short) {
        this.descriptio_short = descriptio_short;
    }

    public String getGetDescriptio_long() {
        return getDescriptio_long;
    }

    public void setGetDescriptio_long(String getDescriptio_long) {
        this.getDescriptio_long = getDescriptio_long;
    }
}
