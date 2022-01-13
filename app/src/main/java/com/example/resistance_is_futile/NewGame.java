package com.example.resistance_is_futile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NewGame extends AppCompatActivity {
    public enum Bands {
        ZERO(R.color.res_black),
        ONE(R.color.res_brown),
        TWO(R.color.res_red),
        THREE(R.color.res_orange),
        FOUR(R.color.res_yellow),
        FIVE(R.color.res_green),
        SIX(R.color.res_blue),
        SEVEN(R.color.res_purple),
        EIGHT(R.color.res_grey),
        NINE(R.color.res_white),
        TEN(R.color.res_silver),
        ELEVEN(R.color.res_gold);

        private int color;

        Bands(int color) {
            this.color = color;
        }

        public int getColor(){
            return this.color;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        findViewById(R.id.powrot).setOnClickListener(v -> openMainActivity());

        findViewById(R.id.button1).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button2).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button3).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button4).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button5).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button6).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button7).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button8).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button9).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button10).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button11).setOnClickListener(v -> onColorButtonClicked(v));
        findViewById(R.id.button12).setOnClickListener(v -> onColorButtonClicked(v));
    }

    private int selectedColorButton = -1;

    public void onColorButtonClicked(View v) {
        int buttonId = v.getId();
        int newSelectedButton = buttonId;
        if (selectedColorButton != -1) {
            Button previousColorButton = findViewById(selectedColorButton);
            previousColorButton.setScaleX(1.0f);
            previousColorButton.setScaleY(1.0f);
            newSelectedButton = -1;
        }
        if (buttonId != selectedColorButton) {
            v.setScaleX(0.8f);
            v.setScaleY(0.8f);
            newSelectedButton = buttonId;
        }
        selectedColorButton = newSelectedButton;
    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
