package id.loginusa.dosis.util.externalconnection;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.StaticVar;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public class OpenbravoGetUserConnection implements Koneksi {
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public JSONObject executeConnection(Map<String,String> param)  throws IOException,JSONException {
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
        String strparamuser = "";
        String strparampass = "";
        for(Map.Entry<String, String> entry : param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("username")) {
                strparamuser+=value;
            }
            if (key.equals("pss")) {
                strparampass+=value;
            }
        }

        if (strparamuser.isEmpty() || strparamuser.length() ==0) {
            throw new IOException("Parameter User tidak boleh Kosong !");
        }

        //String strUrl = StaticVar.SERVER_URL+"/"+StaticVar.SERVER_CONTEXT+"/org.openbravo.service.json.jsonrest/ADUser?_where=username='"+strparamuser+"'&"+StaticVar.SERVER_WS_CREDENT;
        String strUrl = StaticVar.SERVER_URL+"/"+StaticVar.SERVER_CONTEXT+"/ws/id.loginusa.dosis.loginservice";

        //URL myURL = new URL(strUrl);

        ExternalUrl url = new ExternalUrl(strUrl);

        url.put("l","Openbravo");
        url.put("p","PwOd6SgWF74HY4u51bfrUxjtB9g=");
        url.put("username",strparamuser);
        url.put("pss",strparampass);
        //url.put("fields", "items(id,url,object(content,plusoners/totalItems))");
        HttpRequest request = requestFactory.buildGetRequest(url);

        Logging.log('d',"OB_URL",request.getUrl().toString());

        JSONObject respon = parseResponse(request.execute());
        return respon;
    }

    public JSONObject parseResponse(HttpResponse response) throws IOException ,JSONException{
        String hasil = response.parseAsString();
        JSONObject jobject = new JSONObject(hasil);
        return jobject;
//        if (hasil.isEmpty()) {
//            Logging.log('d',"TEST_KONEKSI","HASIL NYA : KOSONG");
//        } else {
//            Logging.log('d',"TEST_KONEKSI","HASIL NYA : "+hasil);
//        }
    }

//nanti lanjutkan, ini class representasi dari entitas ad_user [ dibuat terpisah ]
    /*public static class Data {
        @Key
        public String id;

        @Key
        public String _identifier;

        @Key
        public String title;

        @Key
        public String url;
    }*/

}
