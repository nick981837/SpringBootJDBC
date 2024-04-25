package com.example.SpringBootJDBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.CoverageDAO;
import com.example.SpringBootJDBC.dao.WorkerDAO;
import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Worker;

@RestController
public class CoverageController {
	@Autowired
	private CoverageDAO cDAO;
	
	
	
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@Transactional
	@GetMapping("/coverage")
	public List<Coverage> getCoverage() {
		return cDAO.getAll();
	}
}
