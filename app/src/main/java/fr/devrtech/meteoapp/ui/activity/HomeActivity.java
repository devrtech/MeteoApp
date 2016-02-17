package fr.devrtech.meteoapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.devrtech.meteoapp.api.model.MultipleWeatherResponse;
import fr.devrtech.meteoapp.ui.adapter.MosaicItemRecyclerViewAdapter;
import fr.devrtech.meteoapp.ui.adapter.SimpleItemRecyclerViewAdapter;
import fr.devrtech.meteoapp.ui.fragment.PlaceDetailsFragment;
import fr.devrtech.meteoapp.MeteoApplication;
import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.model.Place;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PlaceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class HomeActivity extends AppCompatActivity {

    // Class name for tag
    static final private String TAG = HomeActivity.class.getSimpleName();

    // Places
    private List<Place> places;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /* *** WIDGETS *** */

    @Bind(R.id.item_list)
    protected RecyclerView recyclerView;

    @Bind(R.id.meteo_fab_add)
    protected FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Binding butterknife
        ButterKnife.bind(this);

        recyclerView.addItemDecoration(new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPlaceIntent();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        MeteoApplication.getDBhelper().open();
        refresh();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        MeteoApplication.getDBhelper().close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add content menu
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_mosaic:
                // Send WS request
                MeteoApplication.getWeatherWebService().currentWeather(places, new WeatherCallback(this));
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_init:
                // Delete and init DB
                MeteoApplication.getDBhelper().deleteAllPlaces();
                MeteoApplication.getDBhelper().fillInitDB();
                refresh();
                return true;
            case R.id.action_new_place:
                newPlaceIntent();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Open details fragment
     *
     * @param owmid Id of place from Open Weather Map id
     */
    public void openDetailsFromOWM(long owmid) {
        // Try to found {@link Place} from DB
        Place place = MeteoApplication.getDBhelper().getPlaceFromOWMid(owmid);
        // Check if object has been found
        if (place != null) {
            // Open fragment
            openDetails(place.getId());
        }
    }

    /**
     * Open details fragment
     *
     * @param id Id of place from DB
     */
    public void openDetails(long id) {
        // Check mode
        if (mTwoPane) {
            // Open fragment in tablet mode
            Bundle arguments = new Bundle();
            arguments.putLong(PlaceDetailsFragment.ARG_PLACE_ID, id);
            PlaceDetailsFragment fragment = new PlaceDetailsFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            // New activity in mobile/portrait mode
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            intent.putExtra(PlaceDetailsFragment.ARG_PLACE_ID, id);
            startActivity(intent);
        }
    }

    private void refresh() {
        // Init data from DB
        places = MeteoApplication.getDBhelper().getAllPlaces();
        // Instantiate layout manager + adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, places));
    }

    /**
     * Launch intent for a new place
     */
    private void newPlaceIntent() {
        // Intent for editing Place
        Intent newPlaceIntent = new Intent(this, PlaceEditActivity.class);
        startActivity(newPlaceIntent);
    }

    /**
     * Callback for Weather
     */
    private class WeatherCallback implements Callback<MultipleWeatherResponse> {

        // App context
        protected Context context;

        /**
         * Constructor
         */
        protected WeatherCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Response<MultipleWeatherResponse> response, Retrofit retrofit) {
            // Check data
            if (response != null && response.body() != null && response.body().getWeathers() != null) {
                // Instantiate layout manager + adapter
                recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
                recyclerView.setAdapter(new MosaicItemRecyclerViewAdapter(HomeActivity.this, response.body().getWeathers()));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            // Print
            Log.d(TAG, "WeatherCallback onFailure");
            t.printStackTrace();
            // Toast for errors
            Toast.makeText(context, R.string.error_api, Toast.LENGTH_LONG).show();
        }

    }

}
