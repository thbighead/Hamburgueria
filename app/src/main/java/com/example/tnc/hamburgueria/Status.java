package com.example.tnc.hamburgueria;

public enum Status {
    WAITING {
        @Override
        public String toString() {
            return "Aguardando confirmação";
        }
    }, CONFIRMED {
        @Override
        public String toString() {
            return "Confirmado! Seu lanche já está sendo feito";
        }
    }, SENT {
        @Override
        public String toString() {
            return "Enviado para entrega";
        }
    }, DELIVERED {
        @Override
        public String toString() {
            return "Entregue, com apetite";
        }
    }
}
