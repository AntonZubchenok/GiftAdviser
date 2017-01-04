package by.zubchenok.gitfadvicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiftListActivity extends AppCompatActivity {
    private GiftDatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        //Database
        mDBHelper = new GiftDatabaseHelper(this);
        if (mDBHelper.isGiftDatabaseEmpty()) {
            mDBHelper.addGiftsToDatabase(createTestGifts());
        }

        Intent intent = getIntent();
        if (intent != null) {
            Gift giftToFind = createGiftObjectFromIntent(intent);
            List<Gift> gifts = mDBHelper.getGiftsFromDatabase(giftToFind);
            showGiftsList(gifts);
        }
    }

    private List<Gift> createTestGifts() {
        int size = 300;
        List<Gift> gifts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Gift gift = new Gift();
            gift.setName("Подарок №" + (i+1));
            gift.setSex(-1 + new Random().nextInt(3));
            int ageMin = 10 + new Random().nextInt(15);
            gift.setAgeMin(ageMin);
            gift.setAgeMax(ageMin + new Random().nextInt(40) + 5);
            int priceMin = 2 + new Random().nextInt(500);
            gift.setPriceMin(priceMin);
            gift.setPriceMax(priceMin + new Random().nextInt(50));
            gift.setImageId("book");
            gift.setReasonAny(new Random().nextInt(2));
            gift.setReasonBirthday(new Random().nextInt(2));
            gift.setReasonNewYear(new Random().nextInt(2));
            gift.setReasonWedding(new Random().nextInt(2));
            gift.setReason8Mar(new Random().nextInt(2));
            gift.setReason23Feb(new Random().nextInt(2));
            gift.setReasonValentinesDay(new Random().nextInt(2));
            gifts.add(gift);
        }
        return gifts;
    }

    private void showGiftsList(List<Gift> gifts) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(gifts);
        recyclerView.setAdapter(adapter);

    }

    private Gift createGiftObjectFromIntent(Intent intent){
        Gift gift = new Gift();

        //Reason
        int reasonPosition = intent.getIntExtra(MainActivity.REASON_POSITION, -1);

        switch (reasonPosition) {
            case 0: gift.setReasonBirthday(1);
                break;
            case 1: gift.setReasonNewYear(1);
                break;
            case 2: gift.setReasonWedding(1);
                break;
            case 3: gift.setReason8Mar(1);
                break;
            case 4: gift.setReason23Feb(1);
                break;
            case 5: gift.setReasonValentinesDay(1);
                break;
            case 6: gift.setReasonAny(1);
                break;
        }

        //Sex
        int sex = intent.getIntExtra(MainActivity.SEX, -2);
        gift.setSex(sex);

        //Age
        int age = intent.getIntExtra(MainActivity.AGE, -1);
        gift.setAge(age);

        //Price
        int maxPrice = intent.getIntExtra(MainActivity.MAX_PRICE,-1);
        gift.setPriceMax(maxPrice);

        return gift;
    }
}
