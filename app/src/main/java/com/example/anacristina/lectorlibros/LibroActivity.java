package com.example.anacristina.lectorlibros;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LibroActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private RelativeLayout l_libro;
    private TextView lb_libro;
    private EditText txt_libro;
    private ImageButton bt_reproducir;
    String titulo;
    String texto;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        // Referenciamos los distintos elementos del layout:

        // LAYOUT:
        l_libro = (RelativeLayout) findViewById(R.id.l_libro);

        // TextView & EditText:
        lb_libro = (TextView) findViewById(R.id.lb_libro);
        txt_libro = (EditText) findViewById(R.id.txt_libro);

        // Configuramos el EditText para que el usuario no pueda escribir en él.
        txt_libro.setKeyListener((KeyListener) txt_libro.getTag());

        // BOTON - Reproducir:
        bt_reproducir = (ImageButton) findViewById(R.id.bt_reproducir);

        titulo = getIntent().getStringExtra("titulo");
        texto = getIntent().getStringExtra("texto");
        lb_libro.setText(titulo.toString());
        txt_libro.setText(texto.toString());

        textToSpeech = new TextToSpeech( this, this );


    }

    public void escuchar(View v){
        textToSpeech.setLanguage( new Locale( "spa", "ESP" ) );
        speak( txt_libro.getText().toString() );
    }

    @Override
    public void onInit( int status )
    {
        if ( status == TextToSpeech.LANG_MISSING_DATA | status == TextToSpeech.LANG_NOT_SUPPORTED )
        {
            Toast.makeText( this, "ERROR DATOS PERDIDOS | NO SOPORTADO", Toast.LENGTH_SHORT ).show();
        }
    }

    private void speak( String str ) {
        //textToSpeech.setVoice(new Voice());
        textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        textToSpeech.setSpeechRate(0.0f);
        textToSpeech.setPitch(0.0f);
        //Log.v("VOZ", textToSpeech.getVoice().getName());
        //for (Voice voice: textToSpeech.getVoices()) {
        //Log.v("Voz",voice.getName());
        //es-es-x-ana
        //
    //}

    }

    @Override
    protected void onDestroy()
    {
        if ( textToSpeech != null )
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
