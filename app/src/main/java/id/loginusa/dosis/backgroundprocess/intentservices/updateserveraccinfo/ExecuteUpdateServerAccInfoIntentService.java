package id.loginusa.dosis.backgroundprocess.intentservices.updateserveraccinfo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import id.loginusa.dosis.util.CryptoSHA1BASE64;
import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.Utility;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoConnection;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoDataService;
import id.loginusa.dosis.util.externalconnection.openbravows.OpenbravoLoginService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ExecuteUpdateServerAccInfoIntentService extends IntentService {

    private static final String className = "ExecuteUpdateServerAccInfoIntentService";

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_UpdateServerAccInfo = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".action.UpdateServerAccInfo";

    // Parameters
    private static final String PARAM_JSON_USER_DATA = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".param.json_user_data";

    // BroadCast Receiver Static variable
    public static final String BROADCAST_REV_DATE = "id.loginusa.dosis.backgroundprocess.intentservices."+className+".broadcast.json_user_data";

    //private Context context;

    public ExecuteUpdateServerAccInfoIntentService() {
        super(className);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startUpdateServerAccInfoIntentService(Context context, String param1){
        Intent intent = new Intent(context, ExecuteUpdateServerAccInfoIntentService.class);
        intent.setAction(ACTION_UpdateServerAccInfo);
        intent.putExtra(PARAM_JSON_USER_DATA, param1);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Logging.log('i',"IS_"+className,className+" sedang berjalan");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UpdateServerAccInfo.equals(action)) {
                final String jsonUserData = intent.getStringExtra(PARAM_JSON_USER_DATA);
                handleActionUpdateServerAccInfo(jsonUserData);
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateServerAccInfo(String param1) {
        //TODO:akses ob ws dan jalankan api update

        OpenbravoConnection dc = new OpenbravoConnection();
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put(StaticVar.SERVER_WS_JSON_USER_DATA_PARAM, param1);
            param.put(StaticVar.SERVER_WS_CREDENT_REQCODE, Utility.generateApiCode(StaticVar.SERVER_WS_SERVICE_DATA, StaticVar.SERVER_WS_DATA_SERVICE_CODE));
            JsonObject jsonResponse = dc.sendRequest(new OpenbravoDataService(), param);

            Logging.log('d',"INFO_Response","Response rev_date : "+jsonResponse.toString());

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ACTION_UpdateServerAccInfo);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(BROADCAST_REV_DATE, jsonResponse.toString()); //edit jadi tanggal aja ntr
            sendBroadcast(broadcastIntent);
            this.stopSelf();
        } catch(Exception e) {

        }
    }
}
