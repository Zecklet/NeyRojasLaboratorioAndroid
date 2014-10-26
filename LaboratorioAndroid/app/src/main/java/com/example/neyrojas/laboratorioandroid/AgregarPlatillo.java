package com.example.neyrojas.laboratorioandroid;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class AgregarPlatillo extends Activity {

    private static final int GALLERY_KITKAT_INTENT_CALLED = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private String ImagenPath;
    private byte[] DatosImagen;
    private String mArrayPaises[];
    private ProgressBar spinner;
    private List<ParseObject> UltimoResultadoParse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        CargarPaises();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agregar_platillo, menu);
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

    public void ObtenerFoto(View button){
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void CargarPaises(){
        spinner.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PAISES");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    mArrayPaises = new String[scoreList.size()];

                    for (int i = 0; i < scoreList.size();i++){
                        mArrayPaises[i] = scoreList.get(i).getString("nombre");
                    }
                    Spinner s = (Spinner) findViewById(R.id.spPaises);

                    ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_list_item_1, mArrayPaises);
                    s.setAdapter(adapter);
                    s.setSelection(0);

                    spinner.setVisibility(View.GONE);

                    UltimoResultadoParse = scoreList;
                    ColocarImagenPais(0);

                    s.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            Spinner s = (Spinner) findViewById(R.id.spPaises);
                            ColocarImagenPais(s.getSelectedItemPosition());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });


                } else {
                    spinner.setVisibility(View.GONE);
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void ColocarImagenPais(int pIndice){
        ParseImageView foodImage = (ParseImageView) findViewById(R.id.imPaisAgregar);
        ParseFile imageFile = UltimoResultadoParse.get(pIndice).getParseFile("Image");
        if	(imageFile != null)	{
            foodImage.setParseFile(imageFile);
            foodImage.loadInBackground();
        }
    }

    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id) {
        Spinner s = (Spinner) findViewById(R.id.spPaises);
        ColocarImagenPais(s.getSelectedItemPosition());
    }


    public void onClickEnviar(View button){

        spinner.setVisibility(View.VISIBLE);

        String mPais = ((Spinner)findViewById(R.id.spPaises)).getSelectedItem().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PAISES");
        query.whereEqualTo("nombre", mPais);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> paisesList, ParseException e) {

                ParseFile file = new ParseFile(ImagenPath, DatosImagen);

                String mNombre = ((EditText)findViewById(R.id.txtNombre)).getText().toString();
                String mDescripcion = ((EditText)findViewById(R.id.txtDescripcion)).getText().toString();

                ParseObject jobApplication = new ParseObject("Food");
                jobApplication.put("Name", mNombre);
                jobApplication.put("Type", mDescripcion);
                jobApplication.put("Pais", paisesList.get(0));
                jobApplication.put("Image", file);
                jobApplication.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null) {
                            spinner.setVisibility(View.GONE);

                        }
                        else {
                            spinner.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_KITKAT_INTENT_CALLED && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
