package musicsearchportal.adapter.controller.http.resolver;

import lombok.extern.slf4j.Slf4j;
import musicsearchportal.adapter.controller.http.post.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorResolver {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Неожиданная ошибка");
    ErrorResponse error =
        new ErrorResponse(
            "Неожиданная ошибка", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
