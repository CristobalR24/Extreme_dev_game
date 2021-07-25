package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.extreme_dev_game.adapters.TablaListViewAdapter;
import com.example.extreme_dev_game.entidades.Estudiante;
import com.example.extreme_dev_game.entidades.Tabla;
import com.example.extreme_dev_game.entidades.Usuarios;
import com.example.extreme_dev_game.services.apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity {

    ListView lstTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        InicializarControles();

        Intent i = getIntent();

        int num = i.getIntExtra("num",0);
        String ced = i.getStringExtra("Cedula");

        if(num>0){
         LoadFiltro(ced);
        }
        else
         LoadListView();
    }

    private void LoadFiltro(String ced) {
        Call<List<Tabla>> response = apiservice.getApiService().getAllTableCed(ced);
        response.enqueue(new Callback<List<Tabla>>() {
            @Override
            public void onResponse(Call<List<Tabla>> call, Response<List<Tabla>> response) {
                if (response.isSuccessful()){
                    List<Tabla> table = response.body();
                    TablaListViewAdapter adapter = new TablaListViewAdapter(getApplicationContext(),table);
                    lstTabla.setAdapter(adapter);} }
            @Override
            public void onFailure(Call<List<Tabla>> call, Throwable t) {
            }
        });
    }

    private void LoadListView() {
        Call<List<Tabla>> response = apiservice.getApiService().getAllTable();
        response.enqueue(new Callback<List<Tabla>>() {
            @Override
            public void onResponse(Call<List<Tabla>> call, Response<List<Tabla>> response) {
                if (response.isSuccessful()){
                    List<Tabla> table = response.body();
                    TablaListViewAdapter adapter = new TablaListViewAdapter(getApplicationContext(),table);
                    lstTabla.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Tabla>> call, Throwable t) {

            }
        });
    }

    private void InicializarControles() {
        lstTabla = (ListView)findViewById(R.id.ListaPuntos);
    }

    public void RankingSalir(View view) {finish();
    }

    public void FiltrarCedula(View view) {
        startActivity(new Intent(getApplicationContext(),FiltrarRankingActivity.class));
    }
}