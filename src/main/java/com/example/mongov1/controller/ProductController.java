package com.example.mongov1.controller;

import com.example.mongov1.Repo.ProductRepo;
import com.example.mongov1.model.Product;
import com.example.mongov1.resource.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>>getAllProduct(){
        return ResponseEntity.ok(this.productRepo.findAll());
    }

    @PostMapping("/save_products")
    public ResponseEntity<Product>CreateProduct(@RequestBody ProductRequest productRequest){
        Product product=new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        return ResponseEntity.status(201).body(this.productRepo.save(product));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProductById(@PathVariable String id){
        Optional<Product>product=this.productRepo.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }else {
            return ResponseEntity.ok("Not Found Product "+id);
        }
    }


    @DeleteMapping("delete_Product/{id}")
    public ResponseEntity deleteProductById(@PathVariable String id){
        Optional<Product>product=this.productRepo.findById(id);
        if(product.isPresent()){
            this.productRepo.deleteById(id);
            return ResponseEntity.ok("Deleted");
        }else {
            return ResponseEntity.ok("Not Found Product "+id);
        }
    }

}
