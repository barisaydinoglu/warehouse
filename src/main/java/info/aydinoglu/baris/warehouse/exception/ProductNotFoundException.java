package info.aydinoglu.baris.warehouse.exception;

public class ProductNotFoundException extends EntityNotFoundException {

  public ProductNotFoundException(final String name) {
    super("product", name);
  }
}
