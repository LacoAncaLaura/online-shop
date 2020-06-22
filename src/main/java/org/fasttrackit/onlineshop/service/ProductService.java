package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Spring Beans(Service,Repositories)
@Service
public class ProductService {
//Lombok(librarie pt generari automate -> pt getteri si setteri,constructori)
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
//    inversion of control (IoC)
    private final ProductRepository productRepository;

//    Dependency Injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product createProduct(SaveProductRequest request){
        //    todo: replace sout with logger for printing-->replaced with logger.info!!!!For detailed/warning/errors Logger use debug/warn/error instead info
        LOGGER.info("Creating product{}",request);
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());
         return   productRepository.save(product);

    }
}
