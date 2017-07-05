package com.swet.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Project implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int ProjectId;
	
	private String name;
	
	private String Owner;
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="ProjectId",insertable=true,updatable=true,nullable=false)
	private List<Employee> Employees;


	public int getProjectId() {
		return ProjectId;
	}


	public void setProjectId(int projectId) {
		ProjectId = projectId;
	}


	


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOwner() {
		return Owner;
	}


	public void setOwner(String owner) {
		Owner = owner;
	}


	public List<Employee> getEmployees() {
		return Employees;
	}


	public void setEmployees(List<Employee> employees) {
		Employees = employees;
	}


	public Project() {
		super();
	}
	
	
	
	
	
	

}
