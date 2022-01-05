package com.example.resistance_is_futile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NewGame extends AppCompatActivity {
    private ImageButton button_powrot;
    private ImageButton button_pasek;

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

        button_powrot = findViewById(R.id.powrot);
        button_powrot.setOnClickListener(v -> openMainActivity());
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
