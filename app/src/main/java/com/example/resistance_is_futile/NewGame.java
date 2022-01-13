package com.example.resistance_is_futile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class NewGame extends AppCompatActivity {
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

        findViewById(R.id.button1).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button2).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button3).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button4).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button5).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button6).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button7).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button8).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button9).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button10).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button11).setOnClickListener(this::onColorButtonClicked);
        findViewById(R.id.button12).setOnClickListener(this::onColorButtonClicked);

        findViewById(R.id.band1_button).setOnClickListener(this::onBandButtonClicked);
        findViewById(R.id.band2_button).setOnClickListener(this::onBandButtonClicked);
        findViewById(R.id.band3_button).setOnClickListener(this::onBandButtonClicked);
        findViewById(R.id.band4_button).setOnClickListener(this::onBandButtonClicked);
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

    private static final int BAND_NOT_ALLOWED = -1;
    private static final int[][] bandResources = {
            {R.drawable.resistor_black_1, R.drawable.resistor_black_2, R.drawable.resistor_black_3, BAND_NOT_ALLOWED},
            {R.drawable.resistor_brown_1, R.drawable.resistor_brown_2, R.drawable.resistor_brown_3, R.drawable.resistor_brown_4},
            {R.drawable.resistor_red_1, R.drawable.resistor_red_2, R.drawable.resistor_red_3, R.drawable.resistor_red_4},
            {R.drawable.resistor_orange_1, R.drawable.resistor_orange_2, R.drawable.resistor_orange_3, BAND_NOT_ALLOWED},
            {R.drawable.resistor_yellow_1, R.drawable.resistor_yellow_2, R.drawable.resistor_yellow_3, BAND_NOT_ALLOWED},
            {R.drawable.resistor_green_1, R.drawable.resistor_green_2, R.drawable.resistor_green_3, R.drawable.resistor_green_4},
            {R.drawable.resistor_blue_1, R.drawable.resistor_blue_2, R.drawable.resistor_blue_3, R.drawable.resistor_blue_4},
            {R.drawable.resistor_purple_1, R.drawable.resistor_purple_2, R.drawable.resistor_purple_3, R.drawable.resistor_purple_4},
            {R.drawable.resistor_grey_1, R.drawable.resistor_grey_2, R.drawable.resistor_grey_3, R.drawable.resistor_grey_4},
            {R.drawable.resistor_white_1, R.drawable.resistor_white_2, R.drawable.resistor_white_3, BAND_NOT_ALLOWED},
            {BAND_NOT_ALLOWED, BAND_NOT_ALLOWED, R.drawable.resistor_gold_3, R.drawable.resistor_gold_4},
            {BAND_NOT_ALLOWED, BAND_NOT_ALLOWED, R.drawable.resistor_silver_3, R.drawable.resistor_silver_4}
    };
    private static final List<Integer> BAND_BUTTONS = Arrays.asList(R.id.band1_button, R.id.band2_button, R.id.band3_button, R.id.band4_button);
    private static final int[] BANDS = {R.id.band_1, R.id.band_2, R.id.band_3, R.id.band_4};
    private static final List<Integer> COLOR_BUTTONS = Arrays.asList(
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12);
    private static final int[] COLOR_NAMES = {
            R.string.color_black, R.string.color_brown, R.string.color_red, R.string.color_orange,
            R.string.color_yellow, R.string.color_green, R.string.color_blue, R.string.color_purple,
            R.string.color_grey, R.string.color_white, R.string.color_gold, R.string.color_silver};

    public void onBandButtonClicked(View v) {
        int bandNumber = BAND_BUTTONS.indexOf(v.getId());
        int bandId = BANDS[bandNumber];
        int colorNumber = COLOR_BUTTONS.indexOf(selectedColorButton);

        setBandColor(bandNumber, bandId, colorNumber);
    }

    private void setBandColor(int bandNumber, int bandId, int colorNumber) {
        ImageView band = findViewById(bandId);
        if (colorNumber != -1) {
            int bandResource = bandResources[colorNumber][bandNumber];
            if (bandResource == BAND_NOT_ALLOWED) {
                String color = getString(COLOR_NAMES[colorNumber]);
                Toast.makeText(this, "Band " + (bandNumber + 1) + " can't be " + color, Toast.LENGTH_SHORT).show();
            }
            else {
                band.setImageResource(bandResource);
                band.setVisibility(View.VISIBLE);
            }
        }
        else {
            band.setVisibility(View.INVISIBLE);
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}