package com.example.neyrojas.laboratorioandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ney Rojas on 25/10/2014.
 */
public class Seleccion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.seleccion, container, false);
    }

    public void CargarContextoAgregar(View button){
        Intent intent = new Intent(getActivity(), AgregarPlatillo.class);
        startActivity(intent);
    }

    public void CargarContextoConsulta(View button){
        Intent intent = new Intent(getActivity(), Consultar.class);
        startActivity(intent);
    }

}
