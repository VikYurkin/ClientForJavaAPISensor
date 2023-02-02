package ru.VYurkin.ClientForJavaAPISensor.model;

import java.util.List;

public class Measurements {
    List<Measurement> measurements;

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }
}
