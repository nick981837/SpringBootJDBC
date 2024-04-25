package com.example.SpringBootJDBC.dao;

import java.util.List;

import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Report;

public interface ReportDAO {
	
	List<Report> getAll();

}
