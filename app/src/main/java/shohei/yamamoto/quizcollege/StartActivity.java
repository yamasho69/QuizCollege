package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature ( Window.FEATURE_NO_TITLE );
        setContentView ( R.layout.activity_start );
        Button button = (Button) findViewById ( R.id.start );
        button.setOnClickListener (this);
        AdView adView = (AdView)this.findViewById ( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder().build ();
        adView.loadAd(adRequest);
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
            //Intent intent = new Intent ( this , QuizActivity.class );
            //startActivity ( intent );
            finish ();
        }
        return false;
    }
}
