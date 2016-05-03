package id.loginusa.dosis.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by mfachmirizal on 5/3/16.
 * Pojo Class for this application
 */
public class Utility {

    /**
     * go to fragment without add BackStack
     * @param context
     * @param fragmentClass
     * @param viewId
     * @param isclear
     */
    public static void goToFragment(Context context,Class fragmentClass,int viewId,boolean isclear) {
        goToFragmentAddBackStack(context,fragmentClass,viewId,isclear,false,"");
    }

    /**
     * go to fragment with BackStack
     *
     * @param context
     * @param fragmentClass
     * @param viewId
     * @param isclear
     * @param addToBackStack
     * @param backstackKey
     */
    public static void goToFragmentAddBackStack(Context context,Class fragmentClass,int viewId,boolean isclear,boolean addToBackStack,String backstackKey) {
        Fragment fragment = null;
        if (isclear) {
            clearFragmentStack(context);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();

            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            if (addToBackStack) {
                fragmentManager.beginTransaction().replace(viewId, fragment).addToBackStack(backstackKey).commit(); //R.id.flContent = viewId
            } else {
                fragmentManager.beginTransaction().replace(viewId, fragment).commit(); //R.id.flContent = viewId
            }
        } catch (Exception e) {
            Toast.makeText(context, "Page Not Available", Toast.LENGTH_SHORT).show();
        } finally {
            fragment = null;
        }

    }

    public static void clearFragmentStack(Context context) {
        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}
