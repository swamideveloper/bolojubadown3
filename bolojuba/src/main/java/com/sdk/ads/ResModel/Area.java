package com.sdk.ads.ResModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

@SerializedName("city")
@Expose
private String city;
@SerializedName("state")
@Expose
private String state;
@SerializedName("country")
@Expose
private String country;

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

}