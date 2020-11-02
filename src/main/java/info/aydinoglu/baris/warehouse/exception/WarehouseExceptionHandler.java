package info.aydinoglu.baris.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WarehouseExceptionHandler {

  @ResponseBody
  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String notFoundExceptionHandler(final EntityNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(ProductArticleNotAvailableInStockException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String notAvailableInStockExceptionHandler(final ProductArticleNotAvailableInStockException ex) {
    return ex.getMessage();
  }
}
