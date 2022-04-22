package com.aaroo.security.service;

import java.util.Map;

import com.aaroo.security.login.request.payload.UserRequest;
import com.aaroo.security.login.response.payload.UserResponse;

public interface UserService {

	Map<Object, Object> getUserById(long userId);

	Map<Object, Object> deleteUser(long userId);

	Map<Object, Object> createUser(UserRequest userRequest);

	Map<Object, Object> updateUser(UserResponse userResponse);

	Map<Object, Object> getAllUser(UserResponse userResponse);

}
