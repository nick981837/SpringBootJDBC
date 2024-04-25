package com.example.SpringBootJDBC.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SpringBootJDBC.model.Forest;

@Repository
public class ForestDAOImp implements ForestDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Forest forest) {
		return jdbcTemplate.update("INSERT INTO FOREST (name, area, "
				+ "acid_level, mbr_xmin, mbr_xmax, mbr_ymin, mbr_ymax) "
				+ "VALUES(?,?,?,?,?,?,?)", new Object[] {forest.getName(), forest.getArea(), forest.getAcid_level(), forest.getMbr_xmin(), forest.getMbr_xmax(), forest.getMbr_ymin(), forest.getMbr_ymax()});
	}
	
	@Override
	public int saveCoverage(int forest_no, String state, double percentage, int area) {
		return jdbcTemplate.update("INSERT INTO COVERAGE (forest_no, state, percentage, "
				+ "area) "
				+ "VALUES(?,?,?,?)", forest_no, state, percentage, area);
	}

	@Override
	public int update(Forest forest) {
		return jdbcTemplate.update("UPDATE forest SET area = ? WHERE name = ?", new Object[]{forest.getArea(), forest.getName()});
	}
	
	
	@Override
	public int updateCoverage(int forest_no, String state, double percentage, int area) {
		return jdbcTemplate.update("UPDATE COVERAGE SET area = ?, percentage = ? WHERE forest_no = ? and state = ?", new Object[]{area, percentage, forest_no, state});
	}
	

	@Override
	public int delete(String name) {
		return jdbcTemplate.update("DELETE FROM forest WHERE name = ?", name);
	}

	@Override
	public List<Forest> getAll() {
		return jdbcTemplate.query("SELECT * FROM FOREST", new BeanPropertyRowMapper<Forest>(Forest.class));
	}

;	@Override
	public Forest getByName(String name) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM FOREST WHERE name = ?", new BeanPropertyRowMapper<Forest>(Forest.class), name);
		}
		catch (EmptyResultDataAccessException e) {
		      return null;
		}
 	}

	@Override
	public int getForestNoByName(String name) {
		try {
			return jdbcTemplate.queryForObject("SELECT forest_no FROM FOREST WHERE name = ?", Integer.class, name);
		}
		catch (EmptyResultDataAccessException e) {
		      return -1;
		}
	}
	
	
	@Override
	public int getStateArea(String state) {
		try {
			return jdbcTemplate.queryForObject("SELECT area FROM STATE WHERE abbreviation  = ?", Integer.class, state);
		}
		catch (EmptyResultDataAccessException e) {
		      return -1;
		}
		}
}
