package fr.devrtech.meteoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.devrtech.meteoapp.model.Place;

/**
 * SQL helper
 * <p/>
 * Created by remi on 14/02/16.
 */
public class MeteoDBHelper extends SQLiteOpenHelper {

    // Class name for tag
    static final private String TAG = MeteoDBHelper.class.getSimpleName();

    // Database object
    private SQLiteDatabase database;

    // DB version (incremented when database change)
    public static final int DATABASE_VERSION = 5;

    // Database name
    public static final String DATABASE_NAME = "Places.db";

    /**
     * Default constructor
     */
    public MeteoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLACES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_PLACES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void open() {
        database = this.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    /* *** EXTRACT FROM DB *** */

    public List<Place> getAllPlaces() {
        // Instantiate global list
        List<Place> places = new ArrayList<>();
        // Query
        Cursor cursor = database.query(PlaceEntry.TABLE_NAME, PlaceEntry.ALL_COLUMNS, null, null, null, null, PlaceEntry.COLUMN_NAME_NAME);
        // First
        cursor.moveToFirst();
        // Iterate
        while (!cursor.isAfterLast()) {
            // Get {@link Place} objetc
            Place place = cursorToPlace(cursor);
            //Log.d(TAG, "place: " + place);
            // Adding object
            places.add(place);
            // Move to next
            cursor.moveToNext();
        }
        // Closing
        cursor.close();
        return places;
    }

    public Place getPlace(long id) {
        // Query
        Cursor cursor = database.query(PlaceEntry.TABLE_NAME, PlaceEntry.ALL_COLUMNS, PlaceEntry._ID + "=?",
                new String[]{Long.toString(id)}, null, null, null);
        // First
        cursor.moveToFirst();
        // Get {@link Place} object
        Place place = cursorToPlace(cursor);
        // Closing
        cursor.close();
        return place;
    }

    public Place getPlaceFromOWMid(long id) {
        // Query
        Cursor cursor = database.query(PlaceEntry.TABLE_NAME, PlaceEntry.ALL_COLUMNS, PlaceEntry.COLUMN_NAME_OWM_ID + "=?",
                new String[]{Long.toString(id)}, null, null, null);
        // First
        cursor.moveToFirst();
        // Get {@link Place} object
        Place place = cursorToPlace(cursor);
        // Closing
        cursor.close();
        return place;
    }

    /* *** UPDATE DB *** */

    public void insertPlace(Place place) {
        Log.d(TAG, "Insert place: " + place);
        // Check instance
        if (place != null) {
            ContentValues values = new ContentValues();
            values.put(PlaceEntry.COLUMN_NAME_NAME, place.getName());
            values.put(PlaceEntry.COLUMN_NAME_TYPE, place.getType().getId());
            values.put(PlaceEntry.COLUMN_NAME_LONGITUDE, place.getLongitude());
            values.put(PlaceEntry.COLUMN_NAME_LATITUDE, place.getLatitude());
            values.put(PlaceEntry.COLUMN_NAME_OWM_ID, place.getOpenWeatherMapId());
            // Insert request
            database.insert(PlaceEntry.TABLE_NAME, null, values);
        }
    }

    public void deleteAllPlaces() {
        Log.d(TAG, "Delete all places !");
        database.delete(PlaceEntry.TABLE_NAME, null, null);
    }

    public void fillInitDB() {
        // Get places
        List<Place> places = Place.createInitialData();
        // Iterate on places
        for (Place place : places) {
            // Insertions
            insertPlace(place);
        }
    }

    /**
     * Get {@link Place} object from {@link Cursor} DB
     */
    private Place cursorToPlace(Cursor cursor) {
        // Instantiate
        Place place = new Place();
        // Setup content
        place.setId(cursor.getLong(0));
        place.setName(cursor.getString(1));
        place.setLongitude(cursor.getFloat(3));
        place.setLatitude(cursor.getFloat(4));
        place.setOpenWeatherMapId(cursor.getLong(5));
        // return place
        return place;
    }

    /* *** SQL *** */

    // Types
    static final private String TEXT_TYPE = " TEXT";
    static final private String INTEGER_TYPE = " INTEGER";
    static final private String FLOAT_TYPE = " FLOAT";
    static final private String COMMA_SEP = ",";

    // SQL requests
    static final private String SQL_CREATE_PLACES =
            "CREATE TABLE " + PlaceEntry.TABLE_NAME + " (" +
                    PlaceEntry._ID + " INTEGER PRIMARY KEY," +
                    PlaceEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PlaceEntry.COLUMN_NAME_TYPE + INTEGER_TYPE + COMMA_SEP +
                    PlaceEntry.COLUMN_NAME_LONGITUDE + FLOAT_TYPE + COMMA_SEP +
                    PlaceEntry.COLUMN_NAME_LATITUDE + FLOAT_TYPE + COMMA_SEP +
                    PlaceEntry.COLUMN_NAME_OWM_ID + INTEGER_TYPE + " )";

    static final private String SQL_DELETE_PLACES =
            "DROP TABLE IF EXISTS " + PlaceEntry.TABLE_NAME;

    /**
     * Inner class that defines the table contents
     */
    public static abstract class PlaceEntry implements BaseColumns {

        // Tables
        static final public String TABLE_NAME = "place";

        // Columns
        static final public String COLUMN_NAME_NAME = "name";
        static final public String COLUMN_NAME_TYPE = "type";
        static final public String COLUMN_NAME_LONGITUDE = "longitude";
        static final public String COLUMN_NAME_LATITUDE = "latitude";
        static final public String COLUMN_NAME_OWM_ID = "owmid";
        static final private String[] ALL_COLUMNS = {_ID, COLUMN_NAME_NAME,
                COLUMN_NAME_TYPE, COLUMN_NAME_LONGITUDE, COLUMN_NAME_LATITUDE, COLUMN_NAME_OWM_ID};
    }

}
