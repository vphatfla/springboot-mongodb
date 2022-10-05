package com.example.mdb.spring.boot.repository;

import com.example.mdb.spring.boot.model.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<GroceryItem, Long> {

    @Query("{name:'?0'}")
    GroceryItem findItemByName(String name);

    @Query(value = "{categoru:'?0'}", fields = "{'name' :  1, 'quantity': 1}")
    List<GroceryItem> findAll(String category);

    public long count();

}
