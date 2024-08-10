package com.tanvx.users.domain.user.service.impl;

import com.tanvx.users.app.client.MeasurementFeignClient;
import com.tanvx.users.app.jwt.JwtService;
import com.tanvx.users.domain.user.dto.request.UserRegisterRequest;
import com.tanvx.users.domain.user.dto.request.UserSignInRequest;
import com.tanvx.users.domain.user.dto.response.UserAuthenticationResponse;
import com.tanvx.users.domain.user.dto.response.UserResponse;
import com.tanvx.users.domain.user.entity.User;
import com.tanvx.users.domain.user.repository.UserRepository;
import com.tanvx.users.domain.user.service.UserService;
import com.tanvx.users.infrastructure.constant.Role;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String USER_SIGN_IN_FAILURE = "Sign in failed";

  private static final String USER_NOT_FOUND = "User not found";

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  private final MeasurementFeignClient measurementFeignClient;

  @Override
  public UserAuthenticationResponse registerUser(UserRegisterRequest request) {
    User user = User.builder()
        .name(request.name())
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .cityId(request.cityId())
        .createdAt(LocalDateTime.now())
        .createdBy(request.email())
        .role(Role.USER)
        .build();
    userRepository.save(user);
    String token = jwtService.generateToken(user);
    return new UserAuthenticationResponse(token);
  }

  @Override
  public UserAuthenticationResponse signInUser(UserSignInRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.email(), request.password()
        )
    );
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new ServiceException(USER_SIGN_IN_FAILURE));
    String token = jwtService.generateToken(user);
    return new UserAuthenticationResponse(token);
  }

  @Override
  public UserResponse getUser(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      throw new ServiceException(USER_NOT_FOUND);
    }
    User user = optionalUser.get();
    var measurement = measurementFeignClient.getMeasurementsByCityId(user.getCityId());
    return new UserResponse(user.getName(), user.getEmail(), user.getCityId(), user.getCreatedAt(),
        user.getCreatedBy(), Objects.requireNonNull(measurement.getBody()).getData());
  }
}
