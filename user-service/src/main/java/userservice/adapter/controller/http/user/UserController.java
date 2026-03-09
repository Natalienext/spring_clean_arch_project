package userservice.adapter.controller.http.user;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import userservice.adapter.controller.http.user.convertor.ResponseConvertor;
import userservice.adapter.controller.http.user.response.GetUserResponse;
import userservice.boundary.usecase.UserUseCase;
import userservice.domain.model.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserUseCase userUseCase;

  @GetMapping("/{id}")
  public ResponseEntity<GetUserResponse> getUserById(@PathVariable UUID id) {

    User user = userUseCase.getUser(id);
    GetUserResponse response = ResponseConvertor.toGetUserResponse(user);
    return ResponseEntity.ok(response);
  }
}
