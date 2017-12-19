package com.example.santiagoborobia.quinielamundial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //PAGINA PRINCIPAL DE INICIO DEL APP

    EditText correo, clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recuperamos los atributos.
        correo = (EditText)findViewById(R.id.etUsuario);
        clave= (EditText)findViewById(R.id.etClaveI);


    }

    protected void inicioSesion(View v){
        String admin= "godAlmighty"; //Creamos el login del administrador
        String corr, cont;
        corr=correo.getText().toString();//Recogemos el correo y contraseña ingresados
        cont=clave.getText().toString();

        if(corr.equals(admin)){ //Revisa si el correo es el login del administrador (Por eso es un nombre extraño)
            //De ser el administrador te lleva a la pagina en donde se hacen los cambios de los resultados reales y calculo de puntos.
            Intent intent = new Intent(this, Main10Activity.class);
            startActivity(intent);
        }
        else { //Si no es el administrador debe revisar si el usuario ingreso la contraseña correcta
            BaseDatos quiniela = new BaseDatos(this, "gol8", null, 1);
            SQLiteDatabase bd = quiniela.getWritableDatabase();
            //Seleccionamos los datos de la persona que tienen la clave y correo ingresadas

            Cursor checa = bd.rawQuery("select nombre from usuario where correo = '"+corr+"' and contra = '"+cont+"'", null);
            if (checa.moveToFirst()) {
                //Si encuentra la clave y correo es porque son correctas y entra a la siguiente pagina.
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                //Guardamos el correo del usuario en un intent para usarlo después.
                Bundle b = new Bundle();
                b.putString("Usuario", correo.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
            }
            else {
                //Si no encontro a ese usuario, se manda el mensaje de error
                Toast.makeText(this, "Correo o contraseña incorrecta", Toast.LENGTH_LONG).show();
            }
            bd.close();
        }


    }
    protected void paginaUsuario(View v){
        //Te lleva a la página de creacion de usuarios.
        Intent intent = new Intent(this, Main5Activity.class);
        startActivity(intent);
    }

    protected void paginaMapa(View v){
        //Te lleva a la página del Mapa.
        Intent intent = new Intent(this, Main7Activity.class);
        startActivity(intent);
    }


}
