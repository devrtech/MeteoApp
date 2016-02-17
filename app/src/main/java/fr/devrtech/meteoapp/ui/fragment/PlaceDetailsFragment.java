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
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.devrtech.meteoapp.MeteoApplication;
import fr.devrtech.meteoapp.R;
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
    static final public String TAG = PlaceEditActivity.class.getSimpleName();

    /**
     * The fragment argument representing the place ID that this fragment represents.
     */
    static final public String ARG_PLACE_ID = "place_id";

    /**
     * The {@link Place} content this fragment is presenting.
     */
    private Place place;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (place != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(place.getDescription());
        }

        return rootView;
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
    private class WeatherCallback<WeatherResponse> implements Callback<WeatherResponse> {

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


        }

        @Override
        public void onFailure(Throwable t) {
            // Toast for errors
            Toast.makeText(context, R.string.error_api, Toast.LENGTH_LONG).show();
            // Print
            Log.d(TAG, "WeatherCallback onFailure");
            t.printStackTrace();
        }

    }

}
