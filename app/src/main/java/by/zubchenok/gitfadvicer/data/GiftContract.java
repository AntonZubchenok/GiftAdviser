package by.zubchenok.gitfadvicer.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class GiftContract {

    public static final String CONTENT_AUTHORITY = "by.zubchenok.gitfadvicer.data.gifts";
    public static final Uri DB_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String DB_NAME = "gifts";
    public static final String DB_PATH = "/data/data/by.zubchenok.gitfadvicer/databases/";

    public static final class GiftEntry implements BaseColumns {

        public static final Uri TABLE_URI = Uri.withAppendedPath(DB_URI, DB_NAME);
        public static final String TABLE_NAME = "gifts";

        public static final String _ID = BaseColumns._ID;                                  //INTEGER
        public static final String COLUMN_NAME = "name";                                   //TEXT
        public static final String COLUMN_SEX = "sex";                                     //INTEGER
        public static final String COLUMN_AGE_MIN = "age_min";                             //INTEGER
        public static final String COLUMN_AGE_MAX = "age_max";                             //INTEGER
        public static final String COLUMN_PRICE_MIN = "price_min";                         //INTEGER
        public static final String COLUMN_PRICE_MAX = "price_max";                         //INTEGER
        public static final String COLUMN_IMAGE = "image";                                 //TEXT
        public static final String COLUMN_REASON_ANY = "reason_any";                       //INTEGER
        public static final String COLUMN_REASON_BIRTHDAY = "reason_birthday";             //INTEGER
        public static final String COLUMN_REASON_NEW_YEAR = "reason_new_year";             //INTEGER
        public static final String COLUMN_REASON_WEDDING = "reason_wedding";               //INTEGER
        public static final String COLUMN_REASON_8_MAR = "reason_8_mar";                   //INTEGER
        public static final String COLUMN_REASON_23_FEB = "reason_23_feb";                 //INTEGER
        public static final String COLUMN_REASON_VALENTINES_DAY = "reason_valentines_day"; //INTEGER

        public static final int SEX_ANY = -1;
        public static final int SEX_MALE = 1;
        public static final int SEX_FEMALE = 0;

        //MIME types
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + DB_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + DB_NAME;

    }

    private GiftContract() {
    }
}
