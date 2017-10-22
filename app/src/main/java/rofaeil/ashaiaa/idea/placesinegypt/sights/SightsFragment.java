package rofaeil.ashaiaa.idea.placesinegypt.sights;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.databinding.FragmentSightsBinding;
import rofaeil.ashaiaa.idea.placesinegypt.recivers.NetworkStateChecker;
import rofaeil.ashaiaa.idea.placesinegypt.sightdetails.SightDetailsActivity;

/**
 * Main UI for sights screen
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class SightsFragment extends Fragment implements FeaturedSightsAdapter.FeaturedListItemClickListener,
        SightsContract.View, ExploreSightsAdapter.ExploreListItemClickListener {

    ExploreSightsAdapter mExploreSightsAdapter;
    private SightsContract.Presenter mPresenter;
    private FragmentSightsBinding mBinding;
    private NetworkStateChecker mNetworkStateChecker;
    private Snackbar mSnackbar;

    public SightsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sights, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void setPresenter(SightsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void bindFeaturedSights(List<Sight> featuredSights) {


        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBinding.featuredSightsRv.setHasFixedSize(true);
        mBinding.featuredSightsRv.setLayoutManager(mLayoutManager);
        mBinding.featuredSightsRv.setAdapter(new FeaturedSightsAdapter(this, featuredSights, getContext()));

    }

    @Override
    public void bindExploreSights(List<Sight> exploreSights) {
        mExploreSightsAdapter = new ExploreSightsAdapter(this, exploreSights, getContext());
        mBinding.exploreSightsRv.setAdapter(mExploreSightsAdapter);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBinding.exploreSightsRv.addOnScrollListener(
                new EndlessRecyclerViewScrollListener(mStaggeredGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mPresenter.loadMoreSightsWithPagination(page, totalItemsCount);
                    }
                });
        mBinding.exploreSightsRv.setLayoutManager(mStaggeredGridLayoutManager);
    }

    @Override
    public void openTargetActivity(Sight sight) {
        Intent intent = new Intent(getActivity(), SightDetailsActivity.class);
        intent.putExtra(getString(R.string.EXTRA_SIGHT_ID), sight.getId());
        intent.putExtra(getString(R.string.EXTRA_SIGHT_DESCRITPION), sight.getPlaceDescription());
        intent.putExtra(getString(R.string.EXTRA_SIGHT_IMAGE_URL), sight.getImage().getUrl());
        intent.putExtra(getString(R.string.EXTRA_SIGHT_PRICE), sight.getPrice());
        startActivity(intent);
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showMessage(String message) {
        mSnackbar = Snackbar.make(mBinding.coordinator, message, Snackbar.LENGTH_LONG);
        final Snackbar snackbar = mSnackbar;
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        mSnackbar.show();
    }

    @Override
    public void showConnectionLostMessage() {
        showMessage(getString(R.string.there_is_no_internet_connection));
    }

    @Override
    public void showConnectedAgainMessage() {
        showMessage(getString(R.string.you_are_connected_again_message));
    }

    @Override
    public void hideFeaturedSightsProgressBar() {
        mBinding.featuredSightsPb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideExploreSightsProgressBar() {
        mBinding.exploreSightsPb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFeaturedSightsProgressBar() {
        mBinding.featuredSightsPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showExploreSightsProgressBar() {
        mBinding.exploreSightsPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showExploreSightsList() {
        mBinding.exploreSightsRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFeaturedSightsList() {
        mBinding.featuredSightsRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFeaturedSightsList() {
        mBinding.featuredSightsRv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideExploreSightsList() {
        mBinding.exploreSightsRv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyRecyclerViewWithNewList(List<Sight> response, int totalItemsCount) {
        mExploreSightsAdapter.notifyItemRangeChanged(
                mExploreSightsAdapter.getItemCount(), mExploreSightsAdapter.getItemCount() + 20);
//        adapter.notifyItemRangeInserted(curSize, newItems.size());
    }

    @Override
    public void onExploreListItemClicked(int clickedItemIndex) {
        mPresenter.SightClicked(clickedItemIndex, false);
    }

    @Override
    public void onFeaturedListItemClicked(int clickedItemIndex) {
        mPresenter.SightClicked(clickedItemIndex, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mNetworkStateChecker == null)
                mNetworkStateChecker = new NetworkStateChecker();
            getActivity().getApplicationContext().registerReceiver(
                    mNetworkStateChecker,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getApplicationContext().unregisterReceiver(mNetworkStateChecker);
        }
    }
}

