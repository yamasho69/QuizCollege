package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import au.com.bytecode.opencsv.CSVReader;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private int NumberOfQuestions = 7;
    private TextView timerText;
    private TextView missText;
    private TextView pointText;
    private TextView questionText;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private int point;
    private String questions[][] = new String[NumberOfQuestions][5];
    private int count = 0;
    private String answerStr;
    int[] order = null;
    private Timer timer = null;
    android.os.Handler handler = new android.os.Handler ();
    private static final double LimitOfTime = 10.0d;
    double time;
    int missCount = 0;
    private static final int LimitOfMiss = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_quiz );
        // ビューの初期化
        timerText = (TextView) findViewById ( R.id.timer );
        missText = (TextView) findViewById ( R.id.miss );
        questionText = (TextView) findViewById ( R.id.question );
        pointText = (TextView) findViewById ( R.id.point );
        answerButton1 = (Button) findViewById ( R.id.one );
        answerButton2 = (Button) findViewById ( R.id.two );
        answerButton3 = (Button) findViewById ( R.id.three );
        answerButton4 = (Button) findViewById ( R.id.four );
        answerButton1.setOnClickListener ( this );
        answerButton2.setOnClickListener ( this );
        answerButton3.setOnClickListener ( this );
        answerButton4.setOnClickListener ( this );
        try {
            AssetManager as = getResources ().getAssets ();
            InputStream is = as.open ( "test.csv" );
            CSVReader reader = new CSVReader ( new InputStreamReader ( is ) , ',' );
            for (int i = 0; i < NumberOfQuestions; i++) {
                questions[i] = reader.readNext ();
            }
        }

        //タイマー生成start
        catch (IOException e) {
            e.printStackTrace ();
        }
        pointText.setText ( "ポイント:" + point );
        setNextText ();
        time = LimitOfTime;
        timer = new Timer ( true );
        timer.schedule ( new TimerTask () {
            @Override
            public void run() {
// UIスレッドへ処理をキューイング
                handler.post ( new Runnable () {
                    @Override
                    public void run() {
                        time -= 0.1d;
// 小数点第1位で丸める
                        BigDecimal bi = new BigDecimal ( time );
                        float outputValue = bi.setScale ( 1 , BigDecimal.ROUND_HALF_UP ).floatValue ();
                        if (outputValue < 0.0f) {
                            missCount++;
                            setNextText ();
                        }
//現在の残り時間を表示
                        timerText.setText ( "残り時間:" + Float.toString ( outputValue ) + "秒" );
                    }
                } );
            }
        } , 100 , 100 );
    }
    //タイマー生成end

    //ボタンが押されたときの動作start
    @Override
    public void onClick(View v) {
        if (((Button) v).getText ().equals ( answerStr )) {
            point += 10;
            pointText.setText ( "ポイント:" + point );
        } else {
            missCount++;
        }
        setNextText ();
    }
    //ボタンが押されたときの動作end

    private int[] createRandomArray(int n , int offset) {
        int data[] = new int[n];
        Random random1 = new Random ();
        Random random2 = new Random ();
        int buf, i, rnd1, rnd2;
        for (i = 0; i < n; i++) {
            data[i] = i + offset;
        }
        for (i = 0; i < n * 10; i++) {
            rnd1 = random1.nextInt ( n );
            rnd2 = random2.nextInt ( n );
            buf = data[rnd1];
            data[rnd1] = data[rnd2];
            data[rnd2] = buf;
        }
        return data;
    }
    //次の問題をセットするメソッドstart
    private void setNextText() {
        if (count >= NumberOfQuestions) {
            Intent intent = new Intent ( this , EndActivity.class );
            intent.putExtra ( "point" , point );
            startActivity ( intent );
            count = 0;
        }
        answerStr = questions[order[count]][1];
        int rndNum[] = createRandomArray ( 4 , 1 );
        questionText.setText ( questions[order[count]][0] );
        answerButton1.setText ( questions[order[count]][rndNum[0]] );
        answerButton2.setText ( questions[order[count]][rndNum[1]] );
        answerButton3.setText ( questions[order[count]][rndNum[2]] );
        answerButton4.setText ( questions[order[count]][rndNum[3]] );
        count++;
        missText.setText ( "残りライフ:" + (LimitOfMiss - missCount) );
        time = LimitOfTime;
        if (missCount >= LimitOfMiss) {
            Intent intent = new Intent ( this , EndActivity.class );
            intent.putExtra ( "point" , point );
            timer.cancel ();
        }
    }
    //次の問題をセットするメソッドend
}

