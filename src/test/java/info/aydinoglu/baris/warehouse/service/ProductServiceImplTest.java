package info.aydinoglu.baris.warehouse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.exception.ProductArticleNotAvailableInStockException;
import info.aydinoglu.baris.warehouse.exception.ProductNotFoundException;
import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.model.ProductArticle;
import info.aydinoglu.baris.warehouse.repository.ProductRepository;
import info.aydinoglu.baris.warehouse.service.impl.ProductServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ArticleService articleService;

  @Mock
  private ProductRepository repository;

  @Mock
  private Article article;

  @Mock
  private Product product;

  @Mock
  private ProductArticle productArticle;

  private ProductService service;

  @BeforeEach
  void init() {
    service = new ProductServiceImpl(articleService, repository);
  }

  @Test
  void testSaveProducts() {
    //GIVEN
    val products = List.of(product);
    when(repository.saveAll(products)).thenReturn(products);
    //WHEN
    val result = service.saveProducts(products);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(products)
    );
  }

  @Test
  void testGetProducts() {
    //GIVEN
    val products = List.of(product);
    when(repository.findAll()).thenReturn(products);
    //WHEN
    val result = service.getProducts();
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(products)
    );
  }

  @Test
  void testGetProduct() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    when(repository.findById(name)).thenReturn(Optional.of(product));
    //WHEN
    val result = service.getProduct(name);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(product)
    );
  }

  @Test
  void testGetProductNotFound() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    when(repository.findById(name)).thenReturn(Optional.empty());
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ProductNotFoundException.class, () -> service.getProduct(name))
    );
  }

  @Test
  void testGetProductNotAvailableInStock() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    val articleId = TestDataUtil.createRandomInt();
    when(productArticle.getArticleId()).thenReturn(articleId);
    when(productArticle.getAmount()).thenReturn(5);
    when(product.getContainArticles()).thenReturn(List.of(productArticle));
    when(article.getStock()).thenReturn(1);
    when(articleService.existsArticle(articleId)).thenReturn(true);
    when(articleService.getArticle(articleId)).thenReturn(article);
    when(repository.findById(name)).thenReturn(Optional.of(product));
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ProductArticleNotAvailableInStockException.class,
            () -> service.getProduct(name))
    );
  }

  @Test
  void testSellProduct() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    val articleId = TestDataUtil.createRandomInt();
    when(productArticle.getArticleId()).thenReturn(articleId);
    when(productArticle.getAmount()).thenReturn(5);
    when(product.getContainArticles()).thenReturn(List.of(productArticle));
    when(article.getStock()).thenReturn(6);
    when(articleService.existsArticle(articleId)).thenReturn(true);
    when(articleService.getArticle(articleId)).thenReturn(article);
    when(repository.findById(name)).thenReturn(Optional.of(product));
    //WHEN
    val result = service.sellProduct(name);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(product)
    );
  }

  @Test
  void testSellProductNotFound() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    when(repository.findById(name)).thenReturn(Optional.empty());
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ProductNotFoundException.class, () -> service.sellProduct(name))
    );
  }

  @Test
  void testSellProductNotAvailableInStock() {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    val articleId = TestDataUtil.createRandomInt();
    when(productArticle.getArticleId()).thenReturn(articleId);
    when(productArticle.getAmount()).thenReturn(5);
    when(product.getContainArticles()).thenReturn(List.of(productArticle));
    when(article.getStock()).thenReturn(1);
    when(articleService.existsArticle(articleId)).thenReturn(true);
    when(articleService.getArticle(articleId)).thenReturn(article);
    when(repository.findById(name)).thenReturn(Optional.of(product));
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ProductArticleNotAvailableInStockException.class,
            () -> service.sellProduct(name))
    );
  }
}
