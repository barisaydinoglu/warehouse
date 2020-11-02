package info.aydinoglu.baris.warehouse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.exception.ArticleNotFoundException;
import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.repository.ArticleRepository;
import info.aydinoglu.baris.warehouse.service.impl.ArticleServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

  @Mock
  private ArticleRepository repository;

  @Mock
  private Article article;

  private ArticleService service;

  @BeforeEach
  void init() {
    service = new ArticleServiceImpl(repository);
  }

  @Test
  void saveArticles() {
    //GIVEN
    val articles = List.of(article);
    when(repository.saveAll(articles)).thenReturn(articles);
    //WHEN
    val result = service.saveArticles(articles);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(articles)
    );
  }

  @Test
  void getArticles() {
    //GIVEN
    val articles = List.of(article);
    when(repository.findAll()).thenReturn(articles);
    //WHEN
    val result = service.getArticles();
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(articles)
    );
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void existsArticle(final boolean exist) {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    when(repository.existsById(id)).thenReturn(exist);
    //WHEN
    val result = service.existsArticle(id);
    //THEN
    assertAll(
        () -> assertThat(result).isEqualTo(exist)
    );
  }

  @Test
  void getArticle() {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    when(repository.findById(id)).thenReturn(Optional.of(article));
    //WHEN
    val result = service.getArticle(id);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(article)
    );
  }

  @Test
  void getArticleNotFound() {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    when(repository.findById(id)).thenReturn(Optional.empty());
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ArticleNotFoundException.class, () -> service.getArticle(id))
    );
  }

  @Test
  void reduceArticleStock() {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    val amount = TestDataUtil.createRandomInt(1, 100);
    val articleStock = TestDataUtil.createRandomInt(1, 100) + amount;
    when(article.getStock()).thenReturn(articleStock);
    when(repository.findById(id)).thenReturn(Optional.of(article));
    //WHEN
    service.reduceArticleStock(id, amount);
    //THEN
    assertAll(
        () -> verify(article).setStock(articleStock - amount)
    );
  }

  @Test
  void reduceArticleStockNotFound() {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    val amount = TestDataUtil.createRandomInt(1, 100);
    when(repository.findById(id)).thenReturn(Optional.empty());
    //THEN
    assertAll(
        //WHEN
        () -> assertThrows(ArticleNotFoundException.class,
            () -> service.reduceArticleStock(id, amount))
    );
  }
}
