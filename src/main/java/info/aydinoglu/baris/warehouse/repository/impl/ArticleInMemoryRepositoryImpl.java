package info.aydinoglu.baris.warehouse.repository.impl;

import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleInMemoryRepositoryImpl extends
    AbstractInMemoryRepositoryImpl<Article, Integer> implements ArticleRepository {

  @Override
  public Article save(final Article entity) {
    repository.put(entity.getId(), entity);
    return entity;
  }
}
