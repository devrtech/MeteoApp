package fr.devrtech.meteoapp.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remi on 09/02/16.
 */
public class WeatherMain {

    @SerializedName("temp")
    private double temperature;

    @SerializedName("pressure")
    private float pressure;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("temp_min")
    private double minimumTemperature;

    @SerializedName("temp_max")
    private double maximalTemperature;

    /* *** GETTERS AND SETTERS *** */

    public double getTemperature() {
        return temperature;
    }

    public double getCelciusTemperature() {
        // convert Kelvin température to Celcius
        return temperature - 273.15;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMaximalTemperature() {
        return maximalTemperature;
    }

    public void setMaximalTemperature(double maximalTemperature) {
        this.maximalTemperature = maximalTemperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(double minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

}

