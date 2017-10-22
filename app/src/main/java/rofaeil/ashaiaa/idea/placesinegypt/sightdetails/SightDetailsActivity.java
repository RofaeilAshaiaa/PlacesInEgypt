package rofaeil.ashaiaa.idea.placesinegypt.sightdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.Image;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.Repository;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.local.LocalDataSource;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.remote.RemoteDataSource;

public class SightDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_details);

        SightDetailsFragment sightDetailsFragment = (SightDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.sight_details_fragment);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(getApplicationContext()),
                LocalDataSource.getInstance(getApplicationContext()),
                getApplicationContext());
        Intent intent = getIntent();
        int sightId = -1;
        int price = 0;
        String description = "";
        String url = "";
        if (intent.hasExtra(getString(R.string.EXTRA_SIGHT_ID)))
            sightId = intent.getIntExtra(getString(R.string.EXTRA_SIGHT_ID), -1);

        if (intent.hasExtra(getString(R.string.EXTRA_SIGHT_DESCRITPION)))
            description = intent.getStringExtra(getString(R.string.EXTRA_SIGHT_DESCRITPION));

        if (intent.hasExtra(getString(R.string.EXTRA_SIGHT_IMAGE_URL)))
            url = intent.getStringExtra(getString(R.string.EXTRA_SIGHT_IMAGE_URL));

        if (intent.hasExtra(getString(R.string.EXTRA_SIGHT_PRICE)))
            price = intent.getIntExtra(getString(R.string.EXTRA_SIGHT_PRICE) ,0);

//        SightDetailsPresenter.getInstance(sightDetailsFragment, repository,chosenSightId);

        Sight sight = new Sight(sightId, description, new Image(url) , price);
        new SightDetailsPresenter(sightDetailsFragment, repository, sight);
    }
}
