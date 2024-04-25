package com.example.SpringBootJDBC.dao;
import java.util.List;

import com.example.SpringBootJDBC.model.Forest;

public interface ForestDAO {
	int save(Forest forest);
	
	
	int saveCoverage(int forest_no, String state, double percentage, int area);
	
	int updateCoverage(int forest_no, String state, double percentage, int area);
	
	int getForestNoByName(String name);
	
	int update(Forest forest);
	
	int delete(String name);
	
	List<Forest> getAll();
	
	Forest getByName(String name);
	
	int getStateArea(String state);

}
