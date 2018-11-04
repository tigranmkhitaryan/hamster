package am.emti.hamsterapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import am.emti.hamsterapp.Application;
import am.emti.hamsterapp.R;
import am.emti.hamsterapp.base.BaseFragment;
import am.emti.hamsterapp.database.DatabaseAsyncTask;
import am.emti.hamsterapp.database.DatabaseHelper;
import am.emti.hamsterapp.model.Hamster;
import am.emti.hamsterapp.ui.activity.HamsterDetailsActivity;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeContract.View, HamstersAdapter.HamstersItemListners{
    @Inject
    HomePresenter mPresenter;
    private Context mContext;

    @BindView(R.id.activity_hamsters_recycler)
    RecyclerView mHamstersRecyler;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance( ) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Application.getAppComponent().inject(this);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    protected void initViews() {
        Log.e("init views" , "asfafawf");
        mPresenter.getHamsters();

        Log.e("init views" , "asfafawf");
    }

    @Override
    public void showError(String errorMessage) {
        initHamstersRecycler();
        Log.e("in error", ">>>>>>");
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Log.e("hamsters" , helper.getHamsters().size() + " ");
        ((HamstersAdapter) mHamstersRecyler.getAdapter()).setData(helper.getHamsters() , true);
    }



    private void initHamstersRecycler(){
        HamstersAdapter adapter = new HamstersAdapter(this);
        mHamstersRecyler.setAdapter(adapter);
        mHamstersRecyler.setHasFixedSize(true);
        mHamstersRecyler.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onShareButtonClicked(Hamster hamster , ImageView imageView) {
        Log.e("share button" , "share");
          shareContent( imageView , hamster.getTitle());
    }

    @Override
    public void onItemClicked(Hamster hamster , ImageView hamsterImage) {
        Intent intent = new Intent(mContext, HamsterDetailsActivity.class);
        intent.putExtra(HamsterDetailsActivity.EXTRA_POSITION, hamster);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((AppCompatActivity) mContext, (View)hamsterImage, "hamster_image");
        startActivity(intent , options.toBundle());
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void showHamsters(List<Hamster> hamsters) {
        Log.e("name" , hamsters.get(0).getDescription());
        initHamstersRecycler();
        new DatabaseHelper(mContext).deleteAllHamsters();
        for (Hamster h : hamsters){
            new DatabaseAsyncTask(h , mContext).execute();
        }
        ((HamstersAdapter) mHamstersRecyler.getAdapter()).setData(hamsters);
    }

    private void shareContent(ImageView imageView , String hamsterName ){

        Bitmap bitmap = getBitmapFromView(imageView);
        try {
            File file = new File(mContext.getExternalCacheDir(),"hamster.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Intent.EXTRA_TEXT, hamsterName);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public void filterList(String query){
        ((HamstersAdapter)mHamstersRecyler.getAdapter()).filterData(query);

    }

}
