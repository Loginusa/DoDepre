package id.loginusa.dosis;

//server API KEY 1 : AIzaSyBDdHaQCUAojjSf-2VtXjWMRVw3ikY6kK4

//server API KEY Generated : AIzaSyDka9aHG5doVzpvfJKqE_gS2lpL_6HPxBM
//SENDER ID  : 657311792154

/* analitik
Analytics tracking ID           : UA-76899863-1
Google Analytics Account        : teknikal.loginusa@gmail.com Apps
Analytics Property              : DoSiS Android: id.loginusa.dosis
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import id.loginusa.dosis.backgroundprocess.gcm.RegistrationIntentService;
import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.SessionManager;
import id.loginusa.dosis.util.StaticVar;

public class GCMRegActivity extends AppCompatActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "GCMRegActivity";

    //Session Manager
    SessionManager session;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    TextView tv_status;
    ProgressBar pb_checkingProgressBar;
    private boolean isReceiverRegistered;
    Button btRetry;
    //splash screen
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    Handler timer;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcmreg);

        //Session Manager
        session = new SessionManager(getApplicationContext());

        tv_status = (TextView) findViewById(R.id.tv_status);
//        tv_status.setText("Selamat Datang !");
//        Logging.toast(this,"Masuk on create : "+(session.getCurrentToken()),1);
        tv_status.setText(getString(R.string.label_daftarkan_device_ke_server));
        pb_checkingProgressBar =(ProgressBar) findViewById(R.id.pb_checkingProgressBar);
        btRetry =(Button) findViewById(R.id.btRetry);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btRetry.setVisibility(View.GONE);
                pb_checkingProgressBar.setVisibility(ProgressBar.VISIBLE);
                tv_status.setText(getString(R.string.label_daftarkan_device_ke_server));
                registerReceiver();
            }
        });
        btRetry.setVisibility(View.GONE);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                pb_checkingProgressBar.setVisibility(ProgressBar.VISIBLE);
                if (session.isTokenSent()) {
                    Logging.log('d',"TOKEN",(session.getCurrentToken()));
                    pb_checkingProgressBar.setVisibility(ProgressBar.GONE);
                    tv_status.setText(getString(R.string.gcm_send_message));
                    //Logging.toast(context,(session.getCurrentToken()),1);
                    //dapatkan parameter
                    Intent i = getIntent();
                    String param = i.getStringExtra("param");

                    Intent intentHomePage = new Intent(GCMRegActivity.this, MainActivity.class); //kahade nanti bukan cuma kesini aja
                    intentHomePage.putExtra("paramButText",param);
                    startActivity(intentHomePage);
                    finish();
                } else {
                    unregisterReceiver();
                    tv_status.setText(getString(R.string.token_error_message));
                    btRetry.setVisibility(View.VISIBLE);
                    pb_checkingProgressBar.setVisibility(View.GONE);
                    Logging.toast(context,getString(R.string.token_error_message),2);
                }
            }
        };

//        // Registering BroadcastReceiver
        registerReceiver();
//        timer = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                registerReceiver();
//            }
//        };
//        timer.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);

//
//        //check google play service
    }


    private void registerReceiver(){

        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(StaticVar.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    //    Logging.toast(this,"Resume",1);
        registerReceiver();
    }

    @Override
    protected void onPause() {
        unregisterReceiver();
        super.onPause();
    }



    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
