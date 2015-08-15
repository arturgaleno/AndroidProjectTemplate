package br.com.app.template;

import android.app.Application;

import com.google.gson.Gson;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import java.util.concurrent.TimeUnit;

import br.com.app.template.utils.ACRASender;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by artur on 05/08/15.
 */
@ReportsCrashes(
        formUri = ""
)
@EApplication
public class AppApplication extends Application {

    @Bean
    ACRASender acraSender;

    private TwitterService twitterService;

    @AfterInject
    public void init() {
        ACRA.init(this);
        ACRA.getErrorReporter().setReportSender(acraSender);
        LeakCanary.install(this);

        //FOR SAMPLE PURPOSE
        OkHttpClient okHttpClient = new OkHttpClient()
                .setCache(new Cache(getCacheDir(), 1024 * 1024 * 10));
        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
        okHttpClient.setRetryOnConnectionFailure(true);
        okHttpClient.setWriteTimeout(1, TimeUnit.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES);

        twitterService = new RestAdapter.Builder()
                .setEndpoint("https://api.twitter.com")
                .setConverter(new GsonConverter(new Gson(), "UTF-8"))
                .setClient(new OkClient(okHttpClient))
                .build()
                .create(TwitterService.class);

    }

    public TwitterService getTwitterService() {
        return twitterService;
    }
}
