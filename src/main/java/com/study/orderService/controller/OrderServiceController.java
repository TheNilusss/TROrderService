package com.study.orderService.controller;

import com.study.orderService.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.study.orderService.repository.OrderRepository;

import java.util.ArrayList;

@RestController
public class OrderServiceController {

    ResponseObject resObj = new ResponseObject();

    @Autowired
    private OrderRepository repository;

    @PostMapping("/order")
    void order(@RequestParam final String CaID) {

        repository.save(new Ordern("Hallo","Hallo",4.99, "€"));

        if (existCart(CaID)) {
            Cart cart = resObj.receive(Cart.class, "http://localhost:8083/cart?ID=" + CaID);

            double price = 0;
            String currency = "none";

            for (String id : cart.getSelectedItems().keySet()) {

                if (existProduct(id)) {
                    Product product = resObj.receive(Product.class, "http://localhost:8082/product?ID=" + id);
                    price = price + product.price * cart.getSelectedItems().get(id).intValue();
                    currency = product.currency;
                } else {
                    System.out.println("Product don't exist anymore: " + id);
                }
            }

            if (existCustomer(cart.cuID)) {
                repository.save(new Ordern(cart.cuID,cart.id,price,currency));
                System.out.println("Order saved.");
            } else {
                System.out.println("Customer don't exist anymore: ");
            }
        } else {
            System.out.println("Cart don't exist anymore: ");
        }
    }

    boolean existCart(String ID) {
        Cart cart = resObj.receive(Cart.class, "http://localhost:8083/cart?ID=" + ID);
        if (cart == null) {
            System.out.println("Cart don't exist.");
            return false;
        }
        return true;
    }

    boolean existProduct(String ID) {
        Product product = resObj.receive(Product.class, "http://localhost:8082/product?ID=" + ID);
        if (product == null) {
            System.out.println("Product don't exist.");
            return false;
        }
        return true;
    }

    boolean existCustomer(String ID) {
        Customer customer = resObj.receive(Customer.class, "http://localhost:8081/customer?ID=" + ID);
        if (customer == null) {
            System.out.println("Customer don't exist.");
            return false;
        }
        return true;
    }

    @GetMapping("/order")
    Ordern getOrder(@RequestParam final String ID) {
        Ordern ordern = repository.findByid(ID);
        if (ordern != null) {
            System.out.println("send order by ID: " + ordern);
        } else {
            System.out.println("send no order");
        }
        return ordern;
    }

    //DEBUGGING
    @GetMapping("/order/db/show")
    ArrayList<Ordern> showDb() {
        ArrayList<Ordern> arrayListObj = new ArrayList<>();
        for (Ordern obj : repository.findAll()) {
            arrayListObj.add(obj);
        }
        return arrayListObj;
    }

    //DEBUGGING
    @PostMapping("/order/db/clear")
    void clear() {
        repository.deleteAll();
    }
}