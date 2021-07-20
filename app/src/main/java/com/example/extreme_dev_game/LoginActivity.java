package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.extreme_dev_game.entidades.Estudiante;
import com.example.extreme_dev_game.entidades.Usuarios;
import com.example.extreme_dev_game.services.apiservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText nombre,contra;
    Button iniciar,salir,olvido,crear_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.InicializarControles();
    }

    public void InicializarControles(){
        nombre=(EditText)findViewById(R.id.txtUsuario);
        contra=(EditText)findViewById(R.id.txtContrasena);
        iniciar=(Button)findViewById(R.id.iniciar);
        salir=(Button)findViewById(R.id.salir);
        olvido=(Button)findViewById(R.id.olvido);
        crear_usuario=(Button)findViewById(R.id.nuevo);
    }

    public void IniciarSession(View v){
        try {
            String user = nombre.getText().toString();
            String pass = contra.getText().toString();

            Call<Estudiante> response = apiservice.getApiService().getEstudianteLogin(user,pass);
            response.enqueue(new Callback<Estudiante>() {
                @Override
                public void onResponse(Call<Estudiante> call, Response<Estudiante> response) {
                    if (response.isSuccessful()){
                        Estudiante estudiante = response.body();
                        if (estudiante != null){

                            Usuarios user =
                                    new Usuarios(
                                            Integer.parseInt(estudiante.getId()),
                                            estudiante.getEmail(),
                                            "",
                                            estudiante.getNombre_completo()
                                    );


                            Toast.makeText(getApplicationContext(),"Inicio de Sesion Exitoso",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),IntroduccionActivity.class));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"no response",Toast.LENGTH_LONG).show();
                        int x = 1;
                    }
                }

                @Override
                public void onFailure(Call<Estudiante> call, Throwable t) {
                    int x = 1;
                }
            });
        }catch (Exception e){
            int x = 1;
        }
    }

    public void InsertarUsuario(View v){
        startActivity(new Intent(getApplicationContext(),InsertarUsuarioActivity.class));
    }

    public void RecuperarCuenta(View v){
        startActivity(new Intent(getApplicationContext(),RecuperarPasswordActivity.class));

    }

    public void SalirJuego(View view) {
        finish();
        System.exit(0);
    }
}