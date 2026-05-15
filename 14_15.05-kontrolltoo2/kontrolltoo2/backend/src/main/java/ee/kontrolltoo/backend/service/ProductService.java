package ee.kontrolltoo.backend.service;

import ee.kontrolltoo.backend.entity.Product;
import ee.kontrolltoo.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void validate(Product product) {

        if (product.getTitle() == null || product.getTitle().isBlank()) {
            throw new RuntimeException("Product title is required.");
        }

        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new RuntimeException("Product price cannot be negative.");
        }

        if (product.getImage() == null || product.getImage().isBlank()) {
            throw new RuntimeException("Product image is required.");
        }
    }

    public Page<Product> getProducts(int page, int size) {
        return productRepository.findAll(
                PageRequest.of(page, size)
        );
    }

    public Page<Product> getFilteredProducts(
            String keyword,
            int page,
            int size
    ) {
        return productRepository.findByTitleContainingIgnoreCase(
                keyword,
                PageRequest.of(page, size)
        );
    }
}
