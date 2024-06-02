package com.example.mhhp;

public class HealthData {
    private float weight;
    private float height;
    private String bloodPressure;
    private int pulse;

    public HealthData(float weight, float height, String bloodPressure, int pulse) {
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
    }

    // Геттеры и сеттеры
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
}
