package com.example.tnc.hamburgueria;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Status status;
    private List<Hamburguer> hamburguers;
    private String cardNumber;
    private Operadora operadora;
    private int cvv; // código de segurança de 3 dígitos
    private String address;

    public Order(Status status,
                 List<Hamburguer> hamburguers,
                 String cardNumber,
                 Operadora operadora,
                 int cvv,
                 String address) {
        this.status = status;
        this.hamburguers = hamburguers;
        //Collections.copy(this.hamburguers, hamburguers);
        this.cardNumber = cardNumber;
        this.operadora = operadora;
        this.cvv = Math.abs(cvv % 1000);
        this.address = address;
    }

    @Override
    public String toString() {
        String podrutos = "";
        Double valor = 0.0;
        for (Hamburguer hamburguer : hamburguers) {
            int qtd = hamburguer.getQuantity();
            valor += hamburguer.getPreco() * qtd;
            podrutos += String.valueOf(qtd) + "x " + hamburguer.getNome() + ", ";
        }
        Log.d("DOIDEIRA", podrutos + "\nTotal: R$" + String.format("%.2f", valor) + "\nStatus: " + this.status.toString());
        return podrutos + "\nTotal: R$" + String.format("%.2f", valor) + "\nStatus: " + this.status.toString();
    }

    public static String nextStatus(Status status) {
        if (status.equals(Status.WAITING))
            return Status.CONFIRMED.toString();
        if (status.equals(Status.CONFIRMED))
            return Status.SENT.toString();
        if (status.equals(Status.SENT) || status.equals(Status.DELIVERED))
            return Status.DELIVERED.toString();

        return Status.WAITING.toString();
    }

    public static Status resolveStatus(String statText) {
        if (statText.equals("Aguardando confirmação")) {
            return Status.WAITING;
        }
        if (statText.equals("Confirmado! Seu lanche já está sendo feito")) {
            return Status.CONFIRMED;
        }
        if (statText.equals("Enviado para entrega")) {
            return Status.SENT;
        }
        if (statText.equals("Entregue, com apetite")) {
            return Status.DELIVERED;
        }

        return Status.WAITING;
    }

    public static Operadora resolveOperadora(String operText) {
        if (operText.equals("Master Card")) {
            return Operadora.MASTER;
        }
        if (operText.equals("Visa")) {
            return Operadora.VISA;
        }
        if (operText.equals("American Express")) {
            return Operadora.AMEX;
        }
        if (operText.equals("ELO")) {
            return Operadora.ELO;
        }

        return Operadora.MASTER;
    }
}
