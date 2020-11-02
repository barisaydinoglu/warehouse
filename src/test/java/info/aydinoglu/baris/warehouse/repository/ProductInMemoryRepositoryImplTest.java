package info.aydinoglu.baris.warehouse.repository;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.model.ProductArticle;
import info.aydinoglu.baris.warehouse.repository.impl.ProductInMemoryRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

class ProductInMemoryRepositoryImplTest extends
    AbstractInMemoryRepositoryImplTest<Product, String> {

  @Mock
  private List<ProductArticle> containedArticles;

  @BeforeEach
  void init() {
    repository = new ProductInMemoryRepositoryImpl();
  }

  @Override
  protected Product createTestEntity() {
    return TestDataUtil.createRandomProduct();
  }

  @Override
  protected String resolveId(final Product entity) {
    return entity.getName();
  }

  @Override
  protected String resolveNonExistingId() {
    return "###NonExistingId!!!";
  }
}
