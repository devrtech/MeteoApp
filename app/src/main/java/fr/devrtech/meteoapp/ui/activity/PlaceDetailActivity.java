package fr.devrtech.meteoapp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.devrtech.meteoapp.model.Place;
import fr.devrtech.meteoapp.ui.fragment.PlaceDetailsFragment;
import fr.devrtech.meteoapp.R;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link HomeActivity}.
 */
public class PlaceDetailActivity extends AppCompatActivity {

    // Class name for tag
    static final private String TAG = PlaceDetailActivity.class.getSimpleName();

    // Place id
    private long placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        placeId = getIntent().getLongExtra(PlaceDetailsFragment.ARG_PLACE_ID, -1L);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.meteo_fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to place
                goPlace();
                //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(PlaceDetailsFragment.ARG_PLACE_ID, placeId);
            PlaceDetailsFragment fragment = new PlaceDetailsFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment, PlaceDetailsFragment.TAG).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Open map to place
     */
    private void goPlace() {
        // Check fragment
        PlaceDetailsFragment fragment = (PlaceDetailsFragment) getFragmentManager().findFragmentByTag(PlaceDetailsFragment.TAG);
        // Check instances
        if (fragment != null && fragment.getPlace() != null) {
            // Place
            Place place = fragment.getPlace();
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
    }

}
