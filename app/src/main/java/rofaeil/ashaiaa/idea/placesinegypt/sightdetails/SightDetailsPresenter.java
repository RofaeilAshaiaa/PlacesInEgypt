package rofaeil.ashaiaa.idea.placesinegypt.sightdetails;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.DataSource;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.Repository;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 20/10/17.
 */

public class SightDetailsPresenter implements SightDetailsContract.Presenter {

    private static SightDetailsPresenter mSightDetailsPresenter;
    private SightDetailsContract.View mSightDetailsFragment;
    private DataSource mRepository;
    private Sight mSight;
    private int mChosenSightId;


    public SightDetailsPresenter(SightDetailsFragment sightDetailsFragment, Repository repository, Sight sight) {
        this.mSightDetailsFragment = sightDetailsFragment;
        this.mRepository = repository;
        this.mSight = sight;
//        mChosenSightId = sight;
        if (mSightDetailsFragment != null) {
            mSightDetailsFragment.setPresenter(this);
        }
    }

    public static SightDetailsPresenter getInstance(SightDetailsFragment sightDetailsFragment, Repository repository, Sight sight) {
        if (mSightDetailsPresenter == null)
            mSightDetailsPresenter = new SightDetailsPresenter(sightDetailsFragment, repository, sight);
        else
            sightDetailsFragment.setPresenter(mSightDetailsPresenter);
        return mSightDetailsPresenter;
    }

    @Override
    public void start() {
        if (mSight == null)
            mRepository.getSight(mChosenSightId, new DataSource.getSightCallback() {
                @Override
                public void onDataLoaded(Sight sight) {
                    mSight = sight;
                    mSightDetailsFragment.setSightDetailsToView(mSight);
                }
            });
        else {
            mSightDetailsFragment.setSightDetailsToView(mSight);
        }
    }

    @Override
    public void userClickedSaveImage() {
        mSightDetailsFragment.saveImageToStorage();
    }
}
