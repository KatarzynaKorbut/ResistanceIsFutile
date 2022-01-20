package com.example.resistance_is_futile;

import java.util.Random;

public class GameLevel {
    final private int[] TOLERANCE_COLORS = {1, 2, 5, 6, 7, 8, 10, 11};
    private int enabledColorsForResistance;
    private int enabledColorsForMultiplier;
    private int enabledColorsForTolerance;
    private int level;

    GameLevel() {
        reset();
    }

    public void reset() {
        level = 0;
        enabledColorsForResistance = 2;
        enabledColorsForMultiplier = 1;
        enabledColorsForTolerance = 1;
    }

    public void increaseDifficulty() {
        this.level = this.getLevel() + 1;
        if (enabledColorsForResistance < 10)
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

    public int[] generateRandomBandsColors() {
        Random random = new Random();
        int[] bandColors = new int[4];
        bandColors[0] = random.nextInt(enabledColorsForResistance);
        bandColors[1] = random.nextInt(enabledColorsForResistance);
        bandColors[2] = random.nextInt(enabledColorsForMultiplier);
        bandColors[3] = TOLERANCE_COLORS[random.nextInt(enabledColorsForTolerance)];
        return bandColors;
    }

    int colorsNeeded() {
        return Math.max(enabledColorsForResistance, enabledColorsForMultiplier);
    }

    public boolean isBandEnabled(int bandNumber) {
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
                    return colorNumber < enabledColorsForResistance || enabledColorsForResistance == 10;
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
