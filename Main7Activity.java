package com.example.santiagoborobia.quinielamundial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main7Activity extends AppCompatActivity {

    //PAGINA DEL MAPA DE LOS PAISES PARTICIPANTES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

    }
    protected void regresarMapa(View v){
        //Te lleva a la página principal.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
