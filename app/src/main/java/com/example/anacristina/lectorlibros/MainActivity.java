package com.example.anacristina.lectorlibros;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private RelativeLayout l_principal;
    private Toolbar toolbar;
    private TextView lb_buscar;
    private TextView lb_titulo;
    private EditText txt_titulo;
    private TextView lb_autor;
    private EditText txt_autor;
    private Button bt_buscar;
    BuscarLibro buscarlibro;
    JSONObject libro;



    // CONSTANTE UTILIZADA PARA SOLICITAR PERMISOS DE ALMACENAMIENTO:
    private static final int P_ALMACENAMIENTO = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciamos los distintos elementos del layout:

        // LAYOUT:
        l_principal = (RelativeLayout) findViewById(R.id.l_principal);

        // TOOLBAR - Menú de opciones del juego:
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // TextView & EditText:
        lb_buscar = (TextView) findViewById(R.id.lb_buscar);
        lb_titulo = (TextView) findViewById(R.id.lb_titulo);
        txt_titulo = (EditText) findViewById(R.id.txt_titulo);
        lb_autor = (TextView) findViewById(R.id.lb_autor);
        txt_autor = (EditText) findViewById(R.id.txt_autor);

        //JSON
        libro = new JSONObject(  );

        //Objeto Descargarlibro
        buscarlibro = new BuscarLibro();

        // BOTON - Buscar:
        bt_buscar = (Button) findViewById(R.id.bt_buscar);
        bt_buscar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo1 = txt_titulo.getText().toString();
                String autor1 = txt_autor.getText().toString();
                System.out.println(titulo1);
                System.out.println("El autor es: "+autor1);
                if((titulo1 != null) && (!titulo1.equals(""))) {
                    buscarlibro.execute( "titulo", titulo1 );
                }else if((autor1 != null) && (!autor1.equals(""))){
                    //buscarlibro.execute( "autor", autor1 );
                }else{
                    Toast.makeText(getApplicationContext(),"No se ha introducido ningun campo",Toast.LENGTH_SHORT).show();
                }
            }
        } );

        // Comprobamos los permisos:
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, P_ALMACENAMIENTO);
        }

    }

    // Resultado de la solicitud de permisos:
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case P_ALMACENAMIENTO: {
                // Si la solicitud es cancelada, no se otorgarán permisos a la aplicación y el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISOS OTORGADOS:
                    String text = getResources().getString(R.string.permisos_otorgados);
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0, text.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText( this, centeredText, Toast.LENGTH_SHORT ).show();
                }
                else {
                    // PERMISOS DENEGADOS:
                    String text = getResources().getString(R.string.permisos_denegados);
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0, text.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText( this, centeredText, Toast.LENGTH_SHORT ).show();
                    // Cerramos la aplicación:
                    finish();
                }
                return;
            }
        }
    }

    // Inflamos el layout para mostrar los items del menú:
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // El método ".inflate()" nos permite inflar el layout del menú y crear los items del mismo; para ello,
        // pasamos como parámetros tanto el layout con los items como el menú a inflar.
        getMenuInflater().inflate(R.menu.toolbar_main_items,menu);
        return true;
    }

    // Configuramos las opciones del menú:
    @Override
    public boolean onOptionsItemSelected(MenuItem opcion_menu) {

        int opcion = opcion_menu.getItemId();

        // INSTRUCCIONES:
        if(opcion==R.id.opt_instrucciones){

            // Creamos el mensaje con las instrucciones del juego:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // TÍTULO:
            String titulo = getResources().getString(R.string.instrucciones_titulo);
            Spannable centeredTitulo = new SpannableString(titulo);
            centeredTitulo.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0, titulo.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setTitle(centeredTitulo);
            // MENSAJE:
            String texto = getResources().getString(R.string.instrucciones_mensaje);
            Spannable centeredTexto = new SpannableString(texto);
            centeredTexto.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0, texto.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setMessage(centeredTexto);
            // BOTÓN POSITIVO:
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });

            // Mostramos el mensaje:
            AlertDialog mensaje = builder.create();
            mensaje.show();

            return true;
        }

        // LISTA DE LIBROS:
        if(opcion == R.id.opt_listadolibros){
            // Lanzamos una nueva actividad donde el usuario podrá visualizar la lista de libros almacenados.
            Intent intent = new Intent(MainActivity.this, ListaActivity.class);
            startActivity(intent);
        }
        return false;
    }



    public class BuscarLibro extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String linea = null;
            HttpURLConnection con = null;
            String cadenaUrl = "http://mywebmario.000webhostapp.com/lectorlibrosmaster/";
            String json = "";
            try {
                StringBuilder resultado = new StringBuilder();
                URL url = new URL( cadenaUrl + "index.php?" + strings[0] + "=" + strings[1]);
                con = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader( new InputStreamReader( con.getInputStream()));
                while ((linea=reader.readLine())!=null){
                    resultado.append( linea );
                }

                JSONObject respuestaJSON = new JSONObject( resultado.toString() );
                System.out.println(respuestaJSON.get( "rutafile" ));
                json = respuestaJSON.toString();
                //JSONArray arrayJSON = respuestaJSON.getJSONArray(  );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            try {
                libro = new JSONObject( s );

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent( getApplicationContext(),DescargaActivity.class );
            intent.putExtra("datoslibro",s );
            startActivity( intent );
        }
    }

}
