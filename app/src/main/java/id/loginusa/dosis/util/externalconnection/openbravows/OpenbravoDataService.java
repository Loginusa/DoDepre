package id.loginusa.dosis.util.externalconnection.openbravows;

/**
 * Created by mfachmirizal on 22-May-16.
 */
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

import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.SessionManager;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.externalconnection.BaseGenericUrl;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public class OpenbravoDataService implements OpenbravoWebService {
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
        String strparamdata = "";
        String strreqcode = "";
        String stractioncode = "";
        String strtoken = "";
        for(Map.Entry<String, String> entry : param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals(StaticVar.SERVER_WS_JSON_USER_DATA_PARAM)) {
                strparamdata+=value;
            }
            if (key.equals(StaticVar.SERVER_WS_CREDENT_REQCODE)) {
                strreqcode+=value;
            }
            if (key.equals(StaticVar.SERVER_WS_SERVICE_DATA_ACTION_PARAM)) {
                stractioncode+=value;
            }
            if (key.equals(SessionManager.CURRENT_TOKEN)) {
                strtoken+=value;
            }
        }

        if (strparamdata.isEmpty() || strparamdata.length() ==0 ||strreqcode.length() ==0 ||strreqcode.isEmpty()
                || stractioncode.isEmpty() || stractioncode.length() ==0
                || strtoken.isEmpty() || strtoken.length() ==0)  {
            throw new IOException("Parameter Data / ReqCode / Action Code / Token tidak boleh Kosong !");
        }

        String strUrl = StaticVar.SERVER_URL+"/"+StaticVar.SERVER_CONTEXT+StaticVar.SERVER_WS_SERVICE_DATA;

        BaseGenericUrl url = new BaseGenericUrl(strUrl);

        setMandatoryParam(url);
        url.put(StaticVar.SERVER_WS_CREDENT_REQCODE,strreqcode);
        url.put(StaticVar.SERVER_WS_SERVICE_DATA_ACTION_PARAM,stractioncode);
        url.put(StaticVar.SERVER_WS_JSON_USER_DATA_PARAM,strparamdata);
        //token untuk check konsistensi data server dan client
        url.put(SessionManager.CURRENT_TOKEN,strtoken);

//        Logging.log('d',"terst1",url.toString());
//        Logging.log('d',"terst2",strparamdata);

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
