package com.example.tnc.hamburgueria;

public enum Operadora {
    MASTER {
        @Override
        public String toString() {
            return "Master Card";
        }
    }, VISA {
        @Override
        public String toString() {
            return "Visa";
        }
    }, AMEX {
        @Override
        public String toString() {
            return "American Express";
        }
    }, ELO {
        @Override
        public String toString() {
            return "ELO";
        }
    }
}
