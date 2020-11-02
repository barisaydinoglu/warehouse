package info.aydinoglu.baris.warehouse.repository.impl;

import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductInMemoryRepositoryImpl extends
    AbstractInMemoryRepositoryImpl<Product, String> implements ProductRepository {

  @Override
  public Product save(final Product entity) {
    repository.put(entity.getName(), entity);
    return entity;
  }
}
