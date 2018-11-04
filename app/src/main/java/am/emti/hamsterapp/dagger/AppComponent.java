package am.emti.hamsterapp.dagger;

import javax.inject.Singleton;

import am.emti.hamsterapp.ui.home.HomeActivity;
import am.emti.hamsterapp.ui.WelcomeActivity;
import am.emti.hamsterapp.ui.home.HomeFragment;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    void inject(HomeActivity activity);
    void inject(WelcomeActivity activity);
    void inject (HomeFragment fragment);
}
