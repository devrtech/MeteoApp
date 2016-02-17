package fr.devrtech.meteoapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response for Weather request for multiple places
 * <p/>
 * Created by remi on 16/02/16.
 */
public class MultipleWeatherResponse {

    @SerializedName("cnt")
    protected int count;

    @SerializedName("list")
    protected List<WeatherResponse> weathers;

    /* *** GETTERS AND SETTERS *** */

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WeatherResponse> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherResponse> weathers) {
        this.weathers = weathers;
    }

}
