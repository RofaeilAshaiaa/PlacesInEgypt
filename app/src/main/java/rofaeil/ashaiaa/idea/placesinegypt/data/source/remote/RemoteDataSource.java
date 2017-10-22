package rofaeil.ashaiaa.idea.placesinegypt.data.source.remote;

import android.content.Context;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.data.source.DataSource;

import static rofaeil.ashaiaa.idea.placesinegypt.data.source.remote.SightsApiService.BASE_URL;

/**
 * Implementation of the data source that handles data from remote server
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    private static SightsApiService mSightsApiService;
    //this context reference to application context to prevent any memory leaks
    private Context mContext;

    private RemoteDataSource(Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().addInterceptor(logging);

        OkHttpClient httpClient = builder.build();

        //since RemoteDataSource is a singleton the mSightsApiService
        //is also a singleton as building retrofit should happens once
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        mSightsApiService = retrofit.create(SightsApiService.class);

        mContext = context;
    }

    /**
     * creates RemoteDataSource if it's not created and return it
     *
     * @return returns {@link RemoteDataSource} object
     */
    public static RemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getFeaturedSights(final getSightsCallback callback, boolean isNetworkAvilabale) {
        Call<List<Sight>> call = mSightsApiService.getFeaturedSights();
        call.enqueue(new Callback<List<Sight>>() {
            @Override
            public void onResponse(Call<List<Sight>> call, Response<List<Sight>> response) {
                int responseCode = response.code();
                if (responseCode >= 200 && responseCode < 300) {
                    final List<Sight> sights = response.body();
                    if (sights != null)
                        callback.onResponse(sights);
                } else if (responseCode >= 400 && responseCode < 600)
                    callback.onError(mContext.getString(R.string.server_error));
            }

            @Override
            public void onFailure(Call<List<Sight>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getSightsWithinRange(int count, int from, final getSightsCallback callback) {

        Call<List<Sight>> call = mSightsApiService.exploreSights(count, from);
        call.enqueue(new Callback<List<Sight>>() {
            @Override
            public void onResponse(Call<List<Sight>> call, Response<List<Sight>> response) {

                int responseCode = response.code();
                if (responseCode >= 200 && responseCode < 300) {
                    final List<Sight> sights = response.body();
                    if (sights != null)
                        callback.onResponse(sights);
                } else if (responseCode >= 400 && responseCode < 600)
                    callback.onError(mContext.getString(R.string.server_error));

            }

            @Override
            public void onFailure(Call<List<Sight>> call, Throwable t) {
                callback.onError(mContext.getString(R.string.failed_to_connect_to_server));

            }
        });
    }

    @Override
    public void saveSights(List<Sight> sights, saveSightsCallbackInDatabase callback) {
        //only implemented in local data source
    }

    @Override
    public void updateSights(List<Sight> sights) {
        //only implemented in local data source
    }

    @Override
    public void getSight(int sightId, getSightCallback callback) {
        //only implemented in local data source
    }

    @Override
    public boolean isFirstRun() {
        //only implemented in local data source
        return false;
    }

    @Override
    public void setFirstRun(boolean firstRun) {
        //only implemented in local data source
    }

    @Override
    public boolean isFeaturedSightsSaved() {
        //only implemented in local data source
        return false;
    }

    @Override
    public void setFeaturedSightsSaved(boolean featuredSightsSaved) {
        //only implemented in local data source
    }

    @Override
    public void loadMoreSightsWithPagination(int page, int totalItemsCount, final getSightsCallback callback) {
        Call<List<Sight>> call =
                mSightsApiService.exploreSights(20, totalItemsCount);
        call.enqueue(new Callback<List<Sight>>() {
            @Override
            public void onResponse(Call<List<Sight>> call, Response<List<Sight>> response) {
                int responseCode = response.code();
                if (responseCode >= 200 && responseCode < 300) {
                    final List<Sight> sights = response.body();
                    if (sights != null)
                        callback.onResponse(sights);
                } else if (responseCode >= 400 && responseCode < 600)
                    callback.onError(mContext.getString(R.string.server_error));
            }

            @Override
            public void onFailure(Call<List<Sight>> call, Throwable t) {
                callback.onError(mContext.getString(R.string.failed_to_connect_to_server));
            }
        });
    }

    @Override
    public int getNumberOfSightsInDatabase() {
        //only implemented in local data source
        return 0;
    }

    @Override
    public void setNumberOfSightsInDatabase(int sightsInDatabase) {
        //only implemented in local data source
    }
}
