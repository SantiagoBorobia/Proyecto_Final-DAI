package com.example.santiagoborobia.quinielamundial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by santiagoborobia on 08/12/17.
 */

public class BaseDatos extends SQLiteOpenHelper{

    //CLASE DE BASE DE DATOS EN SQLITE

    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creamos la tabla de usuarios
        sqLiteDatabase.execSQL("create table usuario(correo text,nombre text, contra text, puntos integer, nomliga text, editable integer)");
        //Creamos la tabla de partidos
        sqLiteDatabase.execSQL("create table partidos(correo text, idPartido text, resultado text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Tiramos la tabla de usuario y creamos una nueva (Para el OnUpgrade)
        sqLiteDatabase.execSQL("drop table if exists usuario");
        sqLiteDatabase.execSQL("create table usuario(correo text,nombre text, contra text, puntos integer, nomliga text, editable integer)");

        //Tiramos la tabla de partidos y creamos una nueva (Para el OnUpgrade)
        sqLiteDatabase.execSQL("drop table if exists partidos");
        sqLiteDatabase.execSQL("create table partidos(correo text, idPartido text, resultado text)");
    }

}
