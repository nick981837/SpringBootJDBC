package com.example.SpringBootJDBC.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.State;

@Service
public class StateDAOImp implements StateDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(State state) {
		return jdbcTemplate.update("INSERT INTO STATE (name, abbreviation, "
				+ "area, population) "
				+ "VALUES(?,?,?,?)", new Object[] {state.getName(), state.getAbbreviation(), state.getArea(), state.getPopulation()});
	}
	
	@Override
	public List<State> getAll() {
		return jdbcTemplate.query("SELECT * FROM STATE", new BeanPropertyRowMapper<State>(State.class));
	}

;	@Override
	public List<State> getByName(String name, String state) {
		try {
			return jdbcTemplate.query("SELECT * FROM STATE WHERE name = ? or abbreviation = ?", new BeanPropertyRowMapper<State>(State.class), name, state);
		}
		catch (EmptyResultDataAccessException e) {
		      return null;
		}
 	}
}
