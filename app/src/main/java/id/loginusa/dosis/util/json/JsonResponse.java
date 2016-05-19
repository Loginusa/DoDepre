/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.loginusa.dosis.util.json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mfachmirizal
 */
public class JsonResponse  {
    private List data;
    private int status_code;
    
    

    public JsonResponse() {
        createList();
    }
    
    public JsonResponse(List dataList, int status_code) {
        createList();
        this.data = dataList;
        this.status_code = status_code;
    }
    
    private void createList(){
        this.data = new ArrayList();
    }

        
    public JsonResponse(Object data, int status_code) {
        createList();
        this.data.add(data);
        this.status_code = status_code;
    }

    public List getDataList() {
        return data;
    }

    public void setDataList(List dataList) {
        this.data = dataList;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
    
    public void add(Object as) {
        this.data.add(as);
    }
    
    
    
}
