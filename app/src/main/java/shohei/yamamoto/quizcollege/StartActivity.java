package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupWindow mPopupWindow;

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
        //タイトル画面だけタイトルバーを消す
        getSupportActionBar().hide();
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent ( this , QuizActivity.class );
        startActivity ( intent );

    }

    //プライバシーポリシー表示
    public void onPolicy(View v){
            mPopupWindow = new PopupWindow(StartActivity.this);

            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
            popupView.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
            });
            mPopupWindow.setContentView(popupView);

            // 背景設定
            mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));

            // タップ時に他のViewでキャッチされないための設定
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);

            // 表示サイズの設定 今回は幅300dp
            float width = TypedValue.applyDimension ( TypedValue.COMPLEX_UNIT_DIP, 300, getResources ().getDisplayMetrics () );
            mPopupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setWidth((int) width);
            mPopupWindow.setHeight ( WindowManager.LayoutParams.WRAP_CONTENT );

            // 画面中央に表示
            mPopupWindow.showAtLocation ( findViewById(R.id.show), Gravity.CENTER, 0, 0 );
        }

    @Override
    protected void onDestroy() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        super.onDestroy();
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
