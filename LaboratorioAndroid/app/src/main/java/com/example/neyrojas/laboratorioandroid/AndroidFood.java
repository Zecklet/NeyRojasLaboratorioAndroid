package com.example.neyrojas.laboratorioandroid;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class AndroidFood extends android.app.Application {

	public void onCreate() {
        Log.d("Mi App", "Inicializando parse");
        Parse.initialize(this, "2XORI1VXD2z0wTinJjVrAvAidx7aOpesJ7SE9kYk", "qyFfqHdIBPHMb1h0zsivjZkKkl3iEvtu75lKKR2S");
        ParseFacebookUtils.initialize("393535750799943");
        ParseTwitterUtils.initialize("V3oCR4iF83M3mBwlFoZ6YXNJa", "YO10JHITQwFi0Fh7quJKW6Vaz7fJywmeZf8b4n3KvTvrCbBEW5");
	}

}
