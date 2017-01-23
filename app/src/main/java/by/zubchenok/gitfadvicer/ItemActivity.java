package by.zubchenok.gitfadvicer;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import by.zubchenok.gitfadvicer.data.GiftContract;

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
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.TABLE_URI;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry._ID;

public class ItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //Достаём из интента id подарка и помещаем его в Bundle, который отправится в Loader
        int giftId = getIntent().getIntExtra(GiftContract.GiftEntry._ID, -1);
        Bundle bundle = new Bundle(1);
        bundle.putInt(GiftContract.GiftEntry._ID, giftId);

        //Стартуем Loader, который достанет из БД данные о выбранном подарке и отобразит их
        getLoaderManager().restartLoader(0, bundle, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Достаём из БД данные о выбранном подарке
        String selection = "(" + _ID + "=?)";
        String[] selectionArgs = {String.valueOf(args.getInt(_ID))};
        return new CursorLoader(this, TABLE_URI, null, selection, selectionArgs, null);
    }
  
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ImageView imageView = (ImageView) findViewById(R.id.image_item_image);
        TextView textViewName = (TextView) findViewById(R.id.text_item_name);
        TextView textViewSex = (TextView) findViewById(R.id.text_sex);
        TextView textViewAge = (TextView) findViewById(R.id.text_age);
        TextView textViewPrice = (TextView) findViewById(R.id.text_price);
        TextView textViewReasons = (TextView) findViewById(R.id.text_reasons);
        data.moveToFirst();

        //Отображаем изображение с подарком
        String imageName = data.getString(data.getColumnIndex(COLUMN_IMAGE));
        int imageId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        imageView.setImageResource(imageId);

        //Отображаем название подарка
        textViewName.setText(data.getString(data.getColumnIndex(COLUMN_NAME)));

        //Отображаем пол, которому подходит подарок
        String sex = "";
        switch (data.getInt(data.getColumnIndex(COLUMN_SEX))) {
            case 0:
                sex = "женский";
                break;
            case 1:
                sex = "мужской";
                break;
            case -1:
                sex = "любой";
                break;
        }
        textViewSex.setText("Пол: " + sex);

        //Отображаем минимальный и максимальный возраст
        textViewAge.setText("Возраст: " + data.getInt(data.getColumnIndex(COLUMN_AGE_MIN)) + "-" +
                data.getInt(data.getColumnIndex(COLUMN_AGE_MAX)) + " лет");

        //Отображаем минимальную и максимальную цену
        textViewPrice.setText("Стоимость: " + data.getInt(data.getColumnIndex(COLUMN_PRICE_MIN)) + "-" +
                data.getInt(data.getColumnIndex(COLUMN_PRICE_MAX)) + " BYN");


        //Отображаем поводы, к которым подходит подарок
        String reasons = "";

        if (data.getInt(data.getColumnIndex(COLUMN_REASON_ANY)) == 1) {
            reasons = "любой";
        } else {
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_23_FEB)) == 1) {
                reasons += "23 февраля";
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_8_MAR)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "8 марта";
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_BIRTHDAY)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "день рождения";
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_NEW_YEAR)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "Новый Год";
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_VALENTINES_DAY)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "день Святого Валентина";
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_WEDDING)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "свадьба";
            }
        }
        textViewReasons.setText("Повод: " + reasons);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
