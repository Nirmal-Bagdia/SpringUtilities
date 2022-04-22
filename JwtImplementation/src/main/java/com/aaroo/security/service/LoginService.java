package com.aaroo.security.service;

import java.util.Map;

import com.aaroo.security.login.request.payload.LoginRequest;

public interface LoginService {

	Map<Object, Object> userLogin(LoginRequest loginRequest);

}
