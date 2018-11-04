package am.emti.hamsterapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import am.emti.hamsterapp.R;
import am.emti.hamsterapp.base.BaseActivity;
import am.emti.hamsterapp.ui.home.HomeActivity;
import am.emti.hamsterapp.ui.view.TintableImageView;
import butterknife.BindView;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.activity_welcome_continue_image)
    TintableImageView mContinueImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        addListeners();
    }

    private void addListeners(){
        ViewTreeObserver vto = mContinueImage.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
               animateImageFalling(countImageBottomPosition(mContinueImage.getMeasuredHeight()));
            }
        });
        mContinueImage.setOnClickListener(this);
    }

    private void animateImageFalling(float translationY){
        mContinueImage.animate()
                .translationY(translationY)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(4000);
    }

    private float countImageBottomPosition(int viewHeight){
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        return screenHeight - statusBarHeight - viewHeight;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_welcome_continue_image:
              startActivity(new Intent(this,HomeActivity.class));
            break;
        }
    }
}
