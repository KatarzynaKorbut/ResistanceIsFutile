package com.example.resistance_is_futile;

import android.os.Bundle;

public class Continue extends GameBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int savedGameLevel = 8; // TODO: odczytać zapisany poziom
        setLevel(savedGameLevel);
    }
}
