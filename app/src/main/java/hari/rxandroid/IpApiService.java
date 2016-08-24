package hari.rxandroid;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hari Prasad on 8/24/16.
 */
public interface IpApiService {
    @GET("/json")
    Call<String> getLocationInfor();
}
