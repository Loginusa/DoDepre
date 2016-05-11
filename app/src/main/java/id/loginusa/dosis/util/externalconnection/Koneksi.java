package id.loginusa.dosis.util.externalconnection;

import com.google.api.client.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public interface Koneksi {
    public JSONObject executeConnection(Map<String,String> param) throws IOException,JSONException;
 //   public void parseResponse(HttpResponse response) throws IOException;
}
