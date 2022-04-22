package com.aaroo.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaroo.security.login.request.payload.LoginRequest;
import com.aaroo.security.service.LoginService;
import com.aaroo.security.util.IConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/login")
@Api(value = "JwtImplementation Api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

	@Autowired
	LoginService loginService;

//	Login Post API , return token
	@ApiOperation(value = "User Login")
	@PostMapping(value = "/userLogin")
	public Map<Object, Object> login(@RequestBody LoginRequest loginRequest) {
		HashMap<Object, Object> map = new HashMap<>();
		if (loginRequest != null) {
			return loginService.userLogin(loginRequest);
		}
		map.put(IConstant.MESSAGE, IConstant.INVALID_REQUEST);
		map.put(IConstant.RESPONSE, IConstant.FOR_INVALID);
		return map;
	}

}
