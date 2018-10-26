package shohei.yamamoto.quizcollege;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

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
    }
}
