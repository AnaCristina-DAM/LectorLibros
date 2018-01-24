package com.example.anacristina.lectorlibros;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;


public class LectorActivity extends AppCompatActivity {

    Button bt_leer,bt_parar;
    TextToSpeech lector;
    ArrayList<String> lineaslibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lector );

        lineaslibro = new ArrayList<>(  );

        bt_leer =(Button) findViewById( R.id.bt_leer );
        bt_parar = (Button) findViewById( R.id.bt_stop );

        lector = new TextToSpeech( this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    lector.setLanguage(new Locale("spa","ES"));
                }
            }
        } );

        String rutaarchivo = getIntent().getExtras().getString( "rutaarchivo" );

        File fichero =new File( rutaarchivo );
        try {
            BufferedReader reader = new BufferedReader( new FileReader(fichero  ));
            String linea ="";
            while ((linea=reader.readLine())!=null){
                lineaslibro.add( linea );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        lector.setOnUtteranceProgressListener( new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                System.out.println("Empieza el texto: " + s);
            }

            @Override
            public void onDone(String s) {
                System.out.println("Termina el texto: " + s);
            }

            @Override
            public void onError(String s) {
                System.out.println("Error en el texto: " + s);
            }
        } );
        */

        bt_leer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println( lineaslibro.get( 0 ) );
                System.out.println( lector.getMaxSpeechInputLength() );
                int i=0;
                while (i<lineaslibro.size()){
                    lector.speak( lineaslibro.get( i ), TextToSpeech.QUEUE_ADD, null );
                    i=i+1;
                }
                }
        } );
        bt_parar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lector.stop();
            }
        } );
    }

}
