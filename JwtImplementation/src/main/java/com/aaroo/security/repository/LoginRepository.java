package com.aaroo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaroo.security.model.LoginDetail;

@Repository
public interface LoginRepository extends JpaRepository<LoginDetail, Long> {

	LoginDetail findByEmail(String email);

}
