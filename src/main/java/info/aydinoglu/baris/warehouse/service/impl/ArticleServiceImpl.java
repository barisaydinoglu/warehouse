package info.aydinoglu.baris.warehouse.service.impl;

import info.aydinoglu.baris.warehouse.exception.ArticleNotFoundException;
import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.repository.ArticleRepository;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository repository;

  @Override
  public List<Article> saveArticles(final List<Article> articles) {
    return this.repository.saveAll(articles);
  }

  @Override
  public List<Article> getArticles() {
    return this.repository.findAll();
  }

  @Override
  public boolean existsArticle(final Integer id) {
    return repository.existsById(id);
  }

  @Override
  public Article getArticle(final Integer id) {
    val existingArticle = repository.findById(id);
    if (existingArticle.isEmpty()) {
      throw new ArticleNotFoundException(id);
    }
    return existingArticle.get();
  }

  @Override
  public void reduceArticleStock(final Integer id, final int amount) {
    val existingArticle = getArticle(id);
    existingArticle.setStock(existingArticle.getStock() - amount);
  }
}
