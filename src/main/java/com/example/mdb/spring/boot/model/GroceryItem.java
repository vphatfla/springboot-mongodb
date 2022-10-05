package com.example.mdb.spring.boot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("groceryitems")
public class GroceryItem {

    private Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

    private String name;
    private int quantity;
    private String category;
    public GroceryItem(String name, int quantity, String category){
        super();
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public Long getId() {
        return this.id;
    }
}
