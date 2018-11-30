package com.example.tnc.hamburgueria;

import android.content.ContentValues;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

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
                try {
                    Toast toast;
                    TextView numCard = (TextView) view.findViewById(R.id.cardNumberTxt);
                    TextView cvv = (TextView) view.findViewById(R.id.cvvTxt);
                    RadioGroup rdgpOpera = (RadioGroup) findViewById(R.id.operadoraGroup);
                    RadioButton selectedOpera = (RadioButton) findViewById(rdgpOpera.getCheckedRadioButtonId());
                    TextView address = (TextView) view.findViewById(R.id.addressTxt);
                    String numCardStr = numCard.getText().toString(),
                            cvvStr = cvv.getText().toString(),
                            operaStr = selectedOpera.getText().toString(),
                            addressStr = address.getText().toString();
                    if (numCardStr.length() > 0 && cvvStr.length() == 3 && operaStr.length() > 0 && addressStr.length() > 0) {
                        toast = Toast.makeText(PaymentActivity.this, "Pagamento efetuado!", Toast.LENGTH_LONG);
                        toast.show();

                        List<Hamburguer> pedidosNoCarrinho = todosOsItens();
                        ContentValues newPedido = new ContentValues();
                        newPedido.put("status", Status.WAITING.toString());
                        newPedido.put("cardNumber", numCardStr);
                        newPedido.put("operadora", operaStr);
                        newPedido.put("address", addressStr);

                        db.insert("`order`", null, newPedido);

                        newPedido = new ContentValues();
                        Cursor cursor = db.query("`order`", new String[]{"*"},
                                null, null, null, null, null);
                        cursor.moveToLast();
                        String justAddedId = String.valueOf(cursor.getInt(0));
                        cursor.close();
                        for (Hamburguer ham : pedidosNoCarrinho) {
                            String nome = ham.getNome();
                            newPedido.put("hamburguer_id", nome.substring(nome.length() - 1));
                            newPedido.put("order_id", justAddedId);
                            newPedido.put("quantity", ham.getQuantity());
                            db.insert("orderItem", null, newPedido);
                        }

                        Intent it = new Intent(PaymentActivity.this, OrderActivity.class);
                        startActivity(it);
                    } else {
                        toast = Toast.makeText(PaymentActivity.this, "Preencha todos os campos corretamente", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (Exception e) {
                    showAlert("Erro cadastrar pedido: " + e.getMessage());
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
    }

    public void showAlert(String mensagem) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Aviso!");
        dialogo.setMessage(mensagem);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

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
        getMenuInflater().inflate(R.menu.payment, menu);
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
            Toast toast = Toast.makeText(this, "Pagamento cancelado", Toast.LENGTH_LONG);
            toast.show();
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
}
