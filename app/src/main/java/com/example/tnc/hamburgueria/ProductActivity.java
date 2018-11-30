package com.example.tnc.hamburgueria;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        db = openOrCreateDatabase("hamdb", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(hamburguer_id INTEGER, quantity INTEGER DEFAULT 0)");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;
                TextView qtd = (TextView)findViewById(R.id.nproducts);
                TextView hamName = (TextView)findViewById(R.id.productName);
                String hamNameStr = hamName.getText().toString();
                int qtdStr = Integer.parseInt(qtd.getText().toString()),
                        hamId = Integer.parseInt(hamNameStr.substring(hamNameStr.length() - 1));
                if (qtdStr > 0) {
                    toast = Toast.makeText(ProductActivity.this, "Adicionando produto(s) ao carrinho...", Toast.LENGTH_LONG);
                    toast.show();

                    ContentValues newPedido = new ContentValues();
                    newPedido.put("hamburguer_id", hamId);
                    newPedido.put("quantity", qtdStr);
                    db.insert("cart", null, newPedido);

                    Intent it = new Intent(ProductActivity.this, CartActivity.class);
                    startActivity(it);
                } else {
                    toast = Toast.makeText(ProductActivity.this, "Selecione a quantidade", Toast.LENGTH_LONG);
                    toast.show();
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

        ImageView img = (ImageView)findViewById(R.id.imageView2);
        TextView price = (TextView)findViewById(R.id.price);
        TextView pname = (TextView)findViewById(R.id.productName);

        String name = myIntent.getStringExtra("name");
        price.setText(myIntent.getStringExtra("price"));
        pname.setText(name);
        int pId = Integer.parseInt(name.substring(name.length() - 1));
        switch (pId) {
            case 0:
                img.setImageResource(R.drawable.p0);
                break;
            case 1:
                img.setImageResource(R.drawable.p1);
                break;
            case 2:
                img.setImageResource(R.drawable.p2);
                break;
            case 3:
                img.setImageResource(R.drawable.p3);
                break;
            case 4:
                img.setImageResource(R.drawable.p4);
                break;
            case 5:
                img.setImageResource(R.drawable.p5);
                break;
            case 6:
                img.setImageResource(R.drawable.p6);
                break;
            case 7:
                img.setImageResource(R.drawable.p7);
                break;
            case 8:
                img.setImageResource(R.drawable.p8);
                break;
            case 9:
                img.setImageResource(R.drawable.p9);
                break;
        }
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
        getMenuInflater().inflate(R.menu.product, menu);
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
            Intent it = new Intent(this, CartActivity.class);
            startActivity(it);
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
            Intent it = new Intent(this, OrderActivity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sumItem(View view) {
        TextView nproducts = (TextView)findViewById(R.id.nproducts);

        nproducts.setText(String.valueOf(Integer.valueOf(nproducts.getText().toString()) + 1));
    }

    public void diminishItem(View view) {
        TextView nproducts = (TextView)findViewById(R.id.nproducts);
        int qtd = Integer.valueOf(nproducts.getText().toString()) - 1;
        qtd = qtd >= 0 ? qtd : 0;

        nproducts.setText(String.valueOf(qtd));
    }
}
