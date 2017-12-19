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

public class Main5Activity extends AppCompatActivity {

    //PAGINA DE CREACION DE USUARIOS

    private EditText correo, usuario, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        //Recuperamos las variables de los edit texts en las que el usuario ingresa sus datos.
        correo=(EditText)findViewById(R.id.etCorreo);
        usuario=(EditText)findViewById(R.id.etUsuario);
        contraseña=(EditText)findViewById(R.id.etClave);
    }

    protected void regresar(View v){
        //Te lleva de regreso a la pagina principal, en la que inicia el app.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void altaUsuario(View v){
        String corr, nombre, cont;

        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//Creamos la base de datos

        //Asignamos variables con los datos puestos por el usuario
        corr=correo.getText().toString();
        nombre=usuario.getText().toString();
        cont= contraseña.getText().toString();
        int pts = 0; //Comienza con cero puntos
        String lig= "sinLiga";
        int edit =0;//Editable comienza en cero, porque aun no ha registrado datos

        //Creamos un registro de content values para realizar la insercion a la base de datos
        ContentValues registro = new ContentValues();
        registro.put("correo", corr);
        registro.put("nombre", nombre);
        registro.put("contra", cont);
        registro.put("puntos", pts);
        registro.put("nomliga", lig);
        registro.put("editable", edit);

        long i= bd.insert("usuario", null, registro); //Hacemos el insert en la base de datos
        bd.close();
        limpiar(v); //Limpiamos los datos que el usuario metió en los campos

        if(i>0) {
            //Si logra hacer el alta, manda el mensaje:
            Toast.makeText(this, "Alta exitosa", Toast.LENGTH_LONG).show();
        }
        else
            //Si hubo un error lo indica con el mensaje:
            Toast.makeText(this, "No se cargaron los datos", Toast.LENGTH_LONG).show();

    }

    public void limpiar(View v){
        //Limpia los campos de texto
        correo.setText("");
        usuario.setText("");
        contraseña.setText("");
    }

    public void consulta2 (View v){
        BaseDatos admin = new BaseDatos(this, "gol8",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //recueperamos el correo del usuario ingresado
        String corr = correo.getText().toString();

        //Recuperamos la contraseña del usuario con ese correo
        Cursor fila = bd.rawQuery("select contra from usuario where correo = '"+corr+"'",null);
        if(fila.moveToFirst()){
            //Si encuentra la contraseña del usuario que ingresó, escribe los datos en los mismos campos y manda el mensaje:
            contraseña.setText(fila.getString(0));
            Toast.makeText(this, "Contraseña recuperada",Toast.LENGTH_SHORT).show();
        }
        else
            //Si se puso mal el correo, lo especifica en el mensaje:
            Toast.makeText(this, "El correo que ingresaste no esta registrado",Toast.LENGTH_SHORT).show();
        bd.close();
    }
}
