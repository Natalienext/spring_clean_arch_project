package userservice.adapter.controller.http.user;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.adapter.controller.http.user.convertor.UserConvertor;
import userservice.adapter.controller.http.user.request.ChangeUserRequest;
import userservice.adapter.controller.http.user.response.ChangeUserResponse;
import userservice.adapter.controller.http.user.response.GetUserResponse;
import userservice.boundary.model.ChangeUserParam;
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
    GetUserResponse response = UserConvertor.toGetUserResponse(user);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ChangeUserResponse> changeUserById(
      @PathVariable UUID id, @RequestBody ChangeUserRequest request) {

    ChangeUserParam param = UserConvertor.requestToParam(request);
    userUseCase.changeUser(id, param);
    return ResponseEntity.ok(UserConvertor.toChangeUserResponse());
  }
}
