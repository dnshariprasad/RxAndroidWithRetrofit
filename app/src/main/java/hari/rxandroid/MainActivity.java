package hari.rxandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView tv_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_output = (TextView) findViewById(R.id.tv_output);
        getIpApiObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        tv_output.setText(s);
                    }
                });
    }

    @Nullable
    private String getIpApi() throws IOException {
        Retrofit ipApiRetrofit = new Retrofit.Builder()
                .baseUrl("http://ip-api.com")
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpApiService ipApiService = ipApiRetrofit.create(IpApiService.class);
        Response<String> response = ipApiService.getLocationInfor().execute();
        if (response.isSuccessful()) return response.body();
        else return null;
    }

    private Observable<String> getIpApiObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                try {
                    return rx.Observable.just(getIpApi());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }
}
