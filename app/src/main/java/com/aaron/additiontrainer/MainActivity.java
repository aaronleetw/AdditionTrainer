package com.aaron.additiontrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MAX_NUMBER = "com.aaron.AdditionTrainer.MAX_NUMBER";
    public static final String MIN_NUMBER = "com.aaron.AdditionTrainer.MIN_NUMBER";
    public static final String TIME_IN_MILLISECONDS = "com.aaron.AdditionTrainer.TIME_IN_MILLISECONDS";
    private EditText timerInSeconds;
    private Intent intent;
    private Intent previousIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, PlayActivity.class);
        previousIntent = getIntent();
        if (previousIntent != null) {
            Log.d("PrivateLog","IntentMessage: "+previousIntent.getStringExtra(PlayActivity.SCORE));
            TextView scoreBoard = findViewById(R.id.scoreBoard);
            scoreBoard.setText("Score:\n"+previousIntent.getStringExtra(PlayActivity.SCORE));
        }

        Button twoDigit = findViewById(R.id.twoDigit);
        Button threeDigit = findViewById(R.id.threeDigit);
        timerInSeconds = findViewById(R.id.timerEditText);

        twoDigit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                intent.putExtra(MAX_NUMBER,"99");
                intent.putExtra(MIN_NUMBER,"10");
                startGame();
            }
        });

        threeDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(MAX_NUMBER,"999");
                intent.putExtra(MIN_NUMBER,"100");
                startGame();
            }
        });
    }

    private void startGame() {
        intent.putExtra(TIME_IN_MILLISECONDS,""+Integer.parseInt(timerInSeconds.getText().toString())*1000);
        startActivity(intent);
    }
}