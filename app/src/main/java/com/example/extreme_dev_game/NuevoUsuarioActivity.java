package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.extreme_dev_game.entidades.Estudiante;
import com.example.extreme_dev_game.R;
import com.example.extreme_dev_game.entidades.Usuarios;
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
    List<String> _years = new ArrayList<>();;

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
        // en este proceso obtenemos las facultades desde el api y las guardamos en un spinner
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
                    //spinner de facultades
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,facultadesListString);

                    codigo_grupo.setAdapter(adapter);
                    //spinner de edad
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
        //_years=Arrays.asList("1","2","3","4");
        for (int i = 1; i <= 100; i++) {
            _years.add(Integer.toString(i));
        }
    }

    public void Registrar(View view){
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre_completo(nombre_completo.getText().toString());
            estudiante.setCedula(cedula.getText().toString());

            estudiante.setEdad(year.getSelectedItem().toString());
            estudiante.setEmail(correo.getText().toString());
            estudiante.setPassword(contra.getText().toString());

            String selectedFac = codigo_grupo.getSelectedItem().toString();
            String facultadId = "";
            //aqui obtenemos a partir de la facultad seleccionada su ID
            for(Facultad facultad : _facultades){
                if (facultad.getFacultad().equals(selectedFac)){
                    facultadId = facultad.getId();
                }
            }
            estudiante.setFacultad(facultadId);
            //verifica que el usuario haya llenado todos los campos
            if(!TextUtils.isEmpty(estudiante.getNombre_completo()) && !TextUtils.isEmpty(estudiante.getCedula()) && !TextUtils.isEmpty(estudiante.getEdad()) && !TextUtils.isEmpty(estudiante.getFacultad()) && !TextUtils.isEmpty(estudiante.getEmail()) && !TextUtils.isEmpty(estudiante.getPassword()))
            {
                Call<Boolean> response = apiservice.getApiService().getEstudianteEmail(estudiante.getEmail());
                response.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()){
                            Boolean existe = response.body();
                            if (!existe){ //si el correo no existe(esta disponible) crea el usuario
                                Call<Integer> resp = apiservice.getApiService().postRegistrarEstudiante(estudiante);
                                resp.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.isSuccessful()) {
                                            UsuarioInsertado();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                    }
                                });

                            }
                            else{Toast.makeText(getApplicationContext(),"Este correo ya esta registrado",Toast.LENGTH_LONG).show();}
                        }else
                            Toast.makeText(getApplicationContext(),"error en el servidor",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        int x = 1;
                    }
                });

            }
            else
                Toast.makeText(this.getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();

        }catch (Exception e){
        }
    }

    public void UsuarioInsertado(){
        Toast.makeText(this.getApplicationContext(),"Usuario insertado",Toast.LENGTH_LONG).show();
        finish();
    }

    public void SalirNuevoUser(View view) {
        finish();
    }
}