package am.emti.hamsterapp.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void hideKeyboard() {
        if (this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

       public void showSoftKeyboard(View view) {
        Log.i(TAG, "setFocusOnEditText: ");
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            Log.i(TAG, "hideSoftKeyboard: ");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        }
    }


    public void replaceFragment(int idContainer, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(idContainer, fragment, fragment.getClass().getName().toString()).commit();
    }

}
