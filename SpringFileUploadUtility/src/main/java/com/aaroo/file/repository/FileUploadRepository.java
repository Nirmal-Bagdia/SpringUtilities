package com.aaroo.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaroo.file.model.FileDetail;

@Repository
public interface FileUploadRepository extends JpaRepository<FileDetail, Long> {

}
