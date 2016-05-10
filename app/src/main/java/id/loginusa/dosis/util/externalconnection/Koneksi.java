package id.loginusa.dosis.util.externalconnection;

import com.google.api.client.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mfachmirizal on 10-May-16.
 */
public interface Koneksi {
    public void startConnection(Map<String,String> param) throws IOException;
 //   public void parseResponse(HttpResponse response) throws IOException;
}
