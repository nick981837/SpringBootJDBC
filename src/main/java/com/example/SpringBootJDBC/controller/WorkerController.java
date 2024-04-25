package com.example.SpringBootJDBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.ForestDAO;
import com.example.SpringBootJDBC.dao.WorkerDAO;
import com.example.SpringBootJDBC.exception.ResourceAlreadyExistsException;
import com.example.SpringBootJDBC.exception.ResourceNotFoundException;
import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Worker;

@RestController
public class WorkerController {
	
	@Autowired
	private WorkerDAO wDAO;
	
	@Autowired
	private ForestDAO fDAO;
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PostMapping("/workers")
	public String saveWorker(@RequestBody Worker worker) {
		List<Worker> res = wDAO.getByNameOrSSN(worker.getName(), worker.getSsn());
		int state = fDAO.getStateArea(worker.getEmploying_state());
		if (res.size() > 0) {
			throw new ResourceAlreadyExistsException("Worker with name " + worker.getName() + " or ssn " + worker.getSsn() + " already exists");
		}
		else if (state == -1) {
			throw new ResourceNotFoundException("State " + worker.getEmploying_state() +  " does not exist!");
		}
		else {
			wDAO.save(worker);
			return  "Worker " + worker.getName() + " saved to the database";
		}
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@GetMapping("/workers/{k}")
	public List<Worker> getTopKBusy(@PathVariable int k) {
		return wDAO.getTopK(k);
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@GetMapping("/workers")
	public List<Worker> getWorker() {
		return wDAO.getAll();
	}
}
