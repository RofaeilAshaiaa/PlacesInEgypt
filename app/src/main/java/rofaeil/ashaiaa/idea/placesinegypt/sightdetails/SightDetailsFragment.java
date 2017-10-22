package rofaeil.ashaiaa.idea.placesinegypt.sightdetails;


import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.databinding.FragmentSightDetailsBinding;

/**
 * Main UI for sight details screen
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class SightDetailsFragment extends Fragment implements SightDetailsContract.View {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private SightDetailsContract.Presenter mPresenter;
    private FragmentSightDetailsBinding mBinding;
    private Bitmap mBitmap;

    public SightDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sight_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void setPresenter(SightDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_image:

                BitmapDrawable draw = (BitmapDrawable) mBinding.imageOfSight.getDrawable();
                mBitmap = draw.getBitmap();
                int permission = ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.userClickedSaveImage();
                } else {
                    askUserForStoragePermission(permission);
                }
                break;

            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void setSightDetailsToView(Sight mSight) {

        mBinding.descriptionTv.setText(mSight.getPlaceDescription());
        mBinding.sightPriceTv.setText(mSight.getPrice() + "$");

        String imageUrl = mSight.getImage().getUrl();

        //there is something wrong with the images url sent by the api,
        // they don't load successfully, despite the code works perfect with another url

        Picasso.with(getContext())
//                .load(imageUrl)
                .load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.drawable.ic_autorenew_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(mBinding.imageOfSight);
    }

    @Override
    public void saveImageToStorage() {
        SaveImageToStorageTask task =
                new SaveImageToStorageTask(new WeakReference<>(getContext()));
        task.execute(mBitmap);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SightDetailsActivity activity = (SightDetailsActivity) getActivity();
        mBinding.toolbar.setTitle("");
        activity.setSupportActionBar(mBinding.toolbar);
        if (null != activity.getSupportActionBar()) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public void askUserForStoragePermission(int permission) {
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            requestPermissions(PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.userClickedSaveImage();
                break;
            } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "You Must Grant Permission to Download the Image",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

}
