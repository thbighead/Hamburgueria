package com.example.tnc.hamburgueria;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterHamburgueresPersonalizado extends BaseAdapter {

    private final List<Hamburguer> hamburgueres;
    private final Activity act;

    public AdapterHamburgueresPersonalizado(List<Hamburguer> hamburgueres, Activity act) {
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
        View view = act.getLayoutInflater().inflate(R.layout.lista_de_hamburgueres, parent, false);

        Hamburguer hamburguer = hamburgueres.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_de_hamburgueres_nome);
        TextView descricao = (TextView)
                view.findViewById(R.id.lista_de_hamburgueres_descricao);
        TextView price = (TextView)
                view.findViewById(R.id.lista_de_hamburgueres_preco);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_de_hamburgueres_imagem);

        nome.setText(hamburguer.getNome());
        descricao.setText(hamburguer.getDescricao());
        price.setText(hamburguer.getPrice());

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