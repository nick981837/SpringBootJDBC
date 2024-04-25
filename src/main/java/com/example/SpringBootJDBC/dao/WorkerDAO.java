package com.example.SpringBootJDBC.dao;

import java.util.List;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Worker;

public interface WorkerDAO {
	int save(Worker worker);
	List<Worker> getByNameOrSSN(String name, String ssn);
	
	List<Worker> getTopK(int k);
	
	List<Worker> getAll();
}
