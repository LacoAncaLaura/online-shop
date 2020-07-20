package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.steps.ProductTestSteps;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;


@SpringBootTest
class ProductServiceIntegrationTests {
    //  Field injection
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void createProduct_whenValidRequest_thenReturnCreatedProduct() {
        productTestSteps.createProduct();
    }


    @Test
    void createProduct_whenMissingMandatoryProperties_thenThrowException() {
        SaveProductRequest request = new SaveProductRequest();
        try {
            Product product = productService.createProduct(request);
        } catch (Exception e) {
            assertThat("Unexpected exception throw", e instanceof ConstraintViolationException);
        }


    }

    //    Negative test
    @Test
    void getProduct_whenExistingProduct_thenReturnProduct() {
        Product product = productTestSteps.createProduct();
        Product response = productService.getProduct(product.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(product.getId()));
        assertThat(response.getName(), is(product.getName()));
        assertThat(response.getPrice(), is((product.getPrice())));
        assertThat(response.getQuantity(), is((product.getQuantity())));
        assertThat(response.getDescription(), is((product.getDescription())));
        assertThat(response.getImageUrl(), is((product.getImageUrl())));

    }

    @Test
    void getProduct_whenNonExistingProduct_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(0));
    }

    @Test
    void updateProduct_whenValidRequest_thenUpdatingProduct() {
        Product product = productTestSteps.createProduct();
        SaveProductRequest request = new SaveProductRequest();
        request.setName(product.getName() + "");
        request.setPrice(product.getPrice());
        request.setQuantity(product.getQuantity());
        Product updateProduct = productService.updateProduct(product.getId(), request);
        assertThat(updateProduct, notNullValue());
        assertThat(updateProduct.getId(), is(product.getId()));
        assertThat(updateProduct.getName(), is(product.getName()));
        assertThat(updateProduct.getPrice(), is((product.getPrice())));
        assertThat(updateProduct.getQuantity(), is((product.getQuantity())));
    }

    @Test
    void deleteProduct_whenExistingProduct_theProductDoseNotExistAnymore() {
        Product product = productTestSteps.createProduct();
        productService.deleteProduct(product.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(0));
    }

}
