package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.extreme_dev_game.adapters.TablaListViewAdapter;
import com.example.extreme_dev_game.entidades.Tabla;
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
        LoadListView();
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
}