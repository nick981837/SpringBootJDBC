package com.example.SpringBootJDBC.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SpringBootJDBC.model.Coverage;
import com.example.SpringBootJDBC.model.Report;

@Service
public class ReportDAOImp implements ReportDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public List<Report> getAll() {
		return jdbcTemplate.query("SELECT * FROM REPORT", new BeanPropertyRowMapper<Report>(Report.class));
	}
}
