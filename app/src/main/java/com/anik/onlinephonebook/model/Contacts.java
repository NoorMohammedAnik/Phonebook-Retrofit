package com.anik.onlinephonebook.model;

import com.google.gson.annotations.SerializedName;

public class Contacts {

    @SerializedName("id")
    private String id;


    @SerializedName("name")
    private String name;


    @SerializedName("cell")
    private String cell;



    @SerializedName("password")
    private String password;

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getCell() {
        return cell;
    }

    public String getPassword() {
        return password;
    }


    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }
}