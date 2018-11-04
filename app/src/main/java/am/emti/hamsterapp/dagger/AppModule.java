package am.emti.hamsterapp.dagger;

import android.content.Context;
import android.os.Build;

import javax.inject.Singleton;

import am.emti.hamsterapp.Application;
import am.emti.hamsterapp.api.ApiService;
import am.emti.hamsterapp.api.interceptor.HeadersInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Application mApp;
    public static String BASE_URL =  "https://unrealmojo.com/porn/";
    public AppModule(Application app) {
        mApp = app;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createClient())
                .build();
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {

        return retrofit.create(ApiService.class);
    }


    private static OkHttpClient createClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new HeadersInterceptor());
        return okHttpClient.build();
    }
}
