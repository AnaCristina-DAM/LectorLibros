package com.example.anacristina.lectorlibros;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RelativeLayout l_lista;
    private TextView lb_lista;
    private ListView lst_libros;

    ArrayAdapter adaptador;

    // Creamos una lista que nos permita guardar los libros que creemos utilizando un "ArrayList":
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
        // Creamos un adaptador para poder visualizar los libros en la "ListView":
        adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista_libros);
        lst_libros.setAdapter(adaptador);

        // Ponemos los items de la lista a la escucha y establecemos los métodos "OnItemClickListener" y
        // "OnItemLongClickListener" para que actúen según los requisitos de la aplicación:

        // > OnItemClickListener - ABRIR EL LIBRO:
        lst_libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent (getApplicationContext(), LibroActivity.class);
                Libro libro_selec =  lista_libros.get(position);
                String titulo = libro_selec.getTitulo().toString();
                String texto = libro_selec.getTexto().toString();
                i.putExtra("titulo", titulo);
                i.putExtra("texto", texto);
                startActivity(i);
            }
        });

        // > OnLongClickListener - BORRAR EL LIBRO:
        lst_libros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                // Se crea el diálogo:
                AlertDialog.Builder builder= new AlertDialog.Builder(ListaActivity.this);
                // Se le añade un icono:
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                // Se le añade un mensaje:
                builder.setMessage(R.string.borrar_mensaje);
                // Se le añade un título:
                builder.setTitle(R.string.borrar_titulo);
                /**
                 * Se crea el boton negativo
                 */
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                /**
                 * Se crea el boton positivo
                 */
                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        // Seleccionamos el libro:
                        Libro libro_selec =  lista_libros.get(position);
                        String titulo = libro_selec.getTitulo().toString();

                        // Obtenemos el directorio:
                        String path = Environment.getExternalStorageDirectory()+ File.separator;
                        File f = new File(path);

                        // Obtemos los nombres de archivos dentro del directorio y borramos el libro seleccionado:
                        File file[] = f.listFiles();
                        if (file != null){
                            for (int x=0; x < file.length; x++){
                                if (file[x].isFile()){
                                    String nombre;
                                    nombre = file[x].getName();
                                    if (nombre.equals(titulo)){
                                        file[x].delete();
                                    }
                                }
                            }
                        }

                        //Configurar "Adapter" a "ListView":
                        // > Se borra el elemento de la lista:
                        lista_libros.remove(position);
                        // > Se actualiza el adaptador de la lista:
                        adaptador.notifyDataSetChanged();

                    }
                });
                builder.show();

                // Para evitar que se ejecute también el código del método "onClick", devolvemos un valor booleano "TRUE".
                return true;

            }

        });

        //Generacion de TXT's de prueba:
        //GestionFicheros.escribirFichero("Primero.txt","Este es el primer texto");
        //GestionFicheros.escribirFichero("Segundo.txt","Y este el segundo");

        listarTXT();

    }

    // Método que muestra los libros en la lista:
    private void listarTXT(){

        List<String> list = new ArrayList<String>();

        // Obtenemos la ruta dónde se encuentran los archivos:
        String path = Environment.getExternalStorageDirectory()+ File.separator;
        File f = new File(path);

        // Obtenemos los nombres de los archivos dentro del directorio y los añadimos a la lista:
        File file[] = f.listFiles();
        if (file != null){
            for (int i=0; i < file.length; i++)
            {
                if (file[i].isFile()){
                    String nombre,texto;
                    nombre = file[i].getName();
                    texto = GestionFicheros.leerFichero(nombre);
                    Libro libro = new Libro(nombre,texto);
                    lista_libros.add(libro);
                }
            }
        }

        //Configurar "Adapter" a "ListView":
        lst_libros.setAdapter(adaptador);
    }

}
