package com.example.cattwo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // (c) Hard-coded unit prices (KES). Chosen so that every discount band
    // can be reached with a maximum of 4 of each item (max grand total = 38,000).
    private static final double PRICE_BREAD = 2500;
    private static final double PRICE_MILK = 3000;
    private static final double PRICE_SUGAR = 4000;

    private static final int MAX_QTY = 4;

    EditText etBreadQty, etMilkQty, etSugarQty;
    EditText etBreadTotal, etMilkTotal, etSugarTotal;
    EditText etGrandTotal, etDiscount, etNetPay;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBreadQty = findViewById(R.id.etBreadQty);
        etMilkQty = findViewById(R.id.etMilkQty);
        etSugarQty = findViewById(R.id.etSugarQty);
        etBreadTotal = findViewById(R.id.etBreadTotal);
        etMilkTotal = findViewById(R.id.etMilkTotal);
        etSugarTotal = findViewById(R.id.etSugarTotal);
        etGrandTotal = findViewById(R.id.etGrandTotal);
        etDiscount = findViewById(R.id.etDiscount);
        etNetPay = findViewById(R.id.etNetPay);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    private void calculate() {
        // (b) Validate each quantity: required, and not more than 4
        int breadQty = readQuantity(etBreadQty);
        if (breadQty < 0) return;
        int milkQty = readQuantity(etMilkQty);
        if (milkQty < 0) return;
        int sugarQty = readQuantity(etSugarQty);
        if (sugarQty < 0) return;

        // (c) Total for each item, then the grand total
        double breadTotal = breadQty * PRICE_BREAD;
        double milkTotal = milkQty * PRICE_MILK;
        double sugarTotal = sugarQty * PRICE_SUGAR;
        double grandTotal = breadTotal + milkTotal + sugarTotal;

        etBreadTotal.setText(format(breadTotal));
        etMilkTotal.setText(format(milkTotal));
        etSugarTotal.setText(format(sugarTotal));
        etGrandTotal.setText(format(grandTotal));

        // (d) Work out the discount percentage, then apply it
        applyDiscount(grandTotal, getDiscountPercent(grandTotal));
    }

    // (b) Returns the quantity, or -1 (after showing a Toast) if it is invalid.
    private int readQuantity(EditText field) {
        String text = field.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "You must enter quantity", Toast.LENGTH_SHORT).show();
            return -1;
        }
        int qty;
        try {
            qty = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "You must enter quantity", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (qty > MAX_QTY) {
            Toast.makeText(this, "You cannot buy more than 4 items", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return qty;
    }

    // Discount bands from the question. The question leaves gaps (10,001-10,999
    // and 25,001-25,999), so each band here runs up to the start of the next one.
    //   below 11,000          -> 0%   (0 to 10,000 = no discount)
    //   11,000 to 25,999      -> 15%
    //   26,000 to 35,000      -> 25%
    //   over 35,000           -> 30%
    private int getDiscountPercent(double grandTotal) {
        if (grandTotal < 11000) {
            return 0;
        } else if (grandTotal < 26000) {
            return 15;
        } else if (grandTotal <= 35000) {
            return 25;
        } else {
            return 30;
        }
    }

    // (d) Two parameters: the grand total and the discount percentage.
    // Shows the discount amount in DISCOUNT and the amount payable in NET PAY.
    private void applyDiscount(double grandTotal, int discountPercent) {
        double discountAmount = grandTotal * discountPercent / 100.0;
        double netPay = grandTotal - discountAmount;

        etDiscount.setText(format(discountAmount));
        etNetPay.setText(format(netPay));
    }

    private String format(double amount) {
        return String.format(Locale.US, "%,.2f", amount);
    }
}
