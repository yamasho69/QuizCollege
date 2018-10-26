package shohei.yamamoto.quizcollege;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private int NumberOfQuestions = 3;
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
// 問題をセット
        questions[0][0] = "テストの問題1です";
        questions[0][1] = "解答1";
        questions[0][2] = "解答2";
        questions[0][3] = "解答3";
        questions[0][4] = "解答4";
        questions[1][0] = "テストの問題2です";
        questions[1][1] = "解答1";
        questions[1][2] = "解答2";
        questions[1][3] = "解答3";
        questions[1][4] = "解答4";
        questions[2][0] = "テストの問題3です";
        questions[2][1] = "解答1";
        questions[2][2] = "解答2";
        questions[2][3] = "解答3";
        questions[2][4] = "解答4";
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
