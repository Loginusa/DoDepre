package id.loginusa.dosis;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class SplashActivity extends AppCompatActivity {
    LinearLayout splashLayout;
    private final int SPLASH_DISPLAY_LENGTH = 2100;
    //Timer dan Animasi
    Handler timer;
    Runnable runnable;
    ViewSwitcher viewSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //animasi
        viewSwitcher = (ViewSwitcher) findViewById(R.id.switcher);
        viewSwitcher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewSwitcher switcher = (ViewSwitcher) v;

                if (switcher.getDisplayedChild() == 0) {
                    switcher.showNext();
                } else {
                    switcher.showPrevious();
                    switcher.showNext();
                }
            }
        });
        viewSwitcher.performClick();


        timer = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                goToGCMRegActivity();
            }
        };

        splashLayout = (LinearLayout) findViewById(R.id.splashLayout);
        splashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.removeCallbacks(runnable);
                goToGCMRegActivity();
            }
        });

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        timer.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    private void goToGCMRegActivity() {
        Intent mainIntent = new Intent(SplashActivity.this, GCMRegActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        timer.removeCallbacks(runnable);
        finish();
    }
}
