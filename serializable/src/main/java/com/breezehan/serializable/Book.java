package com.breezehan.serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author  hands
 * Description
 * Date    2017/8/9 14:25
 * Version
 */

public class Book implements Parcelable {
    private String name;
    private int count;

    protected Book(Parcel in) {
        name = in.readString();
        count = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(count);
    }
}
