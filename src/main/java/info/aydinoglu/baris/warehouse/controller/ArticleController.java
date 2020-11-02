package info.aydinoglu.baris.warehouse.controller;

import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public List<Article> saveArticles(@RequestBody final List<Article> articles) {
    return articleService.saveArticles(articles);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Article> getArticles() {
    return articleService.getArticles();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Article getArticle(@PathVariable final Integer id) {
    return articleService.getArticle(id);
  }
}
