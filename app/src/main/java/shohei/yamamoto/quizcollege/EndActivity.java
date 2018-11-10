package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_end );
        Bundle bundle = getIntent ().getExtras ();
        if (bundle != null) {
            int point = bundle.getInt ( "point" );
            TextView scoreText = (TextView) findViewById ( R.id.point );
            scoreText.setText ( String.format ( "%d" , point ) );
        }
        Button button = (Button) findViewById ( R.id.retry );
        button.setOnClickListener ( this );
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // ad's lifecycle: loading, opening, closing, and so on
        adView.setAdListener(new AdListener () {
            @Override
            public void onAdLoaded() {
                Log.d ( "debug", "Code to be executed when an ad finishes loading." );
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("debug","Code to be executed when an ad request fails.");
            }

            @Override
            public void onAdOpened() {
                Log.d("debug","Code to be executed when an ad opens an overlay that covers the screen.");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("debug","Code to be executed when the user has left the app.");
            }

            @Override
            public void onAdClosed() {
                Log.d("debug","Code to be executed when when the user is about to return to the app after tapping on an ad.");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent ( this , QuizActivity.class );
        startActivity ( intent );
    }
    @Override
    public boolean onKeyDown(int keyCode , KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown ( keyCode , event );
        } else {
            //Intentすると同時にIntentをリセットする。
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return false;
    }
}