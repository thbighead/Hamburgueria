package com.example.tnc.hamburgueria;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, CartActivity.class);
                startActivity(it);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView lista = (ListView) findViewById(R.id.lista_de_hamburgueres);
        List<Hamburguer> hamburgueres = todosOsHamburgueres();
        AdapterHamburgueresPersonalizado adapter = new AdapterHamburgueresPersonalizado(hamburgueres, this);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtra("name", ((TextView)view.findViewById(R.id.lista_de_hamburgueres_nome)).getText().toString());
                intent.putExtra("price", ((TextView)view.findViewById(R.id.lista_de_hamburgueres_preco)).getText().toString());
                listView.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Exemplo qualquer de devolução de uma lista de cursos.
     * Para esse exemplo será considerado um hard coded.
     *
     * @return lista com todos os cursos
     */
    private List<Hamburguer> todosOsHamburgueres() {
        return new ArrayList<>(Arrays.asList(
                new Hamburguer("Hamburguer 0", "Maravilhas e delícias 0", 16.9, ImagemDeVitrine.p0),
                new Hamburguer("Hamburguer 1", "Maravilhas e delícias 1", 15.9, ImagemDeVitrine.p1),
                new Hamburguer("Hamburguer 2", "Maravilhas e delícias 2", 1.99, ImagemDeVitrine.p2),
                new Hamburguer("Hamburguer 3", "Maravilhas e delícias 3", 49.0, ImagemDeVitrine.p3),
                new Hamburguer("Hamburguer 4", "Maravilhas e delícias 4", 10.0, ImagemDeVitrine.p4),
                new Hamburguer("Hamburguer 5", "Maravilhas e delícias 5", 5.99, ImagemDeVitrine.p5),
                new Hamburguer("Hamburguer 6", "Maravilhas e delícias 6", 21.98, ImagemDeVitrine.p6),
                new Hamburguer("Hamburguer 7", "Maravilhas e delícias 7", 31.49, ImagemDeVitrine.p7),
                new Hamburguer("Hamburguer 8", "Maravilhas e delícias 8", 22.0, ImagemDeVitrine.p8),
                new Hamburguer("Hamburguer 9", "Maravilhas e delícias 9", 35.67, ImagemDeVitrine.p9)));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // você já tá aqui, mongol
        } else if (id == R.id.nav_gallery) {
            Intent it = new Intent(this, CartActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_slideshow) {
            Intent it = new Intent(this, OrderActivity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
