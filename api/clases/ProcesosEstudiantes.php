<?php
  class ProcesosEstudiantes{
    
    function ValidarEstudiante($u,$p){
      $con = new Conexion();
      
      $userExist = mysqli_query($con->Conectar(),"SELECT * FROM usuarios WHERE usuario ='{$u}'");
      if($userExist){
        $query = "CALL VerificarUsuarioEstudiante('{$u}','{$p}')";
        $login = mysqli_query($con->Conectar(),$query);
        $countRows = mysqli_num_rows($login);
        if($countRows > 0){
          return mysqli_fetch_object($login);
        }
      }
      return null;
    }

    function ValidarEstudianteEmail($u){
      $con = new Conexion();
      $userExist = mysqli_query($con->Conectar(),"SELECT * FROM usuarios WHERE usuario ='{$u}'");
      if($userExist){
        $countRows = mysqli_num_rows($userExist);
        if($countRows > 0){
          return true;
        }
      }
      return false;
    }


    function ObtenerFacultades(){
      $con = new Conexion();

      $query = "CALL ObtenerFacultades()";
      $allFacultad = mysqli_query($con->Conectar(),$query);
      $countRows = mysqli_num_rows($allFacultad);
      if($countRows > 0){
        $datos = array();
        while($row = mysqli_fetch_assoc($allFacultad)){
            $datos[] = $row;
        }
        return $datos;
      }
      return [];
    }

    function RegistrarEstudianteUsuario($n,$c,$e,$f,$em,$p){
      $con = new Conexion();

      $query ="CALL InsertarEstudianteUsuairo('{$n}','{$c}','{$e}','{$f}','{$em}','{$p}')";

      return mysqli_query($con->Conectar(),$query);

    }
  }
?>