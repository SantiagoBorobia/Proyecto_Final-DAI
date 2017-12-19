package com.example.santiagoborobia.quinielamundial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main6Activity extends AppCompatActivity {

    private EditText nomLiga, claveLiga;
    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        nomLiga=(EditText)findViewById(R.id.etNomLiga);
        claveLiga=(EditText)findViewById(R.id.etClaveLiga);

        Bundle bundle = this.getIntent().getExtras();
        correo = bundle.get("Usuario").toString();

    }
    protected void regresar(View v){
        Intent intent = new Intent(this, Main2Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);

    }

    public void altaLiga(View v){
        String nomL, contL;
        BaseDatos quiniela = new BaseDatos(this, "Quiniela", null, 1);
        SQLiteDatabase bd = quiniela.getWritableDatabase();

        nomL=nomLiga.getText().toString();
        contL= claveLiga.getText().toString();
        try {
            ContentValues registro = new ContentValues();
            registro.put("nomLiga", nomL);
            registro.put("clave", contL);
            bd.insert("liga", null, registro);
            bd.close();
            Toast.makeText(this, "¡Creaste la liga con exito!", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){
            Toast.makeText(this, "Error en la creacion de liga", Toast.LENGTH_LONG).show();
        }
    }
}
