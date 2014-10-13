package com.example.neyrojas.laboratorioandroid;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;

public class AndroidFood extends android.app.Application {

	public void onCreate() {
        Log.d("Mi App", "Inicializando parse");
        Parse.initialize(this, "2XORI1VXD2z0wTinJjVrAvAidx7aOpesJ7SE9kYk", "qyFfqHdIBPHMb1h0zsivjZkKkl3iEvtu75lKKR2S");
	}

}
