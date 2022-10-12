package com.example.mdb.spring.boot.Controller;

import com.example.mdb.spring.boot.Assembler.ItemAssembler;
import com.example.mdb.spring.boot.model.GroceryItem;
import com.example.mdb.spring.boot.repository.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ItemController {

    private final ItemRepository itemRepo;
    private final ItemAssembler assembler;
    ItemController(ItemRepository itemRepo, ItemAssembler assembler) {
        this.itemRepo = itemRepo;
        this.assembler = assembler;
    }

    /*@GetMapping("")
    String emptyPage() {
        return "NOTHING HERE, GO /ITEMS";
    } */


    @GetMapping("/items")
    public CollectionModel<EntityModel<GroceryItem>> all()
    {
        List<EntityModel<GroceryItem>> items = itemRepo.findAll()
                .stream().map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(items,
                linkTo(methodOn(ItemController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/items/{id}")
    public EntityModel<GroceryItem> one(@PathVariable ObjectId id)
    {
        GroceryItem item = itemRepo.findById(id)
                .orElseThrow();
        return assembler.toModel(item);
    }

    // add new items
    @PostMapping("/items")
    ResponseEntity<?> newItem(@RequestBody GroceryItem newItem)
    {
        System.out.println(newItem);
        EntityModel<GroceryItem> entityModel = assembler.toModel(itemRepo.save(newItem));
        System.out.println(entityModel);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("items/{id}")
    ResponseEntity<?> updatingItem(@RequestBody GroceryItem updatingItem, @PathVariable ObjectId id )
    {
        GroceryItem matchingItem = itemRepo.findById(id)
                .map(item -> {
                    item.setName(updatingItem.getName());
                    item.setCategory(updatingItem.getCategory());
                    item.setQuantity(updatingItem.getQuantity());
                    return itemRepo.save(item);
                })
                .orElseGet(() -> {
                    updatingItem.setId(id);
                    return itemRepo.save(updatingItem);
                });
        EntityModel<GroceryItem> entityModel = assembler.toModel(matchingItem);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("items/{id}")
    ResponseEntity<?> deletingItem(@PathVariable ObjectId id)
    {
        itemRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
