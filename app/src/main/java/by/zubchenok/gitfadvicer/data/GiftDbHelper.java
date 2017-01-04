package by.zubchenok.gitfadvicer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_AGE_MAX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_AGE_MIN;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_IMAGE;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_NAME;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_PRICE_MAX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_PRICE_MIN;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_23_FEB;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_8_MAR;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_ANY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_BIRTHDAY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_NEW_YEAR;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_VALENTINES_DAY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_WEDDING;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_SEX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.SEX_ANY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.TABLE_NAME;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry._ID;


public class GiftDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pets.db";
    public static final int DATABASE_VERSION = 1;

    public GiftDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GIFTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SEX + " INTEGER NOT NULL DEFAULT " + SEX_ANY + ", " +
                COLUMN_AGE_MIN + " INTEGER NOT NULL DEFAULT 0," +
                COLUMN_AGE_MAX + " INTEGER NOT NULL DEFAULT 100, " +
                COLUMN_PRICE_MIN + " INTEGER NOT NULL, " +
                COLUMN_PRICE_MAX + " INTEGER NOT NULL, " +
                COLUMN_IMAGE + " TEXT NOT NULL, " +
                COLUMN_REASON_ANY + " INTEGER NOT NULL DEFAULT 1, " +
                COLUMN_REASON_BIRTHDAY + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_REASON_NEW_YEAR + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_REASON_WEDDING + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_REASON_8_MAR + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_REASON_23_FEB + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_REASON_VALENTINES_DAY + " INTEGER NOT NULL DEFAULT 0" +
                ")";
        db.execSQL(SQL_CREATE_GIFTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void addGiftsToDatabase(List<Gift> gifts) {
        SQLiteDatabase db = getWritableDatabase();
        for (Gift gift : gifts) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COLUMN_NAME, gift.getName());
            initialValues.put(COLUMN_SEX, gift.getSex());
            initialValues.put(COLUMN_AGE_MIN, gift.getAgeMin());
            initialValues.put(COLUMN_AGE_MAX, gift.getAgeMax());
            initialValues.put(COLUMN_PRICE_MIN, gift.getPriceMin());
            initialValues.put(COLUMN_PRICE_MAX, gift.getPriceMax());
            initialValues.put(COLUMN_IMAGE, gift.getImageId());
            initialValues.put(COLUMN_REASON_ANY, gift.getReasonAny());
            initialValues.put(COLUMN_REASON_BIRTHDAY, gift.getReasonBirthday());
            initialValues.put(COLUMN_REASON_NEW_YEAR, gift.getReasonNewYear());
            initialValues.put(COLUMN_REASON_WEDDING, gift.getReasonWedding());
            initialValues.put(COLUMN_REASON_8_MAR, gift.getReason8Mar());
            initialValues.put(COLUMN_REASON_23_FEB, gift.getReason23Feb());
            initialValues.put(COLUMN_REASON_VALENTINES_DAY, gift.getReasonValentinesDay());
            db.insert(TABLE_NAME, null, initialValues);
        }
        db.close();
    }

    public List<Gift> getGiftsFromDatabase(Gift giftToFind) {
        String reasonColumn = "";
        String sex;
        String age;
        String priceMax;

        if (giftToFind.getReasonBirthday() == 1) {
            reasonColumn = COLUMN_REASON_BIRTHDAY;
        } else if (giftToFind.getReasonNewYear() == 1) {
            reasonColumn = COLUMN_REASON_NEW_YEAR;
        } else if (giftToFind.getReason8Mar() == 1) {
            reasonColumn = COLUMN_REASON_8_MAR;
        } else if (giftToFind.getReason23Feb() == 1) {
            reasonColumn = COLUMN_REASON_23_FEB;
        } else if (giftToFind.getReasonValentinesDay() == 1) {
            reasonColumn = COLUMN_REASON_VALENTINES_DAY;
        } else if (giftToFind.getReasonWedding() == 1) {
            reasonColumn = COLUMN_REASON_WEDDING;
        }

        sex = String.valueOf(giftToFind.getSex());
        age = String.valueOf(giftToFind.getAge());
        priceMax = String.valueOf(giftToFind.getPriceMax());


        List<Gift> giftsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                "(" + COLUMN_SEX + "=? OR " + COLUMN_SEX + "=-1) AND " +
                        COLUMN_AGE_MAX + ">=? AND " +
                        COLUMN_AGE_MIN + "<=? AND " +
                        COLUMN_PRICE_MAX + "<=? AND " +
                        "(" + reasonColumn + "=1 OR " + COLUMN_REASON_ANY + "=1)",
                new String[]{sex, age, age, priceMax},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Gift gift = new Gift();
                gift.setId(cursor.getInt(0));
                gift.setName(cursor.getString(1));
                gift.setSex(cursor.getInt(2));
                gift.setAgeMin(cursor.getInt(3));
                gift.setAgeMax(cursor.getInt(4));
                gift.setPriceMin(cursor.getInt(5));
                gift.setPriceMax(cursor.getInt(6));
                gift.setImageId(cursor.getString(7));
                gift.setReasonAny(cursor.getInt(8));
                gift.setReasonBirthday(cursor.getInt(9));
                gift.setReasonNewYear(cursor.getInt(10));
                gift.setReasonWedding(cursor.getInt(11));
                gift.setReason8Mar(cursor.getInt(12));
                gift.setReason23Feb(cursor.getInt(13));
                gift.setReasonValentinesDay(cursor.getInt(14));
                giftsList.add(gift);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return giftsList;
    }

    public boolean isGiftDatabaseEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        boolean isEmpty = true;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            isEmpty = (cursor.getInt(0) == 0);
            cursor.close();
        }
        db.close();
        return isEmpty;
    }
}
