package info.aydinoglu.baris.warehouse.exception;

public class ArticleNotFoundException extends EntityNotFoundException {

  public ArticleNotFoundException(final Integer id) {
    super("article", id);
  }
}
