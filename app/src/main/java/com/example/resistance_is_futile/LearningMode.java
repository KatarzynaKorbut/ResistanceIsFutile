package com.example.resistance_is_futile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;

public class LearningMode extends GameBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLevel(gameLevel.getMaxLevel());
        SwitchCompat ohmmeterSwitch = findViewById(R.id.ohmmeter_switch);
        ohmmeterSwitch.setChecked(true);
        findViewById(R.id.given_resistance).setVisibility(View.INVISIBLE);
    }
}
