package fr.devrtech.meteoapp.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;

import fr.devrtech.meteoapp.api.model.MultipleWeatherResponse;
import fr.devrtech.meteoapp.api.model.WeatherResponse;
import fr.devrtech.meteoapp.model.Place;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Object calling APIs
 * <p/>
 * Created by remi on 09/02/16.
 */
public class MeteoAppWebService {

    // Class name for tag
    static final public String TAG = MeteoAppWebService.class.getSimpleName();

    // Api Key for Open Weather Map
    static final public String OPEN_WEATHER_MAP_API_KEY = "88ed3c4440e09b5e5e3da13b69d596bc";

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapApi.OPENWEATHERMAP_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(OpenWeatherMapApi.class);
    }

    /* *** OPEN WEATHER MAP PART *** */

    /**
     * Call to OpenWeatherMap for current weather
     */
    public void currentWeather(double latitude, double longitude, Callback<WeatherResponse> callback) {
        // API call
        final Call<WeatherResponse> call = apiService.currentWeather(OPEN_WEATHER_MAP_API_KEY, Locale.getDefault().getLanguage(),
                latitude, longitude);
        // Asynchronous call
        call.enqueue(callback);
    }

    /**
     * Call to OpenWeatherMap for current weather
     */
    public void currentWeather(List<Place> places, Callback<MultipleWeatherResponse> callback) {
        Log.d(TAG, "ids list: " + Place.getIdsFromPlaces(places));
        // API call
        final Call<MultipleWeatherResponse> call = apiService.currentMultipleWeather(OPEN_WEATHER_MAP_API_KEY,
                Locale.getDefault().getLanguage(), Place.getIdsFromPlaces(places));
        // Asynchronous call
        call.enqueue(callback);
    }

    /* *** STATIC *** */

    /**
     * Get Icon URL
     *
     * @param iconId Id of icon given by Open Weather Map
     */
    static public String getIconURL(String iconId) {
        // Create icon URL
        return OpenWeatherMapApi.OPENWEATHERMAP_ICON_BASEURL + iconId + ".png";
    }

}
