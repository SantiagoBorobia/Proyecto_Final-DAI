package com.example.santiagoborobia.quinielamundial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Main8Activity extends AppCompatActivity {

    //PAGINA WEB DE LA FIFA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        //Escribimos el url de la pagina del mundial, para revisar resultados en tiempo real
        String url= "http://www.fifa.com/worldcup/matches/index.html";

        //Recuperamos el web view
        WebView wvWeb = (WebView)findViewById(R.id.wvPaginaMundial);
        //Habilitamos la opcion de la pagina web y cargamos el url de la pagina.
        wvWeb.getSettings().setJavaScriptEnabled(true);
        wvWeb.loadUrl(url);
    }
}
