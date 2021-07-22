package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeleccionNivelActivity extends AppCompatActivity {
    String _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_nivel);

        Intent i = getIntent();
        _user=i.getStringExtra("usuario");
    }

    public void RegresarMenu(View view) {
        startActivity(new Intent(getApplicationContext(),MenuActivity.class));
    }

    public void IraBasico(View view) {
        Intent i= new Intent(this.getApplicationContext(),JugarActivity.class);
        i.putExtra("nivel",1);
        i.putExtra("usuario",_user);
        startActivity(i);
    }

}