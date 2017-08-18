package com.breezehan.ipc.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.breezehan.ipc.Book;
import com.breezehan.ipc.R;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse("content://com.breezehan.ipc.book.provider/book");//BOOK_CONTENT_URI
        ContentValues values = new ContentValues();
        values.put("_id", "6");
        values.put("name","程序设计的艺术");

        getContentResolver().insert(bookUri, values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book(bookCursor.getInt(0),bookCursor.getString(1));
            Log.d(TAG, "query book:"+book.toString());
        }
        bookCursor.close();
    }
}
