package com.example.anacristina.lectorlibros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LibroActivity extends AppCompatActivity {

    private RelativeLayout l_libro;
    private TextView lb_libro;
    private EditText txt_libro;
    private ImageButton bt_reproducir;

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

    }
}
