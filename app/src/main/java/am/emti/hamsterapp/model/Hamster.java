package am.emti.hamsterapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tigran Mkhitaryan on 26.10.2018.
 */

public class Hamster implements Parcelable{
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("image")
    private String mImageUrl;
    @SerializedName("pinned")
    private boolean mIsPinned;

    private byte[] mImageArray;


    protected Hamster(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mImageUrl = in.readString();
        mIsPinned = in.readByte() != 0;
        mImageArray = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mImageUrl);
        dest.writeByte((byte) (mIsPinned ? 1 : 0));
        dest.writeByteArray(mImageArray);
    }

    public static final Creator<Hamster> CREATOR = new Creator<Hamster>() {
        @Override
        public Hamster createFromParcel(Parcel in) {
            return new Hamster(in);
        }

        @Override
        public Hamster[] newArray(int size) {
            return new Hamster[size];
        }
    };

    public Hamster(String title, String description, byte[] imageArray) {
        mTitle = title;
        mDescription = description;
        mImageArray = imageArray;
    }

    public Hamster(){

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public boolean isPinned() {
        return mIsPinned;
    }

    public byte[] getImageArray() {
        return mImageArray;
    }

    public void setPinned(boolean pinned) {
        mIsPinned = pinned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
