package com.awaiba.products.service;

import com.awaiba.products.model.Product;
import com.awaiba.products.repository.ProductRepository;
import com.awaiba.products.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

//    @BeforeEach
//    void setUp() {
//        productService = new ProductService();
//    }

    @Test
    void shouldAddProduct() {
        ArgumentCaptor<Product> capture = ArgumentCaptor.forClass(Product.class);
        Product p = new Product(" Test ");
        productService.add(p);

        verify(productRepository).save(capture.capture());
        assertEquals("Test", capture.getValue().productName());
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.deleteByName("Test")).thenReturn(true);

        assertDoesNotThrow(() -> productService.delete("Test"));

        verify(productRepository).deleteByName("Test");
    }


    @Test
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        when(productRepository.deleteByName("NonExistentProduct")).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.delete("NonExistentProduct"));

        verify(productRepository).deleteByName("NonExistentProduct");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringAddForNullValues(){
        assertThrows(IllegalArgumentException.class, () -> productService.add(null));
        verifyNoInteractions(productRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t", "   "})
    void shouldThrowIllegalArgumentExceptionDuringAddForEmptyOrBlankValues(String productName) {
        assertThrows(IllegalArgumentException.class, () -> productService.add(new Product(productName)));
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringDeleteForNull() {
        assertThrows(IllegalArgumentException.class, () -> productService.delete(null));
        verifyNoInteractions(productRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t", "   "})
    void shouldThrowIllegalArgumentExceptionDuringDeleteForEmptyOrBlankValues(String productName) {
        assertThrows(IllegalArgumentException.class, () -> productService.delete(productName));
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldDelegateToRepositoryWhenGetAll() {
        Product p = new Product("Test");
        when(productRepository.findAll()).thenReturn(List.of(p));

        List<Product> l = productService.getAll();

        assertEquals(1, l.size());
        verify(productRepository).findAll();
    }
}