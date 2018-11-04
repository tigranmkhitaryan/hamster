package am.emti.hamsterapp.ui.home;

import java.util.List;

import am.emti.hamsterapp.base.BasePresenter;
import am.emti.hamsterapp.base.BaseView;
import am.emti.hamsterapp.model.Hamster;

/**
 * Created by Tigran Mkhitaryan on 26.10.2018.
 */

public interface HomeContract {
    interface View extends BaseView {
        void showHamsters(List<Hamster> hamsters);;
    }

    abstract class Presenter extends BasePresenter {
        abstract void getHamsters();
    }
}
