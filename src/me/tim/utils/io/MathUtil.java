package me.tim.utils.io;

import java.util.ArrayList;

public class MathUtil {
    public double calcAverage(ArrayList<Float> items)
    {
        double avg = 0;
        for (Float f : items)
        {
            avg += f;
        }
        return avg / items.size();
    }

    public boolean almostEqual(double d, double d1, double equalness){
        return Math.abs(d - d1) < equalness;
    }

    public boolean almostEqual_float(float f, float f1, float equalness){
        return Math.abs(f - f1) < equalness;
    }
}
