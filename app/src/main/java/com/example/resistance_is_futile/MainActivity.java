package com.example.resistance_is_futile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_how_to_play;
    private Button button_learning_mode;
    private Button button_continue;
    private Button button_new_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        button_how_to_play = findViewById(R.id.how_to_play);
        button_how_to_play.setOnClickListener(v -> openHowToPlay());
        button_learning_mode = findViewById(R.id.learning_mode);
        button_learning_mode.setOnClickListener(v -> openLearningMode());
        button_continue = findViewById(R.id.continue_game);
        button_continue.setOnClickListener(v -> openContinue());
        button_new_game = findViewById(R.id.start_new_game);
        button_new_game.setOnClickListener(v -> openNewGame());
    }

    public void openHowToPlay() {
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }

    public void openLearningMode() {
        Intent intent = new Intent(this, LearningMode.class);
        startActivity(intent);
    }

    public void openContinue() {
        Intent intent = new Intent(this, Continue.class);
        startActivity(intent);
    }

    public void openNewGame() {
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }
}