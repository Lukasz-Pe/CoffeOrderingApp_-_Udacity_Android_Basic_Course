package com.example.coffeorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    int orderedCoffes=2;
    int coffeePrice =5;

    public void plus(View view){
        orderedCoffes++;
        displayQuantity(orderedCoffes);
    }
    public void minus(View view){
        orderedCoffes--;
        if(orderedCoffes<0){
            orderedCoffes=0;
        }
        displayQuantity(orderedCoffes);
    }

    public void plusUnitPrice(View view){
        coffeePrice++;
        displayUnitPrice(coffeePrice);
    }
    public void minusUnitPrice(View view){
        coffeePrice--;
        if(coffeePrice <0){
            coffeePrice =0;
        }
        displayUnitPrice(coffeePrice);
    }

    public void submitOrder(View view) {
//        String priceMessage="Darmoszka!:)";
//        displayMessage(priceMessage);
        int price=calculatePrice(orderedCoffes, coffeePrice);
        displayOrderSummary(price,orderedCoffes);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView TextView = (TextView) findViewById(R.id.quantityNumber);
        TextView.setText("" + number);
    }

    private void displayUnitPrice(int number) {
        TextView TextView = (TextView) findViewById(R.id.unitPrice);
        TextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayOrderSummary(int priceToPayForCoffees, int quantity){
        TextView priceToPayTextView=(TextView) findViewById(R.id.order_summary);
        priceToPayTextView.setText(orderSummary(priceToPayForCoffees,quantity));
    }

    private String orderSummary(int priceToPayForCoffees, int quantity){
        EditText name = (EditText) findViewById(R.id.name_of_person);
        String options="Opcje:\n";
        CheckBox chocolate=(CheckBox)findViewById(R.id.Chocolate);
        CheckBox ownCup=(CheckBox)findViewById(R.id.ownCup);
        CheckBox whippedCream=(CheckBox)findViewById(R.id.whippedCream);
        if(ownCup.isChecked()){
            options+=getResources().getString(R.string.with_own_cup)+"\n";
        }else{
            options+=getResources().getString(R.string.no_own_cup)+"\n";
            priceToPayForCoffees++;
        }
        if(whippedCream.isChecked()) {
            options += getResources().getString(R.string.with_whipped_cream)+"\n";
            priceToPayForCoffees+=quantity*2;
        }else{
            options+=getResources().getString(R.string.no_whipped_cream)+"\n";
        }
        if(chocolate.isChecked()){
            options+=getResources().getString(R.string.with_chocolate)+"\n";
            priceToPayForCoffees+=quantity*3;
        }else{
            options+=getResources().getString(R.string.no_chocolate)+"\n";
        }
        String priceToPayTextView = getResources().getString(R.string.name)+": " + name.getText().toString() + "\n" + options +
                getResources().getString(R.string.quantity)+": " + quantity + "\n"+getResources().getString(R.string.totalled)+": " + NumberFormat.getCurrencyInstance().format(priceToPayForCoffees) + "\n"+getResources().getString(R.string.thanks);
        Intent msg = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"));
        msg.putExtra(Intent.EXTRA_SUBJECT, "["+getResources().getString(R.string.app_name)+"] "+getResources().getString(R.string.order_for)+": "+ name.getText().toString());
        msg.putExtra(Intent.EXTRA_TEXT,priceToPayTextView);
        if(msg.resolveActivity(getPackageManager())!=null){
            startActivity(msg);
        }
        return priceToPayTextView;
    }

    private int calculatePrice(int quantity, int unitPrice){
        return (quantity*unitPrice);
    }
}
