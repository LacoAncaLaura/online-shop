package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository <Product, Long> {
//    Without "Containing" would be an exact match,and instead of find could also be get
    Page<Product> findByNameContaining(String partialName , Pageable pageable);
    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName , int MinimumQuantity , Pageable pageable);



}
