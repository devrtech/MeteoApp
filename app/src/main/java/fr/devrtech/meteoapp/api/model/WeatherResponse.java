package fr.devrtech.meteoapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by remi on 09/02/16.
 */
public class WeatherResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int code;

    @SerializedName("base")
    private String base;

    @SerializedName("message")
    private String message;

    @SerializedName("weather")
    private List<Weather> weathers;

    @SerializedName("coord")
    // Location coordinates
    private Coordinates coordinates;

    @SerializedName("main")
    private WeatherMain main;

    /* *** GETTERS AND SETTERS *** */

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

}
