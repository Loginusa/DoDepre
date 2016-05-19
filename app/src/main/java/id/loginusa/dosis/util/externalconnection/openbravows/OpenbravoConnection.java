package id.loginusa.dosis.util.externalconnection.openbravows;

import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public class OpenbravoConnection {

    public static JsonObject sendRequest(OpenbravoWebService kon, Map<String,String> parameter) throws IOException,JSONException{
        return kon.executeConnection(parameter);
    }
}
