package shohei.yamamoto.quizcollege;

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

import au.com.bytecode.opencsv.CSVReader;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // ビューの初期化
        questionText  = (TextView) findViewById(R.id.question);
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
        setNextText();
    }
    @Override
    public void onClick(View v) {
        if (((Button)v).getText().equals(answerStr)) {
            point += 10;
        }
        Log.d("Quiz","point:" + point);
        setNextText();
    }
        //次の問題をセットするメソッド
        private void setNextText() {
            answerStr = questions[count][1];
            questionText.setText(questions[count][0]);
            answerButton1.setText(questions[count][1]);
            answerButton2.setText(questions[count][2]);
            answerButton3.setText(questions[count][3]);
            answerButton4.setText(questions[count][4]);
            if (count >= NumberOfQuestions) {
                count = 0;
            }
            count++;
        }
    }
