package rofaeil.ashaiaa.idea.placesinegypt.sights;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.DataSource;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.Repository;

/**
 * Listens to user actions from the UI ({@link SightsFragment}), retrieves the data and updates
 * the UI as required.
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class SightsPresenter implements SightsContract.Presenter {

    private static SightsPresenter mSightsPresenter;
    private static boolean mIsConnected = false;
    private SightsContract.View mSightsFragment;
    private Repository mRepository;
    private List<Sight> mFeaturedSights;
    private List<Sight> mExploreSights;


    public SightsPresenter(SightsContract.View sightsFragment, Repository repository) {
        this.mSightsFragment = sightsFragment;
        this.mRepository = repository;
        if (mSightsFragment != null) {
            mSightsFragment.setPresenter(this);
            mSightsPresenter = this;
            mIsConnected = mSightsFragment.isNetworkAvailable();
        }
    }

    public static SightsPresenter getInstance(SightsContract.View signUpFragment, Repository repository) {
        if (mSightsPresenter == null)
            mSightsPresenter = new SightsPresenter(signUpFragment, repository);
        else
            signUpFragment.setPresenter(mSightsPresenter);
        return mSightsPresenter;
    }

    public static boolean getIsConnected() {
        return mIsConnected;
    }

    public static void setIsConnected(boolean isConnected) {
        if (mSightsPresenter != null) {
            mSightsPresenter.handleNetworkStatesChange(mIsConnected, isConnected);

        }
        mIsConnected = isConnected;
    }

    @Override
    public void start() {
        showProgressBarsAndHideLists();
        mRepository.getFeaturedSights(new DataSource.getSightsCallback() {
            @Override
            public void onResponse(final List<Sight> response) {
                mFeaturedSights = response;

                showFeaturedListAndHideProgressBar();

                mSightsFragment.bindFeaturedSights(mFeaturedSights);
                //mark every item of sights as featured sight
                for (Sight sight : response) {
                    sight.setIsFeatured(1);
                }

                if (!mRepository.isFeaturedSightsSaved()) {
                    mRepository.saveSights(response, new DataSource.saveSightsCallbackInDatabase() {
                        @Override
                        public void onDataSaved() {
                            mRepository.setFeaturedSightsSaved(true);
                        }
                    });
                } else {
                    mRepository.updateSights(response);
                }
                mRepository.getSightsWithinRange(20, 10, new DataSource.getSightsCallback() {
                    @Override
                    public void onResponse(final List<Sight> sights) {
                        mExploreSights = sights;
                        showExploreListAndHideProgressBar();
                        mSightsFragment.bindExploreSights(mExploreSights);
                        if (mRepository.getNumberOfSightsInDatabase() == 0) {
                            mRepository.saveSights(sights, new DataSource.saveSightsCallbackInDatabase() {
                                @Override
                                public void onDataSaved() {
                                    mRepository.setNumberOfSightsInDatabase(sights.size());
                                }
                            });
                        } else {
                            mRepository.updateSights(sights);
                        }
                    }

                    @Override
                    public void onError(String string) {
                        mSightsFragment.showMessage(string);
                    }
                });
            }

            @Override
            public void onError(String string) {
                mSightsFragment.showMessage(string);
            }
        }, mIsConnected);
    }

    private void showExploreListAndHideProgressBar() {
        mSightsFragment.showExploreSightsList();
        mSightsFragment.hideExploreSightsProgressBar();
    }

    private void showFeaturedListAndHideProgressBar() {
        mSightsFragment.showFeaturedSightsList();
        mSightsFragment.hideFeaturedSightsProgressBar();
    }

    @Override
    public void SightClicked(int clickedItemIndex, boolean isFeatured) {
        if (isFeatured)
            mSightsFragment.openTargetActivity(mFeaturedSights.get(clickedItemIndex));
        else
            mSightsFragment.openTargetActivity(mExploreSights.get(clickedItemIndex));

    }

    @Override
    public void handleNetworkStatesChange(boolean previousState, boolean currentState) {
        if (!previousState && currentState) {
            //connected after disconnected
            mRepository.setCachedSights(null);
            mRepository.setCachedFeaturedSights(null);
            mSightsFragment.showConnectedAgainMessage();
            showProgressBarsAndHideLists();
            mRepository.getFeaturedSights(new DataSource.getSightsCallback() {
                @Override
                public void onResponse(final List<Sight> response) {
                    mFeaturedSights = response;
                    showFeaturedListAndHideProgressBar();
                    mSightsFragment.bindFeaturedSights(mFeaturedSights);
                    //mark every item of sights as featured sight
                    for (Sight sight : response) {
                        sight.setIsFeatured(1);
                    }

                    if (!mRepository.isFeaturedSightsSaved()) {
                        mRepository.saveSights(response, new DataSource.saveSightsCallbackInDatabase() {
                            @Override
                            public void onDataSaved() {
                                mRepository.setFeaturedSightsSaved(true);
                            }
                        });
                    } else {
                        mRepository.updateSights(response);
                    }
                    mRepository.getSightsWithinRange(20, 10, new DataSource.getSightsCallback() {
                        @Override
                        public void onResponse(final List<Sight> sights) {
                            mExploreSights = sights;
                            showExploreListAndHideProgressBar();
                            mSightsFragment.bindExploreSights(mExploreSights);
                            if (mRepository.getNumberOfSightsInDatabase() == 0) {
                                mRepository.saveSights(sights, new DataSource.saveSightsCallbackInDatabase() {
                                    @Override
                                    public void onDataSaved() {
                                        mRepository.setNumberOfSightsInDatabase(sights.size());
                                    }
                                });
                            } else {
                                mRepository.updateSights(sights);
                            }
                        }

                        @Override
                        public void onError(String string) {
                            mSightsFragment.showMessage(string);
                        }
                    });
                }

                @Override
                public void onError(String string) {
                    mSightsFragment.showMessage(string);
                }
            }, true);
        } else {
            mSightsFragment.showConnectionLostMessage();
        }
    }

    @Override
    public void loadMoreSightsWithPagination(int page, final int totalItemsCount) {
        mRepository.loadMoreSightsWithPagination(page, totalItemsCount, new DataSource.getSightsCallback() {
            @Override
            public void onResponse(final List<Sight> sights) {
                mSightsFragment.notifyRecyclerViewWithNewList(sights, totalItemsCount);
                mRepository.updateSights(sights);
            }

            @Override
            public void onError(String string) {
                mSightsFragment.showMessage(string);
            }
        });
    }

    private void showProgressBarsAndHideLists() {
        mSightsFragment.hideExploreSightsList();
        mSightsFragment.hideFeaturedSightsList();
        mSightsFragment.showFeaturedSightsProgressBar();
        mSightsFragment.showExploreSightsProgressBar();
    }
}
