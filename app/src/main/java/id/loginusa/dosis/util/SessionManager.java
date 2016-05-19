package id.loginusa.dosis.util;

/**
 * Created by mfachmirizal on 21-Apr-16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;



    // All Shared Preferences Keys
    //token
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String CURRENT_TOKEN = "sentRegidToServer";
    public static final String CURRENT_INSTANCE_ID = "instanceid";




    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(StaticVar.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
/*    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }*/

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
  /*  public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
*/


    /**
     * Get stored session data
     * */
  /*  public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }
*/
    /**
     * Clear session details
     * */
  /*  public void logoutUser(){
        // Clearing all data from Shared Preferences
        clearSharedPreference();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
*/

    public void clearSharedPreference() {
        // Clearing all data from Shared Preferences
        // Storing is token sent value as TRUE
//        editor.putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        // Storing current token
//        editor.putString(CURRENT_TOKEN, "").apply();
        // IS_RECEIVER_REGISTERED
//        setRegisterReceiver(false);

        editor.clear();
        editor.commit();
    }

    /**
     * get Current Instance ID
     * **/
    public String getCurrentInstanceId() {
        return pref.getString(CURRENT_INSTANCE_ID, "");
    }

    /**
     * Quick check Token Sent State
     * **/
    public boolean isTokenSent(){
        return pref.getBoolean(SENT_TOKEN_TO_SERVER, false);
    }

    /**
     * get Current Token
     * **/
    public String getCurrentToken() {
        return pref.getString(CURRENT_TOKEN, "");
    }

    /**
     * register state
     * **/
    //isReceiverRegistered

    /**
     * get Current Token
     * **/
    public void createGCMTokenSentSession(boolean issent, String token, String instanceid){
        // Storing is token sent value as TRUE
        editor.putBoolean(SENT_TOKEN_TO_SERVER, issent).apply();
        // Storing current token
        editor.putString(CURRENT_TOKEN, token).apply();
        // Storing current instanceId
        editor.putString(CURRENT_INSTANCE_ID, instanceid).apply();
        // commit changes
        editor.commit();
    }

}