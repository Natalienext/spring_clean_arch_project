package userservice.adapter.controller.http.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import userservice.adapter.controller.http.user.response.ErrorResponse;
import userservice.domain.exception.UserNotFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorResolver {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("Не найден пользователь с id = {}", ex.getId());
    ErrorResponse error =
        new ErrorResponse(
            "Пользователь не найден",
            "Не найден пользователь с id = " + ex.getId(),
            HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Неожиданная ошибка");
    ErrorResponse error =
        new ErrorResponse(
            "Неожиданная ошибка", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
