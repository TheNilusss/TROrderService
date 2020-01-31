package com.study.orderService.repository;


import com.study.orderService.entity.Ordern;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Ordern, String> {
    public Ordern findByid(String id);
    public Ordern findByCustomerID(String customerID);
    public Ordern findByCartID(String CartID);
    public Ordern findByMoneyPaid(double price);
}