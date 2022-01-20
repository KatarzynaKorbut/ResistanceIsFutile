package com.example.resistance_is_futile;

public class GameLevel {
    final private int[] TOLERANCE_COLORS = {1, 2, 5, 6, 7, 8, 10, 11};
    private int enabledColorsForResistance;
    private int enabledColorsForMultiplier;
    private int enabledColorsForTolerance;
    private int level = 0;

    GameLevel() {
        reset();
    }

    public void reset() {
        enabledColorsForResistance = 2;
        enabledColorsForMultiplier = 1;
        enabledColorsForTolerance = 1;
    }

    public void increaseDifficulty() {
        this.level = this.getLevel() + 1;
        if (enabledColorsForResistance < 12)
            ++enabledColorsForResistance;
        if (enabledColorsForResistance >= 5)
            if (enabledColorsForMultiplier < 12)
                ++enabledColorsForMultiplier;
            else if (enabledColorsForTolerance < TOLERANCE_COLORS.length)
                ++enabledColorsForTolerance;
    }

    public int getMaxLevel() {
        return 21;
    }

    public void setDifficulty(int level) {
        reset();
        while (level-- > 0)
            increaseDifficulty();
    }

    int colorsNeeded() {
        return enabledColorsForResistance;
    }

    private boolean isBandEnabled(int bandNumber) {
        switch (bandNumber) {
            case 0:
            case 1:
                return enabledColorsForResistance > 1;
            case 2:
                return enabledColorsForMultiplier > 1;
            case 3:
                return enabledColorsForTolerance > 1;
        }
        return false;
    }

    public boolean isColorAvailableForBand(int colorNumber, int bandNumber) {
        if (isBandEnabled(bandNumber))
            switch (bandNumber) {
                case 0:
                case 1:
                    return colorNumber < enabledColorsForResistance;
                case 2:
                    return colorNumber < enabledColorsForMultiplier;
                case 3:
                    return colorNumber <= TOLERANCE_COLORS[enabledColorsForTolerance - 1];
            }
        return false;
    }

    public int getInitialBandColor(int bandNumber) {
        if (!isBandEnabled(bandNumber))
            switch (bandNumber) {
                case 0:
                case 1:
                case 2:
                    return 0;
                case 3:
                    return TOLERANCE_COLORS[0];
            }
        return -1;
    }

    public int getLevel() {
        return level;
    }
}
