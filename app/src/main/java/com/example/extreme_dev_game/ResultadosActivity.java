package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.example.extreme_dev_game.datos.DbProccess;
import com.example.extreme_dev_game.entidades.Partida;
import com.example.extreme_dev_game.request.PartidaRequest;
import com.example.extreme_dev_game.services.apiservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    TextView nivel,juego,jugador,fecha,puntaje,partida,correctas;

    List<Partida> _partidas = new ArrayList<>();
    int cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        int partida = getIntent().getIntExtra("Partida",0);
        cantidad = getIntent().getIntExtra("Cantidad",0);

        InicializarControles();
        LoadListView(partida);
        MapearCampos();
        GuardarPartidaApi(_partidas);
    }
    @Override
    public void onBackPressed() { /*no hacer nada*/}

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.bensound_sweet_rf);
        mediaPlayer.setLooping(true); //musica en loop
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private void GuardarPartidaApi(List<Partida> partidas) {
        PartidaRequest request = new PartidaRequest();
        request.setJugador(partidas.get(0).getJugador());
        request.setJuego(partidas.get(0).getJuego());
        request.setFecha(partidas.get(0).getFecha());
        request.setHora(partidas.get(0).getHora());
        request.setNivel(partidas.get(0).getNivel());
        request.setPartida(partidas.get(0).getPartida());
        request.setPuntaje(ObtenerPuntaje(partidas));
        request.setDetalle(partidas);

        Call<Integer> response = apiservice.getApiService().postRegistrarPartida(request);
        response.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    int registrado = response.body();
                    if (registrado > 0){

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                int x = 1;
            }
        });

    }

    private void MapearCampos() {
        nivel.setText("Nivel: "+_partidas.get(0).getNivel());
        juego.setText("Juego: "+_partidas.get(0).getJuego());
        jugador.setText("Jugador: "+_partidas.get(0).getJugador());
        fecha.setText("Fecha: "+_partidas.get(0).getFecha());
        correctas.setText("Respuestas correctas: "+cantidad+"/10");
        puntaje.setText("Puntaje: "+Integer.toString(ObtenerPuntaje(_partidas)));
        partida.setText("Partida: "+Integer.toString(_partidas.get(0).getPartida()));
    }

    //obtenemos el puntaje total
    private int ObtenerPuntaje(List<Partida> partidas){
        int puntaje = 0;
        for (Partida part : partidas){
            puntaje = puntaje + part.getPuntaje();
        }

        return puntaje;
    }

    private void LoadListView(int partida) {
        DbProccess db = new DbProccess(getApplicationContext());
        _partidas = db.ObtenerPartidaById(partida);
    }


    private void InicializarControles() {
        nivel = (TextView)findViewById(R.id.txtNivel);
        juego = (TextView)findViewById(R.id.txtJuego);
        jugador = (TextView)findViewById(R.id.txtJugador);
        fecha = (TextView)findViewById(R.id.txtFecha);
        puntaje = (TextView)findViewById(R.id.txtPuntos);
        partida = (TextView)findViewById(R.id.txtPartida);
        correctas = (TextView)findViewById(R.id.txtAcertadas);
    }

    public void RegresarSeleccion(View view) {
        startActivity(new Intent(getApplicationContext(),SeleccionNivelActivity.class));
    }
}