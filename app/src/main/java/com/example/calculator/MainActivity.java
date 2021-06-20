package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable, View.OnTouchListener {
    private static String operator;
    private EditText resultText;
    private TextView lastExpression;
    private Button clearButton;
    private ImageButton backspace, module, multiply, divide, sum, subtract, opposite, result;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private ImageButton parentheses;
    public Calculator calculator = new Calculator(this);
    private Button point;
    private int nightMode;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_button:
                operator = "clear";
                break;
            case R.id.backspace_button:
                operator = "backspace";
                break;
            case R.id.module_button:
                operator = "module";
                break;
            case R.id.multiply_button:
                operator = "multiply";
                break;
            case R.id.subtract_button:
                operator = "subtract";
                break;
            case R.id.button_opposite:
                operator = "opposite";
                break;
            case R.id.button_one:
                operator = "one";
                break;
            case R.id.button_two:
                operator = "two";
                break;
            case R.id.button_three:
                operator = "three";
                break;
            case R.id.button_four:
                operator = "four";
                break;
            case R.id.button_five:
                operator = "five";
                break;
            case R.id.button_six:
                operator = "six";
                break;
            case R.id.button_seven:
                operator = "seven";
                break;
            case R.id.button_eight:
                operator = "eight";
                break;
            case R.id.button_nine:
                operator = "nine";
                break;
            case R.id.button_point:
                operator = "point";
                break;
            case R.id.button_sum:
                operator = "sum";
                break;
            case R.id.divide_button:
                operator = "divide";
                break;
            case R.id.button_result:
                operator = "result";
                break;
            case R.id.button_zero:
                operator = "zero";
                break;
            case R.id.parentheses_button:
                operator = "parentheses";
                break;
            default:
                break;
        }
        if (resultText.getText().length() == 25 && !operator.equals("backspace") && !operator.equals("clear")) {
            Toast.makeText(this, "Maximum 25 element allowed", Toast.LENGTH_SHORT).show();
        } else {
            resultText.setText(calculator.calculate(operator,resultText.getText().toString()));
            lastExpression.setText(calculator.lastExpression);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(null);
        resultText = findViewById(R.id.resultInput);
        lastExpression = findViewById(R.id.lastExpression);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            resultText.setShowSoftInputOnFocus(false);
        } else { resultText.setTextIsSelectable(true);
        }
        initializeButtons();
        setOnTouchListener();
        nightMode = getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;;
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        int nightMode = AppCompatDelegate.getDefaultNightMode();
//        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
//            is_in_dark_mode = true;
//        } else {
//            is_in_dark_mode = false;
//        }
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean(sharedFile, is_in_dark_mode);
//        editor.apply();
//    }




    public void initializeButtons() {
        backspace = findViewById(R.id.backspace_button);
        module = findViewById(R.id.module_button);
        multiply = findViewById(R.id.multiply_button);
        divide = findViewById(R.id.divide_button);
        subtract = findViewById(R.id.subtract_button);
        sum = findViewById(R.id.button_sum);
        result = findViewById(R.id.button_result);
        one = findViewById(R.id.button_one);
        two = findViewById(R.id.button_two);
        three = findViewById(R.id.button_three);
        four = findViewById(R.id.button_four);
        five = findViewById(R.id.button_five);
        six = findViewById(R.id.button_six);
        seven = findViewById(R.id.button_seven);
        eight = findViewById(R.id.button_eight);
        nine = findViewById(R.id.button_nine);
        zero = findViewById(R.id.button_zero);
        parentheses = findViewById(R.id.parentheses_button);
        point = findViewById(R.id.button_point);
        opposite = findViewById(R.id.button_opposite);
        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);
        backspace.setOnClickListener(MainActivity.this);
        module.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        subtract.setOnClickListener(this);
        sum.setOnClickListener(this);
        result.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        point.setOnClickListener(this);
        opposite.setOnClickListener(this);
        parentheses.setOnClickListener(this);


    }

    @Override
    public void run() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListener() {

        backspace.setOnTouchListener(this);
        clearButton.setOnTouchListener(this);
        backspace.setOnTouchListener(this);
        module.setOnTouchListener(this);
        multiply.setOnTouchListener(this);
        divide.setOnTouchListener(this);
        subtract.setOnTouchListener(this);
        sum.setOnTouchListener(this);
        result.setOnTouchListener(this);
        one.setOnTouchListener(this);
        two.setOnTouchListener(this);
        three.setOnTouchListener(this);
        four.setOnTouchListener(this);
        five.setOnTouchListener(this);
        six.setOnTouchListener(this);
        seven.setOnTouchListener(this);
        eight.setOnTouchListener(this);
        nine.setOnTouchListener(this);
        zero.setOnTouchListener(this);
        point.setOnTouchListener(this);
        opposite.setOnTouchListener(this);
        parentheses.setOnTouchListener(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    view.getBackground().setColorFilter(Color.rgb(23, 22, 22), PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                } else {
                    view.getBackground().setColorFilter(Color.rgb(228, 228, 228), PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
        return false;
    }
}