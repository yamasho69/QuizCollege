package shohei.yamamoto.quizcollege;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int point = bundle.getInt("point");
            TextView scoreText = (TextView) findViewById(R.id.point);
//            scoreText.setText(String.format("%d", point));
        }
        // ビューの初期化
        questionText = (TextView) findViewById(R.id.question);
        answerButton1 = (Button) findViewById(R.id.one);
        answerButton2 = (Button) findViewById(R.id.two);
        answerButton3 = (Button) findViewById(R.id.three);
        answerButton4 = (Button) findViewById(R.id.four);
        answerButton1.setOnClickListener(this);
        answerButton2.setOnClickListener(this);
        answerButton3.setOnClickListener(this);
        answerButton4.setOnClickListener(this);
        try {
            AssetManager as = getResources().getAssets();
            InputStream is = as.open("test.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is), ',');
            for (int i = 0; i < NumberOfQuestions; i++) {
                questions[i] = reader.readNext();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //pointText.setText("ポイント:" + point);
        // 問題をシャッフル
        order = createRandomArray(NumberOfQuestions, 0);
        setNextText();
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText().equals(answerStr)) {
            point += 10;
        }
        Log.d("Quiz", "point:" + point);
        setNextText();
    }

    private int[] createRandomArray(int n, int offset) {
        int data[] = new int[n];
        Random random1 = new Random();
        Random random2 = new Random();
        int buf, i, rnd1, rnd2;
        for (i = 0; i < n; i++) {
            data[i] = i + offset;
        }
        for (i = 0; i < n * 10; i++) {
            rnd1 = random1.nextInt(n);
            rnd2 = random2.nextInt(n);
            buf = data[rnd1];
            data[rnd1] = data[rnd2];
            data[rnd2] = buf;
        }
        return data;
    }

    //次の問題をセットするメソッド
    private void setNextText() {
        if (count >= NumberOfQuestions) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("point", point);
            startActivity(intent);
//本当は終了処理を入れる
            count = 0;
        }
        answerStr = questions[count][1];
        int rndNum[] = createRandomArray(4, 1);
        questionText.setText(questions[order[count]][0]);
        answerButton1.setText(questions[order[count]][rndNum[0]]);
        answerButton2.setText(questions[order[count]][rndNum[1]]);
        answerButton3.setText(questions[order[count]][rndNum[2]]);
        answerButton4.setText(questions[order[count]][rndNum[3]]);
        count++;
    }
}

