package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroduccionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduccion);
    }

    public void SalirJuego(View view) {
        finish();
    }

    public void IrAMenu(View view) {
        startActivity(new Intent(getApplicationContext(),MenuActivity.class));
    }
}