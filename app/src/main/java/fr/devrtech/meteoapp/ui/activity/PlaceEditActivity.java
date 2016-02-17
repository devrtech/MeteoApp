package fr.devrtech.meteoapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.devrtech.meteoapp.MeteoApplication;
import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.model.Place;

/**
 * Activity for editing
 * <p/>
 * Created by remi on 10/03/15.
 */
public class PlaceEditActivity extends AppCompatActivity {

    // Class name for tag
    static final private String TAG = PlaceEditActivity.class.getSimpleName();

    /**
     * The activity argument representing the place ID that this fragment represents.
     */
    static final public String ARG_PLACE_ID = "place_id";

    // Town data
    protected Place place;

    // Eventual id of place
    private long placeId;

    /* ***  Widgets *** */

    @Bind(R.id.meteo_edit_name)
    protected EditText nameEdit;

    @Bind(R.id.meteo_edit_long)
    protected EditText longitudeEdit;

    @Bind(R.id.meteo_edit_lat)
    protected EditText lattitudeEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_edit);

        // Binding butterknife
        ButterKnife.bind(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

        // Setup the Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_new_place);

        // Check if edit
        if (getIntent() != null && getIntent().getLongExtra(ARG_PLACE_ID, -1) > 0) {
            // Extract id
            placeId = getIntent().getLongExtra(ARG_PLACE_ID, -1);
            // Open DB helper
            MeteoApplication.getDBhelper().open();
            // Request for {@link Place}
            place = MeteoApplication.getDBhelper().getPlace(placeId);
            Log.d(TAG, "id: " + placeId + " place: " + place);
            // Close DB helper
            MeteoApplication.getDBhelper().close();
            // Check instance
            if (place != null) {
                // Set up data into front
                getSupportActionBar().setTitle(place.getName());
                nameEdit.setText(place.getName());
                longitudeEdit.setText(Float.toString(place.getLongitude()));
                lattitudeEdit.setText(Float.toString(place.getLatitude()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_save:
                updateInfo();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update infos
     */
    private void updateInfo() {
        // Check instance
        if (place == null) {
            // Instantiate new place
            place = new Place();
            place.setName(nameEdit.getText().toString());
            place.setLongitude(Float.parseFloat(longitudeEdit.getText().toString()));
            place.setLatitude(Float.parseFloat(lattitudeEdit.getText().toString()));
            // Open DB helper
            MeteoApplication.getDBhelper().open();
            MeteoApplication.getDBhelper().insertPlace(place);
            // Close DB helper
            MeteoApplication.getDBhelper().close();
        } else {


            // Update info

        }
    }

}
