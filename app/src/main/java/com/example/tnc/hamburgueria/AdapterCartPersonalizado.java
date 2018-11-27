package com.example.tnc.hamburgueria;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterCartPersonalizado extends BaseAdapter {

    private final List<Hamburguer> hamburgueres;
    private final Activity act;

    public AdapterCartPersonalizado(List<Hamburguer> hamburgueres, Activity act) {
        this.hamburgueres = hamburgueres;
        this.act = act;
    }

    @Override
    public int getCount() {
        return hamburgueres.size();
    }

    @Override
    public Object getItem(int position) {
        return hamburgueres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.lista_do_carrinho, parent, false);

        Hamburguer hamburguer = hamburgueres.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_do_carrinho_nome);
        TextView price = (TextView)
                view.findViewById(R.id.lista_do_carrinho_preco);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_do_carrinho_imagem);

        nome.setText(String.valueOf(hamburguer.getQuantity()) + "x " + hamburguer.getNome());
        price.setText("Valor: R$" + String.format("%.2f", hamburguer.getPreco() * hamburguer.getQuantity()));

        ImagemDeVitrine img = hamburguer.getVitrine();

        if (img.equals(ImagemDeVitrine.p0)) {
            imagem.setImageResource(R.drawable.p0);
        } else if (img.equals(ImagemDeVitrine.p1)) {
            imagem.setImageResource(R.drawable.p1);
        } else if (img.equals(ImagemDeVitrine.p2)) {
            imagem.setImageResource(R.drawable.p2);
        } else if (img.equals(ImagemDeVitrine.p3)) {
            imagem.setImageResource(R.drawable.p3);
        } else if (img.equals(ImagemDeVitrine.p4)) {
            imagem.setImageResource(R.drawable.p4);
        } else if (img.equals(ImagemDeVitrine.p5)) {
            imagem.setImageResource(R.drawable.p5);
        } else if (img.equals(ImagemDeVitrine.p6)) {
            imagem.setImageResource(R.drawable.p6);
        } else if (img.equals(ImagemDeVitrine.p7)) {
            imagem.setImageResource(R.drawable.p7);
        } else if (img.equals(ImagemDeVitrine.p8)) {
            imagem.setImageResource(R.drawable.p8);
        } else if (img.equals(ImagemDeVitrine.p9)) {
            imagem.setImageResource(R.drawable.p9);
        }

        return view;
    }
}