package com.example.extreme_dev_game.services;

import com.example.extreme_dev_game.entidades.Estudiante;
import com.example.extreme_dev_game.entidades.Juego;
import com.example.extreme_dev_game.entidades.Preguntas;
import com.example.extreme_dev_game.entidades.Tabla;
import com.example.extreme_dev_game.request.PartidaRequest;
import com.example.extreme_dev_game.responses.Facultad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApInterface {
    @GET("api.php?ep=login")
    Call<Estudiante> getEstudianteLogin(@Query("u") String u, @Query("p") String p);

    @GET("api.php?ep=registrocheck")
    Call<Boolean> getEstudianteEmail(@Query("u") String u);

    @GET("api.php?ep=juegos")
    Call<List<Juego>> getAllJuegos();

    @GET("api.php?ep=facultades")
    Call<List<Facultad>> getAllFacultades();

    @POST("api.php?ep=estudiantesSave")
    Call<Integer> postRegistrarEstudiante(@Body Estudiante estudiante);

    @POST("api.php?ep=partidaSave")
    Call<Integer> postRegistrarPartida(@Body PartidaRequest partida);

    @GET("api.php?ep=preguntas")
    Call<List<Preguntas>> getPreguntas(@Query("j") int juego,@Query("n") int nivel);

    @GET("api.php?ep=posiciones")
    Call<List<Tabla>> getAllTable();

    @GET("api.php?ep=PROE_posiciones")
    Call<List<Tabla>> proe_getAllTable();

    @GET("api.php?ep=filtro1")
    Call<List<Tabla>> getAllTableCed(@Query("c") String c);
}
