package info.aydinoglu.baris.warehouse.controller;

import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public List<Product> saveProducts(@RequestBody final List<Product> products) {
    return productService.saveProducts(products);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getProducts() {
    return productService.getProducts();
  }

  @GetMapping("/{name}")
  @ResponseStatus(HttpStatus.OK)
  public Product getProduct(@PathVariable final String name) {
    return productService.getProduct(name);
  }

  @DeleteMapping("/{name}")
  @ResponseStatus(HttpStatus.OK)
  public Product sellProduct(@PathVariable final String name) {
    return productService.sellProduct(name);
  }
}
