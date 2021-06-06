package com.padia3d.i;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.Math.toDegrees;

public class MainActivity extends AppCompatActivity {

    EditText inputNumber;
    TextView outputNumber;
    Button buton;

    String lastString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputNumber = findViewById(R.id.inputNumber);
        outputNumber = findViewById(R.id.outputNumberText);
        buton = findViewById(R.id.button1);

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().charAt(s.length() - 1) != 'i') {
                    inputNumber.setText(inputNumber.getText() + "i");
                }

                if (s.toString().contains("+")) {
                    lastString = inputNumber.getText().toString();
                } else {
                    inputNumber.setText(lastString);
                }
            }
        });
        calculate();
        lastString = inputNumber.getText().toString();
    }

    void calculate() {
        String[] temp = null;
        BigDecimal realNumber;
        BigDecimal iNumber;

        if (inputNumber.getText().toString().contains("+")) {
            temp = inputNumber.getText().toString().split("\\+");

        } else if (inputNumber.getText().toString().contains("-")) {
            temp = inputNumber.getText().toString().split("-");
        }
        if (temp[0].contains("i")) {
            realNumber = new BigDecimal(temp[1]);
            iNumber = new BigDecimal(temp[0].trim().split("i")[0]);
        } else {
            realNumber = new BigDecimal(temp[0]);
            iNumber = new BigDecimal(temp[1].trim().split("i")[0]);
        }

        //MODULE AND ANGLE AND VALIDATION
        double angle = 0;
        double z = 0;
        if (realNumber.doubleValue() == 0) {
            if (iNumber.doubleValue() == 0) {
                angle = 0;
                z = 0;
                outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
                return;
            }

            if (iNumber.doubleValue() > 0) {
                angle = 90;
                z = iNumber.doubleValue();
                outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
                return;
            }

            if (iNumber.doubleValue() < 0) {
                angle = 270;
                z = -iNumber.doubleValue();
                outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
                return;
            }
        }

        if (iNumber.doubleValue() == 0) {
            if (realNumber.doubleValue() > 0) {
                angle = 0;
                z = realNumber.doubleValue();
                outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
                return;
            }

            if (realNumber.doubleValue() < 0) {
                angle = 180;
                z = -realNumber.doubleValue();
                outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
                return;
            }
        }

        if (realNumber.doubleValue() > 0 && iNumber.doubleValue() < 0) {
            angle = toDegrees(Math.atan((iNumber.divide(realNumber, MathContext.DECIMAL128)).doubleValue())) + 360;
        } else if (realNumber.doubleValue() < 0 && iNumber.doubleValue() > 0) {
            angle = toDegrees(Math.atan((iNumber.divide(realNumber, MathContext.DECIMAL128)).doubleValue())) + 180;
        } else if (realNumber.doubleValue() < 0 && iNumber.doubleValue() < 0) {
            angle = toDegrees(Math.atan((iNumber.divide(realNumber, MathContext.DECIMAL128)).doubleValue())) + 180;
        } else {
            angle = toDegrees(Math.atan((iNumber.divide(realNumber, MathContext.DECIMAL128)).doubleValue()));
        }

        z = Math.sqrt((realNumber.multiply(realNumber).add(iNumber.multiply(iNumber))).doubleValue());
        outputNumber.setText("|Z| = " + z + "\nAngle = " + angle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.operationsItem) {
            startActivity(new Intent(this, OperationsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}