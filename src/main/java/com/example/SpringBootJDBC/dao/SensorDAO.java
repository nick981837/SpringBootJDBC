package com.example.SpringBootJDBC.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Sensor;

public interface SensorDAO {
	int save(Sensor sensor);
	
	List<Sensor> getByIdOrCoordinates(int sensor_id, int x, int y);
	
	List<Map<String, Object>>getStateIDByWorker(String name);
	
	void updateDuties(int sensor_id, String ssn);
	
	List<Integer> getSensorIDs(String name);
	
	List<Sensor> getSensorRanking();
	
	void updateSensorStatus(int x, int y, int energy, LocalDateTime last_charged, LocalDateTime curDate);
	
	void saveReport(int sensor_id, LocalDateTime curDate, int temperature);
	
	List<Sensor> getAll();
}
