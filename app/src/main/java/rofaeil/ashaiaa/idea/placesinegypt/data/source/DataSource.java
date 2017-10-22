package rofaeil.ashaiaa.idea.placesinegypt.data.source;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public interface DataSource {

    /**
     * retrieve list of featured sights (10 sights) either from the
     * server or from local database
     */
    void getFeaturedSights(getSightsCallback callback, boolean isNetworkAvilabale);

    /**
     * retrieve list of sights either from the server or from local database
     *
     * @param count number of sights to retrieve
     * @param from  get sights starting with this integer
     */
    void getSightsWithinRange(int count, int from, getSightsCallback callback);

    /**
     * saves sites to local database
     *
     * @param sights list of sights to save
     */
    void saveSights(List<Sight> sights, saveSightsCallbackInDatabase callback);

    /**
     * update sights in local database
     *
     * @param sights list of sights to update
     */
    void updateSights(List<Sight> sights);

    /**
     * gets sight from local database
     *
     * @param sightId id of sight
     */
    void getSight(int sightId, getSightCallback callback);

    boolean isFirstRun();

    void setFirstRun(boolean firstRun);

    int getNumberOfSightsInDatabase();

    void setNumberOfSightsInDatabase(int sightsInDatabase);

    boolean isFeaturedSightsSaved();

    void setFeaturedSightsSaved(boolean featuredSightsSaved);

    void loadMoreSightsWithPagination(int page, int totalItemsCount, DataSource.getSightsCallback callback);

    /**
     * request callback of getting sights from server
     */
    interface getSightsCallback {

        void onResponse(List<Sight> response);

        void onError(String string);
    }

    /**
     * callback of loading sights from local database
     */
    interface getLocalSightsCallback {

        void onDataLoaded(List<Sight> sights);

    }

    /**
     * callback of saving sights to local database
     */
    interface saveSightsCallbackInDatabase {
        void onDataSaved();
    }

    /**
     * callback of updating sights in local database
     */
    interface updateSightsCallbackInDatabase {
        void onDataUpdated();
    }

    /**
     * callback of loading sight from local database
     */
    interface getSightCallback {

        void onDataLoaded(Sight sight);

    }
}
