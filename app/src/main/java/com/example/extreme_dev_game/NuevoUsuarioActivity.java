package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.extreme_dev_game.entidades.Estudiante;
import com.example.extreme_dev_game.R;
import com.example.extreme_dev_game.responses.Facultad;
import com.example.extreme_dev_game.services.apiservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoUsuarioActivity extends AppCompatActivity {
    EditText nombre_completo,cedula,correo,contra;
    Spinner codigo_grupo, year;

    List<Facultad> _facultades;
    List<String> _years;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        this.IniControles();
        this.ObtenerYears();
        this.LoadSpinner();
    }

    private void IniControles() {
        nombre_completo=findViewById(R.id.txtNombreUsuario);
        cedula=findViewById(R.id.Cedula);
        correo=findViewById(R.id.CorreoUsuario);
        contra=findViewById(R.id.ContraUsuario);
        codigo_grupo=findViewById(R.id.CodigoGrupo);
        year=findViewById(R.id.Year);
    }

    private void LoadSpinner() {
        Call<List<Facultad>> response = apiservice.getApiService().getAllFacultades();
        response.enqueue(new Callback<List<Facultad>>() {
            @Override
            public void onResponse(Call<List<Facultad>> call, Response<List<Facultad>> response) {
                if (response.isSuccessful()){
                    _facultades = response.body();
                    List<String> facultadesListString = new ArrayList<String>();
                    for(Facultad facultad : _facultades){
                        facultadesListString.add(facultad.getFacultad());
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,facultadesListString);

                    codigo_grupo.setAdapter(adapter);

                    ArrayAdapter<String> adapter2 =
                            new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,_years);

                    year.setAdapter(adapter2);
                }
            }
            @Override
            public void onFailure(Call<List<Facultad>> call, Throwable t) {

            }
        });
    }

    private void ObtenerYears(){
        _years=Arrays.asList("1","2","3","4");
    }

    public void Registrar(View view){
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre_completo(nombre_completo.getText().toString());
            estudiante.setCedula(cedula.getText().toString());
            estudiante.setEdad("20");
            estudiante.setEmail(correo.getText().toString());
            estudiante.setPassword(contra.getText().toString());

            String selectedFac = codigo_grupo.getSelectedItem().toString();
            String facultadId = "";
            for(Facultad facultad : _facultades){
                if (facultad.getFacultad().equals(selectedFac)){
                    facultadId = facultad.getId();
                }
            }
            estudiante.setFacultad(facultadId);

            Call<Integer> response = apiservice.getApiService().postRegistrarEstudiante(estudiante);
            response.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()){
                        int x = 1;
                    }else{
                        int x = 1;
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    int x = 1;
                }
            });

        }catch (Exception e){
            int x= 1;
        }
    }

    public void SalirNuevoUser(View view) {
        finish();
    }
}