package rofaeil.ashaiaa.idea.placesinegypt.sightdetails;

import rofaeil.ashaiaa.idea.placesinegypt.BasePresenter;
import rofaeil.ashaiaa.idea.placesinegypt.BaseView;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 20/10/17.
 */

public interface SightDetailsContract {

    interface Presenter extends BasePresenter {

        /**
         * notify presenter that user clicked save image icon
         */
        void userClickedSaveImage();
    }

    interface View extends BaseView<Presenter> {

        /**
         * binds sight details to ui
         *
         * @param mSight sight object
         */
        void setSightDetailsToView(Sight mSight);

        /**
         * start save image to local storage task
         */
        void saveImageToStorage();
    }
}
