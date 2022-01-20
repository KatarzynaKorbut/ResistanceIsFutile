package com.example.resistance_is_futile;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Arrays;

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
        String givenResistanceText = getResistanceText(randomResistance, R.string.given_resistance);
        if (gameLevel.isBandEnabled(3))
            givenResistanceText += " " + getString(R.string.tolerance_value, doubleToString(randomTolerance));
        givenResistance.setText(givenResistanceText);
    }

    boolean equalResistanceAndTolerance(int[] a, int[] b) {
        return calculateResistance(a) == calculateResistance(b) &&
                calculateTolerance(a) == calculateTolerance(b);
    }

    public void onMeterSwitchChange(CompoundButton meterSwitch, boolean isChecked) {
        super.onMeterSwitchChange(meterSwitch, isChecked);
        if (isChecked) {
            if (equalResistanceAndTolerance(bandValues, randomBandColors)) {
                int newLevel = gameLevel.getLevel() + 1;
                Toast.makeText(this, getString(R.string.congratulations, newLevel), Toast.LENGTH_SHORT).show();
                setLevel(newLevel);
            } else {
                Toast.makeText(this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
            }
        }
    }
}