package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.alexlondon07.arquitecturamvpbase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showCustomers(View view){

        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        startActivity(intent);

    }
    public void showProducts(View view){
        Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
        startActivity(intent);
    }
}
