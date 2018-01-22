package com.example.anacristina.lectorlibros;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RelativeLayout l_lista;
    private TextView lb_lista;
    private ListView lst_libros;
    ArrayAdapter adaptador;


    // Creamos una lista que nos permita guardar las notas que creemos utilizando un "ArrayList":
    ArrayList<Libro> lista_libros = new ArrayList<Libro>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        // Referenciamos los distintos elementos del layout:

        // LAYOUT:
        l_lista = (RelativeLayout) findViewById(R.id.l_lista);

        // TextView:
        lb_lista = (TextView) findViewById(R.id.lb_lista);

        // LIST VIEW:
        lst_libros = (ListView) findViewById(R.id.lst_libros);
        // Creamos un adaptador para poder visualizar las notas en la "ListView":
        adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista_libros);
        lst_libros.setAdapter(adaptador);
        // Ponemos los items de la lista a la escucha y establecemos los métodos "OnItemClickListener" y
        // "OnItemLongClickListener" para que actúen según los requisitos de la aplicación:
        lst_libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
        lst_libros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                // Para evitar que se ejecute también el código del método "onClick", devolvemos un valor booleano "TRUE".
                return true;
            }
        });

        //Generacion de txt's de prueba
        //GestionFicheros.escribirFichero("primero.txt","Es el primer texto");
        //GestionFicheros.escribirFichero("Segundo.txt","Y este el segundo");




        listarTXT();

    }

    private void listarTXT(){
        List<String> list = new ArrayList<String>();
        //obtiene ruta donde se encuentran los archivos.
        String path = Environment.getExternalStorageDirectory()+ File.separator;
        File f = new File(path);
        //obtiene nombres de archivos dentro del directorio.
        File file[] = f.listFiles();
        for (int i=0; i < file.length; i++)
        {
            if (file[i].isFile()){
                String nombre,texto;
                nombre = file[i].getName();
                texto = GestionFicheros.leerFichero(f.getAbsolutePath());
                Libro libro = new Libro(nombre,texto);
                adaptador.add(libro.getTitulo());
                lista_libros.add(libro);
            }

        }
        //Configura Adapter a ListView.
        lst_libros.setAdapter(adaptador);
    }

}
