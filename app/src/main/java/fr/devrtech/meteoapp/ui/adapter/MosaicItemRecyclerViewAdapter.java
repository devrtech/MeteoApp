package fr.devrtech.meteoapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.devrtech.meteoapp.R;
import fr.devrtech.meteoapp.api.MeteoAppWebService;
import fr.devrtech.meteoapp.api.model.Weather;
import fr.devrtech.meteoapp.api.model.WeatherResponse;
import fr.devrtech.meteoapp.ui.activity.HomeActivity;
import fr.devrtech.meteoapp.ui.view.MosaicItemView;

/**
 * Adapter for displaying mosaic items
 * <p/>
 * Created by remi on 17/02/16.
 */
public class MosaicItemRecyclerViewAdapter extends RecyclerView.Adapter<MosaicItemRecyclerViewAdapter.ViewHolder> {

    // Current context
    private HomeActivity context;

    // Data
    private final List<WeatherResponse> mValues;

    /**
     * Constructor
     */
    public MosaicItemRecyclerViewAdapter(HomeActivity context, List<WeatherResponse> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = new MosaicItemView(parent.getContext(), null);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        // set the view's size, margins, paddings and layout parameters
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // Check instances
        if (holder.mItem != null && holder.mItem.getWeathers() != null && holder.mItem.getWeathers().size() > 0) {
            // Get weather
            Weather weather = holder.mItem.getWeathers().get(0);
            // Set place name
            holder.nameView.setText(holder.mItem.getName());
            // Set temperature
            holder.temperatureView.setText(context.getResources().getString(R.string.temperature,
                    (int) holder.mItem.getMain().getCelciusTemperature()));
            // Image loading
            Picasso.with(context).load(MeteoAppWebService.getIconURL(weather.getIcon()))
                    .into(holder.weatherView);
            // Set item listener
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.openDetailsFromOWM(holder.mItem.getId());
                }
            });
        }
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
        private final ImageView weatherView;
        private final TextView temperatureView;
        private final TextView nameView;

        // Data
        private WeatherResponse mItem;

        /**
         * Constructor
         */
        public ViewHolder(View view) {
            super(view);
            rootView = view;
            weatherView = (ImageView) view.findViewById(R.id.meteo_image_weather);
            temperatureView = (TextView) view.findViewById(R.id.meteo_text_temp);
            nameView = (TextView) view.findViewById(R.id.meteo_text_name);
        }

        @Override
        public String toString() {
            return super.toString() + "-" + mItem.getId();
        }

    }

}
