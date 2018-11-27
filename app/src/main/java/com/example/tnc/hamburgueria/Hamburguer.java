package com.example.tnc.hamburgueria;

public class Hamburguer {
    private String name;
    private String descricao;
    private Double price;
    private ImagemDeVitrine img;
    private int quantity;

    public Hamburguer(String name, String descricao, Double price, ImagemDeVitrine img) {
        this.name = name;
        this.descricao = descricao;
        this.price = price;
        this.img = img;
        this.quantity = 0;
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

    public void setQuantity(int quantity) {
        if (quantity >= 0)
            this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + "\nDescrição: " + descricao + "\nPreço: " + price.toString();
    }
}
