package com.spirebase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spirebase.model.FileLog;

public interface FileLogRepository extends JpaRepository<FileLog, Long> {


}