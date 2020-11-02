package info.aydinoglu.baris.warehouse.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(final String entityName, final Object id) {
    super(String.format("Could not find %s '%s'", entityName, id));
  }
}
