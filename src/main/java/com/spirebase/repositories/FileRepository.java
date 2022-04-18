package com.spirebase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spirebase.model.File;

public interface FileRepository extends JpaRepository<File, Long> {

	File findByName(String name);
		
	@Query(value = "" +
			"select * " + 
			"	from files f " + 
			"where name is not null " +
			"",
			nativeQuery = true)
	List<File> findAllNotProcess();

}