package am.emti.hamsterapp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import am.emti.hamsterapp.R;
import am.emti.hamsterapp.model.Hamster;

public class HamsterDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    private Hamster mHamster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamster_details);
        mHamster = getIntent().getParcelableExtra(EXTRA_POSITION);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initHamsterInfo();
    }

    private void initHamsterInfo(){
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mHamster.getTitle());
        ImageView hamsterPicutre = findViewById(R.id.activity_hamster_details_image);
        if(mHamster.getImageArray() == null)
            Glide.with(this).load(mHamster.getImageUrl()).into(hamsterPicutre);
        else
            hamsterPicutre.setImageBitmap(convertByteArrayToBitmap(mHamster.getImageArray()));
        TextView hamsterDetails = findViewById(R.id.activity_hamster_details_description);
        hamsterDetails.setText(mHamster.getDescription());
    }


    private Bitmap convertByteArrayToBitmap(byte[] bitmapdata){
        return   BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
