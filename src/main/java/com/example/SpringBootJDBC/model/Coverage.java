package com.example.SpringBootJDBC.model;

public class Coverage {
	int forest_no;
	String state;
	double percentage;
	int area;
	
	
	
	public int getForest_no() {
		return forest_no;
	}
	public void setForest_no(int forest_no) {
		this.forest_no = forest_no;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
}
