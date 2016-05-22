/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package id.loginusa.dosis.util.json.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.json.JsonBuilder;

/**
 *
 * @author mfachmirizal
 */
public class UserData {
    
    private boolean isActive;
    
    private boolean isLogin;
    private String profpic;
    private String revision_date;
    private String ad_user_id;
    
    private String username;
    private String name;
    private String email;
    private String phone;
    private String altphone;
    private String fax;
    
    private List<Address> address;
    
    public boolean isIsActive() {
        return isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public boolean isIsLogin() {
        return isLogin;
    }
    
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
    public String getProfpic() {
        return profpic;
    }
    
    public void setProfpic(String profpic) {
        this.profpic = profpic;
    }
    
    public String getRevision_date() {
        return revision_date;
    }
    
    public void setRevision_date(Date revision_date) {
        this.revision_date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(revision_date);
    }
    
    public String getAd_user_id() {
        return ad_user_id;
    }
    
    public void setAd_user_id(String ad_user_id) {
        this.ad_user_id = ad_user_id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAltphone() {
        return altphone;
    }
    
    public void setAltphone(String altphone) {
        this.altphone = altphone;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public List<Address> getAddress() {
        return address;
    }
    
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    /**
     * Get Data List from Raw JSON Response
     * @param s Raw JSON Response
     * @return
     */
    public static List<UserData> getList(String s) {
        Type listType = new TypeToken<List<UserData>>() {}.getType();
        Logging.log('d',"UData","String s : "+s);
        List<UserData> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(s) , listType);
        return logJData;
    }

    public static List<UserData> getList(JsonBuilder jBuilder) {
        Type listType = new TypeToken<List<UserData>>() {}.getType();
        List<UserData> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(jBuilder) , listType);
        return logJData;
    }

    public static List<UserData> getList(JsonObject jsonObject) {
        Type listType = new TypeToken<List<UserData>>() {}.getType();
        List<UserData> logJData = new Gson().fromJson(JsonBuilder.extractJsonData(jsonObject) , listType);
        return logJData;
    }



}

