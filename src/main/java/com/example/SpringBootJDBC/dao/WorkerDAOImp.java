package com.example.SpringBootJDBC.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Worker;

@Service
public class WorkerDAOImp implements WorkerDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Worker worker) {
		return jdbcTemplate.update("INSERT INTO WORKER (ssn, name, "
				+ "rank, employing_state) "
				+ "VALUES(?,?,?,?)", new Object[] {worker.getSsn(), worker.getName(), worker.getRank(), worker.getEmploying_state()});
	}
	
	@Override
	public List<Worker> getByNameOrSSN(String name, String ssn) {
		try {
			return jdbcTemplate.query("SELECT * FROM WORKER WHERE name = ? or ssn = ?", new BeanPropertyRowMapper<Worker>(Worker.class), name, ssn);
		}
		catch (EmptyResultDataAccessException e) {
		      return null;
		}
 	}
	
	
	@Override
	public List<Worker> getTopK(int k) {
		return jdbcTemplate.query("SELECT w.name, w.ssn, w.rank, w.employing_state \n"
				+ "FROM WORKER as w JOIN SENSOR as s ON w.ssn = s.maintainer WHERE s.energy <= 2\n"
				+ "GROUP BY w.ssn ORDER BY COUNT(sensor_id) DESC LIMIT " + k + ";", new BeanPropertyRowMapper<Worker>(Worker.class));
	}
	
	@Override
	public List<Worker> getAll() {
		return jdbcTemplate.query("SELECT * FROM WORKER", new BeanPropertyRowMapper<Worker>(Worker.class));
	}
}
