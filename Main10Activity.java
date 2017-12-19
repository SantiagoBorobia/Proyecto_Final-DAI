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

public class Main10Activity extends AppCompatActivity {

    //PAGINA DEL ADMINISTRADOR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        BaseDatos quiniela = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = quiniela.getWritableDatabase();

        //Verificamos si ya existe un administrador en la base de datos
        Cursor checa = bd.rawQuery("select nombre from usuario where correo = 'admin'", null);
        if(!checa.moveToFirst()) {
            //Si no existe el administradot, inmediatamente se crea y manda el mensaje:
            String corr= "admin";
            String nom= "admin";
            String cont= "godAlmighty";
            int pts = 10;
            String lig= "sinLiga";
            int edit =0;
            ContentValues registro = new ContentValues();
            registro.put("correo", corr);
            registro.put("nombre", nom);
            registro.put("contra", cont);
            registro.put("puntos", pts);
            registro.put("nomliga", lig);
            registro.put("editable", edit);
            long i =bd.insert("usuario", null, registro);
            if (i>0){
                Toast.makeText(this, "Administrador creado!", Toast.LENGTH_LONG).show();
            }
        }//Si el administrador ya existe, no hace nada, porque ya esta en la base de datos y ya puede interactuar.
    }

    public void registrarAdmin (View v){
        //Registramos los resultados que el administrador pone para cada partido
        //Estos resultados deben ser los reales para cada partido, por lo que los hará una vez hayan finalizado los partidos.
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //Recuperamos los datos registrados por el administador en cada variable para cada juego
        String val1,val2,val3,val4,val5,val6,val7,val8,val9,val10;
        RadioGroup rg1= (RadioGroup)findViewById(R.id.rg1);
        val1 = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg2= (RadioGroup)findViewById(R.id.rg2);
        val2 =((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg3= (RadioGroup)findViewById(R.id.rg3);
        val3 =((RadioButton)findViewById(rg3.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg4= (RadioGroup)findViewById(R.id.rg4);
        val4 =((RadioButton)findViewById(rg4.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg5= (RadioGroup)findViewById(R.id.rg5);
        val5 =((RadioButton)findViewById(rg5.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg6= (RadioGroup)findViewById(R.id.rg6);
        val6 =((RadioButton)findViewById(rg6.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg7= (RadioGroup)findViewById(R.id.rg7);
        val7 =((RadioButton)findViewById(rg7.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg8= (RadioGroup)findViewById(R.id.rg8);
        val8 =((RadioButton)findViewById(rg8.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg9= (RadioGroup)findViewById(R.id.rg9);
        val9 =((RadioButton)findViewById(rg9.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg10= (RadioGroup)findViewById(R.id.rg10);
        val10 =((RadioButton)findViewById(rg10.getCheckedRadioButtonId())).getText().toString();

        //Creamos un arreglo con los resultados de cada partido
        String[] val = {val1,val2,val3,val4,val5,val6,val7,val8,val9,val10};

        String correo= "admin";//Guardamos una variable con el correo del administrador, para registrar sus partidos
        ContentValues registro = new ContentValues();//Creamos el content value del registro para poder guardar los partidos
        for (int i=1;i<11;i++){
            //Guardamos cada partido en la tabla de partidos para el usuario del administrador
            registro.put("correo", correo);
            registro.put("idPartido", i);
            registro.put("resultado", val[i-1].toString());
            bd.insert("partidos", null, registro);
            registro.clear();
        }

        bd.close();
    }

    protected void regresar(View v){
        //Regresamos a la pagina principal, en la que inicia el app.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void calcularPuntos(View v){
        //Calculo de puntos de los usuarios.
        String nom;
        BaseDatos admin = new BaseDatos(this, "gol8", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Seleccionamos el total de usuarios que estan inscritos para crean un arreglo con ese numero de variables
        Cursor cantidad= bd.rawQuery("select count(correo) from usuario", null);
        cantidad.moveToFirst();
        int max = cantidad.getInt(0);
        //Creamos el arreglo con el total disponible de usuarios
        String[] correos =new String[max];
        Cursor checa = bd.rawQuery("select resultado from partidos where correo = 'admin'", null);
        //Revisa si el administrador ya guardó datos, de lo contrario no tendría con quien comparar a cada usuario.
        if(checa.moveToFirst()) {
            //Si el administrador ya reistro sus partidos, hacemos el calculo de puntos:
            Cursor lista = bd.rawQuery("select correo from usuario", null);
            lista.moveToFirst();
            //Seleccionamos todos los correos de los usuarios y los metemos al arreglo que antes creamos:
            correos[0] = lista.getString(0);
            int i = 1;//Metemos el primer correo y posetriormente metemos los demas correos al arreglo:
            while (lista.moveToNext()) {
                correos[i] = lista.getString(0);
                i++;
            }
            int c = 0;
            //Creamos vriables para el resultado ingresado por el usuario y por el administrador, apra compararlos
            Cursor resUs;
            Cursor resp;
            Cursor pts; //Creamos el cursor de puntos para poder recuperar los puntos que tiene el usuario
            Cursor extras; //Creamos el extras para recuperar los datos faltates para poder hacer el upgrade
            int puntos=-1; //Iniciamos una variable de puntos para poder ir sumando
            String resultUs;
            String resultAd;
            String nomb;
            String cont;
            String lig= "sinLiga";
            int edit;
            //Creamos un registro de content values para poder hacer el upgrade y la modificacion de puntos
            ContentValues registro = new ContentValues();
            while (c < correos.length) { //Hacemos el calculo de puntos para cada usuario con un while
                //Mientras no llegue al final de los usuarios seguirá entrando.
                nom = correos[c].toString();//Obtenemos el primer usuario del arreglo de usuarios que ya habiamos llenado
                int w;
                for (w=1; w < 11; w++) {//Creamos una variable w que ira del 1 al 10, para poder revisar cada uno de los 10 partidos
                    resUs = bd.rawQuery("select resultado from partidos where idPartido = "+w+" and correo = '" + nom + "'", null);
                    resUs.moveToFirst();//Seleccionamos el resultado puesto por el usuario para el partido "w"
                    resp = bd.rawQuery("select resultado from partidos where idPartido = "+w+" and correo = 'admin'", null);
                    resp.moveToFirst();//Seleccionamos el resultado puesto por el administrador (real) para el mismo partido "w"

                    resultUs=resUs.getString(0);//Recuperamos los datos de los resultados para el usuario y el administrador
                    resultAd=resp.getString(0);

                    if (resultUs.equals(resultAd)) {//Si los resultados coinciden es porque el usuario estuvo correcto en su prediccion para ese partido
                        //Como predijo bien el partido se le suma 1 punto
                        pts = bd.rawQuery("select puntos from usuario where correo = '" + nom + "'", null);
                        pts.moveToFirst();//Seleccionamos los puntos que lleva el usuario y los recuperamos
                        extras=bd.rawQuery("select nombre, contra, editable from usuario where correo = '"+nom+"'",null);
                        extras.moveToFirst();//Seleccionamos las demas variables del usuario para poder hacer el upgrade con el contetn values
                        nomb=extras.getString(0);
                        cont=extras.getString(1);
                        edit=extras.getInt(2);
                        puntos = pts.getInt(0) + 1; //A los puntos que recuperamos antes le sumamos 1 porque estuvo correcto

                        //Metemos los datos al registro en el content values y realizamos el update con los nuevos puntos sumados.
                        registro.put("correo", nom);
                        registro.put("nombre", nomb);
                        registro.put("contra", cont);
                        registro.put("puntos", puntos);
                        registro.put("nomliga", lig);
                        registro.put("editable", edit);
                        registro.put("puntos", puntos);

                        bd.update("usuario", registro, "correo= '" + nom + "'", null);
                    }
                    //Si el resultado del usuario no coincide con el del administrador no sumara ningun punto.
                }
                c++;//Pasamos entonces al siguiente usuario y repetimos el ciclo para cada uno
            }
        }
        else{
        //Si el administrador no ha registrado datos, no podemos comprara los resultados del usuario, asi que no entra y manda el mensaje:
            Toast.makeText(this, "Aun no se registran resultados del administrador", Toast.LENGTH_LONG).show();
        }
    }
}
