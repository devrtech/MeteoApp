package fr.devrtech.meteoapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.model.Place;
import fr.devrtech.meteoapp.ui.activity.HomeActivity;

/**
 * Adapter for displaying simple items
 * <p/>
 * Created by remi on 17/02/16.
 */
public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    // Current context
    private HomeActivity context;

    // Data
    private final List<Place> mValues;

    /**
     * Constructor
     */
    public SimpleItemRecyclerViewAdapter(HomeActivity context, List<Place> items) {
        mValues = items;
        this.context = context;
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
        // Set listener
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDetails(holder.mItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Root view
        private View rootView;

        // Widgets
        public final TextView mIdView;
        public final TextView mContentView;

        // Data
        public Place mItem;

        /**
         * Constructor
         */
        public ViewHolder(View view) {
            super(view);
            rootView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + "-" + mItem.getId();
        }

    }

}
