package com.example.santiagoborobia.quinielamundial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class Main9Activity extends AppCompatActivity {

    //PAGINA DE TABLA DE PUNTOS

    private String usuario;
    private GridView gv, gv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        //Recuperamos el usuario del intent
        Bundle bundle = this.getIntent().getExtras();
        usuario = bundle.get("Usuario").toString();

        //Recuperamos las variables del gridView
        //Creamos 2 gridViews porque teniamos dos columnas (Usuario y puntos), pero no sabiamos que se acomodaran correctamente
        gv= (GridView)findViewById(R.id.gvTabla);//GridView para los usuarios
        gv2=(GridView)findViewById(R.id.gvTabla2);//GridView para los puntos
        llenarTabla1();//Llenamos GridView de usuarios
        llenarTabla2();//Llenamos GridView de puntos
    }

    public void llenarTabla1(){
        //Llenado de GridView de usuarios
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Seleccionamos el total de usuarios que hay, apra poder crear un arreglo con ese numero de variables
        Cursor cantidad= bd.rawQuery("select count(correo) from usuario", null);
        cantidad.moveToFirst();
        int max = cantidad.getInt(0);
        //Creamos el arreglo para los usuarios
        String datos[]= new String [max];

        Cursor lista = bd.rawQuery("select nombre from usuario order by puntos desc", null);
        if(lista.moveToFirst()) {
            //Metemos los nombres de los usuarios en el arreglo de usuarios
            datos[0] = lista.getString(0);
            int i = 1;
            while (lista.moveToNext()) {
                datos[i] = lista.getString(0);
                i++;
            }
        }
        //Creamos un adaptador de arreglos para poder llenar con este el primer GridView
        ArrayAdapter adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        gv.setAdapter(adaptador);

    }
    public void llenarTabla2(){
        //Llenado del GridView de puntos
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Igualmente, seleccionamos el total de usuarios inscritos para el arreglo
        Cursor cantidad= bd.rawQuery("select count(correo) from usuario", null);
        cantidad.moveToFirst();
        int max = cantidad.getInt(0);
        //Creamos el arreglo paralos puntos de cada usuario
        String datos[]= new String [max];

        //Seleccionamos lso puntos de cada usuario y los ordenamos de la misma froma que en el otro lleando de tablas para que esten ordenados los datos.
        //Ordenamos de mayor a menor para crear la tabla
        Cursor lista = bd.rawQuery("select puntos from usuario order by puntos desc", null);
        if(lista.moveToFirst()) {
            //Llenamos el arreglo de puntos con los puntos de cada usuario
            datos[0] = lista.getString(0);
            int i = 1;
            while (lista.moveToNext()) {
                datos[i] = lista.getString(0);
                i++;
            }
        }
        //Creamos un adaptador de arreglo para llenar con este el segundo GridView.
        ArrayAdapter adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        gv2.setAdapter(adaptador);

    }

    protected void regresar(View v){
        //Regresamos al menú principal, enviando el intent con el correo.
        Intent intent = new Intent(this, Main3Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", usuario);
        intent.putExtras(b);
        startActivity(intent);
    }
}
