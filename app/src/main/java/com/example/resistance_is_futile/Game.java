package com.example.resistance_is_futile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

public class Game extends GameBase {
    public static final String CONTINUE_GAME = "continue";
    public static final String LEVEL = "level";

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(LEVEL, gameLevel.getLevel());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int level = gameLevel.getLevel();
        if (getIntent().getBooleanExtra(CONTINUE_GAME, true) && savedInstanceState != null) {
            level = savedInstanceState.getInt(LEVEL, gameLevel.getLevel());
        }
        setLevel(level);
    }

    public void onBandButtonClicked(View v) {
        super.onBandButtonClicked(v);
        SwitchCompat ohmmeterSwitch = findViewById(R.id.ohmmeter_switch);
        ohmmeterSwitch.setChecked(false);
    }

    int[] randomBandColors;

    void setLevel(int level) {
        super.setLevel(level);
        randomBandColors = gameLevel.generateRandomBandsColors();
        double randomResistance = calculateResistance(randomBandColors);
        double randomTolerance = calculateTolerance(randomBandColors);
        TextView givenResistance = findViewById(R.id.given_resistance);
        givenResistance.setText(getResistanceText(randomResistance, R.string.given_resistance));
    }
}