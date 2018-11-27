package com.example.tnc.hamburgueria;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(OrderActivity.this, CartActivity.class);
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

        List<Order> pedidos = todosOsPedidos();
        ListView lista = (ListView) findViewById(R.id.lista_de_pedidos);
        ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, pedidos);
        lista.setAdapter(adapter);
    }

    /**
     * Exemplo qualquer de devolução de uma lista de pedidos.
     * TODO: trocar para a leitura do SQLite.
     *
     * @return lista com todos os pedidos
     */
    private List<Order> todosOsPedidos() {
        List<Hamburguer> todosOsHamburgueres = MainActivity.todosOsHamburgueres();
        List<Hamburguer> order0Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(0, 3)),
                order1Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(3, 5)),
                order2Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(2, 3)),
                order3Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(0, 5)),
                order4Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(1, 8)),
                order5Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(8, 9)),
                order6Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(7, 9)),
                order7Hamburguers = new ArrayList<>(todosOsHamburgueres.subList(0, 9));

        for (Hamburguer hamburguer : order0Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order1Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order2Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order3Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order4Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order5Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order6Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }
        for (Hamburguer hamburguer : order7Hamburguers) { hamburguer.setQuantity((new Random()).nextInt(10)); }

        return new ArrayList<>(Arrays.asList(
                new Order(Status.CONFIRMED, order0Hamburguers, "456457344635734134536", Operadora.AMEX, 152, "Rua sei lá, vai que é tua"),
                new Order(Status.DELIVERED, order1Hamburguers, "1436262457573673625625", Operadora.AMEX, 100, "E lá vamos nós"),
                new Order(Status.SENT, order2Hamburguers, "3462567346234523462", Operadora.AMEX, 152, "Ninguém sabe onde fica isso"),
                new Order(Status.WAITING, order3Hamburguers, "4636262573451346256", Operadora.AMEX, 268, "Doideira"),
                new Order(Status.SENT, order4Hamburguers, "45246256357324538478", Operadora.AMEX, 753, "Estrada do sei lá onde que eu tô"),
                new Order(Status.DELIVERED, order5Hamburguers, "1346257368368365736787", Operadora.AMEX, 951, "Av. Aí sim"),
                new Order(Status.WAITING, order6Hamburguers, "95690346090564590776362461", Operadora.AMEX, 666, "Algum lugar, sem número"),
                new Order(Status.CONFIRMED, order7Hamburguers, "87980998763446748950437461", Operadora.AMEX, 951, "Rua da Alfândega, 333, Hell de January")));
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
        getMenuInflater().inflate(R.menu.order, menu);
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
            Toast toast = Toast.makeText(this, "Atualizando status dos pedidos...", Toast.LENGTH_LONG);
            toast.show();
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
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_gallery) {
            Intent it = new Intent(this, CartActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_slideshow) {
            // você já tá aqui, mongol
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
