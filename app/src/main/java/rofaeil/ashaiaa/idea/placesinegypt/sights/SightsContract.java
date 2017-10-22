package rofaeil.ashaiaa.idea.placesinegypt.sights;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.BasePresenter;
import rofaeil.ashaiaa.idea.placesinegypt.BaseView;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * defines the contract between the view and the presenter.
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public interface SightsContract {

    interface Presenter extends BasePresenter {

        /**
         * notify presenter when user clicks on sight
         *
         * @param clickedItemIndex index of item clicked
         * @param isFeatured       indicates whether the item clicked in featured list sights or not
         */
        void SightClicked(int clickedItemIndex, boolean isFeatured);

        /**
         * handles network state when changed
         *
         * @param previousState previous state of network
         * @param currentState  current state of network
         */
        void handleNetworkStatesChange(boolean previousState, boolean currentState);

        /**
         * notify presenter to  handle infinite scrolling
         *
         * @param page            page to load
         * @param totalItemsCount current total number of  items in list
         */
        void loadMoreSightsWithPagination(int page, int totalItemsCount);
    }

    interface View extends BaseView<Presenter> {

        /**
         * bind list of sights to featured sights recycler view
         *
         * @param featuredSights list of featured sights
         */

        void bindFeaturedSights(List<Sight> featuredSights);

        /**
         * bind list of sights to explore sights recycler view
         *
         * @param exploreSights list of sights
         */
        void bindExploreSights(List<Sight> exploreSights);

        /**
         * opens details activity of chosen sight
         *
         * @param sight id of chosen sight in database
         */
        void openTargetActivity(Sight sight);

        /**
         * checks is network available and connected or not
         */
        boolean isNetworkAvailable();

        /**
         * show message to user
         *
         * @param message message to display
         */
        void showMessage(String message);

        /**
         * show message when internet connection lost
         */
        void showConnectionLostMessage();

        /**
         * show message when internet connection is active again
         */
        void showConnectedAgainMessage();

        /**
         * hide progress bar of featured list of sights when data loaded
         */
        void hideFeaturedSightsProgressBar();

        /**
         * hide progress bar of explore list of sights when data loaded
         */
        void hideExploreSightsProgressBar();

        /**
         * show progress bar of featured list of sights when data is loading
         */
        void showFeaturedSightsProgressBar();

        /**
         * show progress bar of explore list of sights when data is loading
         */
        void showExploreSightsProgressBar();

        /**
         * show  recycler of explore sights list when data loaded
         */
        void showExploreSightsList();

        /**
         * show  recycler of Featured sights list when data loaded
         */
        void showFeaturedSightsList();

        /**
         * hide recycler of Featured sights list when data is loading
         */
        void hideFeaturedSightsList();

        /**
         * hide recycler of explore list when data is loading
         */
        void hideExploreSightsList();

        /**
         * notify recycler view that list has changed to update the ui
         *
         * @param response new list of sights
         * @param totalItemsCount
         */
        void notifyRecyclerViewWithNewList(List<Sight> response, int totalItemsCount);
    }
}
