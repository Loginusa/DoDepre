package id.loginusa.dosis.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import id.loginusa.dosis.LoginActivity;
import id.loginusa.dosis.MainActivity;

/**
 * Created by mfachmirizal on 5/3/16.
 */
public class LoginUtility  extends SessionManager{

    // Is Login
    private static final String USER_IS_LOGIN = "UserIsLoggedIn";
    // Username
    public static final String LOGIN_USERNAME = "username";
    // Name
    public static final String LOGIN_NAME = "name";
    // Email address
    public static final String LOGIN_EMAIL = "email";
    // Profpic
    public static final String LOGIN_PROFPIC = "profpic";


    public LoginUtility(Context context) {
        super(context);
    }


    /**
     * Create Login Session
     *
     * @param username user key
     * @param name user name
     * @param email user email
     * @param profpic user profpic
     */
    public void createLoginSession(String username,String name,String email, String profpic){
        // Storing login value as TRUE
        editor.putBoolean(USER_IS_LOGIN, true);

        // Storing user name in pref
        editor.putString(LOGIN_USERNAME, username);

        // Storing name in pref
        editor.putString(LOGIN_NAME, name);

        // Storing email in pref
        editor.putString(LOGIN_EMAIL, email);

        // Storing profpic in pref
        editor.putString(LOGIN_PROFPIC, profpic);

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
        // Clearing all data from Shared Preferences
        editor.remove(USER_IS_LOGIN);
        editor.remove(LOGIN_USERNAME);
        editor.remove(LOGIN_NAME);
        editor.remove(LOGIN_EMAIL);
        editor.remove(LOGIN_PROFPIC);

        editor.commit();
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
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(LOGIN_USERNAME, pref.getString(LOGIN_USERNAME, ""));
        user.put(LOGIN_NAME, pref.getString(LOGIN_NAME, ""));
        user.put(LOGIN_EMAIL, pref.getString(LOGIN_EMAIL, ""));
        user.put(LOGIN_PROFPIC, pref.getString(LOGIN_PROFPIC, ""));

        return user;
    }
}
