package by.zubchenok.gitfadvicer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import by.zubchenok.gitfadvicer.data.Gift;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ImageView imageView = (ImageView) findViewById(R.id.iv_item_image);
        TextView textViewSex = (TextView) findViewById(R.id.tv_sex);
        TextView textViewAge = (TextView) findViewById(R.id.tv_age);
        TextView textViewPrice = (TextView) findViewById(R.id.tv_price);
        TextView textViewReasons = (TextView) findViewById(R.id.tv_reasons);
        Gift gift = (Gift) getIntent().getSerializableExtra(RecyclerViewAdapter.GIFT_EXTRA);

        int imageId = this.getResources().getIdentifier(gift.getImageId(), "drawable", this.getPackageName());
        imageView.setImageResource(imageId);

        String sex = "";
        switch (gift.getSex()) {
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

        textViewAge.setText("Возраст: " + gift.getAgeMin() + "-" + gift.getAgeMax() + " лет");
        textViewPrice.setText("Стоимость: " + gift.getPriceMin() + "-" + gift.getPriceMax() + " BYN");

        String reasons = "";

        if (gift.getReasonAny() == 1) {
            reasons = "любой";
        } else {
            if (gift.getReason23Feb() == 1) {
                reasons += "23 февраля";
            }
            if (gift.getReason8Mar() == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "8 марта";
            }
            if (gift.getReasonBirthday() == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "день рождения";
            }
            if (gift.getReasonNewYear() == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "Новый Год";
            }
            if (gift.getReasonValentinesDay() == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "день Святого Валентина";
            }
            if (gift.getReasonWedding() == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", ";
                }
                reasons += "свадьба";
            }
        }
        textViewReasons.setText("Повод: " + reasons);
    }
}
