package com.example.anacristina.lectorlibros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout l_principal;
    private Toolbar toolbar;
    private TextView lb_buscar;
    private TextView lb_titulo;
    private EditText txt_titulo;
    private TextView lb_autor;
    private EditText txt_autor;
    private Button bt_buscar;

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

        // BOTON - Buscar:
        bt_buscar = (Button) findViewById(R.id.bt_buscar);

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
        return false;
    }

}
