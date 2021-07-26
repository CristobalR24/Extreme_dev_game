package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroduccionActivity extends AppCompatActivity {
    String _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduccion);

        Intent i = getIntent();
        _user=i.getStringExtra("usuario");
    }

    public void SalirJuego(View view) {
        finish();
    }

    public void IrAMenu(View view) {
        Intent i = new Intent(getApplicationContext(),MenuActivity.class);
        i.putExtra("usuario",_user);
        startActivity(i);
        finish();
    }
}