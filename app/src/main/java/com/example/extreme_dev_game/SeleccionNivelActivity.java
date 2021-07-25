package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class SeleccionNivelActivity extends AppCompatActivity {
    String _user;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_nivel);
        //mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.bensound_dreams_rf);
       // mediaPlayer.start();

        Intent i = getIntent();
        _user=i.getStringExtra("usuario");
    }

    @Override
    public void onBackPressed() { /*no hacer nada*/}

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.bensound_sweet_rf);
        mediaPlayer.setLooping(true); //musica en loop
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void RegresarMenu(View view) {
        startActivity(new Intent(getApplicationContext(),MenuActivity.class));
    }

    public void IraBasico(View view) {
        mediaPlayer.stop();
        Intent i= new Intent(this.getApplicationContext(),JugarActivity.class);
        i.putExtra("nivel",1);
        i.putExtra("usuario",_user);
        startActivity(i);
    }

}