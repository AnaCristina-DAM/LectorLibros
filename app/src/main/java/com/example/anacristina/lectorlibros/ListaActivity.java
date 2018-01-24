package com.example.anacristina.lectorlibros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private RelativeLayout l_lista;
    private TextView lb_lista;
    private ListView lst_libros;

    // Creamos una lista que nos permita guardar las notas que creemos utilizando un "ArrayList":
    ArrayList<Libro> libros = new ArrayList<Libro>();

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
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,libros);
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

    }

}
