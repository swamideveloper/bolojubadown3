package com.sdk.ads.ResModel;

public class Country_Model {


    public Country_Model(String name1,String name, String code, int resourceId) {
        this.name1 = name1;
        this.name = name;
        this.code = code;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }
    public String getName1() {
        return name1;
    }

    public String getCode() {
        return code;
    }

    public String name1;
    public String name;
    public String code;

    public int getResourceId() {
        return resourceId;
    }

    public int resourceId;
}
