package am.emti.hamsterapp.ui.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import am.emti.hamsterapp.Application;
import am.emti.hamsterapp.R;
import am.emti.hamsterapp.base.BaseActivity;
import am.emti.hamsterapp.ui.about.AboutFragment;
import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.activity_home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private HomeFragment mHomeFragment;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Application.getAppComponent().inject(this);
        configureToolbar();
        configureNavigationDrawer();
        initDefaultFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initDefaultFragment() {
        mHomeFragment = HomeFragment.newInstance();
        replaceFragment(R.id.activity_home_fragment_container, mHomeFragment);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_48dp);
        }
    }

    private void configureNavigationDrawer() {
        NavigationView navView = findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.nav_about_item:
                    replaceFragment(R.id.activity_home_fragment_container, AboutFragment.newInstance());
                    mToolbar.getMenu().findItem(R.id.action_search).setVisible(false);
                    break;
                case R.id.nav_hamsters_list_item:
                    replaceFragment(R.id.activity_home_fragment_container, mHomeFragment);
                    mToolbar.getMenu().findItem(R.id.action_search).setVisible(true);
                    break;

            }
            drawerLayout.closeDrawers();
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            // Android home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mHomeFragment != null)
                    mHomeFragment.filterList(s);
                return false;
            }
        });
        return true;
    }
}
