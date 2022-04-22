package com.aaroo.security.login.response.payload;

public class UserResponse {

	private Long userId;

	private String email;

	private String password;

	private Integer pNumber;

	private Integer pSize;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getpNumber() {
		return pNumber;
	}

	public void setpNumber(Integer pNumber) {
		this.pNumber = pNumber;
	}

	public Integer getpSize() {
		return pSize;
	}

	public void setpSize(Integer pSize) {
		this.pSize = pSize;
	}

}
