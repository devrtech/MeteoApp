package fr.devrtech.meteoapp.api;

import fr.devrtech.meteoapp.api.model.WeatherResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * API interface for Open Weather Map
 * <p/>
 * Created by remi on 09/02/16.
 */
public interface OpenWeatherMapApi {

    // Open Weather Map endpoint
    static final String OPENWEATHERMAP_ENDPOINT = "http://api.openweathermap.org";

    @GET("/data/2.5/weather")
    Call<WeatherResponse> currentWeather(
            @Query("APPID") String apiKey,
            @Query("lat") double lattitude,
            @Query("lon") double longitude);

}
