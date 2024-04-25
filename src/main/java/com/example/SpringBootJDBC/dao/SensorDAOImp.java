package com.example.SpringBootJDBC.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Sensor;
import com.example.SpringBootJDBC.model.Worker;

@Service
public class SensorDAOImp implements SensorDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Sensor sensor) {
		return jdbcTemplate.update("INSERT INTO SENSOR (sensor_id, x, "
				+ "y, last_charged, maintainer, last_read, energy) "
				+ "VALUES(?,?,?,?,?,?,?)", new Object[] {sensor.getSensor_id(), sensor.getX(), sensor.getY(), sensor.getLast_read(), sensor.getMaintainer(), sensor.getLast_read(), sensor.getEnergy()});
	}
	
	@Override
	public void saveReport(int sensor_id, LocalDateTime curDate, int temperature) {
		jdbcTemplate.update("INSERT INTO REPORT (sensor_id, report_time, "
				+ "temperature) VALUES(?,?,?)", sensor_id, curDate, temperature);
	}
	
	@Override
	public List<Sensor> getByIdOrCoordinates(int sensor_id, int x, int y) {
		try {
			return jdbcTemplate.query("SELECT * FROM SENSOR WHERE sensor_id = ? or (x = ? and y = ?)", new BeanPropertyRowMapper<Sensor>(Sensor.class), sensor_id, x, y);
		}
		catch (EmptyResultDataAccessException e) {
		      return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getStateIDByWorker(String name) {
		try {
			return jdbcTemplate.queryForList("SELECT employing_state, ssn FROM WORKER WHERE name = ?", name);
		}
		catch (EmptyResultDataAccessException e) {
		      return null;
		}
	}
	
	@Override
	public List<Integer> getSensorIDs(String name) {
		return jdbcTemplate.queryForList("SELECT sensor_id FROM SENSOR as s JOIN WORKER as w ON s.maintainer = w.ssn WHERE w.name = ? ", Integer.class, name);
	}
	
	@Override
	public List<Sensor> getSensorRanking() {
		return jdbcTemplate.query("select s.sensor_id, s.x, s.y, s.maintainer, s.last_read, s.last_charged, s.energy FROM SENSOR as s  JOIN REPORT as r ON s.sensor_id = r.sensor_id GROUP BY s.sensor_id\n"
				+ "having count(*) = (select max(c) from (SELECT s.sensor_id, COUNT(report_time) as c FROM SENSOR as s \n"
				+ "JOIN REPORT as r ON s.sensor_id = r.sensor_id GROUP BY s.sensor_id) t);", new BeanPropertyRowMapper<Sensor>(Sensor.class));
	}
	
	@Override
	public void updateDuties(int sensor_id, String ssn) {
	jdbcTemplate.update("UPDATE SENSOR SET maintainer = ? WHERE sensor_id= ?", ssn, sensor_id);
	}
	
	
	@Override
	public void updateSensorStatus(int x, int y, int energy, LocalDateTime last_charged, LocalDateTime curDate) {
	jdbcTemplate.update("UPDATE SENSOR SET energy = ?, last_charged = ?, last_read = ? WHERE x= ? and y = ?", energy, last_charged, curDate, x, y);
	}
	
	@Override
	public List<Sensor> getAll() {
		return jdbcTemplate.query("SELECT * FROM SENSOR", new BeanPropertyRowMapper<Sensor>(Sensor.class));
	}
		
}
