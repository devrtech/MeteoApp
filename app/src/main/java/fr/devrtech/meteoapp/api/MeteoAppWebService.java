package fr.devrtech.meteoapp.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.devrtech.meteoapp.api.model.WeatherResponse;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Object calling APIs
 * <p/>
 * Created by remi on 09/02/16.
 */
public class MeteoAppWebService {

    // TODO set homni api key (this api key is actually for tests)
    // Api Key for Open Weather Map
    static final public String OPEN_WEATHER_MAP_API_KEY = "88ed3c4440e09b5e5e3da13b69d596bc";

    // Retrofit
    private Retrofit retrofit;


    // Service object
    private OpenWeatherMapApi apiService;

    /**
     * Constructor
     */
    public MeteoAppWebService() {
        super();
        // Gson builder for date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        // Retrofit setup
        retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapApi.OPENWEATHERMAP_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(OpenWeatherMapApi.class);
    }

    /* *** OPEN WEATHER MAP PART *** */

    /**
     * Call to OpenWeatherMap
     */
    public void openWeatherMapService(double lattitude, double longitude) {
        // API call
        final Call<WeatherResponse> call = apiService.currentWeather(OPEN_WEATHER_MAP_API_KEY, lattitude, longitude);

    }

}
