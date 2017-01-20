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

import static by.zubchenok.gitfadvicer.MainActivity.AGE;
import static by.zubchenok.gitfadvicer.MainActivity.MAX_PRICE;
import static by.zubchenok.gitfadvicer.MainActivity.REASON_SPINNER_POSITION;
import static by.zubchenok.gitfadvicer.MainActivity.SEX;
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
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.CONTENT_URI;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry._ID;

public class GiftListActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>,
        GiftCursorRecyclerViewAdapter.OnItemClickListener {

    GiftCursorRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        mAdapter = new GiftCursorRecyclerViewAdapter(this, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        Bundle bundle = new Bundle(4);
        if (getIntent() != null) {
            bundle.putInt(REASON_SPINNER_POSITION, getIntent().getIntExtra(REASON_SPINNER_POSITION, -1));
            bundle.putInt(SEX, getIntent().getIntExtra(REASON_SPINNER_POSITION, -2));
            bundle.putInt(AGE, getIntent().getIntExtra(AGE, -1));
            bundle.putInt(MAX_PRICE, getIntent().getIntExtra(MAX_PRICE, -1));
        }

        getLoaderManager().restartLoader(0, bundle, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int reasonSpinnerPosition = args.getInt(REASON_SPINNER_POSITION);
        String sex = String.valueOf(args.getInt(SEX));
        String age = String.valueOf(args.getInt(AGE));
        String maxPrice = String.valueOf(args.getInt(MAX_PRICE));

        String[] projection = {_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE_MIN, COLUMN_PRICE_MAX};

        String reasonRequest = new String("reason_any=1");
        switch (reasonSpinnerPosition) {
            case SPINNER_POSITION_BIRTHDAY:
                reasonRequest += (" OR " + COLUMN_REASON_BIRTHDAY + "=1");
                break;
            case SPINNER_POSITION_NEW_YEAR:
                reasonRequest += (" OR " + COLUMN_REASON_NEW_YEAR + "=1");
                break;
            case SPINNER_POSITION_WEDDING:
                reasonRequest += (" OR " + COLUMN_REASON_WEDDING + "=1");
                break;
            case SPINNER_POSITION_8_MAR:
                reasonRequest += (" OR " + COLUMN_REASON_8_MAR + "=1");
                break;
            case SPINNER_POSITION_23_FEB:
                reasonRequest += (" OR " + COLUMN_REASON_23_FEB + "=1");
                break;
            case SPINNER_POSITION_VALENTINES_DAY:
                reasonRequest += (" OR " + COLUMN_REASON_VALENTINES_DAY + "=1");
                break;
        }

        String selection = "(" + COLUMN_SEX + "=? OR " + COLUMN_SEX + "=-1) AND " +
                COLUMN_AGE_MAX + ">=? AND " +
                COLUMN_AGE_MIN + "<=? AND " +
                COLUMN_PRICE_MAX + "<=? AND " +
                "(" + reasonRequest + ")";

        String[] selectionArgs = new String[]{sex, age, age, maxPrice};

        return new CursorLoader(this, CONTENT_URI, projection, selection, selectionArgs, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClicked(Cursor cursor) {
        int giftId = cursor.getInt(cursor.getColumnIndex(GiftContract.GiftEntry._ID));
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(GiftContract.GiftEntry._ID, giftId);
        startActivity(intent);
    }
}

