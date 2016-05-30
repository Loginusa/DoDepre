package id.loginusa.dosis.util.externalconnection.openbravows;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;


import java.io.IOException;
import java.util.Map;

import id.loginusa.dosis.util.SessionManager;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.externalconnection.BaseGenericUrl;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public class OpenbravoLoginService implements OpenbravoWebService {
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public JsonObject executeConnection(Map<String,String> param)  throws IOException,JSONException {
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
        String strparamuser = "";
        String strparampass = "";
        String strlogoutdate = "";
        String strreqcode = "";
        String strtoken = "";
        String islogin = "";
        for(Map.Entry<String, String> entry : param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals(StaticVar.SERVER_WS_USER_USERNAME_PARAM)) {
                strparamuser+=value;
            }
            if (key.equals(StaticVar.SERVER_WS_USER_PASS_PARAM)) {
                strparampass+=value;
            }
            if (key.equals(StaticVar.SERVER_WS_CREDENT_REQCODE)) {
                strreqcode+=value;
            }
            if (key.equals(SessionManager.CURRENT_TOKEN)) {
                strtoken+=value;
            }
            if (key.equals(StaticVar.SERVER_WS_IS_LOGIN)) {
                islogin+=value.toUpperCase();
            }
            if (key.equals(StaticVar.SERVER_WS_USER_LOGOUTDATE_PARAM)) {
                strlogoutdate+=value;
            }
        }

        if (islogin.isEmpty() || islogin.length() == 0) {
            throw new IOException("Error : Parameter IS_LOGIN tidak boleh Kosong !");
        }

        if ((strparamuser.isEmpty() || strparamuser.length() ==0 ||strreqcode.length() ==0 ||strreqcode.isEmpty()
                || strtoken.isEmpty() || strtoken.length() ==0) && islogin.equals(StaticVar.SERVER_WS_LOGIN)  ) {
            throw new IOException("Error : Parameter User / ReqCode / Token tidak boleh Kosong !");
        }

        if ((strparamuser.isEmpty() || strparamuser.length() ==0 ||strreqcode.length() ==0 ||strreqcode.isEmpty()
                ) && islogin.equals(StaticVar.SERVER_WS_LOGOUT)  ) {
            throw new IOException("Error : Parameter User / ReqCode / Logout Date tidak boleh Kosong !");
        }

        String strUrl = StaticVar.SERVER_URL+"/"+StaticVar.SERVER_CONTEXT+StaticVar.SERVER_WS_SERVICE_LOGIN;
        BaseGenericUrl url = new BaseGenericUrl(strUrl);

        setMandatoryParam(url);
        url.put(StaticVar.SERVER_WS_USER_USERNAME_PARAM,strparamuser);
        url.put(StaticVar.SERVER_WS_CREDENT_REQCODE, strreqcode);
        if (islogin.equals(StaticVar.SERVER_WS_LOGIN)) {
            url.put(StaticVar.SERVER_WS_USER_PASS_PARAM, strparampass);
            url.put(SessionManager.CURRENT_TOKEN, strtoken);
        } else {
            url.put(StaticVar.SERVER_WS_USER_LOGOUTDATE_PARAM, strlogoutdate);
        }

        HttpRequest request = requestFactory.buildGetRequest(url);
        JsonObject respon = parseResponse(request.execute());
        return respon;
    }

    public JsonObject parseResponse(HttpResponse response) throws IOException ,JSONException{
        String hasil = response.parseAsString();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(hasil).getAsJsonObject();
        return o;
    }

    private void setMandatoryParam(BaseGenericUrl url){
        url.put(StaticVar.SERVER_WS_CREDENT_L_PARAM,StaticVar.SERVER_WS_CREDENT_L_VAL);
        url.put(StaticVar.SERVER_WS_CREDENT_P_PARAM,StaticVar.SERVER_WS_CREDENT_P_VAL);
    }

}
