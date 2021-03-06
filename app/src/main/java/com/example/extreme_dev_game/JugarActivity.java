package com.example.extreme_dev_game;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.extreme_dev_game.datos.DbProccess;
import com.example.extreme_dev_game.entidades.Partida;
import com.example.extreme_dev_game.entidades.Preguntas;
import com.example.extreme_dev_game.entidades.Respuestas;
import com.example.extreme_dev_game.entidades.Usuarios;
import com.example.extreme_dev_game.services.apiservice;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugarActivity extends AppCompatActivity {

    List<Preguntas> _preguntas;
    Preguntas _preguntaActual;

    LinearLayout lnRender;
    TextView nivel,tipo,pregunta;
    Button verdad,falso;

    DbProccess _db;
    int _numPartida = 0;
    String _jugador = "";
    String retro="";
    String _juego = "Extreme dev game";
    int _juegoId = 5;
    int _nivel = 1;
    int c_respuestas=0;

    List<String> _selectedCheckboxs = new ArrayList<>();

    MediaPlayer mediaPlayer;
    MediaPlayer buena;
    MediaPlayer error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        _db = new DbProccess(getApplicationContext());

        Intent i = getIntent();
        //obtenemos el nombre del jugador y el nivel seleccionado desde la seleccion de nivel
        _jugador=i.getStringExtra("usuario");
        _nivel=i.getIntExtra("nivel",0);

        buena = MediaPlayer.create(getApplicationContext(),R.raw.bonus_collect);
        error = MediaPlayer.create(getApplicationContext(),R.raw.answer_fail);

        _numPartida = _db.ObtenerSiguientePartida("Extreme dev game");

        InicializarControles();
        ObtenerPreguntas();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.summer_bensound_rf);
        mediaPlayer.setLooping(true); //musica en loop
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private void ObtenerPreguntas() {
        Call<List<Preguntas>> response = apiservice.getApiService().getPreguntas(_juegoId,_nivel);
        response.enqueue(new Callback<List<Preguntas>>() {
            @Override
            public void onResponse(Call<List<Preguntas>> call, Response<List<Preguntas>> response) {
                if (response.isSuccessful()){
                    _preguntas = response.body();
                    Collections.shuffle(_preguntas); //mezclamos el contenido de la lista para a??adir aleatoriedad
                    if (_preguntas.size() > 0){
                        nivel.setText(_preguntas.get(0).getNivel());
                        _preguntaActual = _preguntas.get(0);
                        RenderPrimeraPregunta();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Preguntas>> call, Throwable t) {
                int x = 1;
            }
        });
    }

    private void InicializarControles() {
        lnRender = (LinearLayout)findViewById(R.id.renderRespuestas);

        nivel = (TextView)findViewById(R.id.lblNivel);
        tipo = (TextView)findViewById(R.id.lblTipoPregunta);
        pregunta = (TextView)findViewById(R.id.lblPregunta);

        verdad=findViewById(R.id.BVerdadero);
        falso=findViewById(R.id.BFalso);
    }

    private void RenderPrimeraPregunta(){
        pregunta.setText(_preguntaActual.getPregunta());
        tipo.setText(_preguntaActual.getTipo());

        if (_preguntaActual.getTipo_pregunta_id().equals("2")){
            RenderPreguntaOpcionMultiple(_preguntaActual);
        }else if(_preguntaActual.getTipo_pregunta_id().equals("3")){
            RenderPreguntaVF(_preguntaActual);
        }else if(_preguntaActual.getTipo_pregunta_id().equals("4")){
            RenderPreguntaMejorOpcion(_preguntaActual);
        }
    }

    private void RenderPregunta(Preguntas preguntaAnterior){
        int indiceActual = _preguntas.indexOf(preguntaAnterior);

        if(indiceActual == 9){ //si se acabaron las preguntas 0 a 9 (10 preguntas)
            Intent i = new Intent(getApplicationContext(),ResultadosActivity.class);
            i.putExtra("Cantidad",c_respuestas);
            i.putExtra("Partida",_numPartida);
            startActivity(i);
        }else{
            _preguntaActual = _preguntas.get(_preguntas.indexOf(preguntaAnterior)+1);

            pregunta.setText(_preguntaActual.getPregunta());
            tipo.setText(_preguntaActual.getTipo());

            //obtenemos la estructura de la pregunta a partir del tipo de pregunta actual
            if (_preguntaActual.getTipo_pregunta_id().equals("2")){
                RenderPreguntaOpcionMultiple(_preguntaActual);
            }else if(_preguntaActual.getTipo_pregunta_id().equals("3")){
                RenderPreguntaVF(_preguntaActual);
            }else if(_preguntaActual.getTipo_pregunta_id().equals("4")){
                RenderPreguntaMejorOpcion(_preguntaActual);
            }
        }
    }

    private void RenderPreguntaMejorOpcion(Preguntas pregunta) {

        RadioGroup group = new RadioGroup(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        group.setLayoutParams(params);

        for(Respuestas respuesta : pregunta.getRespuestas()){
            RadioButton newCheck = new RadioButton(getApplicationContext());

            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());
            newCheck.setId(View.generateViewId());

            group.addView(newCheck);
        }

        lnRender.addView(group);

        Button validar = new Button(getApplicationContext());
        validar.setLayoutParams(params);
        validar.setText("Validar Respuestas");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puntaje = 0;
                RadioButton checkResponse = group.findViewById(group.getCheckedRadioButtonId());
                String selectedResponse = checkResponse.getText().toString();

                for (Respuestas res : pregunta.getRespuestas()){
                    if (res.getRespuesta().equals(selectedResponse) && res.getCorrecta().equals("1")){
                        puntaje = Integer.parseInt(res.getPuntaje());
                    }
                }

                lnRender.removeAllViews();
                RenderPregunta(pregunta);

                GuardarRespuesta(pregunta,selectedResponse,puntaje);
            }
        });

        lnRender.addView(validar);
    }

    private void RenderPreguntaVF(Preguntas pregunta) {

        verdad.setVisibility(View.VISIBLE);
        falso.setVisibility(View.VISIBLE);

        verdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puntaje = 0;
                String respuestas = "";

                //verifica si la pregunta es correcta o no
                if (pregunta.getRespuestas().get(0).getRespuesta().equals("Si")){
                    puntaje = Integer.parseInt(pregunta.getRespuestas().get(0).getPuntaje());
                    respuestas = "Si";
                    buena.start();
                    c_respuestas+=1;
                    Toast.makeText(getApplicationContext(),"Respuesta correcta", Toast.LENGTH_LONG).show();
                }else{
                    error.start();
                    respuestas = "No";
                    retro=pregunta.getRespuestas().get(0).getRetroalimentacion();
                    showCustomDialog(retro);

                }
                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
                lnRender.addView(verdad); //arriba
                lnRender.addView(falso);
            }
        });

        falso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puntaje = 0;
                String respuestas = "";
                //verifica si la pregunta es correcta o no
                if (pregunta.getRespuestas().get(0).getRespuesta().equals("No")){
                    puntaje = Integer.parseInt(pregunta.getRespuestas().get(0).getPuntaje());
                    respuestas = "No";
                    buena.start();
                    c_respuestas+=1;
                    Toast.makeText(getApplicationContext(),"Respuesta Correcta", Toast.LENGTH_LONG).show();
                }else{
                    respuestas = "Si";
                    error.start();
                    retro= pregunta.getRespuestas().get(0).getRetroalimentacion();
                    showCustomDialog(retro);
                }
                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
                lnRender.addView(verdad); //arriba
                lnRender.addView(falso);
            }
        });
        
    }

    public void showCustomDialog(String retro) {
        TextView retroalimento,anima;
        Button ok;

        Dialog dialog = new Dialog(JugarActivity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.estilo_dialog);
        anima=dialog.findViewById(R.id.animo);
        retroalimento=dialog.findViewById(R.id.retroalimentacion);
        ok=dialog.findViewById(R.id.retro_ok);

        List<String> texto = new ArrayList<>(List.of("??Animo tu puedes hacerlo!",
                "??no te rindas!","??estamos aprendiendo!","??equivocarse es parte del aprendizaje!",
                "no hay nada que ense??e m??s que equivocarse","??sigue adelante!",
                "??una experiencia nunca es un fracaso!","??Sigamos aprendiendo!"));
        Collections.shuffle(texto);

        retroalimento.setText(retro);
        anima.setText(texto.get(0));
        dialog.show();
        ok.setOnClickListener(v ->{
            dialog.dismiss();
        });

    }

    private void RenderPreguntaOpcionMultiple(Preguntas pregunta) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for(Respuestas respuesta : pregunta.getRespuestas()){
            CheckBox newCheck = new CheckBox(getApplicationContext());

            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());

            newCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newCheck.isChecked()){
                        _selectedCheckboxs.add(newCheck.getText().toString());
                    }else{
                        _selectedCheckboxs.remove(newCheck.getText().toString());
                    }
                }
            });

            lnRender.addView(newCheck);
        }

        Button validar = new Button(getApplicationContext());
        validar.setLayoutParams(params);
        validar.setText("Validar Respuestas");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puntaje = 0;
                String respuestas = "";
                for (String res : _selectedCheckboxs){
                    respuestas = respuestas + "," + res;
                    for (Respuestas resp : pregunta.getRespuestas()){
                        if (resp.getRespuesta().equals(res) && resp.getCorrecta().equals("1")){
                            puntaje = puntaje + Integer.parseInt(resp.getPuntaje());
                        }
                    }
                }
                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        lnRender.addView(validar);
    }

    private void GuardarRespuesta(Preguntas pregunta, String respuestas, int puntaje){
        try {
            Partida respuesta = new Partida();
            respuesta.setPartida(_numPartida);
            respuesta.setJugador(_jugador);
            respuesta.setJuego(_juego);
            respuesta.setNivel(pregunta.getNivel());
            respuesta.setPregunta(pregunta.getPregunta());
            respuesta.setRespuestas(respuestas);
            respuesta.setPuntaje(puntaje);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat tf = new SimpleDateFormat("hh:mm");
            respuesta.setFecha(df.format(new Date()));
            respuesta.setHora(tf.format(new Date()));

            _selectedCheckboxs.clear();
            _db.InsentarRespuestaPartida(respuesta,_numPartida);
        }catch (Exception e){
            int x = 1;
        }
    }

    public void Salir_Juego(View view) {
        finish();
    }
}