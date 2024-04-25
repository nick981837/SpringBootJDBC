package com.example.SpringBootJDBC.dao;

import java.util.List;

import com.example.SpringBootJDBC.model.Coverage;

public interface CoverageDAO {
	int save(Coverage coverage);
	
	List<Coverage> getAll();
}
