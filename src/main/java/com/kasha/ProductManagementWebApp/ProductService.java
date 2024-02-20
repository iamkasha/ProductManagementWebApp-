package com.kasha.ProductManagementWebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  ProductService {

    @Autowired
    ProductDB db;

//    public void show(){
//        System.out.println("In show");
//    }

    public void addProduct(Product p) {
        db.save(p);
    }
//
    public List<Product> getAllProducts() {
        return db.findAll();
    }
//
    public Product getProduct(String name) {
        return db.findByName(name);
    }

//
}
