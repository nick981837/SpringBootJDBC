package com.example.SpringBootJDBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootJDBC.dao.ForestDAO;
import com.example.SpringBootJDBC.dao.StateDAO;
import com.example.SpringBootJDBC.exception.ResourceAlreadyExistsException;
import com.example.SpringBootJDBC.exception.ResourceNotFoundException;
import com.example.SpringBootJDBC.model.Forest;
import com.example.SpringBootJDBC.model.State;

@RestController
public class StateController {
	@Autowired
	private StateDAO sDAO;
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@GetMapping("/states")
	public List<State> getState() {
		return sDAO.getAll();
	}
	
	@Transactional
	@CrossOrigin(origins = "http://brandeis-cosi-127b.s3-website-us-east-1.amazonaws.com/")
	@PostMapping("/states")
	public String saveState(@RequestBody State state) {
		List<State> res = sDAO.getByName(state.getName(), state.getAbbreviation());
		if (res.size() > 0) {
			throw new ResourceAlreadyExistsException("State with name " + state.getName() + " or abbreviation " + state.getAbbreviation() + " already exists");
		}
		else {
			sDAO.save(state);
			return "State " + state.getName() + " Succefully saved to the database ";
		}
	}
}
