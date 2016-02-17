package fr.devrtech.meteoapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import fr.devrtech.meteoapp.ui.fragment.PlaceDetailsFragment;
import fr.devrtech.meteoapp.MeteoApplication;
import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.model.Place;

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

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Bind(R.id.item_list)
    protected View recyclerView;

    @Bind(R.id.meteo_fab_add)
    protected FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Binding butterknife
        ButterKnife.bind(this);

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

    private void refresh() {
        setupRecyclerView((RecyclerView) recyclerView);
    }

    /**
     * Launch intent for a new place
     */
    private void newPlaceIntent() {
        // Intent for editing Place
        Intent newPlaceIntent = new Intent(this, PlaceEditActivity.class);
        startActivity(newPlaceIntent);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        List<Place> places = MeteoApplication.getDBhelper().getAllPlaces();
        //List<Place> places = DummyContent.ITEMS;

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(places));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Place> mValues;

        public SimpleItemRecyclerViewAdapter(List<Place> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("" + mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putLong(PlaceDetailsFragment.ARG_PLACE_ID, (int) holder.mItem.getId());
                        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
                        fragment.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PlaceDetailActivity.class);
                        intent.putExtra(PlaceDetailsFragment.ARG_PLACE_ID, holder.mItem.getId());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Place mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
