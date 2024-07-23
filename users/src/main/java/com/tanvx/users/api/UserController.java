package com.tanvx.users.api;

import com.tanvx.users.domain.user.dto.request.UserSignInRequest;
import com.tanvx.users.domain.user.dto.request.UserRegisterRequest;
import com.tanvx.users.domain.user.dto.response.UserAuthenticationResponse;
import com.tanvx.users.domain.user.dto.response.UserResponse;
import com.tanvx.users.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserAuthenticationResponse> registerUser(
      @RequestBody UserRegisterRequest request) {
    // TODO: Implement user registration logic
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.registerUser(request));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<UserAuthenticationResponse> signInUser(
      @RequestBody UserSignInRequest request) {
    // TODO: Implement user sign in logic
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.signInUser(request));
  }

  @GetMapping("/{email}/profile")
  public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
    // TODO: Implement user get logic
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUser(email));
  }
}
