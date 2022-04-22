package com.aaroo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaroo.security.model.UserDetail;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Long> {

	UserDetail findByEmail(String email);

	UserDetail findByUserId(Long userId);

}
