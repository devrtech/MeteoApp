package fr.devrtech.meteoapp.model;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for town
 * <p/>
 * Created by remi on 14/02/16.
 */
public class Place {

    // Types
    public enum PlaceType {

        DEFAULT(0), TOWN(1), REGION(2);

        private int id;

        PlaceType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        static final public PlaceType getPlaceType(int id) {
            switch (id) {
                case 1:
                    return TOWN;
                case 2:
                    return REGION;
                default:
                    return DEFAULT;
            }
        }

    }

    // Id of town
    private long id;

    // Place type
    private PlaceType type = PlaceType.DEFAULT;

    // Name of town
    private String name;

    // Descrription of place
    private String description;

    // Longitude
    private float longitude;

    // Latitude
    private float latitude;

    /**
     * Constructor
     */
    public Place() {
        super();
    }

    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getType() + " -> " + getLongitude() + ";" + getLatitude();
    }

    /* *** GETTERS AND SETTERS *** */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* *** STATIC *** */

    /**
     * Create data for places
     *
     * @return {@link List}
     */
    static final public List<Place> createInitialData() {
        // Instantiate list
        List<Place> places = new ArrayList<>();
        // Paris (cité / ND / point 0)
        Place place1 = new Place();
        place1.setName("Paris");
        place1.setType(Place.PlaceType.TOWN);
        place1.setLatitude(48.8533808F);
        place1.setLongitude(2.3486682F);
        places.add(place1);
        // Lyon
        Place place2 = new Place();
        place2.setName("Lyon");
        place1.setType(Place.PlaceType.TOWN);
        place2.setLatitude(45.750000F);
        place2.setLongitude(4.850000F);
        places.add(place2);
        // Lyon
        Place place3 = new Place();
        place3.setName("Colombes");
        place1.setType(Place.PlaceType.TOWN);
        place3.setLatitude(48.9146023F);
        place3.setLongitude(2.2272975F);
        places.add(place3);
        // Return places
        return places;
    }


}
