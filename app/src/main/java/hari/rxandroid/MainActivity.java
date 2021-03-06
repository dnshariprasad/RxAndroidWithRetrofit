package hari.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView tv_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_output = (TextView) findViewById(R.id.tv_output);

        Retrofit ipApiRetrofit = new Retrofit.Builder()
                .baseUrl("http://ip-api.com")
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IpApiService ipApiService = ipApiRetrofit.create(IpApiService.class);

        ipApiService.getLocationInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> tv_output.setText(s),
                        throwable -> Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show(),
                        () -> Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_SHORT).show());
    }
}
