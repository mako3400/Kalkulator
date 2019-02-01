package com.example.makocalc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String DISPLAY_KEY = "display";
    public static final String ACCUMULATOR_KEY = "accumulator";
    public static final String OPERATION_KEY = "operation";
    private  String display ="0";
    private double accumulator = 0.0;
    private Operation currentOperation = Operation.NONE;
    private TextView displayTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = (TextView) (TextView) findViewById(R.id.textView);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DISPLAY_KEY, display);
        outState.putDouble(ACCUMULATOR_KEY, accumulator);
        outState.getString(OPERATION_KEY, currentOperation.name());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display = savedInstanceState.getString(DISPLAY_KEY, "0");
        accumulator = savedInstanceState.getDouble(ACCUMULATOR_KEY);
        currentOperation = Operation.valueOf(savedInstanceState.getString(OPERATION_KEY));

        updateDisplay();

    }

    public void keyClicked(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();



    switch (key){
        case "0":
        case "1":
        case "2":
        case "3":
        case "4":
        case "5":
        case "6":
        case "7":
        case "8":
        case "9":
            if (display.equals("0")){
                display = "";
            }
            display += key;
            break;
        case ".":
            if (!display.contains(".")){
                display += key;
            }
            break;
        case "+":
        case "-":
        case "*":
        case "/":

            calculateOperation(key);
            break;
        case "SQRT(x)":
            calculateSquareRoot();
            break;
        case "x^2":
            calculateSqare();
            break;
            case "=":
                calculateResult();
                break;
        case "CE":
            clearOne();
            break;
        case "C":
            clearAll();
            break;
    }
        updateDisplay();
    }

    private void calculateSqare() {
        double displayValue = Double.parseDouble(display);
        displayResult(displayValue * displayValue);
    }

    private void calculateSquareRoot() {
        double displayValue = Double.parseDouble(display);
        displayResult(Math.sqrt(displayValue));
    }

    private void updateDisplay() {
        displayTextView.setText(display);
    }

    private void clearAll() {
        display = "0";
        accumulator = 0.0;
        currentOperation = Operation.NONE;

    }

    private void clearOne() {
        if (display.length() >1){
            display = display.substring(0,display.length() -1);
        }else{
            display = "0";
        }
    }

    private void calculateResult() {
        double displayValue = Double.parseDouble(display);
        switch (currentOperation){
            case ADD:
                displayResult(accumulator + displayValue);
                break;
            case SUBSTRACT:
                displayResult(accumulator - displayValue);
                break;
            case MULTIPLY:
                displayResult(accumulator * displayValue);
                break;
            case DIVIDE:
                displayResult(accumulator / displayValue);
                break;
        }
        accumulator = 0.0;
        currentOperation = Operation.NONE;
    }

    private void displayResult(double result) {
        if (result==(long)result){
            display = String.format("%d", (long)result);
        }else {
            display = String.format("%s", result);
        }

    }

    private void calculateOperation(String key) {
        currentOperation = Operation.operationFromKey(key);
        accumulator = Double.parseDouble(display);
        display = "0";

    }
}
