package com.example.neyrojas.laboratorioandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;


public class Consultar extends Activity {

    private ParseQueryAdapter<ParseObject> foodAdapter;
    private ListView listView;
    private ProgressBar pbConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar);
        pbConsulta = (ProgressBar) findViewById(R.id.pbConsulta);
        pbConsulta.setVisibility(View.GONE);
        CargarElementos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.consultar, menu);
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


    public void CargarElementos() {
        pbConsulta.setVisibility(View.VISIBLE);
        foodAdapter = new FoodAdapter(this, "", "");
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(foodAdapter);
        foodAdapter.loadObjects();
        pbConsulta.setVisibility(View.GONE);
    }

    public void RealizarBusqueda(String pPalabra) {
        pbConsulta.setVisibility(View.VISIBLE);
        foodAdapter = new FoodAdapter(this, "Name", pPalabra);
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(foodAdapter);
        foodAdapter.loadObjects();
        pbConsulta.setVisibility(View.GONE);
    }

    public void onClickBuscar(View button) {
        String mPalabra = ((EditText) findViewById(R.id.txtBuscar)).getText().toString();
        RealizarBusqueda(mPalabra);
    }


}