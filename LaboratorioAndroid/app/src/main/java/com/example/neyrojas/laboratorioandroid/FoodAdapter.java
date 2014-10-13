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

public class FoodAdapter extends ParseQueryAdapter<ParseObject>	{
    public FoodAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Food");
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
        TextView nameTextView = (TextView) v.findViewById(R.id.name);
        nameTextView.setText(object.getString("Name")  );
        TextView typeView = (TextView) v.findViewById(R.id.type);
        typeView.setText(object.getString("Type") );
        return v;
    }
}
