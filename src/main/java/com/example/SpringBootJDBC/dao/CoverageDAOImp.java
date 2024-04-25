package com.example.SpringBootJDBC.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Worker;

@Service
public class CoverageDAOImp implements CoverageDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Coverage coverage) {
		return jdbcTemplate.update("INSERT INTO Coverage (forest_no, state, percentage, "
				+ "area) VALUES(?,?,?,?) ", new Object[] {coverage.getForest_no(), coverage.getState(), coverage.getPercentage(), coverage.getArea()});
	}
	
	@Override
	public List<Coverage> getAll() {
		return jdbcTemplate.query("SELECT * FROM COVERAGE", new BeanPropertyRowMapper<Coverage>(Coverage.class));
	}
}
