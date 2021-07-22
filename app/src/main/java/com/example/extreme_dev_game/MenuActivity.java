package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void SalirJuego(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void IraJugar(View view) {
        startActivity(new Intent(getApplicationContext(),SeleccionNivelActivity.class));
    }

    public void IraAyuda(View view) {
        startActivity(new Intent(getApplicationContext(),AyudaActivity.class));
    }
}