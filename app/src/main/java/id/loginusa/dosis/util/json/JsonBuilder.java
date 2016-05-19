/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.loginusa.dosis.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mfachmirizal @ Teknologi Nusantara
 */
public class JsonBuilder {
    //private String response;
    
    @SerializedName("response")
    private JsonResponse response;

    public JsonBuilder() {
        response = new JsonResponse();
    }
    
    public JsonBuilder(JsonResponse outResponse) {
        response = outResponse;
    }

    public JsonResponse getResponse() {
        return response;
    }

    public void setResponse(JsonResponse response) {
        this.response = response;
    }
    
    
    public void add(Object data) {
        this.response.add(data);
    }
    
    public int getStatus_code() {
        return response.getStatus_code();
    }
    
    public void setStatus_code(int status_code) {
        this.response.setStatus_code(status_code);
    }

    /**
     * Method untuk mengkonversi object yg sudah di instansiasi ke JSON
     * @return json string
     */
    @Deprecated
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(new JsonBuilder(response));
    }

    /**
     * Method untuk mengkonversi object yg sudah di instansiasi ke JSON
     * @param status_code override status_code
     * @return
     */
    public String toJson(int status_code){
        Gson gson = new Gson();
        response.setStatus_code(status_code);
        return gson.toJson(new JsonBuilder(response));
    }

    /**
     * Method untuk mengkonversi object yg sudah di instansiasi ke JSON
     * @param datalist data list object yang akan di convert ke JSON
     * @param status_code override status_code
     * @return
     */
    static public String toJson(List datalist,int status_code) {
        Gson gson = new Gson();
        JsonResponse response = new JsonResponse(datalist, status_code);
        return gson.toJson(new JsonBuilder(response));
    }

    /**
     * Method untuk mengkonversi object yg sudah di instansiasi ke JSON
     * @param obj object yang akan di convert ke JSON
     * @param status_code override status_code
     * @return
     */
    static public String toJson(Object obj,int status_code) {
        Gson gson = new Gson();
        JsonResponse response = new JsonResponse(obj, status_code);
        return gson.toJson(new JsonBuilder(response));
    }

    /**
     * Mendapatkan Inti data yang berasal dari JSON Response
     * @return  JSON data bentuk string yg siap di convert menjadi List Object
     */
    public String extractJsonData() {
        Gson gson = new Gson();
        String jsonString = getRawJsonData();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jsonString).getAsJsonObject();
        o = o.get("response").getAsJsonObject();

        return o.get("data").toString();
    }

//    public String extractJsonData() {
//        Gson gson = new Gson();
//        JsonParser parser = new JsonParser();
//        JsonObject o = parser.parse(gson.toJson(new JsonBuilder(response))).getAsJsonObject();
//        o = o.get("response").getAsJsonObject();
//
//        return o.get("data").toString();
//    }

    /**
     *
     * Mendapatkan Inti data yang berasal dari class JSON Builder
     * @param jBuilder object JSON Builder
     * @return JSON data bentuk string yg siap di convert menjadi List Object
     */
    static public String extractJsonData(JsonBuilder jBuilder) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jBuilder.toJson(jBuilder.getStatus_code())).getAsJsonObject();
        o = o.get("response").getAsJsonObject();

        return o.get("data").toString();
    }

    /**
     * Mendapatkan / mengekstrak Inti data yang berasal dari JSON String
     * @param s String
     * @return JSON data bentuk string yg siap di convert menjadi List Object
     */
    static public String extractJsonData(String s) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(s).getAsJsonObject();
        o = o.get("response").getAsJsonObject();

        return o.get("data").toString();
    }

    /**
     * Mendapatkan / mengekstrak Inti data yang berasal dari Instance JsonObject
     * @param jobject Instance JsonObject
     * @return JSON data bentuk string yg siap di convert menjadi List Object
     */
    static public String extractJsonData(JsonObject jobject) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jobject.toString()).getAsJsonObject();
        o = o.get("response").getAsJsonObject();

        return o.get("data").toString();
    }

    /**
     * Method untuk mendapatkan status_code dari instance JsonBuilder
     * @return status_code
     */
    public int getJsonStatusCode() {
        String jsonString = getRawJsonData();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jsonString).getAsJsonObject();
        o = o.get("response").getAsJsonObject();
        return  o.get("status_code").getAsInt();
    }

    /**
     * Method untuk mendapatkan status_code dari JSON String
     * @param s JSON String yang akan di cek status_code nya
     * @return status_code
     */
    static public int getJsonStatusCode(String s) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(s).getAsJsonObject();
        o = o.get("response").getAsJsonObject();
        return  o.get("status_code").getAsInt();
    }

    /**
     * Method untuk mendapatkan status_code dari Instance JsonBuilder
     * @param jBuilder JsonBuilder
     * @return status_code
     */
    static public int getJsonStatusCode(JsonBuilder jBuilder) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jBuilder.toJson(jBuilder.getStatus_code())).getAsJsonObject();
        o = o.get("response").getAsJsonObject();
        return o.get("status_code").getAsInt();
    }

    /**
     * Method untuk mendapatkan status_code dari Instance JsonObject
     * @param jObject Instance JsonObject
     * @return status_code
     */
    static public int getJsonStatusCode(JsonObject jObject) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jObject.toString()).getAsJsonObject();
        o = o.get("response").getAsJsonObject();
        return o.get("status_code").getAsInt();
    }

    /**
     * Method untuk mendapatkan String JSON Data mentah
     * @return String JSON Data
     */
    public String getRawJsonData() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(gson.toJson(new JsonBuilder(response))).getAsJsonObject();
        return o.toString();
    }



    /**
     * Method untuk mendapatkan String JSON Data mentah
     * @param jBuilder Instance JsonBuilder
     * @return String JSON Data
     */
    static public String getRawJsonData(JsonBuilder jBuilder) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jBuilder.toJson(jBuilder.getStatus_code())).getAsJsonObject();
        return o.toString();
    }
}
