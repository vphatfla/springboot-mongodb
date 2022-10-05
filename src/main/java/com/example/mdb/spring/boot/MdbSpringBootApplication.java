package com.example.mdb.spring.boot;

import com.example.mdb.spring.boot.model.GroceryItem;
import com.example.mdb.spring.boot.repository.CustomeItemrepository;
import com.example.mdb.spring.boot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class MdbSpringBootApplication  implements CommandLineRunner {

    @Autowired
    ItemRepository groceryItemRepo;

    @Autowired
    CustomeItemrepository customRepo;

    public static void main(String[] args) {
        SpringApplication.run(MdbSpringBootApplication.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("START RUN");
        System.out.println("Delete all old storage");
        itemsDeleteAll();
        System.out.println("-------------CREATE GROCERY ITEMS-------------------------------\n");

        createGroceryItems();

        System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");

        showAllGroceryItems();
/*
        System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");

        getGroceryItemByName("Whole Wheat Biscuit");

        System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");

        getItemsByCategory("millets");

        System.out.println("\n-----------UPDATE CATEGORY NAME OF SNACKS CATEGORY----------------\n");

        updateCategoryName("snacks");

        System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");

        //deleteGroceryItem("Kodo Millet");

        System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");

        findCountOfGroceryItems();

        System.out.println("\n-------------------THANK YOU---------------------------");

        System.out.println("\n-----------UPDATE QUANTITY OF A GROCERY ITEM------------------------\n");

        updateItemQuantity("Bonny Cheese Crackers Plain", 121); */
        System.out.println("RUN");
    }

    public void createGroceryItems()
    {
        System.out.println("Data creation started...");
        groceryItemRepo.save(new GroceryItem( "Whole Wheat Biscuit", 5, "snacks"));
        groceryItemRepo.save(new GroceryItem( "XYZ Kodo Millet healthy", 2, "millets"));
        groceryItemRepo.save(new GroceryItem( "Dried Whole Red Chilli", 2, "spices"));
        groceryItemRepo.save(new GroceryItem( "Healthy Pearl Millet", 1, "millets"));
        groceryItemRepo.save(new GroceryItem( "Bonny Cheese Crackers Plain", 6, "snacks"));
        System.out.println("Data creation complete");

    }

    public void showAllGroceryItems()
    {
        groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    public void getGroceryItemByName(String name)
    {
        System.out.println("Getting item by name: " +name);
        GroceryItem item = groceryItemRepo.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

    // 3. Get name and quantity of a all items of a particular category
    public void getItemsByCategory(String category) {
        System.out.println("Getting items for the category " + category);
        List<GroceryItem> list = groceryItemRepo.findAll(category);

        list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
    }
    public void findCountOfGroceryItems(){
        long count = groceryItemRepo.count();
        System.out.println("Number of documents in the collection: " +count);
    }

    // Print details in readable form

    public String getItemDetails(GroceryItem item) {

        System.out.println(
                "Item Name: " + item.getName() +
                        ", \nQuantity: " + item.getQuantity() +
                        ", \nItem Category: " + item.getCategory()
        );

        return "";
    }

    // update
    public void updateCategoryName(String category)
    {
        String newCategory = "somethingnew";

        List<GroceryItem> list = groceryItemRepo.findAll(category);

        list.forEach(item -> {
            item.setCategory(newCategory);
        });

        List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);

        if (itemsUpdated != null)
        {
            System.out.println("Successfully updated " + itemsUpdated.size() + " size" );
        }
    }

    // delete
    public void deleteGroceryItem(Long id)
    {
        groceryItemRepo.deleteById(id);
        System.out.println("item with id " + id + " has been deleted");
    }

    // update quantity
    public void updateItemQuantity(String name, float newQuantity)
    {
        System.out.println("Updating quantity for " + name);
        customRepo.updateItemQuantity(name, newQuantity);
    }

    public void itemsDeleteAll()
    {
        groceryItemRepo.deleteAll();

    }
}
