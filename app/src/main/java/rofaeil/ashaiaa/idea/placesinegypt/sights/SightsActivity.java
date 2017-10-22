package rofaeil.ashaiaa.idea.placesinegypt.sights;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.Repository;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.local.LocalDataSource;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.remote.RemoteDataSource;

public class SightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);

        SightsFragment sightsFragment = (SightsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.sights_fragment);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(getApplicationContext()),
                LocalDataSource.getInstance(getApplicationContext()) ,
                getApplicationContext());

//        SightsPresenter.getInstance(sightsFragment, repository);

        new SightsPresenter(sightsFragment, repository);
    }
}
