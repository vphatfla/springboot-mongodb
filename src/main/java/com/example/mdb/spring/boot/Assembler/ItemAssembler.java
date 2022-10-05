package com.example.mdb.spring.boot.Assembler;

import com.example.mdb.spring.boot.Controller.ItemController;
import com.example.mdb.spring.boot.model.GroceryItem;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemAssembler implements RepresentationModelAssembler<GroceryItem, EntityModel<GroceryItem>>
{

    @Override
    public EntityModel<GroceryItem> toModel(GroceryItem groceryItem){

        return EntityModel.of(groceryItem,
                linkTo(methodOn(ItemController.class).one(groceryItem.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all()).withRel("items"));
    }
}
