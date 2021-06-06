package com.padia3d.i;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

public class OperationsActivity extends AppCompatActivity {

    Spinner operator;
    TextView outputText;
    Button buton;
    EditText inputNumber, inputNumber2;

    String lastString, lastString2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        operator = findViewById(R.id.operations);
        outputText = findViewById(R.id.outputNumberText);
        buton = findViewById(R.id.button1);
        inputNumber = findViewById(R.id.inputNumber);
        inputNumber2 = findViewById(R.id.inputNumber2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, R.layout.support_simple_spinner_dropdown_item);
        operator.setAdapter(adapter);

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

        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().charAt(s.length() - 1) != 'i') {
                    inputNumber2.setText(inputNumber2.getText() + "i");
                }

                if (s.toString().contains("+")) {
                    lastString2 = inputNumber2.getText().toString();
                } else {
                    inputNumber2.setText(lastString2);
                }
            }
        });
        lastString = inputNumber.getText().toString();
        lastString2 = inputNumber2.getText().toString();
        calculate();
    }

    public void calculate() {
        List<BigDecimal> number1 = parse(inputNumber);
        List<BigDecimal> number2 = parse(inputNumber2);

        if (operator.getSelectedItem().equals("Sum")) {
            outputText.setText(number1.get(0).add(number2.get(0)) + " + " + number1.get(1).add(number2.get(1)) + "i");
        }
        if (operator.getSelectedItem().equals("Sub")) {
            outputText.setText(number1.get(0).subtract(number2.get(0)) + " + " + number1.get(1).subtract(number2.get(1)) + "i");
        }
        if (operator.getSelectedItem().equals("Mul")) {
            outputText.setText(number1.get(0).multiply(number2.get(0)).subtract(number1.get(1).multiply(number2.get(1))) + " + " + number1.get(0).multiply(number2.get(1)).add(number1.get(1).multiply(number1.get(0))) + "i");
        }

        if (operator.getSelectedItem().equals("Div")) {
            BigDecimal divisor = (number2.get(0).pow(2)).add(number2.get(1).pow(2));
            BigDecimal n1 = (number1.get(0).multiply(number2.get(0)).add(number1.get(1).multiply(number2.get(1)))).divide(divisor, MathContext.DECIMAL32);
            BigDecimal n2 = (number1.get(1).multiply(number2.get(0)).subtract(number1.get(0).multiply(number2.get(1)))).divide(divisor, MathContext.DECIMAL32);
            outputText.setText(n1 + " + " + n2+ "i");
        }
        //Toast.makeText(getApplicationContext(), operator.getSelectedItem().toString(), Toast.LENGTH_LONG).show(); //DEBUG
    }

    public List<BigDecimal> parse(EditText e) {
        String[] temp = null;
        BigDecimal realNumber;
        BigDecimal iNumber;

        if (e.getText().toString().contains("+")) {
            temp = e.getText().toString().split("\\+");

        } else if (e.getText().toString().contains("-")) {
            temp = e.getText().toString().split("-");
        }
        if (temp[0].contains("i")) {
            realNumber = new BigDecimal(temp[1]);
            iNumber = new BigDecimal(temp[0].trim().split("i")[0]);
        } else {
            realNumber = new BigDecimal(temp[0]);
            iNumber = new BigDecimal(temp[1].trim().split("i")[0]);
        }

        return Arrays.asList(realNumber, iNumber);
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