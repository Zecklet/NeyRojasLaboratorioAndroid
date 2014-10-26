package com.example.neyrojas.laboratorioandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.*;

import java.util.List;

public class FoodAdapter extends ParseQueryAdapter<ParseObject>	{

    private View cVista;

    public FoodAdapter(Context context, final String pNombreColumna, final String pValor) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Food");
                if (pNombreColumna!="" || pValor!="") {
                    query.whereContains(pNombreColumna, pValor);
                }
                query.include("Pais");
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if	(v	==	null)	{
            v	=	View.inflate(getContext(),	R.layout.food_item,	null);
        }
        super.getItemView(object, v, parent);
        ParseImageView	foodImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("Image");

        if	(imageFile != null)	{
            foodImage.setParseFile(imageFile);
            foodImage.loadInBackground();
        }

        ParseObject pais = object.getParseObject("Pais");

        TextView nameTextView = (TextView) v.findViewById(R.id.name);
        nameTextView.setText(object.getString("Name")  );
        TextView typeView = (TextView) v.findViewById(R.id.type);
        typeView.setText(object.getString("Type") );


                ParseImageView ImagenBandera = (ParseImageView) v.findViewById(R.id.iconBandera);
                ParseFile FileBandera = pais.getParseFile("imagen");

                if (FileBandera != null) {
                    ImagenBandera.setParseFile(FileBandera);
                    ImagenBandera.loadInBackground();
                }



        return v;
    }
}
