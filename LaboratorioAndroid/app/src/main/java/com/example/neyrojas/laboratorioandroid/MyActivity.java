package com.example.neyrojas.laboratorioandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.example.neyrojas.laboratorioandroid.AgregarPlatillo;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class MyActivity extends Activity {

    private static final int GALLERY_KITKAT_INTENT_CALLED = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ParseQueryAdapter<ParseObject> foodAdapter;
    private ParseQueryAdapter<ParseObject> custom;
    private ListView listView;
    private String ImagenPath;
    private byte[] DatosImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Mi App", "estoy en el onCreate()");
        CargarContextoPrincipal();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("On Start()","Esta apunto de cargar los datos de parse");
        //custom = new FoodAdapter(this);
        //listView = (ListView) findViewById(R.id.list);
        //listView.setAdapter(custom);
        //custom.loadObjects();
        Log.d("On Start()","Solicitud envidad");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

//        Button button = (Button) findViewById(R.id.btnCargar);
//
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Log.d("on click", "BOTON CREADO APROPIADAMENTE");
//                foodAdapter.loadObjects();
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("Food");
//                query.findInBackground(new FindCallback<ParseObject>() {
//                    public void done(List<ParseObject> scoreList, ParseException e) {
//                        if (e == null) {
//                            Log.d("score", "Retrieved " + scoreList.size() + " scores");
//
//                        } else {
//                            Log.d("score", "Error: " + e.getMessage());
//                        }
//                    }
//                });
//            }
//
//        });

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


    public void CargarContextoPrincipal(){
        setContentView(R.layout.activity_my);

  /*      Button botonAgregar = (Button) findViewById(R.id.btnIrAgregar);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarContextoAgregar();
            }
        });

        Button botonConsultar = (Button) findViewById(R.id.btnIrConsultar);
        botonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarContextoConsulta();
            }
        });*/
    }

    public void CargarContextoAgregar(View button){
        setContentView(R.layout.agregar);
    }

    public void CargarContextoConsulta(View button){
        setContentView(R.layout.consultar);
        CargarElementos();
    }

    public void CargarElementos(){
        foodAdapter = new FoodAdapter(this);
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(foodAdapter);
        foodAdapter.loadObjects();
    }

    public void ObtenerFoto(View button){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void onClickEnviar(View button){

        Log.d("score", "Estoy a punto de enviar informacion al servidor scores");
        Log.d("score", ImagenPath);
        Log.d("score", "+"+ DatosImagen);

        //byte[] data = "Working at Parse is great!".getBytes();
        ParseFile file = new ParseFile(ImagenPath, DatosImagen);

        ParseObject jobApplication = new ParseObject("Food");
        jobApplication.put("Name", "Joe Smith");
        jobApplication.put("Type", "Platillo especial");
        jobApplication.put("Image", file);
        jobApplication.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) {
                    Log.d("GUARDADO", "saved in background");
                }
                else {
                    Log.d("ERROR", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                            "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    Log.d("ERROR MENSAJE", e.getMessage());
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_KITKAT_INTENT_CALLED && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imSubir);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));



            Bitmap bmp = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 20, stream);
            DatosImagen = stream.toByteArray();

            File f = new File("" + picturePath);
            ImagenPath = f.getName();
        }
    }

}
