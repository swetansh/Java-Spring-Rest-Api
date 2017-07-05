package com.swet.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.swet.dto.Employee;
import com.swet.dto.Project;
import com.swet.repository.EmployeeRepo;
import com.swet.repository.ProjectRepo;
import java.util.Random;
import org.json.*;



@RestController
public class AppController {
	
	
	@Autowired
	ProjectRepo projectRepo;

	@Autowired
	EmployeeRepo EmployeeRepo;

	
	@RequestMapping(value="/show")
	public String getShow()
	{
		return "show this ";
	}
	
	@RequestMapping(value="/display")
	public String getdisplay()
	{
		return "display this ";
	}

	


//method to save Project and Its employee
	@RequestMapping(value={"/saveProject"}, method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public String getProject() {

		Project p1 = new Project();
	    p1.setProjectId(15);
		p1.setName("p15");
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee e1 = new Employee();
		e1.setEmployeeId(211);
		e1.setAge(13);
		e1.setName("Ramu");

		Employee e2 = new Employee();
		e2.setEmployeeId(112);
		e2.setAge(23);
		e2.setName("shayam");
		employeeList.add(e1);
		employeeList.add(e2);
		p1.setEmployees(employeeList);
		projectRepo.save(p1);
		return "saved project";
	}
	
	
	
	
		@RequestMapping(value={"/saveEmp"}, method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public String saveEmployee() {

		Project p1 = new Project();
		p1.setProjectId(15);
		p1.setName("p15");
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee e1 = new Employee();
		e1.setEmployeeId(101);
		e1.setAge(13);
		e1.setName("Ram");
		e1.setProject(p1);
		
		Employee e2 = new Employee();
		e2.setEmployeeId(102);
		e2.setAge(23);
		e2.setName("shayam");
		e2.setProject(p1);
		
		employeeList.add(e1);
		employeeList.add(e2);
		EmployeeRepo.save(employeeList);
		return "saved Employees";
	}


	@RequestMapping(value="/report", method=RequestMethod.GET, produces="application/json")
	@Transactional
	public String generateReport() {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empproject","root","admin1234");
			JasperReport jreport = JasperCompileManager.compileReport("D:\\reports\\demoreport.jrxml");    //provide name of report
			JasperPrint jprint = JasperFillManager.fillReport(jreport,null,conn);
			JasperViewer.viewReport(jprint);
			conn.close();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "report generated";
	}


	//method to get all employees of a project
	@RequestMapping(value={"/getEmployee/{projectId}"}, method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public String getProjectEmployee(@PathVariable String projectId) {
		String response="";
		try
		{

			int pId = Integer.parseInt(projectId);
			Project project = projectRepo.findOne(pId);
			List<Employee> Employeelist = EmployeeRepo.findByProject(project);

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(Employeelist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}

	//method to get all employees of a project
	@RequestMapping(value="/getAllEmployee", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public String getAllEmployee() {
		String response="";
		try
		{
			List<Employee> Employeelist = EmployeeRepo.findAll();
			//Page<Employee> Employeelist = EmployeeRepo.findByProject(project, pageable);

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(Employeelist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/savedata", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional
	public String savefromclient(@RequestParam String ClientData){
		String response = "";
		try {
			JSONObject jsonObj = new JSONObject(ClientData);
			int id = jsonObj.getInt("id");
			int age = jsonObj.getInt("age");
			String employeeName = jsonObj.getString("name");
			Random rand = new Random();

			int  n = rand.nextInt(50) + 1;

			Project p1 = new Project();
		    p1.setProjectId(id);
			p1.setName("new Project");
			List<Employee> employeeList = new ArrayList<Employee>();
			Employee e1 = new Employee();
			e1.setEmployeeId(n);
			e1.setAge(age);
			e1.setName(employeeName);
			employeeList.add(e1);
			p1.setEmployees(employeeList);
			projectRepo.save(p1);
			response =  "saved project";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return response;
	}

	//method to set employee project wise
		@RequestMapping(value={"/setEmployee/{projectId}/{projectName}/{employeeName}/{age}"}, method=RequestMethod.GET, produces="application/json;charset=utf-8")
		@Transactional
		public String setProjectEmployee(@PathVariable String projectId,@PathVariable String projectName,@PathVariable String employeeName,@PathVariable String age) {

			String response="";
			try
			{
				Random rand = new Random();

				int  n = rand.nextInt(50) + 1;
				int pId = Integer.parseInt(projectId);
				int eage = Integer.parseInt(age);

				Project p1 = new Project();
			    p1.setProjectId(pId);
				p1.setName(projectName);
				List<Employee> employeeList = new ArrayList<Employee>();
				Employee e1 = new Employee();
				e1.setEmployeeId(n);
				e1.setAge(eage);
				e1.setName(employeeName);
				employeeList.add(e1);
				p1.setEmployees(employeeList);
				projectRepo.save(p1);
				response =  "saved project";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return response;

		}


	//method to get all Emplyoyee page wise
	@RequestMapping(value={"/getEmployee/{projectId}/{page}/{size}"}, method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public String getProjectEmployeeBypage(@PathVariable String projectId,@PathVariable String page,@PathVariable String size) {

		String response="";
		try
		{

			int pId = Integer.parseInt(projectId);
			int pageno = Integer.parseInt(page);
			int pagesize = Integer.parseInt(size);

			Project project = projectRepo.findOne(pId);
			Pageable pageable = new PageRequest(pageno, pagesize);
			//Page<Employee> Employeelist = EmployeeRepo.findAll(pageable);
			Page<Employee> Employeelist = EmployeeRepo.findByProject(project, pageable);

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(Employeelist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;

	}
	
	//method  get  all project
	@RequestMapping(value="/getProjects", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional
	public  String getProjects() {
		String response="";
		try
		{
		List<Project> ProjectList =   projectRepo.findAll();
		ObjectMapper mapper = new ObjectMapper();
	
		response = mapper.writeValueAsString(ProjectList);
		
	     
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return response;
	}
	
	
}
