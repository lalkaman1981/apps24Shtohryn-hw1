package ua.edu.ucu.apps.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;


public class TemperatureSeriesAnalysis {

    private double[] series;
    private int len = 0;
    private int capacity;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double i: temperatureSeries) {
            if (i <= -273){
                throw new InputMismatchException("There is no such temperature in nature");
            }
        }
        this.series = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        int len = series.length;
        this.len = len;
        this.capacity = len;
    }

    public int getLength() {
        return len;
    }

    public double[] getSeries() {
        return series;
    }

    public double average() {

        if (len == 0) {
            throw new IllegalArgumentException("series size is 0");
        }

        double sum = 0;

        for (double i: this.series) {
            sum += i;
        }

        return sum/len;

    }

    public double deviation() {
        double ave = average();

        double varianceSum = 0.0;
        for (double value : series) {
            varianceSum += Math.pow(value - ave, 2);
        }
        double variance = varianceSum / series.length;

        return Math.sqrt(variance);
    }

    public double min() {
        if (len == 0) {
            throw new IllegalArgumentException("series size is 0");
        }

        double min = series[0];

        for (int i = 1; i < len; ++i) {
            if (min > series[i]) {
                min = series[i];
            }
        }

        return min;
    }

    public double max() {
        if (len == 0) {
            throw new IllegalArgumentException("series size is 0");
        }

        double max = series[0];

        for (int i = 1; i < len; ++i) {
            if (max <= series[i]) {
                max = series[i];
            }
        }

        return max;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (len == 0) {
            throw new IllegalArgumentException("series size is 0");
        }

        double closest = Math.abs(tempValue - series[0]);
        double temp;

        for (int i = 1; i < len; ++i) {
            temp = Math.abs(tempValue - series[i]);

            if (closest > temp) {
                closest = series[i];
            } else if (closest == temp && closest < series[i]) {
                closest = series[i];
            }
        }

        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] needed_temps = new double[len];
        int counter = 0;

        for (double i: series) {
            if (tempValue > i) {
                needed_temps[counter] = i;
                ++counter;
            }
        }

        double[] needed = Arrays.copyOf(needed_temps, counter);

        return needed;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] needed_temps = new double[len];
        int counter = 0;

        for (double i: series) {
            if (tempValue <= i) {
                needed_temps[counter] = i;
                ++counter;
            }
        }

        double[] needed = Arrays.copyOf(needed_temps, counter);

        return needed;
    }

    public double[] findTempsInRange(double lowerBound, double upperBound) {
        double[] needed_temps = new double[len];
        int counter = 0;

        for (double i: series) {
            if (lowerBound < i && upperBound >= i) {
                needed_temps[counter] = i;
                ++counter;
            }
        }

        double[] needed = Arrays.copyOf(needed_temps, counter);

        return needed;
    }


    public void reset() {
        series =  new double[0];
        len = 0;
        capacity = 0;
    }

    public double[] sortTemps() {
        double[] new_series = Arrays.copyOf(series, len);
        Arrays.sort(new_series);
        return new_series;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        int len1 = temps.length;
        int abs_len = len1 + len;

        if (abs_len >= capacity) {
            double[] temp = new double[2 * abs_len];
            System.arraycopy(series, 0, temp, 0, len);
            series = temp;
            capacity = 2 * abs_len;
        }

        for (int i = 0; i < len1; ++i) {
            series[i + len] = temps[i];
        }

        len = abs_len;

        return len;
    }
}
