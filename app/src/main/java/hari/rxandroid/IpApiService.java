package hari.rxandroid;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Hari Prasad on 8/24/16.
 */
public interface IpApiService {
    @GET("/json")
    Observable<String> getLocationInfo();
}
