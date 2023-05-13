package com.example.easyrestapp.model;

public class Payment {

    String paymentMethod;
    Double price;

    public Payment(String paymentMethod, Double price) {
        this.paymentMethod = paymentMethod;
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
