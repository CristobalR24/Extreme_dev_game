package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    String _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent i = getIntent();
        _user=i.getStringExtra("usuario");
    }

    public void SalirJuego(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void IraJugar(View view) {
        //startActivity(new Intent(getApplicationContext(),SeleccionNivelActivity.class));
        Intent i = new Intent(getApplicationContext(),SeleccionNivelActivity.class);
        i.putExtra("usuario",_user);
        startActivity(i);
    }

    public void IraAyuda(View view) {
        startActivity(new Intent(getApplicationContext(),AyudaActivity.class));
    }

    public void VerLecciones(View view) {
        startActivity(new Intent(getApplicationContext(),LeccionActivity.class));
    }
}