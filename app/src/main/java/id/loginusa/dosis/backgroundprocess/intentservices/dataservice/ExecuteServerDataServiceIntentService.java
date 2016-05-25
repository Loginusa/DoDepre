package id.loginusa.dosis.backgroundprocess.intentservices.dataservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.SessionManager;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.Utility;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoConnection;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoDataService;
import id.loginusa.dosis.util.json.JsonBuilder;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ExecuteServerDataServiceIntentService extends IntentService {

    private static final String className = "ExecuteServerDataServiceIntentService";

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_UpdateServerAccInfo = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".action.UpdateServerAccInfo";

    public static final String ACTION_CheckServerAccConsistency = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".action.CheckServerAccConsistency";

    // Parameters
    private static final String PARAM_JSON_USER_DATA = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".param.json_user_data";
    private static final String PARAM_JSON_TOKEN= "id.loginusa.dosis.backgroundprocess.intentservices."+className+".param.token";

    // BroadCast Receiver Static variable
    public static final String BROADCAST_REV_DATE = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".broadcast.broadcast_rev_date";
    public static final String IS_FORCE_LOGOUT = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".broadcast.force_logout";
    public static final String SERVER_RESPONSE_CODE = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".broadcast.server_response_code";
    public static final String SERVER_RESPONSE = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".broadcast.server_response";


    public ExecuteServerDataServiceIntentService() {
        super(className);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startUpdateServerAccInfoIntentService(Context context, JsonObject data){
        Intent intent = new Intent(context, ExecuteServerDataServiceIntentService.class);
        intent.setAction(ACTION_UpdateServerAccInfo);
        intent.putExtra(PARAM_JSON_USER_DATA, data.toString());


        context.startService(intent);
    }

    public static void startCheckServerAccConsistency(Context context, JsonObject data, String token){
        Intent intent = new Intent(context, ExecuteServerDataServiceIntentService.class);
        intent.setAction(ACTION_CheckServerAccConsistency);
        intent.putExtra(PARAM_JSON_USER_DATA, data.toString());
        intent.putExtra(PARAM_JSON_TOKEN, token);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Logging.log('i',"IS_"+className,className+" sedang berjalan");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UpdateServerAccInfo.equals(action)) {
                final String jsonUserData = intent.getStringExtra(PARAM_JSON_USER_DATA);

                handleActionUpdateServerAccInfo(jsonUserData,StaticVar.SERVER_WS_SERVICE_DATA_ACTION_UPDATE_DATA);
            } else if (ACTION_CheckServerAccConsistency.equals(action)) {
                final String jsonUserData = intent.getStringExtra(PARAM_JSON_USER_DATA);
                final String token = intent.getStringExtra(PARAM_JSON_TOKEN);
                handleActionCheckServerAccConsistency(jsonUserData,token,StaticVar.SERVER_WS_SERVICE_DATA_ACTION_CHECK_CONST_DATA);
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateServerAccInfo(String param1,String actionCode) {
        OpenbravoConnection dc = new OpenbravoConnection();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_UpdateServerAccInfo);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put(StaticVar.SERVER_WS_JSON_USER_DATA_PARAM, param1);
            param.put(StaticVar.SERVER_WS_SERVICE_DATA_ACTION_PARAM, actionCode);
            param.put(StaticVar.SERVER_WS_CREDENT_REQCODE, Utility.generateApiCode((StaticVar.SERVER_WS_SERVICE_DATA+actionCode), StaticVar.SERVER_WS_DATA_SERVICE_CODE));
            JsonObject jsonResponseDate = dc.sendRequest(new OpenbravoDataService(), param);


            broadcastIntent.putExtra(BROADCAST_REV_DATE, jsonResponseDate.toString());
            broadcastIntent.putExtra(StaticVar.INTENT_STATUS_EXTRA,StaticVar.INTENT_STATUS_EXTRA_SUCCESS);  //juga sebagai error message, bila status_extra nya melenceng dari apa yg ditentukan
        } catch(Exception e) {
            Logging.log('d', "INI", "Response Exception : " + e.getMessage());
            broadcastIntent.putExtra(StaticVar.INTENT_STATUS_EXTRA, e.getMessage()); //juga sebagai penampung error message, bila terjadi exception
        }


        sendBroadcast(broadcastIntent);
        this.stopSelf();
    }

    private void handleActionCheckServerAccConsistency(String data,String token,String actionCode) {
        OpenbravoConnection dc = new OpenbravoConnection();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_CheckServerAccConsistency);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put(StaticVar.SERVER_WS_JSON_USER_DATA_PARAM, data);
            param.put(StaticVar.SERVER_WS_SERVICE_DATA_ACTION_PARAM, actionCode);
            param.put(StaticVar.SERVER_WS_CREDENT_REQCODE, Utility.generateApiCode((StaticVar.SERVER_WS_SERVICE_DATA+actionCode), StaticVar.SERVER_WS_DATA_SERVICE_CODE));
            //tambahan token
            param.put(SessionManager.CURRENT_TOKEN, token);

            JsonObject jsonResponse = dc.sendRequest(new OpenbravoDataService(), param); //return all data
            int jawaban = JsonBuilder.getJsonStatusCode(jsonResponse);
            if (jawaban == StaticVar.OB_RESPONSE_CODE_ACCOUNT_LOGOFF) {
                //logoff secara paksa karena status di server adalah tidak login
                broadcastIntent.putExtra(IS_FORCE_LOGOUT,true);
            } else {
                broadcastIntent.putExtra(SERVER_RESPONSE_CODE,jawaban);
                broadcastIntent.putExtra(SERVER_RESPONSE,jsonResponse.toString());
                broadcastIntent.putExtra(IS_FORCE_LOGOUT,false);
            }

            broadcastIntent.putExtra(StaticVar.INTENT_STATUS_EXTRA,StaticVar.INTENT_STATUS_EXTRA_SUCCESS);

            //Logging.log('d',"INFO_Response","Response check data IS_LOGIN: "+jawaban);
        } catch(Exception e) {
            Logging.log('d', "INI", "Response Exception : " + e.getMessage());
            broadcastIntent.putExtra(StaticVar.INTENT_STATUS_EXTRA, e.getMessage()); //juga sebagai penampung error message, bila terjadi exception
        }
        sendBroadcast(broadcastIntent);
        this.stopSelf();
    }

}
