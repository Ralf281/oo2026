package ee.ralf.veebipood.controller;

import ee.ralf.veebipood.entity.Product;
import ee.ralf.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    // localhost:2004/products
    // application.properties server.port=2004
//    @GetMapping("products")
//    public String helloworld(){
//        return "Hello World";
//    }
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("products")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getOneProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if (product.getId()!=null){
            throw new RuntimeException("Cannot add with ID");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        if (product.getId()==null){
            throw new RuntimeException("Cannot edit without ID");
        }
        if (!productRepository.existsById(product.getId())){
            throw new RuntimeException("Product ID does not exist");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
}
