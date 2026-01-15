package music_search_portal.adapter.controller.http.resolver;

import music_search_portal.adapter.controller.http.post.Controller;
import music_search_portal.adapter.controller.http.post.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {Controller.class})
public class ErrorResolver {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(
            "Ошибка при создании объявления",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
