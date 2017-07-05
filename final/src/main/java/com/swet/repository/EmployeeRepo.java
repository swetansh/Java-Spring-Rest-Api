package com.swet.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swet.dto.Employee;
import com.swet.dto.Project;




@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer>{
	
	List<Employee> findByProject(Project Project);
	
	Page<Employee> findByProject(Project Project,Pageable pageable);
	
	

}
