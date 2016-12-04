package by.zubchenok.gitfadvicer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class GiftListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        //Gift list initialization
        ArrayList<Gift> gifts = new ArrayList<>();
        gifts.add(new Gift("Книга", "20 руб.", R.drawable.book));
        gifts.add(new Gift("Кошелек", "40 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));
        gifts.add(new Gift("Коврик для мыши", "15 руб.", R.drawable.book));

        //RecyclerView initialization
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(gifts);
        recyclerView.setAdapter(adapter);
    }
}
