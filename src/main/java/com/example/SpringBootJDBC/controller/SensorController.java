package com.example.SpringBootJDBC.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.SensorDAO;
import com.example.SpringBootJDBC.dao.WorkerDAO;
import com.example.SpringBootJDBC.exception.APIException;
import com.example.SpringBootJDBC.exception.ResourceAlreadyExistsException;
import com.example.SpringBootJDBC.exception.ResourceNotFoundException;
import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.Sensor;
import com.example.SpringBootJDBC.model.Worker;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class SensorController {
	@Autowired
	private SensorDAO sDAO;
	@Autowired
	private WorkerDAO wDAO;
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PostMapping("/sensors")
	public String save(@RequestBody Sensor sensor) {
		List<Sensor> res = sDAO.getByIdOrCoordinates(sensor.getSensor_id(), sensor.getX(), sensor.getY());
		List<Worker> workerList = wDAO.getByNameOrSSN(null, sensor.getMaintainer());
		if (res.size() > 0) {
			throw new ResourceAlreadyExistsException("Sensor with ID " + sensor.getSensor_id() + " or coordinates " + sensor.getX() + ", " + sensor.getY() + " already exists");
		}
		else if (workerList.size() == 0) {
			throw new ResourceNotFoundException("Maintainer with SSN " + sensor.getMaintainer() + " does not exist!");
		}
		else {
			sDAO.save(sensor); 
			return "Sensor ID " + sensor.getSensor_id() + " with coordinates " + sensor.getX() + ", " + sensor.getY() +  " saved to the database";
		}
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PutMapping("/sensors")
	public String updateSensorMaintainer(@RequestBody ObjectNode json) {
		List<Worker> workerAList = wDAO.getByNameOrSSN(json.get("maintainerOne").asText(), null);
		List<Worker> workerBList = wDAO.getByNameOrSSN(json.get("maintainerTwo").asText(), null);
		if (workerAList.size() == 0 && workerBList.size() != 0) {
			throw new ResourceNotFoundException("Worker with name " + json.get("maintainerOne").asText() + " does not exist!");
		}
		else if (workerBList.size() == 0 && workerAList.size() != 0) {
			throw new ResourceNotFoundException("Worker with name " + json.get("maintainerTwo").asText() + " does not exist!");
		}
		else if (workerAList.size() == 0 && workerBList.size() == 0) {
			throw new ResourceNotFoundException("Both workers do not exist!");
		}
		else {
			List<Map<String, Object>> One = sDAO.getStateIDByWorker(json.get("maintainerOne").asText());
			List<Map<String, Object>> Two = sDAO.getStateIDByWorker(json.get("maintainerTwo").asText());
		    String stateOne = One.get(0).get("employing_state").toString();
		    String stateTwo = Two.get(0).get("employing_state").toString();
		    String ssnOne = One.get(0).get("ssn").toString();
		    String ssnTwo = Two.get(0).get("ssn").toString();
		    if (stateOne.equals(stateTwo)) {
		    	List<Integer> sensorIdOne =  sDAO.getSensorIDs(json.get("maintainerOne").asText());
			    List<Integer> sensorIdTwo =  sDAO.getSensorIDs(json.get("maintainerTwo").asText());
		    	for (int i = 0; i < sensorIdOne.size(); i ++) {
		    		sDAO.updateDuties(sensorIdOne.get(i), ssnTwo);
		    	}
		    	
		    	for (int i = 0; i < sensorIdTwo.size(); i ++) {
		    		sDAO.updateDuties(sensorIdTwo.get(i), ssnOne);
		    	}
				return "Sensors updated to the database";
		    }
		    else {
		    	throw new APIException("Two workers are not in same state!");
		    }
		}

	}
	
	@Transactional
	@CrossOrigin
	@GetMapping("/sensors/rankings")
	public List<Sensor> getSensorRanking() {
		return sDAO.getSensorRanking();
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@GetMapping("/sensors")
	public List<Sensor> getSensors() {
		return sDAO.getAll();
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PutMapping("/sensors/updateStatus")
	public String updateSensorStatus(@RequestBody ObjectNode json) {
		List<Sensor> res = sDAO.getByIdOrCoordinates(0, json.get("x").asInt(), json.get("y").asInt());
	    if (res.size() > 0) {
	    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
	    	LocalDateTime curDate = LocalDateTime.now();
	    	LocalDateTime last_charged = LocalDateTime.parse(json.get("last_charged").asText(), inputFormatter);
	    	sDAO.updateSensorStatus(json.get("x").asInt(), json.get("y").asInt(), json.get("energy").asInt(),last_charged, curDate);
	    	if (json.get("temperature").asInt() > 100) {
	    		sDAO.saveReport(res.get(0).getSensor_id(), curDate, json.get("temperature").asInt());
	    		return "Sensors updated to the database with emergency was reported";
	    	}
			return "Sensors updated to the database with no emergency was reported";
	    }
	    else {
	    	throw new ResourceNotFoundException("The sensor with coordinates " + json.get("x").asInt() + ", " + json.get("y").asInt() + " does not exist!"
	    			);
	    }
	}
	
}
