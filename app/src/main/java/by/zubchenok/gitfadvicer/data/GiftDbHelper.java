package by.zubchenok.gitfadvicer.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class GiftDbHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "gifts.db";
    public static final int DATABASE_VERSION = 1;

    public GiftDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
