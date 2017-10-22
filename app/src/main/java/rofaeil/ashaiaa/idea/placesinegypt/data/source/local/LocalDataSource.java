package rofaeil.ashaiaa.idea.placesinegypt.data.source.local;

import android.arch.persistence.room.Room;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.DataSource;

/**
 * Implementation of the {@link DataSource} that handles data from local database
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class LocalDataSource implements DataSource {

    private static final String DATABASE_NAME = "sights.db";
    private static LocalDataSource INSTANCE;
    private SightsDatabase mSightsDatabase;
    //this context reference to application context to prevent any memory leaks
    private Context mContext;

    public LocalDataSource(Context context) {
        mSightsDatabase = Room.databaseBuilder(
                context.getApplicationContext(), SightsDatabase.class, DATABASE_NAME).build();
        this.mContext = context;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getFeaturedSights(final getSightsCallback callback, boolean isNetworkAvilabale) {
        AsyncTaskLoader asyncTaskLoader = new AsyncTaskLoader(mContext) {
            @Override
            public Object loadInBackground() {
                return mSightsDatabase.sightsDao().getFeaturedSights();
            }

            @Override
            public void deliverResult(Object data) {
               List<Sight>  sights = (List<Sight>) data;
                callback.onResponse(sights);
            }
        };

        asyncTaskLoader.forceLoad();
    }

    @Override
    public void getSightsWithinRange(int count, int from, final getSightsCallback callback) {
        AsyncTaskLoader asyncTaskLoader = new AsyncTaskLoader(mContext) {
            @Override
            public Object loadInBackground() {
                return mSightsDatabase.sightsDao().getExploreSights();
            }

            @Override
            public void deliverResult(Object data) {
                List<Sight>  sights = (List<Sight>) data;
                callback.onResponse(sights);
            }
        };

        asyncTaskLoader.forceLoad();
    }

    @Override
    public void saveSights(final List<Sight> sights, final saveSightsCallbackInDatabase callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mSightsDatabase.sightsDao().insertSights(sights);
                callback.onDataSaved();
            }
        }).start();
    }

    @Override
    public void updateSights(final List<Sight> sights) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSightsDatabase.sightsDao().updateSights(sights);
            }
        }).start();
    }

    @Override
    public void getSight(final int sightId, final getSightCallback callback) {

        AsyncTaskLoader asyncTaskLoader = new AsyncTaskLoader(mContext) {
            @Override
            public Object loadInBackground() {
                return mSightsDatabase.sightsDao().getSightWithId(sightId);
            }

            @Override
            public void deliverResult(Object data) {
                Sight sight = (Sight) data;
                callback.onDataLoaded(sight);
            }
        };

        asyncTaskLoader.forceLoad();
    }

    @Override
    public boolean isFirstRun() {
        return PreferencesHelper.getInstance(mContext).isFirstRun();
    }

    @Override
    public void setFirstRun(boolean firstRun) {
        PreferencesHelper.getInstance(mContext).setFirstRun(firstRun);
    }

    @Override
    public int getNumberOfSightsInDatabase() {
        return PreferencesHelper.getInstance(mContext).getNumberOfSightsInDatabase();
    }

    @Override
    public void setNumberOfSightsInDatabase(int sightsInDatabase) {
        PreferencesHelper.getInstance(mContext).setNumberOfSightsInDatabase(sightsInDatabase);
    }

    @Override
    public boolean isFeaturedSightsSaved() {
        return PreferencesHelper.getInstance(mContext).isFeaturedSightsSaved();
    }

    @Override
    public void setFeaturedSightsSaved(boolean featuredSightsSaved) {
        PreferencesHelper.getInstance(mContext).setFeaturedSightsSaved(featuredSightsSaved);
    }

    @Override
    public void loadMoreSightsWithPagination(int page, int totalItemsCount, getSightsCallback callback) {

    }
}
