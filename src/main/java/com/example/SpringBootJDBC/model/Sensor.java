package com.example.SpringBootJDBC.model;

import java.util.Date;

public class Sensor {
	private int sensor_id;
	private int x;
	private int y;
	private Date last_charged;
	private String maintainer;
	private Date last_read;
	private int energy;
	private String maintainerOne;
	private String maintainerTwo;
	
	public String getMaintainerOne() {
		return maintainerOne;
	}
	public void setMaintainerOne(String maintainerOne) {
		this.maintainerOne = maintainerOne;
	}
	public String getMaintainerTwo() {
		return maintainerTwo;
	}
	public void setMaintainerTwo(String maintainerTwo) {
		this.maintainerTwo = maintainerTwo;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Date getLast_charged() {
		return last_charged;
	}
	public void setLast_charged(Date last_charged) {
		this.last_charged = last_charged;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public Date getLast_read() {
		return last_read;
	}
	public void setLast_read(Date last_read) {
		this.last_read = last_read;
	}
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	
}
