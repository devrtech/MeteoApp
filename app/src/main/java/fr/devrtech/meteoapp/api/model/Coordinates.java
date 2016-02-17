package fr.devrtech.meteoapp.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remi on 09/02/16.
 */
public class Coordinates {

    @SerializedName("lon")
    private float longitude;

    @SerializedName("lat")
    private float lattitude;

    /* CONSTRUCTORS *** */

    public Coordinates() {
        super();
    }

    public Coordinates(float longitude, float lattitude) {
        this();
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    /* *** GETTERS AND SETTERS *** */

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

}
