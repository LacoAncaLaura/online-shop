package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.GetProductsRequest;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Spring Beans(Service,Repositories)
@Service
public class ProductService {
    //Lombok(librarie pt generari automate -> pt getteri si setteri,constructori) ATTENTION import org.slf4j.Logger !!!!
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    //    inversion of control (IoC)
    private final ProductRepository productRepository;

    //    Dependency Injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        //    todo: replace sout with logger for printing-->replaced with logger.info!!!!For detailed/warning/errors Logger use debug/warn/error instead info
        LOGGER.info("Creating product{}", request);
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());
        return productRepository.save(product);

    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product{}", id);

//        Optional<Product> productOptional = productRepository.findById(id);
//        if(productOptional.isPresent()){
//            return productOptional.get();
//        }else{
//            throw new ResourceNotFoundException("Product" + id + "not found");
//        }
//        or
//        --Lambda expression--
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product" + id + "not found"));

//        Does the same thing like the OPTIONAL above
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        if (request.getPartialName() != null && request.getMinimumQuantity() != null) {

            productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(), request.getMinimumQuantity(), pageable);

        } else if (request.getPartialName() != null) {

            return productRepository.findByNameContaining(request.getPartialName(), pageable);

        } else{ return productRepository.findAll(pageable); }

        return productRepository.findAll(pageable);
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);
        Product product = getProduct(id);
        BeanUtils.copyProperties(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
    }

}
