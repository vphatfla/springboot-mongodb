package com.example.mdb.spring.boot.Controller;

import com.example.mdb.spring.boot.Assembler.ItemAssembler;
import com.example.mdb.spring.boot.model.GroceryItem;
import com.example.mdb.spring.boot.repository.ItemRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ItemController {

    private final ItemRepository itemRepo;
    private final ItemAssembler assembler;
    ItemController(ItemRepository itemRepo, ItemAssembler assembler) {
        this.itemRepo = itemRepo;
        this.assembler = assembler;
    }

    @GetMapping("")
    String emptyPage() {
        return "NOTHING HERE, GO /ITEMS";
    }

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
    public EntityModel<GroceryItem> one(@PathVariable Long id)
    {
        GroceryItem item = itemRepo.findById(id)
                .orElseThrow();
        return assembler.toModel(item);
    }

}
