package info.aydinoglu.baris.warehouse.repository;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.repository.impl.ArticleInMemoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;

class ArticleInMemoryRepositoryImplTest extends
    AbstractInMemoryRepositoryImplTest<Article, Integer> {

  @BeforeEach
  void init() {
    repository = new ArticleInMemoryRepositoryImpl();
  }

  protected Article createTestEntity() {
    return TestDataUtil.createRandomArticle();
  }

  @Override
  protected Integer resolveId(final Article entity) {
    return entity.getId();
  }

  @Override
  protected Integer resolveNonExistingId() {
    return Integer.MIN_VALUE;
  }
}
