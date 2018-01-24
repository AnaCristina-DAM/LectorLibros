package com.example.anacristina.lectorlibros;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DescargaActivity extends AppCompatActivity {
    TextView tb_titulo,tb_autor,tb_isbn;
    Button bt_descargar;
    JSONObject libro;
    DescargarLibro descargarlibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_descarga );
        libro= new JSONObject();
        tb_isbn =(TextView) findViewById( R.id.tb_isbn );
        tb_titulo =(TextView) findViewById( R.id.tb_titulo );
        tb_autor = (TextView) findViewById( R.id.tb_autor );
        bt_descargar =(Button) findViewById( R.id.bt_descargar );
        descargarlibro = new DescargarLibro();
        String datoslibro = getIntent().getExtras().getString( "datoslibro" );
        try {
            libro = new JSONObject( datoslibro );
            tb_isbn.setText(libro.get( "isbn" ).toString());
            tb_titulo.setText( libro.get( "titulo" ).toString() );
            tb_autor.setText( libro.get( "autor" ).toString() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bt_descargar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    descargarlibro.execute(libro.get( "rutafile" ).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } );
    }
    public class DescargarLibro extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String linea = null;
            HttpURLConnection con = null;
            String cadenaUrl = "http://192.168.1.34/librolectormaster/";
            try {
                String estado = Environment.getExternalStorageState();

                boolean sdDisponible = false;
                boolean sdEscribir = false;
                if(estado.equals(Environment.MEDIA_MOUNTED)) {
                    sdDisponible = true;
                    sdEscribir = true;
                }

                else if(estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                    sdDisponible = true;
                    sdEscribir = false;
                }

                else {
                    sdDisponible = false;
                    sdEscribir = false;
                }


                if(sdDisponible && sdEscribir) {
                    StringBuilder resultado = new StringBuilder();
                    URL url = new URL( cadenaUrl + strings[0] );
                    con = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
                    while ((linea = reader.readLine()) != null) {
                        resultado.append( linea + "\n" );
                    }

                    File rutaSD = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                    File rutaLibros = new File( rutaSD, "libros" );

                    if (!rutaLibros.exists()) {
                        rutaLibros.mkdir();
                    }

                    File archivo = new File( rutaLibros,
                            strings[0].replace( "libros/", "" ) );

                    OutputStreamWriter archivoSalida = new OutputStreamWriter(
                            new FileOutputStream( archivo ) );
                    System.out.println( archivo.getAbsoluteFile() );

                    String[] lineas = resultado.toString().split( "\n" );
                    String separator = System.getProperty( "line.separator" );
                    for (String lineaArchivo : lineas) {
                        archivoSalida.write( lineaArchivo );
                        archivoSalida.write( separator );
                    }
                    archivoSalida.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "si";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            Toast.makeText( getApplicationContext(),"Descarga completad",Toast.LENGTH_SHORT ).show();
            Intent intent = new Intent(getApplicationContext(),BibliotecaActivity.class);
            startActivity( intent );
        }
    }
}
