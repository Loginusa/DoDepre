package id.loginusa.dosis;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.loginusa.dosis.backgroundprocess.intentservices.dataservice.ExecuteServerDataServiceIntentService;
import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.LoginSession;
import id.loginusa.dosis.util.StaticVar;
import id.loginusa.dosis.util.Utility;
import id.loginusa.dosis.util.json.JsonBuilder;
import id.loginusa.dosis.util.json.entity.UserData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LoginSession loginSession;
    FloatingActionButton fab;
    Toolbar toolbar;

    static Class fragmentClass = null;
    static DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    //BroadcastReceiver
    private DataServiceBroadcastReceiver dataServiceBroadcastReceiver;

    //testing
    Button bt;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loginSession = new LoginSession(getApplicationContext());

        //TODO:Tambahkan pengecekan is login nanti, bila tidak login / password berubah, akan kembali ke guest screen

        EditText edToken, edInstanceId;
        edToken = (EditText) findViewById(R.id.edToken);
        edInstanceId = (EditText) findViewById(R.id.edInstanceId);

        edToken.setText(loginSession.getCurrentToken());
        edInstanceId.setText(loginSession.getCurrentInstanceId());

        Intent i = getIntent();
        String param = i.getStringExtra("paramButText");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //toolbar.setTitle("Anjayyy");
                Logging.toast(MainActivity.this,"Session Sekarang : "+loginSession.getUserData(),2);
                Logging.log('i',"SESSION_SKRG","Session Sekarang : "+loginSession.getUserData());
            }
        });

        bt = (Button) findViewById(R.id.buttest);
        assert bt != null;
        if (param != null) { //bila berasal dari notif
            bt.setText(param);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginSession.clearSharedPreference();
                //Logging.toast(MainActivity.this, "Dihapus", 1);
                /*UserData user = loginSession.getUserDetails();
                user.setName("Luhur Pambudi");
                user.setProfpic("gambaras"); /* */

                try {
//                                      loginSession.updateServerAccInfo(JsonBuilder.toJson(user,1));
                    loginSession.refreshAccountInfo();
                } catch (Exception e) {
                    Logging.toast(MainActivity.this,"Error saat check : "+e.getMessage(),1);
                }


//                finish();
//                startActivity(getIntent());
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setDrawerMenuByRole();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            drawer.closeDrawer(GravityCompat.START);
            //startActivityForResult(intent, U.LOGIN_ACTIVITY_RESULT);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.logout_confirm)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            loginSession.logoutUser();
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create();
            builder.show();
        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.exit_confirm)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = getIntent();
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create();
            builder.show();
            return true;
        } /* else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loginSession.refreshAccountInfo();
        } catch (Exception e) {
            Logging.toast(MainActivity.this,"Error saat check : "+e.getMessage(),1);
        }

//        setDrawerMenuByRole();

        //register receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(ExecuteServerDataServiceIntentService.ACTION_UpdateServerAccInfo);
        filter.addAction(ExecuteServerDataServiceIntentService.ACTION_CheckServerAccConsistency);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        dataServiceBroadcastReceiver = new DataServiceBroadcastReceiver();
        registerReceiver(dataServiceBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dataServiceBroadcastReceiver);
        Logging.toast(this, "Pause, matikan receiver",1);
    }

    public void setDrawerMenuByRole() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // View navHeader = navigationView.getHeaderView(R.layout.nav_header_main);
        UserData user = loginSession.getUserDetails();

        TextView navhead_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navhead_name);
        TextView navhead_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navhead_email);
        //if (loginSession.isLoggedIn()) {
        if (user.isIsLogin()) {
            navigationView.getMenu().setGroupVisible(R.id.gr_not_login, false);
            navigationView.getMenu().setGroupVisible(R.id.gr_is_login, true);
            navhead_name.setText(user.getName());
            navhead_email.setText(user.getEmail());

            //test button yg preference
            bt.setVisibility(View.VISIBLE);

/*            String imagePath = sharedPreferences.getString(LoginSharedPreference.PROFPIC,"");
            if (imagePath != "") {
                navhead_profpic.setImageBitmap(U.getBitmap(imagePath));
            }
            navhead_profpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;
                    try {
                        fragmentClass = FragmentPhoto.class;
                        fragment = (Fragment) fragmentClass.newInstance();

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(FragmentStack.BIODATA_STACK).commit();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Page Not Available", Toast.LENGTH_SHORT).show();
                    } finally {
                        fragment = null;
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });*/

        } else {
            navigationView.getMenu().setGroupVisible(R.id.gr_not_login, true);
            navigationView.getMenu().setGroupVisible(R.id.gr_is_login, false);

            bt.setText("Test Ganti Nama");
            bt.setVisibility(View.INVISIBLE);
            //navhead_profpic.setOnClickListener(null);
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://id.loginusa.dosis/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://id.loginusa.dosis/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //broadcast receiver
    public class DataServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String resultResponse = (String) extras.get(StaticVar.INTENT_STATUS_EXTRA);
            String resultAction = intent.getAction();
            if (resultAction.equals(ExecuteServerDataServiceIntentService.ACTION_UpdateServerAccInfo) && resultResponse.equals(StaticVar.INTENT_STATUS_EXTRA_SUCCESS)) {
                String strRevDate = (String) extras.get(ExecuteServerDataServiceIntentService.BROADCAST_REV_DATE);
                JsonObject jRevDate = JsonBuilder.getJsonObject(strRevDate);

                try {
                    Date newRevDate = Utility.toDateWithFormat(StaticVar.STRING_DATE_FORMAT,jRevDate.getAsJsonObject("response").get("data").getAsJsonArray().get(0).getAsString());
                    loginSession.setNewRevisionDate(newRevDate);
                    Logging.toast(MainActivity.this, getString(R.string.change_data_succes), 2);
                } catch (ParseException e) {
                    Logging.toast(MainActivity.this, getString(R.string.failed_to_get_new_revdate) + e.getMessage(), 2);
                } catch (Exception ex) {
                    Logging.toast(MainActivity.this, getString(R.string.failed_to_get_new_revdate)+" : " + ex.getMessage(), 2);
                }
            } else if (resultAction.equals(ExecuteServerDataServiceIntentService.ACTION_CheckServerAccConsistency) && resultResponse.equals(StaticVar.INTENT_STATUS_EXTRA_SUCCESS)) {
                boolean forceLogout = (boolean) extras.get(ExecuteServerDataServiceIntentService.IS_FORCE_LOGOUT);
                try {
                    if (forceLogout) {
                        loginSession.logoutUser();
                        finish();
                        startActivity(getIntent());
                    } else {
                        //tidak di logoff paksa, lakukan pengecekan data
                        int server_response_code = (int) extras.get(ExecuteServerDataServiceIntentService.SERVER_RESPONSE_CODE);
                        String server_response =  (String) extras.get(ExecuteServerDataServiceIntentService.SERVER_RESPONSE);
                        JsonObject jServerResponse = JsonBuilder.getJsonObject(server_response);

                        if (server_response_code == StaticVar.OB_RESPONSE_CODE_SERVER_DATA_NEWER) {
                            loginSession.updateAccInfo(jServerResponse);
                        } else if (server_response_code == StaticVar.OB_RESPONSE_CODE_CLIENT_DATA_NEWER) {
                            Date newRevDate = Utility.toDateWithFormat(StaticVar.STRING_DATE_FORMAT,jServerResponse.getAsJsonObject("response").get("data").getAsJsonArray().get(0).getAsString());
                            loginSession.setNewRevisionDate(newRevDate);
                        }

                        //Logging.log('d',"Pesan_Broadcast","Pesan_Broadcast("+server_response_code+") : "+server_response);
                        //Logging.toast(MainActivity.this,"Pesan_Broadcast("+server_response_code+") : "+server_response,2);

                    }
                } catch (ParseException e) {
                    Logging.toast(MainActivity.this, getString(R.string.failed_to_get_new_revdate) + e.getMessage(), 2);
                } catch (Exception ex) {
                    Logging.toast(MainActivity.this, getString(R.string.failed_to_get_server_response)+" : " + ex.getMessage(), 2);
                }
            }
            else { //tidak masuk action manapun / terdapat error di proses IntentService
                Logging.toast(MainActivity.this,"Error "+" : "+resultResponse,2);
            }

            setDrawerMenuByRole();
        }

    }


}
