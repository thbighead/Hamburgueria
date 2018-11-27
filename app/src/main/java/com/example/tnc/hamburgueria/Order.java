package com.example.tnc.hamburgueria;

import java.util.Collections;
import java.util.List;

public class Order {
    private Status status;
    private List<Hamburguer> hamburguers;
    private String cardNumber;
    private Operadora operadora;
    private int cvv; // código de segurança de 3 dígitos
    private String address;

    public Order(Status status, List<Hamburguer> hamburguers, String cardNumber, Operadora operadora, int cvv, String address) {
        this.status = status;
        // this.hamburguers = hamburguers; só sem referência
        Collections.copy(this.hamburguers, hamburguers);
        this.cardNumber = cardNumber;
        this.operadora = operadora;
        this.cvv = Math.abs(cvv % 1000);
        this.address = address;
    }
}
