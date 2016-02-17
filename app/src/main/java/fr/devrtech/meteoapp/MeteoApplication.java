package fr.devrtech.meteoapp;

import android.app.Application;

import fr.devrtech.meteoapp.api.MeteoAppWebService;
import fr.devrtech.meteoapp.db.MeteoDBHelper;
import fr.devrtech.meteoapp.model.Place;

/**
 * Instance of application
 * <p/>
 * Created by remi on 14/02/16.
 */
public class MeteoApplication extends Application {

    // App instance
    static private MeteoApplication APP_INSTANCE;

    // Database instance
    private MeteoDBHelper dbhelper;

    // Web Service Object
    private MeteoAppWebService webServiceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_INSTANCE = this;
        // Instantiate DB helper
        dbhelper = new MeteoDBHelper(this);
        // Instantiate Web Service Manager
        webServiceManager = new MeteoAppWebService();
    }

    /**
     * Get the application instance for Context / Application
     */
    static final public MeteoApplication getApplication() {
        return APP_INSTANCE;
    }

    static final public MeteoDBHelper getDBhelper() {
        return APP_INSTANCE.dbhelper;
    }

    static public MeteoAppWebService getWeatherWebService() {
        return APP_INSTANCE.webServiceManager;
    }

}
