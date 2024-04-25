package com.example.SpringBootJDBC.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forest {
	
	private int forest_no;
	private String name;
	private int area;
	private float acid_level;
	private int mbr_xmin;
	private int mbr_xmax;
	private int mbr_ymin;
	private int mbr_ymax;
	private String state;
	
	public int getForest_no() {
		return forest_no;
	}
	public void setForest_no(int forest_no) {
		this.forest_no = forest_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public float getAcid_level() {
		return acid_level;
	}
	public void setAcid_level(float acid_level) {
		this.acid_level = acid_level;
	}
	public int getMbr_xmin() {
		return mbr_xmin;
	}
	public void setMbr_xmin(int mbr_xmin) {
		this.mbr_xmin = mbr_xmin;
	}
	public int getMbr_xmax() {
		return mbr_xmax;
	}
	public void setMbr_xmax(int mbr_xmax) {
		this.mbr_xmax = mbr_xmax;
	}
	public int getMbr_ymin() {
		return mbr_ymin;
	}
	public void setMbr_ymin(int mbr_ymin) {
		this.mbr_ymin = mbr_ymin;
	}
	public int getMbr_ymax() {
		return mbr_ymax;
	}
	public void setMbr_ymax(int mbr_ymax) {
		this.mbr_ymax = mbr_ymax;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
