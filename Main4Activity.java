package com.example.santiagoborobia.quinielamundial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    //PAGINA DE PREDICCIONES DEL USUARIO (REGISTRO DE PARTIDOS)

    private String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //Recuperar el usuario del intent
        Bundle bundle = this.getIntent().getExtras();
        correo = bundle.get("Usuario").toString();

        //Revisamos si el usuario ya guardo los datos anteriormente:
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Se obtiene la variable "editable" del usuario, que dice si puede o no editar sus partidos
        Cursor checha= bd.rawQuery("select editable from usuario where correo = '"+correo+"'", null);
        checha.moveToFirst();
        int a= checha.getInt(0);
        if (checha.getInt(0) == 0)
            //Si tiene la variable "editable" en "sí" le permite registrar los resultados
            Toast.makeText(this, "Selecciona el resultado que creas para cada partido", Toast.LENGTH_LONG).show();
        else
            //Si no puede editar es porque ya guardo antes los resultados, entonces manda el mensaje
            Toast.makeText(this, "Ya no puedes editar, tus resultados ya fueron registrados", Toast.LENGTH_LONG).show();
    }


    public void guardarDatos(View v){
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Revisa si tiene la opcion de editar en "sí":
        Cursor checa= bd.rawQuery("select editable from usuario where correo = '"+correo+"'", null);
        checa.moveToFirst();
        int a=checa.getInt(0);
        //Si puede editar, entonces guarda los resultados de cada partido en la tabla de partidos, para ese usuario.
        if (a == 0) {
            String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10;
            //Recuperamos los datos seleccionados en cada partido
            RadioGroup rg1 = (RadioGroup) findViewById(R.id.urg1);
            val1 = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg2 = (RadioGroup) findViewById(R.id.urg2);
            val2 = ((RadioButton) findViewById(rg2.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg3 = (RadioGroup) findViewById(R.id.urg3);
            val3 = ((RadioButton) findViewById(rg3.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg4 = (RadioGroup) findViewById(R.id.urg4);
            val4 = ((RadioButton) findViewById(rg4.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg5 = (RadioGroup) findViewById(R.id.urg5);
            val5 = ((RadioButton) findViewById(rg5.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg6 = (RadioGroup) findViewById(R.id.urg6);
            val6 = ((RadioButton) findViewById(rg6.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg7 = (RadioGroup) findViewById(R.id.urg7);
            val7 = ((RadioButton) findViewById(rg7.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg8 = (RadioGroup) findViewById(R.id.urg8);
            val8 = ((RadioButton) findViewById(rg8.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg9 = (RadioGroup) findViewById(R.id.urg9);
            val9 = ((RadioButton) findViewById(rg9.getCheckedRadioButtonId())).getText().toString();
            RadioGroup rg10 = (RadioGroup) findViewById(R.id.urg10);
            val10 = ((RadioButton) findViewById(rg10.getCheckedRadioButtonId())).getText().toString();

            //Guardamos los resultados en un arreglo de String
            String[] val = {val1, val2, val3, val4, val5, val6, val7, val8, val9, val10};
            //Creamos un registro al que le meteremos los datos del arreglo
            ContentValues registro = new ContentValues();
            String corr=  correo;
            long z;

            for (int i=1;i<11;i++){
                //Para cada partido, hacemos la insercion de datos en la tabla de partidos
                registro.put("correo", corr);
                registro.put("idPartido", i);
                registro.put("resultado", val[i-1].toString());
                z= bd.insert("partidos", null, registro);
                registro.clear();//Eliminamos los datos del registro para e siguiente partido
                if(z>0){
                    //Si logró insertar los datos del partido manda el mensaje de exito
                    Toast.makeText(this, "Partido "+i+ " guardado", Toast.LENGTH_SHORT).show();
                }
                else
                    //Si por alguna razon falla en la insercion, indica el mensaje de error
                    Toast.makeText(this, "Error, partido no ingresado", Toast.LENGTH_SHORT).show();
            }
            //Una vez guardados todos los datos, se cambia la opcion de editar a "No", para que no pueda modificar sus registros.
            setEditable();
            bd.close();
        }

    }

    private void setEditable(){
        String corr, nomb, cont, liga;
        int edit=1;//Ponemos la variable de editable en 1, porque esto significa que ya guardo datos
        int pts;
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Recuperamos todos los datos del usuario para poder crear el registro en el content Values.
        Cursor checha= bd.rawQuery("select correo, nombre, contra, puntos, nomliga, editable from usuario where correo = '"+correo+"'", null);
        checha.moveToFirst();
        corr=checha.getString(0);
        nomb=checha.getString(1);
        cont=checha.getString(2);
        pts= checha.getInt(3);
        liga= checha.getString(4);

        //Creamos un registro en un content values para guardar los datos del usuario
        ContentValues registro= new ContentValues();
        registro.put("correo", corr);
        registro.put("nombre", nomb);
        registro.put("contra", cont);
        registro.put("puntos", pts);
        registro.put("nomliga", liga);
        registro.put("editable", edit);//En el editable ponemos la variable iniciada con "1", porque ya metió los datos

        long r=bd.update("usuario", registro, "correo= '"+corr+"'",null);
        if(r>0) {
            //Si logró hacer correctamente la modificacion en el update, manda el mensaje:
            Toast.makeText(this, "Datos guardados, ya no se puede modificar", Toast.LENGTH_LONG).show();
        }
        bd.close();
    }

    protected void regresar(View v){
        //Regresa a la pagina del menu principal de "Mi liga".
        Intent intent = new Intent(this, Main3Activity.class);
        Bundle b = new Bundle();
        b.putString("Usuario", correo);
        intent.putExtras(b);
        startActivity(intent);

    }

}
