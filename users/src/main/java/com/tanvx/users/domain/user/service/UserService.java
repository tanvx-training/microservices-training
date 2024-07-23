package com.tanvx.users.domain.user.service;

import com.tanvx.users.domain.user.dto.request.UserSignInRequest;
import com.tanvx.users.domain.user.dto.request.UserRegisterRequest;
import com.tanvx.users.domain.user.dto.response.UserAuthenticationResponse;
import com.tanvx.users.domain.user.dto.response.UserResponse;

public interface UserService {

  UserAuthenticationResponse registerUser(UserRegisterRequest request);

  UserAuthenticationResponse signInUser(UserSignInRequest request);

  UserResponse getUser(String email);
}
