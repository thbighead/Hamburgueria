package com.example.tnc.hamburgueria;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = openOrCreateDatabase("hamdb", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(hamburguer_id INTEGER, quantity INTEGER DEFAULT 0)");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Hamburguer> hamburgueres = todosOsItens();
                    if (hamburgueres.size() > 0) {
                        Intent it = new Intent(CartActivity.this, PaymentActivity.class);
                        startActivity(it);
                    } else {
                        Toast toast = Toast.makeText(CartActivity.this, "Carrinho vazio...", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (Exception e) {
                    showAlert("Erro recuperando itens do carrinho: " + e.getMessage());
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<Hamburguer> hamburgueres = new ArrayList<>();
        try {
            hamburgueres = todosOsItens();
        } catch (Exception e) {
            showAlert("Erro recuperando itens do carrinho: " + e.getMessage());
        }
        ListView lista = (ListView) findViewById(R.id.lista_do_carrinho);
        AdapterCartPersonalizado adapter = new AdapterCartPersonalizado(hamburgueres, this);
        lista.setAdapter(adapter);
    }

    public void showAlert(String mensagem) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Aviso!");
        dialogo.setMessage(mensagem);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    /**
     * @return lista com todos os hamburgueres
     */
    public List<Hamburguer> todosOsItens() {
        List<Hamburguer> hamburgueres = MainActivity.todosOsHamburgueres();
        Hamburguer ham;
        List<Hamburguer> lista = new ArrayList<>();
        Cursor cart = db.query("cart", (new String[]{
                "hamburguer_id",
                "quantity"
        }), null, null, null, null, null);
        boolean cartCursor = cart.moveToFirst();

        while (cartCursor) {
            ham = hamburgueres.get(cart.getInt(0));
            ham.setQuantity(cart.getInt(1));
            lista.add(ham);
            cartCursor = cart.moveToNext();
        }
        cart.close();

        return lista;
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
        getMenuInflater().inflate(R.menu.cart, menu);
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
            Toast toast = Toast.makeText(this, "Limpando lista de produtos do carrinho...", Toast.LENGTH_LONG);
            toast.show();
            db.delete("cart", null, null);
            finish();
            startActivity(getIntent());
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
            // você já tá aqui, mongol
        } else if (id == R.id.nav_slideshow) {
            Intent it = new Intent(this, OrderActivity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
