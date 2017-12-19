package com.example.santiagoborobia.quinielamundial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    //PAGINA DE MENU PRINCIPAL

    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Recuperar el nombre del usuario del intent
        Bundle bundle = this.getIntent().getExtras();
        correo = bundle.get("Usuario").toString();

        TextView bienvenida= (TextView)findViewById(R.id.tvBienvenida);
        bienvenida.setText("Bienvenido " + correo); //Mandamos el mensaje de bienvenida con el usuario recogido en el intent
    }
    protected void paginaPredicciones(View v){
        //Te envia a la pagina de "Predicciones", ademas manda el usuario en el intent, para usarlo en la siguiente pagina
        Intent intent = new Intent(this, Main4Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);

    }
    protected void paginaResultados(View v){
        //Te envia a la pagina de "Consulta de resultados", que es la pagina web de la FIFA
        Intent intent = new Intent(this, Main8Activity.class);
        startActivity(intent);


    }

    protected void paginaPuntos(View v){
        //Te envia a la pagina de "Puntos", ademas envia el usuario en el intent para usarlo en la misma.
        Intent intent = new Intent(this, Main9Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);

    }

    public void cerrarSesion(View v){
        //Te lleva a la página principal con la que se empezó.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
