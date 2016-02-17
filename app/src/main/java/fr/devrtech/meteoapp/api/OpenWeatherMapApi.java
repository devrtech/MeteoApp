package fr.devrtech.meteoapp.api;

import fr.devrtech.meteoapp.api.model.MultipleWeatherResponse;
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

    // Base URL of icons
    static final String OPENWEATHERMAP_ICON_BASEURL = "http://openweathermap.org/img/w/";

    @GET("/data/2.5/weather")
    Call<WeatherResponse> currentWeather(
            @Query("APPID") String apiKey,
            @Query("lang") String lang,
            @Query("lat") double latitude,
            @Query("lon") double longitude);

    @GET("/data/2.5/group")
    Call<MultipleWeatherResponse> currentMultipleWeather(
            @Query("APPID") String apiKey,
            @Query("lang") String lang,
            @Query("id") String ids);

}
