package am.emti.hamsterapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import am.emti.hamsterapp.model.Hamster;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "hamster_db";
    private static final String TABLE_HAMSTERS = "hamsters";
    private static final String COL_1_HAMSTER_TITLE = "title";
    private static final String COL_2_HAMSTER_DESCRIPTION = "description";
    private static final String COL_3_HAMSTER_IMAGE = "image";
    private SQLiteDatabase mSQLiteDatabase;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mSQLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + TABLE_HAMSTERS +
                "(" + COL_1_HAMSTER_TITLE + " TEXT ," + COL_2_HAMSTER_DESCRIPTION + " TEXT" + ", " + COL_3_HAMSTER_IMAGE + " BLOB" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HAMSTERS);
        onCreate(sqLiteDatabase);
    }

    void insertHamster(Hamster hamster, Bitmap b) {
            ContentValues values = new ContentValues();
            values.put(COL_1_HAMSTER_TITLE, hamster.getTitle());
            values.put(COL_2_HAMSTER_DESCRIPTION, hamster.getDescription());
            values.put(COL_3_HAMSTER_IMAGE , getByteArrayFromBitmap(b));
            mSQLiteDatabase.insert(TABLE_HAMSTERS, null, values) ;
    }

    private byte[] getByteArrayFromBitmap(Bitmap photo) {
        if(photo == null)
            return null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public List<Hamster> getHamsters() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Hamster> hamsters = new ArrayList<>(15);
        Cursor cursor = db.rawQuery("Select * from " + TABLE_HAMSTERS, null);
        if (cursor.moveToFirst()) {
            int titleId = cursor.getColumnIndex(COL_1_HAMSTER_TITLE);
            int descriptionId = cursor.getColumnIndex(COL_2_HAMSTER_DESCRIPTION);
            int imageId = cursor.getColumnIndex(COL_3_HAMSTER_IMAGE);

            do {
                String title = cursor.getString(titleId);
                String description = cursor.getString(descriptionId);
                byte[] image = cursor.getBlob(imageId);
                hamsters.add(new Hamster(title, description, image));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return hamsters;
    }

    public void deleteAllHamsters() {
        mSQLiteDatabase.execSQL("delete from " + TABLE_HAMSTERS);
    }

}
