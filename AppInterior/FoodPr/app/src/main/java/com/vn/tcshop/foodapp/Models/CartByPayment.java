package com.vn.tcshop.foodapp.Models;

public class CartByPayment {
    private int transportation_costs;
    private int total_price;
    private int total_quantity;
    private int total_payment;

    public int getTransportation_costs() {
        return transportation_costs;
    }

    public void setTransportation_costs(int transportation_costs) {
        this.transportation_costs = transportation_costs;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }
}
