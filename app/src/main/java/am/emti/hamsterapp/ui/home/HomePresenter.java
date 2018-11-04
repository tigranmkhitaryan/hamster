package am.emti.hamsterapp.ui.home;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import am.emti.hamsterapp.api.ApiService;
import am.emti.hamsterapp.base.BaseView;
import am.emti.hamsterapp.model.Hamster;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tigran Mkhitaryan on 26.10.2018.
 */

public class HomePresenter extends HomeContract.Presenter{
    private ApiService mApiService;
    private HomeContract.View mView;
    @Inject
    public HomePresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void attachView(BaseView view) {
        mView = (HomeContract.View) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    void getHamsters() {
        mApiService.loadHamsters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Hamster>>() {
                    Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onSuccess(List<Hamster> hamsters) {
                        if (mView == null) return;
                        mView.showHamsters(hamsters);
                        Log.e("hamster" , "success");
                        if (!mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        if (!mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }
}
