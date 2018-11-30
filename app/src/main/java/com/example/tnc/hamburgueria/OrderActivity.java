package com.example.tnc.hamburgueria;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order);

            db = openOrCreateDatabase("hamdb", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS `order`(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "status TEXT, " +
                    "cardNumber TEXT, " +
                    "operadora TEXT, " +
                    "cvv INTEGER, " +
                    "address TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS orderItem(hamburguer_id INTEGER, " +
                    "order_id INTEGER, " +
                    "quantity INTEGER)");

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
        } catch (Exception e) {
            showAlert("Erro recuperando pedidos: " + e.getMessage());
        }
    }

    public void showAlert(String mensagem) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Aviso!");
        dialogo.setMessage(mensagem);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    /**
     * Exemplo qualquer de devolução de uma lista de pedidos.
     *
     * @return lista com todos os pedidos
     */
    private List<Order> todosOsPedidos() {
        Cursor orders = db.query("`order`", (new String[]{
                "id",
                "status",
                "cardNumber",
                "operadora",
                "cvv",
                "address"
        }), null, null, null, null, null);
        List<Order> list = new ArrayList<>();
        List<Hamburguer> productsList = new ArrayList<>();
        boolean orderCursor = orders.moveToFirst(), itemsCursor;
        List<Hamburguer> hamburguers = MainActivity.todosOsHamburgueres();

        while (orderCursor) {
            Cursor orderItems = db.query("orderItem",
                    (new String[]{
                            "hamburguer_id",
                            "order_id",
                            "quantity"
                    }),
                    "order_id = " + String.valueOf(orders.getInt(0)),
                    null, null, null, null);
            itemsCursor = orderItems.moveToFirst();

            while (itemsCursor) {
                Hamburguer ham = hamburguers.get(orderItems.getInt(0));
                ham.setQuantity(orderItems.getInt(2));
                //showAlert("debuger de item:\n" + orderItems.getInt(0) + "\n" + ham.toString());
                productsList.add(ham);
                itemsCursor = orderItems.moveToNext();
            }

            //for (Hamburguer h : productsList) {
              //  showAlert("debuger de item:\n" + h.toString() + "\nQuantity: " + String.valueOf(h.getQuantity()));
            //}

            //showAlert("debuger:\n"
              //      + orders.getString(1) + "\n"
                //    + orders.getString(2) + "\n"
            //        + orders.getString(3) + "\n"
              //      + orders.getInt(4) + "\n"
                //    + orders.getString(5) + "\n");

            Order mountedOrder = new Order(Order.resolveStatus(orders.getString(1)),
                    productsList,
                    orders.getString(2),
                    Order.resolveOperadora(orders.getString(3)),
                    orders.getInt(4),
                    orders.getString(5));

            list.add(mountedOrder);

            productsList = new ArrayList<>();
            orderItems.close();
            orderCursor = orders.moveToNext();
        }
        orders.close();

        return list;
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
            try {
                Toast toast = Toast.makeText(this, "Atualizando status dos pedidos...", Toast.LENGTH_LONG);
                toast.show();
                Status[] status = Status.values();

                for (int i = status.length - 2; i >= 0; i--) {
                    db.execSQL("UPDATE `order` SET status='" + Order.nextStatus(status[i]) + "' WHERE status='" + status[i].toString() + "'");
                }

                finish();
                startActivity(getIntent());
            } catch (Exception e) {
                showAlert("Erro ao atualizar pedidos: " + e.getMessage());
            }

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
