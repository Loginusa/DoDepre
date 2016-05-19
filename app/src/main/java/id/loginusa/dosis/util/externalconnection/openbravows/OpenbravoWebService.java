package id.loginusa.dosis.util.externalconnection.openbravows;

import com.google.api.client.http.HttpResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public interface OpenbravoWebService {
    public JsonObject executeConnection(Map<String,String> param) throws IOException,JSONException;
 //   public void parseResponse(HttpResponse response) throws IOException;
}
