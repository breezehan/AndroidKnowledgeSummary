package com.breezehan.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.IllegalFormatException;

/**
 * Author  hands
 * Description  ContentProvider简单使用
 * Date    2017/8/18 14:36
 * Version
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    public static final String AUTHRORITY = "com.breezehan.ipc.book.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHRORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHRORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHRORITY, "book", BOOK_URI_CODE);//将uri和uricode关联起来
        sUriMatcher.addURI(AUTHRORITY,"user",USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate,current Thread: "+Thread.currentThread().getName());
        mContext  =getContext();
        //初始化数据库，真正使用不能这样写，MainThread
        initProviderData();
        return true;
    }

    private void initProviderData() {
        sqLiteDatabase = new DbOpenHelper(mContext).getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        sqLiteDatabase.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        sqLiteDatabase.execSQL("insert into book values(3,'Android');");
        sqLiteDatabase.execSQL("insert into book values(4,'Ios');");
        sqLiteDatabase.execSQL("insert into book values(5,'Html5');");
        sqLiteDatabase.execSQL("insert into user values(1,'jake',1);");
        sqLiteDatabase.execSQL("insert into user values(5,'Html5',0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query,current Thread: "+Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        return sqLiteDatabase.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        sqLiteDatabase.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri,null);//通知外界，ContentProvider的数据已经改变
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int count = sqLiteDatabase.delete(table, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = sqLiteDatabase.update(table, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
