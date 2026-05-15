package ee.kontrolltoo.backend.controller;

import ee.kontrolltoo.backend.entity.Product;
import ee.kontrolltoo.backend.repository.ProductRepository;
import ee.kontrolltoo.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    // 🔥 3.a: pagination + filter
    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (keyword != null && !keyword.isBlank()) {
            return productRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }

        return productRepository.findAll(pageable);
    }

    // ➕ ADD product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new RuntimeException("Cannot add product with id");
        }

        productService.validate(product);
        return productRepository.save(product);
    }

    // ✏️ EDIT product
    @PutMapping
    public Product editProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("Cannot update product without id");
        }

        productService.validate(product);
        return productRepository.save(product);
    }

    // ❌ DELETE product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    // 🔍 GET one product
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
