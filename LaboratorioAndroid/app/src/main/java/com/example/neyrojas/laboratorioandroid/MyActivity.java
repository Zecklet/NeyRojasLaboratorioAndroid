package com.example.neyrojas.laboratorioandroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

;

public class MyActivity extends Activity {
    private int UltimoLayout = R.layout.activity_my;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(UltimoLayout);
        Log.d("Mi App", "estoy en el onCreate()");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void CargarContextoAgregar(View button){
        Intent intent = new Intent(this, AgregarPlatillo.class);
        startActivity(intent);
    }

    public void CargarContextoConsulta(View button){
        Intent intent = new Intent(this, Consultar.class);
        startActivity(intent);
    }


    public void LoginWithTwitter(View button){
        ParseTwitterUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Twitter!");
                    IniciarSession(user);
                } else {
                    Log.d("MyApp", "User logged in through Twitter!");
                    IniciarSession(user);
                }
            }
        });
    }

    public void LoginWithFacebook(View button){
        ParseFacebookUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    IniciarSession(user);
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    IniciarSession(user);
                }
            }
        });
    }

    public void IniciarSession(ParseUser pUsuario){
        Usuario.getInstancia().setNombreUsuario(pUsuario.getUsername());
        Usuario.getInstancia().setIdUsuario(pUsuario.getSessionToken());
        //Intent intent = new Intent(getBaseContext(), Seleccion.class);
        //startActivity(intent);
        UltimoLayout = R.layout.seleccion;
        setContentView(UltimoLayout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}
