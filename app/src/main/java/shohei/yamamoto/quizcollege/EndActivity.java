package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            Intent intent = new Intent ( this , QuizActivity.class );
            startActivity ( intent );
        }
        return false;
    }
}