package com.aaroo.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaroo.security.login.request.payload.UserRequest;
import com.aaroo.security.login.response.payload.UserResponse;
import com.aaroo.security.service.UserService;
import com.aaroo.security.util.IConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api(value = "JwtImplementation Api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	UserService userService;
	
//	Register_User
	@ApiOperation(value = "Register User")
	@PostMapping(value = "/registerUser")
	public Map<Object, Object> registerUser(@RequestBody UserRequest userRequest) {
		return userService.createUser(userRequest);
	}

//	Get_All_User
	@ApiOperation(value = "Get All User")
	@PostMapping(value = "/getAllUser")
	public Map<Object, Object> getAllUser(@RequestHeader(IConstant.HEADER_TAG) String header,
			@RequestBody UserResponse userResponse) {
		HashMap<Object, Object> map = new HashMap<>();
		if (header.equals(IConstant.HEADER_STRING)) {
			return userService.getAllUser(userResponse);
		}
		map.put(IConstant.MESSAGE, IConstant.INVALID_REQUEST);
		map.put(IConstant.RESPONSE, IConstant.FOR_INVALID);
		return map;
	}

//	Get_User
	@ApiOperation(value = "Get User")
	@GetMapping(value = "/getUserById")
	public Map<Object, Object> getUserById(@RequestHeader(IConstant.HEADER_TAG) String header,
			@RequestParam("UserId") long userId) {
		return userService.getUserById(userId);

	}

//	Delete_User
	@ApiOperation(value = "Delete User")
	@DeleteMapping(value = "/deleteUser")
	public Map<Object, Object> deleteUser(@RequestHeader(IConstant.HEADER_TAG) String header,
			@RequestParam("UserId") long userId) {
		HashMap<Object, Object> map = new HashMap<>();
		if (header.equals(IConstant.HEADER_STRING)) {
			return userService.deleteUser(userId);
		}
		map.put(IConstant.MESSAGE, IConstant.INVALID_REQUEST);
		map.put(IConstant.RESPONSE, IConstant.FOR_INVALID);
		return map;
	}

//	Update_User
	@ApiOperation(value = "Update User")
	@PutMapping(value = "/updateUser")
	public Map<Object, Object> updateUser(@RequestHeader(IConstant.HEADER_TAG) String header,
			@RequestBody UserResponse userResponse) {
		HashMap<Object, Object> map = new HashMap<>();
		if (header.equals(IConstant.HEADER_STRING)) {
			return userService.updateUser(userResponse);
		}
		map.put(IConstant.MESSAGE, IConstant.INVALID_REQUEST);
		map.put(IConstant.RESPONSE, IConstant.FOR_INVALID);
		return map;
	}

}
