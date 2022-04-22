package com.aaroo.security.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aaroo.security.config.JwtTokenUtil;
import com.aaroo.security.login.request.payload.LoginRequest;
import com.aaroo.security.login.response.payload.UserLoginResponse;
import com.aaroo.security.model.LoginDetail;
import com.aaroo.security.repository.LoginRepository;
import com.aaroo.security.service.LoginService;
import com.aaroo.security.util.IConstant;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public Map<Object, Object> userLogin(LoginRequest loginRequest) {
		Map<Object, Object> map = new HashMap<>();
		UserLoginResponse UserLoginResponse = new UserLoginResponse();
		try {
			LoginDetail loginDetail = loginRepository.findByEmail(loginRequest.getEmail().trim());
			if (loginDetail != null) {
				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().trim(),
								loginRequest.getPassword().trim()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(authentication);
				if (StringUtils.isNotEmpty(token)) {
					loginDetail.setPassword("");

					BeanUtils.copyProperties(loginDetail, UserLoginResponse);
					map.put(IConstant.RESPONSE, IConstant.SUCCESS);
					map.put(IConstant.MESSAGE, "login successfuly");
					map.put("Token", token);
					map.put(IConstant.OBJECT_RESPONSE, UserLoginResponse);
					return map;

				}
				map.put(IConstant.MESSAGE, IConstant.NOT_FOUND);
				map.put(IConstant.RESPONSE, IConstant.AUTHENTICATION_FAILED);
			}
		} catch (BadCredentialsException e) {
			map.put(IConstant.RESPONSE, IConstant.AUTHENTICATION_FAILED);
			map.put(IConstant.MESSAGE, IConstant.INCORRECT_PASSWORD_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;
	}

}
