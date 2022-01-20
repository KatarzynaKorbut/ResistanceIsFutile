package com.example.resistance_is_futile;

import android.os.Bundle;

public class LearningMode extends GameBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLevel(gameLevel.getMaxLevel());
    }
}
