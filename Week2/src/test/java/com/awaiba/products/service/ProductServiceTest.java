package com.awaiba.products.service;

import com.awaiba.products.model.Product;
import com.awaiba.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        Product p = new Product("Test");
        productService.add(p);

        verify(productRepository).save(p);
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.deleteByName("Test")).thenReturn(true);

        boolean returnVal = productService.delete("Test");

        assertTrue(returnVal);
        verify(productRepository).deleteByName("Test");
    }


    @Test
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        when(productRepository.deleteByName("NonExistentProduct")).thenReturn(false);

        boolean returnVal = productService.delete("NonExistentProduct");

        assertFalse(returnVal);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryHasNoProducts() {
        when(productRepository.findAll()).thenReturn(List.of());

        assertEquals(0, productService.getAll().size());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringAddForNullValues(){
        assertThrows(IllegalArgumentException.class, () -> productService.add(null));
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringAddForEmptyOrBlankValues() {
        assertThrows(IllegalArgumentException.class, () -> productService.add(new Product("")));
        assertThrows(IllegalArgumentException.class, () -> productService.add(new Product(" ")));
        assertThrows(IllegalArgumentException.class, () -> productService.add(new Product("\n")));
        assertThrows(IllegalArgumentException.class, () -> productService.add(new Product("\t")));
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringDeleteForNull() {
        assertThrows(IllegalArgumentException.class, () -> productService.delete(null));
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDuringDeleteForEmptyOrBlankValues() {
        assertThrows(IllegalArgumentException.class, () -> productService.delete(""));
        assertThrows(IllegalArgumentException.class, () -> productService.delete(" "));
        assertThrows(IllegalArgumentException.class, () -> productService.delete("\n"));
        assertThrows(IllegalArgumentException.class, () -> productService.delete("\t"));
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