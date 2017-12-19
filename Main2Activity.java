package com.example.santiagoborobia.quinielamundial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    //MENU 1

    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Recuperamos el correo del intent para mandarlo a las otras paginas
        Bundle bundle = this.getIntent().getExtras();
        correo = bundle.get("Usuario").toString();
    }

    protected void paginaMiLiga(View v){
        //Te envia a la pagina de "mi liga", ademas manda el usuario en el intent.
        Intent intent = new Intent(this, Main3Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);


    }
    protected void paginaCrearLiga(View v){
        //Te envia a la pagina de "crear una liga"
        Intent intent = new Intent(this, Main6Activity.class);
        startActivity(intent);

    }
    protected void paginaUnirseLiga(View v){
        //Te envia a la pagina de "unirse a una liga", ademas manda el usuario en el intent
        Intent intent = new Intent(this, Main7Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);

    }
}
