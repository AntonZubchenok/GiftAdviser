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

    //Константы для помещения и доставания данных из интента и бандла
    public static final String EXTRA_SEX = "sex";
    public static final String EXTRA_REASON_SPINNER_POSITION = "reason spinner position";
    public static final String EXTRA_AGE = "age";
    public static final String EXTRA_MAX_PRICE = "max price";

    //Константы, обозначающие соответствие выбранной позиции спинера и повода
    public static final int SPINNER_POSITION_BIRTHDAY = 0;
    public static final int SPINNER_POSITION_NEW_YEAR = 1;
    public static final int SPINNER_POSITION_WEDDING = 2;
    public static final int SPINNER_POSITION_8_MAR = 3;
    public static final int SPINNER_POSITION_23_FEB = 4;
    public static final int SPINNER_POSITION_VALENTINES_DAY = 5;
    public static final int SPINNER_POSITION_ANY_REASON = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Обработка нажатия кнопки "Подобрать"
        Button button = (Button) findViewById(R.id.button_find);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Считывание выбранной позиции спиннера с поводом
                final Spinner reasonSpinner = (Spinner) findViewById(R.id.spinner_holidays);
                int reasonSpinnerPosition = reasonSpinner.getSelectedItemPosition();


                //Считывание выбранной RadioButton с полом
                final RadioGroup sexGroup = (RadioGroup) findViewById(R.id.radiogroup_sex);
                int sex = SEX_ANY;
                if (sexGroup.getCheckedRadioButtonId() == R.id.radiobutton_male) {
                    sex = SEX_MALE;
                } else if (sexGroup.getCheckedRadioButtonId() == R.id.radiobutton_female) {
                    sex = SEX_FEMALE;
                }


                //Считывание EditText с возрастом
                final EditText ageEditText = (EditText) findViewById(R.id.edittext_age);
                int age = Integer.valueOf(ageEditText.getText().toString());


                //Считывание EditText с максимальной ценой
                final EditText maxPriceEditText = (EditText) findViewById(R.id.edittext_price);
                int maxPrice = Integer.valueOf(maxPriceEditText.getText().toString());


                //Создание и отправка интента со считанными данными в GiftListActivity
                Intent intent = new Intent(MainActivity.this, GiftListActivity.class);
                intent.putExtra(EXTRA_REASON_SPINNER_POSITION, reasonSpinnerPosition);
                intent.putExtra(EXTRA_SEX, sex);
                intent.putExtra(EXTRA_AGE, age);
                intent.putExtra(EXTRA_MAX_PRICE, maxPrice);
                startActivity(intent);
            }
        });
    }
}
