package org.example.model;

import org.example.services.UserService;

import java.util.Objects;
import java.util.Random;

public class Order {

    private String clientUsername;
    private Product product;
    private int orderNo;
    private String status;

    public Order(){

    }
    public Order(String clientUsername, Product product, int orderNo, String status) {
        this.clientUsername = clientUsername;
        this.product = product;
        this.orderNo = orderNo;
        this.status = status;
        //System.out.println(this.clientUsername);
       // System.out.println(this.orderNo);
        //System.out.println(this.product);

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public Product getProduct() {return product;};

    public void setProduct(Product product) {this.product = product;};

    public int getOrderNo() { return orderNo;}

    public void setOrderNo(int orderNo) { this.orderNo = orderNo;};

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNo == order.orderNo &&
                Objects.equals(clientUsername, order.clientUsername) &&
                Objects.equals(product, order.product);

    }

    public int hashCode() {
        return Objects.hash(clientUsername, product, orderNo);
    }

    @Override
    public String toString() {
        try {
            return "Order no: " + this.orderNo + ", Product: " + this.product.getProductName() + ", Price: " + this.product.getPrice() + ", Brand: " + this.product.getBrandName() + ", Client: " + UserService.getUser(this.clientUsername).getName() + ", Status: " + this.status;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}
