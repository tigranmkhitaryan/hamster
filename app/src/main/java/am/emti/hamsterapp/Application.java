package am.emti.hamsterapp;


import am.emti.hamsterapp.dagger.AppComponent;
import am.emti.hamsterapp.dagger.AppModule;
import am.emti.hamsterapp.dagger.DaggerAppComponent;

public class Application extends android.app.Application {

    private static AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = createComponent();
    }

    private AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mApplicationComponent;
    }
}
