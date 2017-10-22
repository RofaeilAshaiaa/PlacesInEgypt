package rofaeil.ashaiaa.idea.placesinegypt.data.source;

import android.content.Context;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.sights.SightsPresenter;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private List<Sight> mCachedFeaturedSights;
    private List<Sight> mCachedSights;
    private DataSource mRemoteDataSource;
    private DataSource mLocalDataSource;

    private Repository(DataSource mRemoteDataSource, DataSource mLocalDataSource, Context context) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static Repository getInstance(DataSource remoteDataSource, DataSource localDataSource, Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource, context);
        }
        return INSTANCE;
    }

    public void setCachedFeaturedSights(List<Sight> mCachedFeaturedSights) {
        this.mCachedFeaturedSights = mCachedFeaturedSights;
    }

    public void setCachedSights(List<Sight> mCachedSights) {
        this.mCachedSights = mCachedSights;
    }

    @Override
    public void getFeaturedSights(final getSightsCallback callback, boolean isNetworkAvilabale) {
        if (mCachedFeaturedSights == null)
            if (isNetworkAvilabale) {
                mRemoteDataSource.getFeaturedSights(new getSightsCallback() {
                    @Override
                    public void onResponse(List<Sight> response) {
                        mCachedFeaturedSights = response;
                        callback.onResponse(mCachedFeaturedSights);
                    }

                    @Override
                    public void onError(String string) {
                        callback.onError(string);
                    }
                }, false);
            } else {
                mLocalDataSource.getFeaturedSights(new getSightsCallback() {
                    @Override
                    public void onResponse(List<Sight> response) {
                        mCachedFeaturedSights = response;
                        callback.onResponse(mCachedFeaturedSights);
                    }

                    @Override
                    public void onError(String string) {
                        callback.onError(string);
                    }
                }, false);
            }
        else
            callback.onResponse(mCachedFeaturedSights);
    }

    @Override
    public void getSightsWithinRange(int count, int from, final getSightsCallback callback) {

        if (mCachedSights == null)
            if (SightsPresenter.getIsConnected()) {

                mRemoteDataSource.getSightsWithinRange(count, from, new getSightsCallback() {
                    @Override
                    public void onResponse(List<Sight> response) {
                        mCachedSights = response;
                        callback.onResponse(mCachedSights);
                    }

                    @Override
                    public void onError(String string) {
                        callback.onError(string);
                    }
                });
            } else {
                mLocalDataSource.getSightsWithinRange(count, from, new getSightsCallback() {
                    @Override
                    public void onResponse(List<Sight> response) {
                        mCachedSights = response;
                        callback.onResponse(mCachedSights);
                    }

                    @Override
                    public void onError(String string) {
                        callback.onError(string);
                    }
                });
            }

        else
            callback.onResponse(mCachedSights);
    }

    @Override
    public void saveSights(List<Sight> sights, final saveSightsCallbackInDatabase callback) {
        mLocalDataSource.saveSights(sights, new saveSightsCallbackInDatabase() {
            @Override
            public void onDataSaved() {
                callback.onDataSaved();
            }
        });

    }

    @Override
    public void updateSights(List<Sight> sights) {

    }

    @Override
    public void getSight(int sightId, getSightCallback callback) {
        mLocalDataSource.getSight(sightId, callback);
    }

    @Override
    public boolean isFirstRun() {
        return false;
    }

    @Override
    public void setFirstRun(boolean firstRun) {

    }

    @Override
    public boolean isFeaturedSightsSaved() {
        return mLocalDataSource.isFeaturedSightsSaved();
    }

    @Override
    public void setFeaturedSightsSaved(boolean featuredSightsSaved) {
        mLocalDataSource.setFeaturedSightsSaved(featuredSightsSaved);
    }

    @Override
    public void loadMoreSightsWithPagination(int page, int totalItemsCount, final getSightsCallback callback) {
        if (SightsPresenter.getIsConnected()) {
            mRemoteDataSource.loadMoreSightsWithPagination(page, totalItemsCount, new getSightsCallback() {
                @Override
                public void onResponse(List<Sight> response) {
                    mCachedSights.addAll(response);
                    callback.onResponse(response);
                }

                @Override
                public void onError(String string) {
                    callback.onError(string);
                }
            });
        } else {
            mLocalDataSource.loadMoreSightsWithPagination(page,totalItemsCount,new getSightsCallback() {
                @Override
                public void onResponse(List<Sight> response) {
                    mCachedSights.addAll(response);
                    callback.onResponse(response);
                }

                @Override
                public void onError(String string) {
                    callback.onError(string);
                }
            });
        }
    }

    @Override
    public int getNumberOfSightsInDatabase() {
        return mLocalDataSource.getNumberOfSightsInDatabase();
    }

    @Override
    public void setNumberOfSightsInDatabase(int sightsInDatabase) {
        mLocalDataSource.setNumberOfSightsInDatabase(sightsInDatabase);
    }
}
