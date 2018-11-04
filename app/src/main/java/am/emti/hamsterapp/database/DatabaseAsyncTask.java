package am.emti.hamsterapp.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.util.List;

import am.emti.hamsterapp.model.Hamster;

/**
 * Created by Tigran Mkhitaryan on 04.11.2018.
 */

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Bitmap> {
    private Hamster mHamster;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    public DatabaseAsyncTask(Hamster hamster , Context context) {
        super();
        mHamster = hamster;
        mContext = context;
    }

    public Bitmap  doInBackground(Void... params) {
        String urldisplay = mHamster.getImageUrl();
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        DatabaseHelper helper = new DatabaseHelper(mContext);
        helper.insertHamster(mHamster , bitmap);
        mHamster = null;
        mContext = null;

    }
}