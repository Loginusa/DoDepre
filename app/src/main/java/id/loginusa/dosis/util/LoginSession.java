package id.loginusa.dosis.util;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.loginusa.dosis.LoginActivity;
import id.loginusa.dosis.util.json.JsonBuilder;
import id.loginusa.dosis.util.json.entity.UserData;

/**
 * Created by mfachmirizal on 5/3/16.
 */



/* TODO: UBAH PENYIMPINAN SESSION DENGAN MENGANDALKAN OBJECT MAPPING DARI JSON OBJECT KE JAVA OBJECT, dan sebaliknya jadi semuanya jadi JSON

 */
public class LoginSession extends SessionManager{

    // User Info
    /*session static lama*/
    /*//ldos_dosisaccount
    private static final String USER_IS_LOGIN = "UserIsLoggedIn";
    public static final String USER_PROFPIC = "profpic";
    public static final String USER_REVISION_DATE = "revdate";
    public static final String USER_AD_USER_ID = "aduserid";
    //ad_user_id
    public static final String USER_USERNAME = "username";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone"; //ini phone hp, phone rumah / tempat tugas menyatu di address
    public static final String USER_ALTPHONE = "altphone";
    public static final String USER_FAX = "fax";
    //ldos_dosisaddress
    public static final String USER_ADDRESS = "userAddress"; //termasuk lat long*/

    //Session static baru
    private static final String USER_IS_LOGIN = "UserIsLoggedIn";
    public static final String USER_DATA = "userData";

    public LoginSession(Context context) {
        super(context);
    }


    /**
     * Create Login Session
     *
     */
    //public void createLoginSession(String username,String name,String email, String profpic){
    public void createLoginSession(JsonObject data){
        // Storing login value as TRUE

/*        editor.putBoolean(USER_IS_LOGIN, true);
        editor.putString(USER_PROFPIC, data.get("profpic").getAsString() );
        editor.putString(USER_REVISION_DATE, data.get("revision_date").getAsString() );
        editor.putString(USER_AD_USER_ID, data.get("ad_user_id").getAsString() );

        editor.putString(USER_USERNAME, data.get("username").getAsString());
        editor.putString(USER_NAME, data.get("name").getAsString());
        editor.putString(USER_EMAIL, data.get("email").getAsString());

        editor.putString(USER_PHONE, data.get("phone").getAsString());       //ini phone hp, phone rumah / tempat tugas menyatu di address
        editor.putString(USER_ALTPHONE, data.get("altphone").getAsString()); //alternatif phone hp
        editor.putString(USER_FAX, data.get("fax").getAsString());

        //bagaimana memecah lat long nya ?
        editor.putString(USER_ADDRESS, data.get("address").getAsString()); // isinya JSON*/

        editor.putBoolean(USER_IS_LOGIN, true);
        editor.putString(USER_DATA,data.toString());

        // commit changes
        editor.commit();
    }

    /**
     * check this user login status, if not login, the redirect to login page
     */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences, excepts GCM session
        boolean issent = pref.getBoolean(SENT_TOKEN_TO_SERVER, false);
        String token = pref.getString(CURRENT_TOKEN, "");
        String instanceid = pref.getString(CURRENT_INSTANCE_ID, "");

        editor.clear();
        editor.commit();
        //reset session token gcm
        createGCMTokenSentSession(issent,token,instanceid);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(USER_IS_LOGIN, false);
    }

    /**
     * Get stored session data
     * */
/*    @Deprecated
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
//        user.put(USER_USERNAME, pref.getString(USER_USERNAME, ""));
//        user.put(USER_NAME, pref.getString(USER_NAME, ""));
//        user.put(USER_EMAIL, pref.getString(USER_EMAIL, ""));
//        user.put(USER_PROFPIC, pref.getString(USER_PROFPIC, ""));

        return user;
    }*/

    /**
     * Get stored session data
     * */

    public UserData getUserDetails(){
        UserData user = new UserData();
        List<UserData> userList = UserData.getList(pref.getString(USER_DATA, JsonBuilder.toJson(user,0)));
        if (userList != null) {
            user = userList.get(0);
        }
        return user;
    }
}
