package com.example.anacristina.lectorlibros;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class BibliotecaActivity extends AppCompatActivity {
    ListView listalibros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_biblioteca );
        listalibros =(ListView) findViewById( R.id.listalibros );
        final File rutaSD = new File( Environment.getExternalStorageDirectory().getAbsolutePath());

        final File rutaLibros = new File( rutaSD, "libros" );

        if (!rutaLibros.exists()) {
            rutaLibros.mkdir();
        }
        ArrayList<String> libros = new ArrayList<>( );
        for (File libro: rutaLibros.listFiles()) {
            libros.add( libro.getName().replace( ".txt", "" ));
        }

        ArrayAdapter adaptador = new ArrayAdapter( getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,libros);
        listalibros.setAdapter( adaptador );
        listalibros.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String rutaarchivo = rutaLibros.getAbsolutePath() + "/" +
                        adapterView.getItemAtPosition( i ).toString() +
                        ".txt";
                Toast.makeText(getApplicationContext(), rutaarchivo, Toast.LENGTH_LONG).show();
                Intent intent = new Intent( getApplicationContext(),LectorActivity.class );
                intent.putExtra( "rutaarchivo",rutaarchivo );
                startActivity( intent );
            }
        } );

    }
}
