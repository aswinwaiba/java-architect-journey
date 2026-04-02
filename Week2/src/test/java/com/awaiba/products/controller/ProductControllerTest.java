package com.awaiba.products.controller;

import com.awaiba.products.model.Product;
import com.awaiba.products.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

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

//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "\n", "\t", "   "})
//    void shouldNotAddInvalidProduct(String invalidProductName) throws Exception {
//        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= post("/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"productName\": \""+invalidProductName+"\"}");
//
//        mockMvc.perform(mockHttpServletRequestBuilder)
//                .andExpect(status().is());
//    }

    @Test
    void shouldDelete() throws Exception {
        when(productService.delete("TestProduct")).thenReturn(true);

        mockMvc.perform(delete("/products/TestProduct"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentProduct() throws Exception {
        when(productService.delete("NonExistentProduct")).thenReturn(false);

        mockMvc.perform(delete("/products/NonExistentProduct"))
                .andExpect(status().isNotFound());
    }
}