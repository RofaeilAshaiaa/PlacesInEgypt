package rofaeil.ashaiaa.idea.placesinegypt.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * this API Service defines all api network operations
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

public interface SightsApiService {

    String BASE_URL = "http://grapes-n-berries.getsandbox.com/sightsofegypt/";
    String CONTENT_TYPE_LABEL = "Content-Type";
    String CONTENT_TYPE_VALUE_JSON = "application/json";
    String HEADER_1 = CONTENT_TYPE_LABEL + ": " + CONTENT_TYPE_VALUE_JSON;

    /**
     * gets 10 featured sights available from server
     *
     * @return list of {@link Sight}
     */
    @Headers(HEADER_1)
    @GET("featured")
    Call<List<Sight>> getFeaturedSights();

    /**
     * explore sights available from server
     *
     * @param count number of sights to return
     * @param from start getting sights from given integer
     * @return list of {@link Sight}
     */
    @Headers(HEADER_1)
    @GET("explore")
    Call<List<Sight>> exploreSights(@Query("count") int count , @Query("from") int from);

}
