package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    String _user;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent i = getIntent();
        _user=i.getStringExtra("usuario");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.bensound_dreams_rf);
        mediaPlayer.setLooping(true); //musica en loop
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    public void SalirJuego(View view) {

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void IraJugar(View view) {

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

    public void IraVerRanking(View view) {
        startActivity(new Intent(getApplicationContext(),RankingActivity.class));
    }
}