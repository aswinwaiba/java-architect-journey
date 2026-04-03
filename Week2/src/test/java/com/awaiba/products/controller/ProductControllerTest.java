package com.awaiba.products.controller;

import com.awaiba.products.exception.ProductNotFoundException;
import com.awaiba.products.model.Product;
import com.awaiba.products.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldGetAll() throws Exception {
        List<Product> products = List.of(new Product("Test1"), new Product("Test2"));
        when(productService.getAll()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productName").value("Test1"))
                .andExpect(jsonPath("$[1].productName").value("Test2"));
    }

    @Test
    void shouldReturn201WhenProductIsAdded() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= post("/products")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content("{\"productName\": \"Test Product\"}");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings={"", " ", "\n", "\t", "   "})
    void shouldReturnBadRequestWhenAddingInvalidProduct(String invalidProductName) throws Exception {
        doThrow(new IllegalArgumentException("Empty or blank value")).when(productService).add(new Product(invalidProductName));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\": \""+invalidProductName+"\"}");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDelete() throws Exception {
        mockMvc.perform(delete("/products/TestProduct"))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentProduct() throws Exception {
        doThrow(new ProductNotFoundException("NonExistentProduct")).when(productService).delete("NonExistentProduct");

        mockMvc.perform(delete("/products/NonExistentProduct"))
                .andExpect(status().isNotFound());
    }
}