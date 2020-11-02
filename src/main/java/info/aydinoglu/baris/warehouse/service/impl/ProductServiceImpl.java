package info.aydinoglu.baris.warehouse.service.impl;

import info.aydinoglu.baris.warehouse.exception.ProductArticleNotAvailableInStockException;
import info.aydinoglu.baris.warehouse.exception.ProductNotFoundException;
import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.repository.ProductRepository;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import info.aydinoglu.baris.warehouse.service.ProductService;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ArticleService articleService;

  private final ProductRepository repository;

  @Override
  public List<Product> saveProducts(final List<Product> products) {
    return repository.saveAll(products)
        .stream()
        .filter(isProductArticlesAvailableInStock())
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> getProducts() {
    return repository.findAll()
        .stream()
        .filter(isProductArticlesAvailableInStock())
        .collect(Collectors.toList());
  }

  @Override
  public Product getProduct(final String name) {
    val existingProductOptional = repository.findById(name);
    if (existingProductOptional.isEmpty()) {
      throw new ProductNotFoundException(name);
    }
    val existingProduct = existingProductOptional.get();
    if (!isProductArticlesAvailableInStock().test(existingProduct)) {
      throw new ProductArticleNotAvailableInStockException(existingProduct.getName());
    }
    return existingProduct;
  }

  @Override
  public Product sellProduct(final String name) {
    val existingProduct = getProduct(name);
    existingProduct.getContainArticles()
        .forEach(
            productArticle -> articleService.reduceArticleStock(productArticle.getArticleId(),
                productArticle.getAmount()));
    repository.deleteById(name);
    return existingProduct;
  }

  private Predicate<Product> isProductArticlesAvailableInStock() {
    return product -> product.getContainArticles()
        .stream()
        .allMatch(productArticle -> articleService.existsArticle(productArticle.getArticleId())
            && articleService.getArticle(productArticle.getArticleId()).getStock()
            >= productArticle.getAmount());
  }
}
