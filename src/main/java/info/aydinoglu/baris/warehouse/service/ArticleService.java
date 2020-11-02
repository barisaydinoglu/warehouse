package info.aydinoglu.baris.warehouse.service;

import info.aydinoglu.baris.warehouse.model.Article;
import java.util.List;

public interface ArticleService {

  List<Article> saveArticles(final List<Article> articles);

  List<Article> getArticles();

  boolean existsArticle(final Integer id);

  Article getArticle(final Integer id);

  void reduceArticleStock(final Integer id, int amount);
}
