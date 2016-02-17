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

    // Open Weather Map City Id
    private long openWeatherMapId = -1;

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

    public long getOpenWeatherMapId() {
        return openWeatherMapId;
    }

    public void setOpenWeatherMapId(long openWeatherMapId) {
        this.openWeatherMapId = openWeatherMapId;
    }

    /* *** STATIC *** */

    /**
     * Create all ids list {@link String}
     *
     * @return {@link String} list of ids (comma separated) or {@code null}
     */
    static public String getIdsFromPlaces(List<Place> places) {
        // All IDs
        String ids = null;
        // Check instance
        if (places != null) {
            // Iterate on places
            for (Place place : places) {
                // Check if have owm id
                if (place.getOpenWeatherMapId() > 0) {
                    // Check instance
                    if (ids == null) {
                        // Id
                        ids = Long.toString(place.getOpenWeatherMapId());
                    } else {
                        // Add id
                        ids += "," + place.getOpenWeatherMapId();
                    }
                }
            }
        }
        // Ids
        return ids;
    }

    /**
     * Create data for places
     *
     * @return {@link List}
     */
    static public List<Place> createInitialData() {
        // Instantiate list
        List<Place> places = new ArrayList<>();
        // Paris (cité / ND / point 0)
        Place place1 = new Place();
        place1.setName("Paris");
        place1.setType(Place.PlaceType.TOWN);
        place1.setLatitude(48.8533808F);
        place1.setLongitude(2.3486682F);
        place1.setOpenWeatherMapId(2988507L);
        places.add(place1);
        // Lyon
        Place place2 = new Place();
        place2.setName("Lyon");
        place2.setType(Place.PlaceType.TOWN);
        place2.setLatitude(45.750000F);
        place2.setLongitude(4.850000F);
        place2.setOpenWeatherMapId(2996944L);
        places.add(place2);
        // Lyon
        Place place3 = new Place();
        place3.setName("Colombes");
        place3.setType(Place.PlaceType.TOWN);
        place3.setLatitude(48.9146023F);
        place3.setLongitude(2.2272975F);
        place2.setOpenWeatherMapId(3024266L);
        places.add(place3);
        // Moscou
        Place place4 = new Place();
        place4.setName("Moscou");
        place4.setType(Place.PlaceType.TOWN);
        place4.setLatitude(55.752220F);
        place4.setLongitude(37.615555F);
        place4.setOpenWeatherMapId(524901L);
        places.add(place4);
        // Kyoto (JP)
        Place place5 = new Place();
        place5.setName("Kyoto");
        place5.setType(Place.PlaceType.TOWN);
        place5.setLatitude(35.021069F);
        place5.setLongitude(135.753845F);
        place5.setOpenWeatherMapId(1857910L);
        places.add(place5);
        // Toride (JP)
        Place place6 = new Place();
        place6.setName("Toride");
        place6.setType(Place.PlaceType.TOWN);
        place6.setLatitude(35.900002F);
        place6.setLongitude(140.083328F);
        place6.setOpenWeatherMapId(2110729L);
        places.add(place6);
        // Tokyo
        Place place7 = new Place();
        place7.setName("Tokyo");
        place7.setType(Place.PlaceType.TOWN);
        place7.setLatitude(35.689499F);
        place7.setLongitude(139.691711F);
        place7.setOpenWeatherMapId(1850147L);
        places.add(place7);
        // Barcelona
        Place place8 = new Place();
        place8.setName("Barcelona");
        place8.setType(Place.PlaceType.TOWN);
        place8.setLatitude(41.388790F);
        place8.setLongitude(2.158990F);
        place8.setOpenWeatherMapId(3128760L);
        places.add(place8);
        // Kourou
        Place place9 = new Place();
        place9.setName("Kourou");
        place9.setType(Place.PlaceType.TOWN);
        place9.setLatitude(5.155180F);
        place9.setLongitude(-52.647789F);
        place9.setOpenWeatherMapId(3381303L);
        places.add(place9);
        // Abidjan
        Place place10 = new Place();
        place10.setName("Abidjan");
        place10.setType(Place.PlaceType.TOWN);
        place10.setLatitude(5.309430F);
        place10.setLongitude(-4.019720F);
        place10.setOpenWeatherMapId(2293538L);
        places.add(place10);
        // Adelaide Hills
        Place place11 = new Place();
        place11.setName("Adelaide Hills");
        place11.setType(Place.PlaceType.TOWN);
        place11.setLatitude(-34.911179F);
        place11.setLongitude(138.707352F);
        place11.setOpenWeatherMapId(7302628L);
        places.add(place11);
        // Londres
        Place place12 = new Place();
        place12.setName("Londres");
        place12.setType(Place.PlaceType.TOWN);
        place12.setLatitude(51.512791F);
        place12.setLongitude(-0.09184F);
        place12.setOpenWeatherMapId(2643741L);
        places.add(place12);
        // New York
        Place place13 = new Place();
        place13.setName("New York");
        place13.setType(Place.PlaceType.TOWN);
        place13.setLatitude(40.714272F);
        place13.setLongitude(-74.005966F);
        place13.setOpenWeatherMapId(5128581L);
        places.add(place13);
        // Washington
        Place place14 = new Place();
        place14.setName("Washington");
        place14.setType(Place.PlaceType.TOWN);
        place14.setLatitude(37.130539F);
        place14.setLongitude(-113.508293F);
        place14.setOpenWeatherMapId(5549222L);
        places.add(place14);
        // Las Vegas
        Place place15 = new Place();
        place15.setName("Las Vegas");
        place15.setType(Place.PlaceType.TOWN);
        place15.setLatitude(35.593929F);
        place15.setLongitude(-105.223900F);
        place15.setOpenWeatherMapId(5475433L);
        places.add(place15);
        // Toulouse
        Place place16 = new Place();
        place16.setName("Toulouse");
        place16.setType(Place.PlaceType.TOWN);
        place16.setLatitude(43.604259F);
        place16.setLongitude(1.443670F);
        place16.setOpenWeatherMapId(2972315L);
        places.add(place16);
        // Bordeaux
        Place place17 = new Place();
        place17.setName("Bordeaux");
        place17.setType(Place.PlaceType.TOWN);
        place17.setLatitude(44.840439F);
        place17.setLongitude(-0.580500F);
        place17.setOpenWeatherMapId(3031582L);
        places.add(place17);
        // Nantes
        Place place18 = new Place();
        place18.setName("Nantes");
        place18.setType(Place.PlaceType.TOWN);
        place18.setLatitude(47.217251F);
        place18.setLongitude(-1.553360F);
        place18.setOpenWeatherMapId(2990969L);
        places.add(place18);
        // Lille
        Place place19 = new Place();
        place19.setName("Lille");
        place19.setType(Place.PlaceType.TOWN);
        place19.setLatitude(50.632969F);
        place19.setLongitude(3.058580F);
        place19.setOpenWeatherMapId(2998324L);
        places.add(place19);
        // Saint-Etienne
        Place place20 = new Place();
        place20.setName("Saint-Etienne");
        place20.setType(Place.PlaceType.TOWN);
        place20.setLatitude(45.433331F);
        place20.setLongitude(4.400000F);
        place20.setOpenWeatherMapId(2980291L);
        places.add(place20);
        // Saint-Symphorien-sur-Coise
        Place place21 = new Place();
        place21.setName("Saint-Symphorien-sur-Coise");
        place21.setType(Place.PlaceType.TOWN);
        place21.setLatitude(45.632000F);
        place21.setLongitude(4.458110F);
        place21.setOpenWeatherMapId(2976794L);
        places.add(place21);
        // Montpellier
        Place place22 = new Place();
        place22.setName("Montpellier");
        place22.setType(Place.PlaceType.TOWN);
        place22.setLatitude(43.610920F);
        place22.setLongitude(3.877230F);
        place22.setOpenWeatherMapId(2992166L);
        places.add(place22);
        // Nice
        Place place23 = new Place();
        place23.setName("Nice");
        place23.setType(Place.PlaceType.TOWN);
        place23.setLatitude(43.703129F);
        place23.setLongitude(7.266080F);
        place23.setOpenWeatherMapId(2990440L);
        places.add(place23);
        // Madrid
        Place place24 = new Place();
        place24.setName("Madrid");
        place24.setType(Place.PlaceType.TOWN);
        place24.setLatitude(40.416500F);
        place24.setLongitude(-3.702560F);
        place24.setOpenWeatherMapId(3117735L);
        places.add(place24);
        // Roma
        Place place25 = new Place();
        place25.setName("Rome");
        place25.setType(Place.PlaceType.TOWN);
        place25.setLatitude(41.894741F);
        place25.setLongitude(12.483900F);
        place25.setOpenWeatherMapId(3169070L);
        places.add(place25);
        // Turin
        Place place26 = new Place();
        place26.setName("Turin");
        place26.setType(Place.PlaceType.TOWN);
        place26.setLatitude(45.070492F);
        place26.setLongitude(7.686820F);
        place26.setOpenWeatherMapId(3165524L);
        places.add(place26);
        // Berlin
        Place place27 = new Place();
        place27.setName("Berlin");
        place27.setType(Place.PlaceType.TOWN);
        place27.setLatitude(52.524368F);
        place27.setLongitude(13.410530F);
        place27.setOpenWeatherMapId(2950159L);
        places.add(place27);
        // Return places
        return places;
    }

}
