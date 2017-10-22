package rofaeil.ashaiaa.idea.placesinegypt.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * this class handles SharedPreferences object and defines all
 * methods and different variables in this SharedPreferences
 *
 * @author Rofaeil Ashaiaa
 *         Created on 17/09/17.
 */

public class PreferencesHelper {

    private static final String SIGHTS_OF_EGYPT_PREFERENCES = "SIGHTS_OF_EGYPT_PREFERENCES";
    private static final String IS_FIRST_RUN_KEY = "IS_FIRST_RUN_KEY";
    private static final String IS_FEATURED_SIGHTS_SAVED_KEY = "IS_FEATURED_SIGHTS_SAVED";
    private static final String NUMBER_OF_SIGHTS_IN_DATABASE_KEY = "NUMBER_OF_SIGHTS";
    private static PreferencesHelper INSTANCE;
    private SharedPreferences sharedPreferences;

    private PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SIGHTS_OF_EGYPT_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * make PreferencesHelper singleton
     * i.e. SharedPreferences object is also a singleton
     *
     * @param context used to instantiate SharedPreferences
     * @return object of  {@link PreferencesHelper}
     */
    public static synchronized PreferencesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper(context);
        }
        return INSTANCE;
    }

    /**
     * check if it is first time the app runs or not
     *
     * @return true if it is first time the app runs
     */
    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(IS_FIRST_RUN_KEY, true);
    }

    /**
     * set boolean whether it is first time the app runs or not
     *
     * @param firstRun value of fist run to change in shared prefernces
     */
    public void setFirstRun(boolean firstRun) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_RUN_KEY, firstRun);
        editor.apply();
    }

    /**
     * gets number of sights retrieved from server and saved to database
     */
    public int getNumberOfSightsInDatabase() {
        return sharedPreferences.getInt(NUMBER_OF_SIGHTS_IN_DATABASE_KEY, 0);
    }

    /**
     * set boolean whether captain is verified or not.
     */
    public void setNumberOfSightsInDatabase(int sightsInDatabase) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMBER_OF_SIGHTS_IN_DATABASE_KEY, sightsInDatabase);
        editor.apply();
    }

    public boolean isFeaturedSightsSaved(){
        return sharedPreferences.getBoolean(IS_FEATURED_SIGHTS_SAVED_KEY, false);
    }

    /**
     * sets boolean whether featured Sights Saved in local database or not
     */
    public void setFeaturedSightsSaved(boolean featuredSightsSaved) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FEATURED_SIGHTS_SAVED_KEY, featuredSightsSaved);
        editor.apply();
    }
}
