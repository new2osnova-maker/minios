package com.minios;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static { System.loadLibrary("minios"); }

    public native String calculate(double a, double b, String op);

    private EditText display;
    private StringBuilder current = new StringBuilder();
    private double first = 0;
    private String op = "";
    private boolean newNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] btnIds = {
            R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4,
            R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9,
            R.id.bPlus, R.id.bMinus, R.id.bMul, R.id.bDiv,
            R.id.bPow, R.id.bDot, R.id.bEq, R.id.bClear,
            R.id.bBack
        };

        for (int id : btnIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(this::onClick);
        }
    }

    private void onClick(View v) {
        Button b = (Button) v;
        String text = b.getText().toString();

        if (text.matches("[0-9]")) {
            if (newNumber) { current.setLength(0); newNumber = false; }
            current.append(text);
            display.setText(current.toString());
        }
        else if (text.equals(".")) {
            if (!current.toString().contains(".")) current.append(".");
            display.setText(current.toString());
        }
        else if (text.equals("C")) {
            current.setLength(0); first = 0; op = ""; newNumber = true;
            display.setText("0");
        }
        else if (text.equals("⌫")) {
            if (current.length() > 0) current.deleteCharAt(current.length()-1);
            display.setText(current.length() == 0 ? "0" : current.toString());
        }
        else if (text.equals("=")) {
            if (!op.isEmpty() && current.length() > 0) {
                double second = Double.parseDouble(current.toString());
                String result = calculate(first, second, op);
                display.setText(result);
                current.setLength(0);
                current.append(result);
                op = ""; newNumber = true;
            }
        }
        else {
            if (current.length() > 0) {
                first = Double.parseDouble(current.toString());
                op = text;
                newNumber = true;
            }
        }
    }
}
