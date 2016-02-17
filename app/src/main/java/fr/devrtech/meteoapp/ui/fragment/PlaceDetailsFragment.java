package fr.devrtech.meteoapp.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.devrtech.meteoapp.MeteoApplication;
import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.api.MeteoAppWebService;
import fr.devrtech.meteoapp.api.model.Weather;
import fr.devrtech.meteoapp.api.model.WeatherResponse;
import fr.devrtech.meteoapp.model.Place;
import fr.devrtech.meteoapp.ui.activity.HomeActivity;
import fr.devrtech.meteoapp.ui.activity.PlaceDetailActivity;
import fr.devrtech.meteoapp.ui.activity.PlaceEditActivity;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a single Place detail screen.
 * This fragment is either contained in a {@link HomeActivity}
 * in two-pane mode (on tablets) or a {@link PlaceDetailActivity}
 * on handsets.
 */
public class PlaceDetailsFragment extends Fragment {

    // Class name for tag
    static final public String TAG = PlaceDetailsFragment.class.getSimpleName();

    /**
     * The fragment argument representing the place ID that this fragment represents.
     */
    static final public String ARG_PLACE_ID = "place_id";

    /**
     * The {@link Place} content this fragment is presenting.
     */
    private Place place;

    /* *** WIDGETS *** */

    @Bind(R.id.meteo_progressbar)
    protected ProgressBar progressBar;

    @Bind(R.id.meteo_image_weather)
    protected ImageView weatherImageView;

    @Bind(R.id.meteo_text_weather)
    protected TextView weatherText;

    @Bind(R.id.meteo_text_temp)
    protected TextView tempertureText;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceDetailsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For enabling specific menu
        setHasOptionsMenu(true);
        // Check argument
        if (getArguments().containsKey(ARG_PLACE_ID)) {
            // Extract id
            long id = getArguments().getLong(ARG_PLACE_ID, -1);
            // Open DB helper
            MeteoApplication.getDBhelper().open();
            // Request for {@link Place}
            place = MeteoApplication.getDBhelper().getPlace(id);
            Log.d(TAG, "id: " + id + " place: " + place);
            // Close DB helper
            MeteoApplication.getDBhelper().close();

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(place.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Root view
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        // Binding butterknife
        ButterKnife.bind(this, rootView);
        // Return
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check data
        if (place != null) {
            // Show progress bar
            progressBar.setVisibility(View.VISIBLE);
            // Send WS request
            MeteoApplication.getWeatherWebService().currentWeather(place.getLatitude(), place.getLongitude(),
                    new WeatherCallback(getActivity()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Specific menu for map
        inflater.inflate(R.menu.details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get id
        int id = item.getItemId();
        // Iterate on action
        switch (id) {
            case R.id.action_goto:
                // Go to place
                goPlace();
                return true;
            case R.id.action_edit:
                // Intent for editing Place
                Intent editPlaceIntent = new Intent(getActivity(), PlaceEditActivity.class);
                editPlaceIntent.putExtra(PlaceEditActivity.ARG_PLACE_ID, place.getId());
                startActivity(editPlaceIntent);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Open map to place
     */
    private void goPlace() {
        try {
            // Uri requesting for displaing map on GeoPoint
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + place.getLatitude() + "," + place.getLongitude()
                    + "(" + URLEncoder.encode(place.getName(), "utf-8") + ")");
            Log.d(TAG, "Google Maps uri : " + gmmIntentUri.toString());
            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Place getPlace() {
        return place;
    }

    /**
     * Callback for Weather
     */
    private class WeatherCallback implements Callback<WeatherResponse> {

        // App context
        protected Context context;

        /**
         * Constructor
         */
        protected WeatherCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Response<WeatherResponse> response, Retrofit retrofit) {
            // Hide progress bar
            progressBar.setVisibility(View.GONE);
            // Check data
            if (response != null && response.body() != null && response.body().getWeathers() != null
                    && response.body().getWeathers().size() > 0 && response.body().getMain() != null) {
                // Weather
                Weather weather = response.body().getWeathers().get(0);
                // Set weather description
                weatherText.setText(weather.getDescription());
                // Set temperature
                tempertureText.setText(getResources().getString(R.string.temperature,
                        (int) response.body().getMain().getCelciusTemperature()));
                // Get URL
                String url = MeteoAppWebService.getIconURL(weather.getIcon());
                // Image loading
                Picasso.with(getActivity()).load(url).into(weatherImageView);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            // Print
            Log.d(TAG, "WeatherCallback onFailure");
            t.printStackTrace();
            // Hide progress bar
            progressBar.setVisibility(View.GONE);
            // Toast for errors
            Toast.makeText(context, R.string.error_api, Toast.LENGTH_LONG).show();
        }

    }

}
