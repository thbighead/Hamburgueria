package com.example.tnc.hamburgueria;

public class Hamburguer {
    private String name;
    private String descricao;
    private Double price;
    private ImagemDeVitrine img;

    public Hamburguer(String name, String descricao, Double price, ImagemDeVitrine img) {
        this.name = name;
        this.descricao = descricao;
        this.price = price;
        this.img = img;
    }

    public String getNome() {
        return name;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPrice() {
        return "R$" + String.format("%.2f", price);
    }

    public ImagemDeVitrine getVitrine() {
        return img;
    }

    @Override
    public String toString() {
        return name + "\nDescrição: " + descricao + "\nPreço: " + price.toString();
    }
}
