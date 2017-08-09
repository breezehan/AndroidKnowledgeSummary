package com.breezehan.serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author  hands
 * Description
 * Date    2017/8/9 14:14
 * Version
 */

public class People implements Parcelable {
    private int age;
    private String name;
    private boolean isMale;
    private Book book;


    protected People(Parcel in) {
        age = in.readInt();
        name = in.readString();
        isMale = in.readByte() != 0;
        book = in.readParcelable(Book.class.getClassLoader());
    }
    //反序列化
    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    //序列化过程
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(book, flags);
    }
}
