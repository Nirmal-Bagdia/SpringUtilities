package com.aaroo.security.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aaroo.security.config.AESCipher;
import com.aaroo.security.login.request.payload.UserRequest;
import com.aaroo.security.login.response.payload.UserResponse;
import com.aaroo.security.model.LoginDetail;
import com.aaroo.security.model.UserDetail;
import com.aaroo.security.repository.LoginRepository;
import com.aaroo.security.repository.UserRepository;
import com.aaroo.security.service.UserService;
import com.aaroo.security.util.ConstantAction;
import com.aaroo.security.util.IConstant;
import com.aaroo.security.util.MailSender;
import com.aaroo.security.util.PasswordGenerator;
import com.aaroo.security.util.UserRegMailTemplate;

@Service
public class UserServiceImpl implements UserService {

	final private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@SuppressWarnings("null")
	@Override
	public Map<Object, Object> createUser(UserRequest userRequest) {
		Map<Object, Object> map = new HashMap<>();
		try {
			UserDetail userDetail = userRepository.findByEmail(userRequest.getEmail());
			if (userDetail != null) {
				map.put(IConstant.MESSAGE, IConstant.USER_ALLREADY_EXIST);
				map.put(IConstant.OBJECT_RESPONSE, userDetail);
			} else {
				UserDetail detail = new UserDetail();
				detail.setEmail(userRequest.getEmail());
				detail.setPassword(passwordEncoder.encode(userRequest.getPassword()));
				detail.setCreationDate(new Date());
				userDetail = userRepository.save(detail);
				if (userDetail != null) {
					LoginDetail loginDetail = new LoginDetail();
					loginDetail.setEmail(userDetail.getEmail());
					loginDetail.setPassword(userDetail.getPassword());
					LoginDetail loginDetails = loginRepository.save(loginDetail);
					if (loginDetails != null) {
						map.put(IConstant.MESSAGE, IConstant.USER_SAVED_SUCCESSFULLY);
						map.put(IConstant.OBJECT_RESPONSE, userDetail);

//					    Email
						String decryptMail = AESCipher.decrypt(userDetail.getEmail());
						String decryptPass = AESCipher.decrypt(userDetail.getPassword());
						String clientName = "jwtimpl";
						String description = UserRegMailTemplate.userregtemplate(clientName, decryptMail, decryptPass,
								"aaroo.com");
						MailSender mailSender = new MailSender();
						String receiverEMail[] = { decryptMail };
						mailSender.setEmailDetails(receiverEMail, ConstantAction.FROM_EMAIL_ID,
								ConstantAction.FROM_PASSWORD, AESCipher.decrypt(userDetail.getEmail()), description,
								"Registration Mail", clientName.toUpperCase());
						Thread thread = new Thread(mailSender);
						thread.start();
					} else {
						userRepository.deleteById(userDetail.getUserId());
						map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
					}
				} else {
					userRepository.deleteById(userDetail.getUserId());
					map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e.getMessage());
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;
	}

	@Override
	public Map<Object, Object> getAllUser(UserResponse userResponse) {
		Map<Object, Object> map = new HashMap<>();
		try {
			Pageable firstPageWithTwoElements = PageRequest.of(userResponse.getpNumber(), userResponse.getpSize(),
					Sort.by("employeeId").descending());
			Page<UserDetail> userList = userRepository.findAll(firstPageWithTwoElements);
			if (userList.hasContent()) {
				map.put(IConstant.MESSAGE, IConstant.USER_FOUND);
				map.put(IConstant.OBJECT_RESPONSE, userList);
			} else {
				map.put(IConstant.MESSAGE, IConstant.USER_NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e.getMessage());
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;

	}

	@Override
	public Map<Object, Object> getUserById(long userId) {
		Map<Object, Object> map = new HashMap<>();
		try {
			Optional<UserDetail> user = userRepository.findById(userId);
			if (user.isPresent()) {
				map.put(IConstant.MESSAGE, IConstant.USER_FOUND);
				map.put(IConstant.OBJECT_RESPONSE, user);
			} else {
				map.put(IConstant.MESSAGE, IConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e.getMessage());
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;

	}

	@Override
	public Map<Object, Object> deleteUser(long userId) {
		Map<Object, Object> map = new HashMap<>();
		try {
			Optional<UserDetail> user = userRepository.findById(userId);
			if (user.isPresent()) {
				userRepository.deleteById(userId);
				map.put(IConstant.MESSAGE, IConstant.DELETE_SUCCESSFULLY);
			} else {
				map.put(IConstant.MESSAGE, IConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e.getMessage());
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;

	}

	@SuppressWarnings("null")
	@Override
	public Map<Object, Object> updateUser(UserResponse userResponse) {
		Map<Object, Object> map = new HashMap<>();
		try {
			UserDetail userDetail = userRepository.findByUserId(userResponse.getUserId());
			if (userDetail != null) {
				userDetail.setEmail(AESCipher.encrypt(userResponse.getEmail()));

				PasswordGenerator passwordGenerator = new PasswordGenerator();
				String password = AESCipher.encrypt(passwordGenerator.getSaltString());
				userDetail.setPassword(password);
				userDetail.setUpdationDate(new Date());
				userDetail = userRepository.save(userDetail);
				if (userDetail != null) {
					map.put(IConstant.MESSAGE, IConstant.USER_UPDATE_SUCCESSFULLY);
					map.put(IConstant.OBJECT_RESPONSE, userDetail);

//					Email
					String decryptMail = AESCipher.decrypt(userDetail.getEmail());
					String decryptPass = AESCipher.decrypt(userDetail.getPassword());
					String clientName = "jwtimpl";
					String description = UserRegMailTemplate.userregtemplate(clientName, decryptMail, decryptPass,
							"aaroo.com");
					MailSender mailSender = new MailSender();
					String receiverEMail[] = { decryptMail };
					mailSender.setEmailDetails(receiverEMail, ConstantAction.FROM_EMAIL_ID,
							ConstantAction.FROM_PASSWORD, AESCipher.decrypt(userDetail.getEmail()), description,
							"Registration Mail", clientName.toUpperCase());
					Thread thread = new Thread(mailSender);
					thread.start();

				} else {
					userRepository.deleteById(userDetail.getUserId());
					map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
				}

			} else {
				map.put(IConstant.MESSAGE, IConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e.getMessage());
			map.put(IConstant.MESSAGE, IConstant.ERROR_MESSAGE);
		}
		return map;
	}

}
