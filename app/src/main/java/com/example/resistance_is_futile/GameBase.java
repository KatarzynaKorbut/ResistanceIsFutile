package com.example.resistance_is_futile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Arrays;
import java.util.List;

public class GameBase extends AppCompatActivity {
    protected GameLevel gameLevel;
    private static final int NO_COLOR = -1;
    protected int[] bandValues = new int[]{NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR};
    private int selectedColorButton = -1;

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

        gameLevel = new GameLevel();

        for (int colorButtonId : COLOR_BUTTONS)
            findViewById(colorButtonId).setOnClickListener(this::onColorButtonClicked);

        for (int bandButtonId : BAND_BUTTONS)
            findViewById(bandButtonId).setOnClickListener(this::onBandButtonClicked);

        TextView meter_tolerance = findViewById(R.id.meter_tolerance);
        meter_tolerance.setText(getString(R.string.tolerance_value, getText(R.string.tolerance_value_unknown)));

        findViewById(R.id.meter_resistance).setVisibility(View.INVISIBLE);
        findViewById(R.id.meter_tolerance).setVisibility(View.INVISIBLE);
        SwitchCompat ohmmeterSwitch = findViewById(R.id.ohmmeter_switch);
        ohmmeterSwitch.setOnCheckedChangeListener(this::onMeterSwitchChange);
    }

    void setLevel(int level) {
        gameLevel.setDifficulty(level);
        int maxColorNumber = gameLevel.colorsNeeded() - 1;
        for (int colorNumber = 0; colorNumber < COLOR_BUTTONS.size(); ++colorNumber) {
            int colorButtonId = COLOR_BUTTONS.get(colorNumber);
            Button colorButton = findViewById(colorButtonId);
            colorButton.setVisibility(colorNumber <= maxColorNumber ? View.VISIBLE : View.INVISIBLE);
        }
        for (int bandNumber = 0; bandNumber < BANDS.length; ++bandNumber)
            setBandColor(bandNumber, BANDS[bandNumber], gameLevel.getInitialBandColor(bandNumber));

        setMeter(calculateResistance(bandValues), calculateTolerance(bandValues));
    }

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

        if (gameLevel.isColorAvailableForBand(colorNumber, bandNumber)) {
            setBandColor(bandNumber, bandId, colorNumber);
            setMeter(calculateResistance(bandValues), calculateTolerance(bandValues));
        }
        else if (colorNumber != bandValues[bandNumber]) {
            Toast.makeText(this,
                    getString(R.string.unavailable_band_color, bandNumber + 1, getColorString(colorNumber)),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onMeterSwitchChange(CompoundButton meterSwitch, boolean isChecked) {
        int visibility = meterSwitch.isChecked() ? View.VISIBLE : View.INVISIBLE;
        findViewById(R.id.meter_resistance).setVisibility(visibility);
        findViewById(R.id.meter_tolerance).setVisibility(visibility);
    }

    private String getColorString(int colorNumber) {
        if (colorNumber == NO_COLOR)
            return getString(R.string.color_unset);
        else
            return getString(COLOR_NAMES[colorNumber]);
    }

    static String doubleToString(double value) {
        if (value == (long)value)
            return Long.toString((long)value);
        else
            return Double.toString(value);
    }

    private void setMeter(double resistance, double tolerance) {
        TextView meter_resistance = findViewById(R.id.meter_resistance);
        if (resistance < 0)
            meter_resistance.setText(R.string.resistance_value_missing);
        else
            meter_resistance.setText(getResistanceText(resistance, R.string.resistance_value));

        TextView meter_tolerance = findViewById(R.id.meter_tolerance);
        String tolerance_text;
        if (tolerance == 0)
            tolerance_text = getString(R.string.tolerance_value_unknown);
        else
            tolerance_text = doubleToString(tolerance);
        meter_tolerance.setText(getString(R.string.tolerance_value, tolerance_text));
    }

    protected String getResistanceText(double resistance, int textId) {
        String prefix;
        double base = resistance;
        if (resistance >= 1e9) {
            prefix = "G";
            base /= 1e9;
        }
        else if (resistance >= 1e6) {
            prefix = "M";
            base /= 1e6;
        }
        else if (resistance >= 1e3) {
            prefix = "k";
            base /= 1e3;
        }
        else if (resistance >= 1e0 || resistance == 0) {
            prefix = "";
        }
        else {
            prefix = "m";
            base /= 1e-3;
        }
        return getString(textId, doubleToString(base), prefix);
    }

    private void setBandColor(int bandNumber, int bandId, int colorNumber) {
        ImageView band = findViewById(bandId);
        if (colorNumber != -1) {
            int bandResource = bandResources[colorNumber][bandNumber];
            if (bandResource == BAND_NOT_ALLOWED) {
                Toast.makeText(this,
                        getString(R.string.invalid_band_color, bandNumber + 1, getColorString(colorNumber)),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                band.setImageResource(bandResource);
                band.setVisibility(View.VISIBLE);
                bandValues[bandNumber] = colorNumber;
            }
        }
        else {
            band.setVisibility(View.INVISIBLE);
            bandValues[bandNumber] = NO_COLOR;
        }
    }

    boolean resistanceAvailable(int[] bandValues) {
        return bandValues[0] != NO_COLOR && bandValues[1] != NO_COLOR && bandValues[2] != NO_COLOR;
    }

    double calculateResistance(int[] bandValues) {
        if (resistanceAvailable(bandValues)) {
            int exponent = bandValues[2];
            if (exponent >= 10)
                exponent = 9 - exponent;
            return (10 * bandValues[0] + bandValues[1]) * Math.pow(10, exponent);
        }
        else
            return -1.0f;
    }

    boolean toleranceAvailable(int[] bandValues) {
        return bandValues[3] != NO_COLOR;
    }

    private static final double[] TOLERANCES = {0, 1, 2, 0, 0, 0.5, 0.25, 0.1, 0.05, 0, 5, 10};
    double calculateTolerance(int[] bandValues) {
        if (toleranceAvailable(bandValues))
            return TOLERANCES[bandValues[3]];
        else
            return 0;
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }}
