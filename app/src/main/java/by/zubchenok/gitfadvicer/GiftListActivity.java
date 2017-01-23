package by.zubchenok.gitfadvicer;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import by.zubchenok.gitfadvicer.data.GiftContract;
import by.zubchenok.gitfadvicer.data.GiftCursorRecyclerViewAdapter;

import static by.zubchenok.gitfadvicer.MainActivity.EXTRA_AGE;
import static by.zubchenok.gitfadvicer.MainActivity.EXTRA_MAX_PRICE;
import static by.zubchenok.gitfadvicer.MainActivity.EXTRA_REASON_SPINNER_POSITION;
import static by.zubchenok.gitfadvicer.MainActivity.EXTRA_SEX;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_23_FEB;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_8_MAR;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_BIRTHDAY;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_NEW_YEAR;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_VALENTINES_DAY;
import static by.zubchenok.gitfadvicer.MainActivity.SPINNER_POSITION_WEDDING;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_AGE_MAX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_AGE_MIN;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_IMAGE;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_NAME;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_PRICE_MAX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_PRICE_MIN;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_23_FEB;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_8_MAR;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_BIRTHDAY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_NEW_YEAR;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_VALENTINES_DAY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_REASON_WEDDING;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_SEX;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.TABLE_URI;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry._ID;

public class GiftListActivity extends AppCompatActivity implements
        android.app.LoaderManager.LoaderCallbacks<Cursor>,
        GiftCursorRecyclerViewAdapter.OnItemClickListener {

    GiftCursorRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        //Достаём данные о поводе, поле, возрасте, максимальной цене из интента и помещаем их в
        //Bundle, который отправится в Loader
        Bundle bundle = new Bundle(4);
        if (getIntent() != null) {
            bundle.putInt(EXTRA_REASON_SPINNER_POSITION, getIntent().getIntExtra(EXTRA_REASON_SPINNER_POSITION, -1));
            bundle.putInt(EXTRA_SEX, getIntent().getIntExtra(EXTRA_SEX, -2));
            bundle.putInt(EXTRA_AGE, getIntent().getIntExtra(EXTRA_AGE, -1));
            bundle.putInt(EXTRA_MAX_PRICE, getIntent().getIntExtra(EXTRA_MAX_PRICE, -1));
        }

        //Стартуем Loader, который подтягивает из БД курсор с подходящими подарками и показывает
        //их в RecyclerView
        getLoaderManager().restartLoader(0, bundle, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Достаём данные из Bundle
        int reasonSpinnerPosition = args.getInt(EXTRA_REASON_SPINNER_POSITION);
        String sex = String.valueOf(args.getInt(EXTRA_SEX));
        String age = String.valueOf(args.getInt(EXTRA_AGE));
        String maxPrice = String.valueOf(args.getInt(EXTRA_MAX_PRICE));

        //Столбцы, которые нужно достать из БД
        String[] projection = {_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE_MIN, COLUMN_PRICE_MAX};

        //Формирование SQL-запроса для того, чтобы достать из БД подарки, подходящие как
        //для всех праздников, так и для нужного выбранного (reasonAny и reasonWedding например)
        String reasonSelection = new String("reason_any=1");
        switch (reasonSpinnerPosition) {
            case SPINNER_POSITION_BIRTHDAY:
                reasonSelection += (" OR " + COLUMN_REASON_BIRTHDAY + "=1");
                break;
            case SPINNER_POSITION_NEW_YEAR:
                reasonSelection += (" OR " + COLUMN_REASON_NEW_YEAR + "=1");
                break;
            case SPINNER_POSITION_WEDDING:
                reasonSelection += (" OR " + COLUMN_REASON_WEDDING + "=1");
                break;
            case SPINNER_POSITION_8_MAR:
                reasonSelection += (" OR " + COLUMN_REASON_8_MAR + "=1");
                break;
            case SPINNER_POSITION_23_FEB:
                reasonSelection += (" OR " + COLUMN_REASON_23_FEB + "=1");
                break;
            case SPINNER_POSITION_VALENTINES_DAY:
                reasonSelection += (" OR " + COLUMN_REASON_VALENTINES_DAY + "=1");
                break;
        }

        //Условия выборки данных из БД
        String selection = "(" + COLUMN_SEX + "=? OR " + COLUMN_SEX + "=-1) AND " +
                COLUMN_AGE_MAX + ">=? AND " +
                COLUMN_AGE_MIN + "<=? AND " +
                COLUMN_PRICE_MAX + "<=? AND " +
                "(" + reasonSelection + ")";

        //Значения условий выбора
        String[] selectionArgs = new String[]{sex, age, age, maxPrice};

        return new CursorLoader(this, TABLE_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //Ставим адаптер на RecyclerView
        mAdapter = new GiftCursorRecyclerViewAdapter(this, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_gift_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClicked(Cursor cursor) {

        //Считываем с курсора id подарка
        int giftId = cursor.getInt(cursor.getColumnIndex(GiftContract.GiftEntry._ID));

        //помещаем id в интент и отправляем в ItemActivity
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(GiftContract.GiftEntry._ID, giftId);
        startActivity(intent);
    }
}

