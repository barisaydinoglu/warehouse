package info.aydinoglu.baris.warehouse.exception;

public class ProductArticleNotAvailableInStockException extends RuntimeException {

  public ProductArticleNotAvailableInStockException(final String name) {
    super(String.format("Articles for product '%s' are not available in stock.", name));
  }
}
