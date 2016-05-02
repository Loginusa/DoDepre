package id.loginusa.dosis.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mfachmirizal on 21-Apr-16.
 */
public class Logging {
    static private final boolean IS_LOG_ACTIVE=true;

    /**
     *
     * @param type logging type (i/d/e/v)
     * @param tag logging tag
     * @param text logging text
     */
    public static void log(char type,String tag,String text) {
        if (IS_LOG_ACTIVE) {
            switch (type) {
                case 'i':
                    Log.i(tag, text);
                    break;
                case 'd':
                    Log.d(tag, text);
                    break;
                case 'e':
                    Log.e(tag, text);
                    break;
                case 'v':
                    Log.v(tag, text);
                    break;
                default:
                    throw new RuntimeException("Logging type not correct");
            }
        }
    }

    /**
     * Toast for Debugging
     * @param cont Android Context
     * @param message Toast Message
     * @param duration duration  <= 1 = SHORT , > 1 = LONG
     */
    public static void toast(Context cont, String message, int duration) {
        if (IS_LOG_ACTIVE) {
            if (duration <= 1) {
                duration = Toast.LENGTH_SHORT;
            } else {
                duration = Toast.LENGTH_LONG;
            }

            Toast.makeText(cont, message, duration).show();
        }
    }

}
