package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository  extends JpaRepository <Product, Long> {
//    Without "Containing" would be an exact match,and instead of "find" could also be "get"
    Page<Product> findByNameContaining(String partialName , Pageable pageable);
    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName , int minimumQuantity , Pageable pageable);

    //    Java Persistence Query Language syntax
    @Query(value = "SELECT product FROM Product product WHERE" +
            "(:partialName IS null OR product.name LIKE %:partialName) AND"+
            "(:minimumQuantity IS null OR product.quantity >= :minimimQuantity)")
    Page<Product> findByOptionalCriteria(String partialName, Integer minimumQuantity , Pageable pageable);

}
