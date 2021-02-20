package com.aaron.additiontrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    public static final String SCORE = "com.aaron.AdditionTrainer.SCORE";
    private Intent intent;
    private TextView timerView, scoreBoard, questionList;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private Integer maxNumber, minNumber;
    private Integer score = 0;
    private Integer questionOne, questionTwo, answer;
    private Button submitButton;
    private EditText yourAnswer;
    private boolean gameStillRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        // ---------- Init Variables ----
        intent = getIntent();
        timeLeft = Long.parseLong(intent.getStringExtra(MainActivity.TIME_IN_MILLISECONDS));
        timerView = findViewById(R.id.timerText);
        maxNumber = Integer.parseInt(intent.getStringExtra(MainActivity.MAX_NUMBER));
        minNumber = Integer.parseInt(intent.getStringExtra(MainActivity.MIN_NUMBER));
        submitButton = findViewById(R.id.submitButton);
        yourAnswer = findViewById(R.id.yourAnswerEditText);
        scoreBoard = findViewById(R.id.scoreText);
        questionList = findViewById(R.id.questionText);

        // ---------- Set CountDownTimer ---
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                timerView.setText("Timer: "+timeLeft / 1000);
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();

        // -------- Start Actual Game --
        initRound();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStillRunning) {
                    if (Integer.parseInt(yourAnswer.getText().toString()) == answer) {
                        score++;
                    } else {
                        score--;
                    }
                    initRound();
                } else {
                    Log.d("PrivateLog","else");
                    Intent backToMain = new Intent(PlayActivity.this, MainActivity.class);
                    backToMain.putExtra(SCORE,""+score);
                    startActivity(backToMain);
                }
            }
        });
    }

    private void endGame() {
        gameStillRunning = false;
        yourAnswer.setEnabled(false);
        questionList.setText("Game has Ended");
        submitButton.setText("Back to Home");
    }

    private void initRound() {
        scoreBoard.setText("Score: "+score);
        yourAnswer.setText("");
        questionOne = new Random().nextInt((maxNumber - minNumber) + 1) + minNumber;
        questionTwo = new Random().nextInt((maxNumber - minNumber) + 1) + minNumber;
        answer = questionOne + questionTwo;
        questionList.setText(questionOne+"+"+questionTwo);
    }
}