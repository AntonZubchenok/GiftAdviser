package by.zubchenok.gitfadvicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.SEX_ANY;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.SEX_FEMALE;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.SEX_MALE;

public class MainActivity extends AppCompatActivity {
    public static final String SEX = "sex";
    public static final String REASON_POSITION = "reason_position";
    public static final String AGE = "age";
    public static final String MAX_PRICE = "max_price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button handling
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Reason
                final Spinner reasonSpinner = (Spinner) findViewById(R.id.spinner_holidays);
                int reasonPosition = reasonSpinner.getSelectedItemPosition();

                //Sex
                final RadioGroup sexGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int sex = SEX_ANY;
                if (sexGroup.getCheckedRadioButtonId() == R.id.radioButton) {
                    sex = SEX_MALE;
                } else if (sexGroup.getCheckedRadioButtonId() == R.id.radioButton2) {
                    sex = SEX_FEMALE;
                }

                //Age
                final EditText ageEditText = (EditText) findViewById(R.id.editText_age);
                int age = Integer.valueOf(ageEditText.getText().toString());

                //Max price
                final EditText maxPriceEditText = (EditText) findViewById(R.id.editText_price);
                int maxPrice = Integer.valueOf(maxPriceEditText.getText().toString());

                Intent intent = new Intent(MainActivity.this, GiftListActivity.class);
                intent.putExtra(REASON_POSITION, reasonPosition);
                intent.putExtra(SEX, sex);
                intent.putExtra(AGE, age);
                intent.putExtra(MAX_PRICE, maxPrice);
                startActivity(intent);
            }
        });
    }
}
