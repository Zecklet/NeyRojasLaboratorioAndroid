package com.example.neyrojas.laboratorioandroid;

import com.parse.ParseUser;

/**
 * Created by Ney Rojas on 25/10/2014.
 */
public class Usuario {

    private static Usuario Instancia;
    private String NombreUsuario;
    private String Id;


    private Usuario(){

    }

    public static Usuario getInstancia(){
        if(Instancia==null){
            Instancia = new Usuario();
        }
        return Instancia;
    }

    public void setNombreUsuario(String pNombreUsuario){
        this.NombreUsuario = pNombreUsuario;
    }

    public void setIdUsuario(String pIdUsuario){
        this.Id = pIdUsuario;
    }

    public String getNombreUsuario(){
        return this.NombreUsuario;
    }

    public String getIdUsuario(){
        return this.Id;
    }
}
