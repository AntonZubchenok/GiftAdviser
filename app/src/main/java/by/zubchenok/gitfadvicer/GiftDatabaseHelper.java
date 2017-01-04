package by.zubchenok.gitfadvicer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GiftDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gifts.db";
    public static final String TABLE_GIFTS = "gifts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SEX = "sex";
    public static final String KEY_AGE_MIN = "age_min";
    public static final String KEY_AGE_MAX = "age_max";
    public static final String KEY_PRICE_MIN = "price_min";
    public static final String KEY_PRICE_MAX = "price_max";
    public static final String KEY_IMAGE = "image";

    public static final String KEY_REASON_ANY = "reason_any";
    public static final String KEY_REASON_BIRTHDAY = "reason_birthday";
    public static final String KEY_REASON_NEW_YEAR = "reason_new_year";
    public static final String KEY_REASON_WEDDING = "reason_wedding";
    public static final String KEY_REASON_8_MAR = "reason_8_mar";
    public static final String KEY_REASON_23_FEB = "reason_23_feb";
    public static final String KEY_REASON_VALENTINES_DAY = "reason_valentines_day";

    private static final String CREATE_TABLE_GIFTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_GIFTS + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_SEX + " INTEGER NOT NULL DEFAULT -1, " +
                    KEY_AGE_MIN + " INTEGER NOT NULL," +
                    KEY_AGE_MAX + " INTEGER NOT NULL," +
                    KEY_PRICE_MIN + " INTEGER_NOT NULL," +
                    KEY_PRICE_MAX + " INTEGER NOT NULL," +
                    KEY_IMAGE + " TEXT NOT NULL," +
                    KEY_REASON_ANY + " INTEGER NOT NULL DEFAULT 1," +
                    KEY_REASON_BIRTHDAY + " INTEGER NOT NULL DEFAULT 0," +
                    KEY_REASON_NEW_YEAR + " INTEGER NOT NULL DEFAULT 0," +
                    KEY_REASON_WEDDING + " INTEGER NOT NULL DEFAULT 0," +
                    KEY_REASON_8_MAR + " INTEGER NOT NULL DEFAULT 0," +
                    KEY_REASON_23_FEB + " INTEGER NOT NULL DEFAULT 0," +
                    KEY_REASON_VALENTINES_DAY + " INTEGER NOT NULL DEFAULT 0" +
                    ")";


    public GiftDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addGiftsToDatabase(List<Gift> gifts) {
        SQLiteDatabase db = getWritableDatabase();
        for (Gift gift : gifts) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_NAME, gift.getName());
            initialValues.put(KEY_SEX, gift.getSex());
            initialValues.put(KEY_AGE_MIN, gift.getAgeMin());
            initialValues.put(KEY_AGE_MAX, gift.getAgeMax());
            initialValues.put(KEY_PRICE_MIN, gift.getPriceMin());
            initialValues.put(KEY_PRICE_MAX, gift.getPriceMax());
            initialValues.put(KEY_IMAGE, gift.getImageId());
            initialValues.put(KEY_REASON_ANY, gift.getReasonAny());
            initialValues.put(KEY_REASON_BIRTHDAY, gift.getReasonBirthday());
            initialValues.put(KEY_REASON_NEW_YEAR, gift.getReasonNewYear());
            initialValues.put(KEY_REASON_WEDDING, gift.getReasonWedding());
            initialValues.put(KEY_REASON_8_MAR, gift.getReason8Mar());
            initialValues.put(KEY_REASON_23_FEB, gift.getReason23Feb());
            initialValues.put(KEY_REASON_VALENTINES_DAY, gift.getReasonValentinesDay());
            db.insert(TABLE_GIFTS, null, initialValues);
        }
        db.close();
    }

    public List<Gift> getGiftsFromDatabase(Gift giftToFind) {
        String reasonColumn = "";
        String sex;
        String age;
        String priceMax;

        if (giftToFind.getReasonBirthday() == 1) {
            reasonColumn = KEY_REASON_BIRTHDAY;
        } else if (giftToFind.getReasonNewYear() == 1) {
            reasonColumn = KEY_REASON_NEW_YEAR;
        } else if (giftToFind.getReason8Mar() == 1) {
            reasonColumn = KEY_REASON_8_MAR;
        } else if (giftToFind.getReason23Feb() == 1) {
            reasonColumn = KEY_REASON_23_FEB;
        } else if (giftToFind.getReasonValentinesDay() == 1) {
            reasonColumn = KEY_REASON_VALENTINES_DAY;
        } else if (giftToFind.getReasonWedding() == 1) {
            reasonColumn = KEY_REASON_WEDDING;
        }

        sex = String.valueOf(giftToFind.getSex());
        age = String.valueOf(giftToFind.getAge());
        priceMax = String.valueOf(giftToFind.getPriceMax());


        List<Gift> giftsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_GIFTS, null,
                        "(" + KEY_SEX + "=? OR " + KEY_SEX + "=-1) AND " +
                        KEY_AGE_MAX + ">=? AND " +
                        KEY_AGE_MIN + "<=? AND " +
                        KEY_PRICE_MAX + "<=? AND " +
                        "(" + reasonColumn + "=1 OR " + KEY_REASON_ANY + "=1)",
                new String[]{sex, age, age, priceMax},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Gift gift = new Gift();
                gift.setName(cursor.getString(1));
                gift.setSex(cursor.getInt(2));
                gift.setAgeMin(cursor.getInt(3));
                gift.setAgeMax(cursor.getInt(4));
                gift.setPriceMin(cursor.getInt(5));
                gift.setPriceMax(cursor.getInt(6));
                gift.setImageId(cursor.getString(7));
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
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_GIFTS, null);
        if (cursor != null && cursor.moveToFirst()) {
            isEmpty = (cursor.getInt(0) == 0);
            cursor.close();
        }
        db.close();
        return isEmpty;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GIFTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
