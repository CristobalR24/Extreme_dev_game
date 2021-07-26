package com.example.extreme_dev_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.extreme_dev_game.datos.DbProccess;
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
        try {  //obtenemos el nombre de usuario y contraseña
            String user = nombre.getText().toString();
            String pass = contra.getText().toString();

            //llamado a la interface ApInterface donde se obtiene todos los metodos definidos en la api
            Call<Estudiante> response = apiservice.getApiService().getEstudianteLogin(user,pass);
            response.enqueue(new Callback<Estudiante>() {
                @Override
                public void onResponse(Call<Estudiante> call, Response<Estudiante> response) {
                    if (response.isSuccessful()){
                        Estudiante estudiante = response.body();
                        if (estudiante != null){
                    /*los datos recibidos del api los guardamos en un objeto
                      Usuario para acceder a sus datos, en este caso necesitamos el nombre del usuario*/
                            Usuarios user =
                                    new Usuarios(
                                            Integer.parseInt(estudiante.getId()),
                                            estudiante.getEmail(),
                                            "",
                                            estudiante.getNombre_completo()
                                    );
                            Intent i = new Intent(getApplicationContext(),IntroduccionActivity.class);
                            i.putExtra("usuario",user.getNombre());
                            startActivity(i);
                        }
                        else{Toast.makeText(getApplicationContext(),"Correo o contraseñas incorrectos",Toast.LENGTH_LONG).show();}
                    }else
                        Toast.makeText(getApplicationContext(),"error en el servidor",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Estudiante> call, Throwable t) {

                }
            });
        }catch (Exception e){
            int x = 1;
        }
    }

    public void InsertarUsuario(View v){
        startActivity(new Intent(getApplicationContext(),NuevoUsuarioActivity.class));
    }

    public void RecuperarCuenta(View v){
        startActivity(new Intent(getApplicationContext(),RecuperarPasswordActivity.class));

    }

    public void SalirdeJuego(View view) {
        finishAffinity();
        System.exit(0);
    }
}