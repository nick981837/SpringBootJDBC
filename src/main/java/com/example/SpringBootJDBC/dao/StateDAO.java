package com.example.SpringBootJDBC.dao;

import java.util.List;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.State;

public interface StateDAO {
	
	int save(State state);
	
	List<State> getAll();
	
	List<State> getByName(String name, String state);
	
}
