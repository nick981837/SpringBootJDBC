package com.example.SpringBootJDBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.CoverageDAO;
import com.example.SpringBootJDBC.dao.ReportDAO;
import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Report;

@RestController
public class ReportController {
	@Autowired
	private ReportDAO rDAO;
	
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@GetMapping("/report")
	public List<Report> getReport() {
		return rDAO.getAll();
	}
}
