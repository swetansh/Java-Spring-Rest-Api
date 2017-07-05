package com.swet.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swet.dto.Project;




@Repository
public interface ProjectRepo extends JpaRepository<Project,Integer>{

	List<Project> findByName(String Name);

}

