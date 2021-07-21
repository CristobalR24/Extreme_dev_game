package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
    }

    public void RegresarMenu(View view) {
        finish();
    }

    public void IraContacto(View view) {
        startActivity(new Intent(getApplicationContext(),ContactanosActivity.class));
    }
}