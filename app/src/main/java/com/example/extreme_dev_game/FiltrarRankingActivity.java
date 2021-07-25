package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FiltrarRankingActivity extends AppCompatActivity {
    EditText arg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_ranking);
        this.InitControl();
    }

    public void RegresarRanking(View view) {
        finish();
    }

    private void InitControl(){
        arg1=findViewById(R.id.CedulaBuscada);
    }

    public void Filtrar(View view) {
        if(!TextUtils.isEmpty(arg1.getText().toString())){
            Intent i = new Intent(getApplicationContext(),RankingActivity.class);
            i.putExtra("num",1);
            i.putExtra("Cedula",arg1.getText().toString());
            startActivity(i);
        }
        else
            Toast.makeText(this.getApplicationContext(),"Debe llenar el campo",Toast.LENGTH_LONG).show();

    }
}