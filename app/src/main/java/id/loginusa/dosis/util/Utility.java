package id.loginusa.dosis.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    public static String generateApiCode(String source,int servercode) throws Exception{
        String sss = source+StaticVar.BUMBU+servercode;
        return CryptoSHA1BASE64.hash(sss);
    }

    // default / minimum date
    static public Date getMinimumDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1900);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Convert Date String to given string format
     * @param format Date Format
     * @param strDate String Date
     * @return Date with given date format
     * @throws Exception
     */
    static public Date toDateWithFormat(String format,String strDate) throws Exception{
        if (strDate == null) {
            String strDateTemp = new SimpleDateFormat(format).format(getMinimumDate());
            return new SimpleDateFormat(format).parse(strDateTemp);
        }
        if (strDate.equals("")) {
            String strDateTemp = new SimpleDateFormat(format).format(getMinimumDate());
            return new SimpleDateFormat(format).parse(strDateTemp);
        }
        return new SimpleDateFormat(format).parse(strDate);
    }
/*
    static public Date toDateWithFormat(String format,String strDate) throws Exception{
        return new SimpleDateFormat(format).parse(strDate);
    }
*/

    /**
     * Convert Date to given string format
     * @param format Date Format
     * @param date String Date
     * @return Date with given date format
     * @throws Exception
     */
    static public Date toDateWithFormat(String format,Date date) throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if (date == null) {
            return dateFormat.parse(dateFormat.format(getMinimumDate()));
        } else {
            return dateFormat.parse(dateFormat.format(date));
        }
    }

    public static String GetUTCdatetimeAsString()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(StaticVar.STRING_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        final String utcTime = sdf.format(new Date());

        return utcTime;
    }
}
