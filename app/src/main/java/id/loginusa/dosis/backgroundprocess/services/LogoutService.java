package id.loginusa.dosis.backgroundprocess.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.loginusa.dosis.R;
import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.LoginSession;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.Utility;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoConnection;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoLoginService;
import id.loginusa.dosis.util.json.JsonBuilder;

public class LogoutService extends Service {

    private static final String TAG = "LogoutService";

    //Parameter
    public static final String EXTRA_PARAM_USERNAME = "id.loginusa.dosis.backgroundprocess.services."+TAG+".extra.EXTRA_PARAM_USERNAME";

    volatile private boolean isRunning  = false;

    LoginSession loginSession ;

    //final static String ;
    public LogoutService() {
    }

    //helper
    public static void startLogoutService(Context context, String username){
        Intent intent = new Intent(context, LogoutService.class);
        intent.putExtra(EXTRA_PARAM_USERNAME, username);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "LogoutService onCreate");
        isRunning = true;
        loginSession = new LoginSession(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (loginSession.getPendingUserLogout().length() == 0) {
            loginSession.createPendingUserLogout( intent.getStringExtra(EXTRA_PARAM_USERNAME));
        }
        handleActionLogout();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        loginSession.clearPendingUserLogout();
        Log.i(TAG, "Service onDestroy");
    }

    //handle method
    private void handleActionLogout() {
        Log.i(TAG, "Service onStartCommand");

        final String param1 = loginSession.getPendingUserLogout();
        //Creating new thread for service
        //Always write long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {

                String strLogoutDate = Utility.GetUTCdatetimeAsString();
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                while (isRunning) {
                    if (isRunning) {
                        //check apakah dalam keadaan login?, bila iya maka batalkan
                        boolean isLogin = loginSession.isLoggedIn();
                        if (isLogin) {
                            Log.i(TAG, "stop service paksa : " + isLogin);
                            stopSelf();
                            try {
                                Thread.sleep(1500);
                            } catch (Exception e) {
                            }
                        } else {
                            //logic
                            Log.i(TAG, "Melakukan Request Logoff ke server : " + param1);
                            OpenbravoConnection dc = new OpenbravoConnection();
                            try {
                                Map<String, String> param = new HashMap<String, String>();
                                param.put(StaticVar.SERVER_WS_USER_USERNAME_PARAM, param1);
                                param.put(StaticVar.SERVER_WS_IS_LOGIN, StaticVar.SERVER_WS_LOGOUT);
                                param.put(StaticVar.SERVER_WS_USER_LOGOUTDATE_PARAM, strLogoutDate);
                                param.put(StaticVar.SERVER_WS_CREDENT_REQCODE, Utility.generateApiCode((StaticVar.SERVER_WS_SERVICE_LOGIN + StaticVar.SERVER_WS_LOGOUT), StaticVar.SERVER_WS_LOGIN_SERVICE_CODE));
                                JsonObject jsonResponse = dc.sendRequest(new OpenbravoLoginService(), param); //return response data
                                int jawaban = JsonBuilder.getJsonStatusCode(jsonResponse);
                                if (jawaban == StaticVar.OB_RESPONSE_CODE_SUCCESS) {
                                    Logging.log('i', TAG, getString(R.string.logout_success));
                                } else {
                                    Logging.log('e', TAG, jsonResponse.toString());
                                }
                                //terminator
                                stopSelf();
                            } catch (IOException io) {
                                Logging.log('e', TAG, getString(R.string.not_connected_to_server));
                            } catch (Exception e) {
                                Logging.log('d', TAG, "Response Exception : " + e.getMessage());
                            }

                            try {
                                Thread.sleep(5003);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }).start();

    }
}
