/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.loginusa.dosis.util.json.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import id.loginusa.dosis.util.json.JsonBuilder;

/**
 *
 * @author mfachmirizal
 */
public class Address {
    private boolean isActive;

    private String name;
    private String address;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private String phone;

    private boolean ishome;
    private boolean iswork;

    private String id;

    public Address(boolean isActive, String name, String address, BigDecimal latitude, BigDecimal longitude, String phone, boolean ishome, boolean iswork,String id) {
        this.isActive = isActive;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.ishome = ishome;
        this.iswork = iswork;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIshome() {
        return ishome;
    }

    public void setIshome(boolean ishome) {
        this.ishome = ishome;
    }

    public boolean isIswork() {
        return iswork;
    }

    public void setIswork(boolean iswork) {
        this.iswork = iswork;
    }

    /**
     * Get Data List from Raw JSON Response
     * @param s Raw JSON Response
     * @return
     */
    public static List<Address> getList(String s) {
        Type listType = new TypeToken<List<Address>>() {}.getType();
        List<Address> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(s) , listType);
        return logJData;
    }

    public static List<Address> getList(JsonBuilder jBuilder) {
        Type listType = new TypeToken<List<Address>>() {}.getType();
        List<Address> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(jBuilder) , listType);
        return logJData;
    }

    public static List<Address> getList(JsonObject jsonObject) {
        Type listType = new TypeToken<List<UserData>>() {}.getType();
        List<Address> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(jsonObject) , listType);
        return logJData;
    }
}
