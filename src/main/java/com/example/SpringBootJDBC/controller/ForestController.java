package com.example.SpringBootJDBC.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.CoverageDAO;
import com.example.SpringBootJDBC.dao.ForestDAO;
import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.exception.ResourceAlreadyExistsException;
import com.example.SpringBootJDBC.exception.ResourceNotFoundException;


@RestController
public class ForestController {
	@Autowired
	private ForestDAO fDAO;
	private CoverageDAO cDAO;
	
	@CrossOrigin
	@Transactional
	@GetMapping("/forests")
	public List<Forest> getForest() {
		return fDAO.getAll();
	}
	
	@CrossOrigin
	@Transactional
	@GetMapping("/")
	public String test() {
		return "Hello World!";
	}
	
	@Transactional
	@GetMapping("/forests/{name}")
	public Forest getForestByName(@PathVariable String name) {
		Forest res = fDAO.getByName(name);
		if (res != null) {
			return res;
		}
		else {
			throw new ResourceNotFoundException("Forest not found with name:" + name);
		}
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PostMapping("/forests")
	public String saveForest(@RequestBody Forest forest) {
		Forest res = fDAO.getByName(forest.getName());
		int state = fDAO.getStateArea(forest.getState());
		if (res != null) {
			System.out.println("failed");
			throw new ResourceAlreadyExistsException("Forest already exists:" + forest.getName());
		}
		else if (state == -1) {
			throw new ResourceNotFoundException("State " + forest.getState() +  " does not exist!");
		}
		else {
			fDAO.save(forest);
			int forest_no = fDAO.getForestNoByName(forest.getName());
			double stateArea = fDAO.getStateArea(forest.getState());
			double percentage = forest.getArea() / stateArea * 100;
			fDAO.saveCoverage(forest_no, forest.getState(), percentage, forest.getArea());
			return "Forest " + forest.getName() + " Succefully saved to the database ";
		}
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PutMapping("/forests")
	public String updateForestArea(@RequestBody Forest forest) {
		int forest_no = fDAO.getForestNoByName(forest.getName());
		if (forest_no == -1) {
			throw new ResourceNotFoundException("Forest " + forest.getName() +  " does not exist!");
		}
		double stateArea = fDAO.getStateArea(forest.getState());
		if (stateArea == -1) {
			throw new ResourceNotFoundException("State " + forest.getState() +  " does not exist!");
		}
		double percentage = forest.getArea() / stateArea * 100;
		fDAO.update(forest);
		fDAO.updateCoverage(forest_no, forest.getState(), percentage, forest.getArea());
		return "Forest " + forest.getName() + " updated to the database";
	}
	
	@Transactional
	@DeleteMapping("/forests/{name}")
	public String deleteForestByName(@PathVariable String name) {
		return fDAO.delete(name) + " No. of rows deleted from the database";
	}
}
