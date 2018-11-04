package am.emti.hamsterapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
    implements BaseView {

    protected abstract void initViews();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
    }

    public void setTitle(String title) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void showError(String errorMessage) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
